package com.example.bitguess;

import com.example.bitguess.Files.CSVFile;
import com.example.bitguess.Models.Tweet;
import com.opencsv.exceptions.CsvException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TagController implements Initializable {
    private static final String COMMA_DELIMITER = ",";
    private static final String L_FILE_PATH = Objects.requireNonNull(TagController.class.getResource("turkish_tweets.csv")).getPath();
    private static final String W_FILE_PATH = L_FILE_PATH.substring(1);
    private static final String OS = System.getProperty("os.name");
    private static final String FILE_PATH = OS.startsWith("Win") ? W_FILE_PATH : L_FILE_PATH;
    private static final String SAVED_FILE_PATH = "./src/main/resources/com/example/bitguess/turkish_tweets.csv";
    private static final DateTimeFormatter FORMATTER_WITH_SECOND = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
    private static final DateTimeFormatter FORMATTER_WITHOUT_SECOND = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mmX");
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(new Locale("tr_TR"));
    private static final char DATE_TIME_HOUR_SEPARATOR = ':';

    ObservableList<Tweet> tweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> positiveTweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> negativeTweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> neutralTweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> irrelevantTweetObservableList = FXCollections.observableArrayList();

    private static DateTimeFormatter formatter = FORMATTER_WITH_SECOND;

    @FXML
    private Label lblNotTagCount;
    @FXML
    private Label lblPositiveCount;
    @FXML
    private Label lblNegativeCount;
    @FXML
    private Label lblNeutralCount;
    @FXML
    private Label lblIrrelevantCount;

    @FXML
    private Button btnSaveButton;
    @FXML
    private Button btnPositiveNegative;
    @FXML
    private Button btnPositiveNeutral;
    @FXML
    private Button btnPositiveIrrelevant;
    @FXML
    private Button btnPositiveEject;
    @FXML
    private Button btnNegativePositive;
    @FXML
    private Button btnNegativeNeutral;
    @FXML
    private Button btnNegativeIrrelevant;
    @FXML
    private Button btnNegativeEject;
    @FXML
    private Button btnNeutralPositive;
    @FXML
    private Button btnNeutralNegative;
    @FXML
    private Button btnNeutralIrrelevant;
    @FXML
    private Button btnNeutralEject;
    @FXML
    private Button btnIrrelevantPositive;
    @FXML
    private Button btnIrrelevantNegative;
    @FXML
    private Button btnIrrelevantNeutral;
    @FXML
    private Button btnIrrelevantEject;
    @FXML
    private Button btnPositive;
    @FXML
    private Button btnNeutral;
    @FXML
    private Button btnNegative;
    @FXML
    private Button btnIrrelevant;
    @FXML
    private Button btnExtractToFiles;

    @FXML
    private ListView<Tweet> lvPositiveTweets;
    @FXML
    private ListView<Tweet> lvNegativeTweets;
    @FXML
    private ListView<Tweet> lvNeutralTweets;
    @FXML
    private ListView<Tweet> lvIrrelevantTweets;
    @FXML
    private ListView<Tweet> lvTweetText;

    private final CSVFile csvFile = new CSVFile();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readFile(FILE_PATH);
        } catch (IOException | CsvException ignore) {
        }
    }

    public void readFile(String filePath) throws IOException, CsvException {

        List<List<String>> result = csvFile.readCsvFile(filePath, COMMA_DELIMITER);

        if (characterCountAtString(result.get(1).get(4), DATE_TIME_HOUR_SEPARATOR) != 2) {
            formatter = FORMATTER_WITHOUT_SECOND;
        }

        for (List<String> line : result) {
            if (line.size() >= 10) {
                switch (line.get(9).replace("\"", "")) {
                    case "1":
                        addTweetList(positiveTweetObservableList, line);
                        break;
                    case "-1":
                        addTweetList(negativeTweetObservableList, line);
                        break;
                    case "0":
                        addTweetList(neutralTweetObservableList, line);
                        break;
                    case "2":
                        addTweetList(irrelevantTweetObservableList, line);
                        break;
                    default:
                        addTweetList(tweetObservableList, line);
                        break;
                }
            } else if (line.size() > 2) {
                addTweetList(tweetObservableList, line);
            }
        }

        updateTagCountLabels();

        listViewAddListener(lvTweetText, btnPositive, btnNeutral, btnNegative, btnIrrelevant, lvPositiveTweets, btnPositiveNegative, btnPositiveNeutral, btnPositiveIrrelevant, btnPositiveEject);
        listViewAddListener(lvNegativeTweets, btnNegativePositive, btnNegativeNeutral, btnNegativeIrrelevant, btnNegativeEject, lvNeutralTweets, btnNeutralPositive, btnNeutralNegative, btnNeutralIrrelevant, btnNeutralEject);
        lvIrrelevantTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeButtonDisables(new Button[]{btnIrrelevantPositive,btnIrrelevantNegative,btnIrrelevantNeutral,btnIrrelevantEject}, false);
            }
        });
        lvTweetText.setItems(tweetObservableList);
        lvPositiveTweets.setItems(positiveTweetObservableList);
        lvNegativeTweets.setItems(negativeTweetObservableList);
        lvNeutralTweets.setItems(neutralTweetObservableList);
        lvIrrelevantTweets.setItems(irrelevantTweetObservableList);
    }

    private void listViewAddListener(ListView<Tweet> lvNegativeTweets, Button btnNegativePositive, Button btnNegativeNeutral, Button btnNegativeIrrelevant, Button btnNegativeEject, ListView<Tweet> lvNeutralTweets, Button btnNeutralPositive, Button btnNeutralNegative, Button btnNeutralIrrelevant, Button btnNeutralEject) {
        lvNegativeTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeButtonDisables(new Button[]{btnNegativePositive, btnNegativeNeutral, btnNegativeIrrelevant, btnNegativeEject}, false);
            }
        });
        lvNeutralTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeButtonDisables(new Button[]{btnNeutralPositive, btnNeutralNegative, btnNeutralIrrelevant, btnNeutralEject}, false);
            }
        });
    }

    private void updateTagCountLabels() {
        lblNotTagCount.setText(NUMBER_FORMAT.format(tweetObservableList.size()));
        lblPositiveCount.setText(NUMBER_FORMAT.format(positiveTweetObservableList.size()));
        lblNegativeCount.setText(NUMBER_FORMAT.format(negativeTweetObservableList.size()));
        lblNeutralCount.setText(NUMBER_FORMAT.format(neutralTweetObservableList.size()));
        lblIrrelevantCount.setText(NUMBER_FORMAT.format(irrelevantTweetObservableList.size()));
    }

    public void changeButtonDisables(Button[] buttons, Boolean isDisable) {
        for (Button button: buttons) {
            button.setDisable(isDisable);
        }
    }

    public void onActionChangeTagTweet(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();

        switch (btn.getId()) {
            case "btnPositive":
                changeTweetTag(tweetObservableList, positiveTweetObservableList, lvTweetText, lvPositiveTweets, "1");
                break;
            case "btnNegative":
                changeTweetTag(tweetObservableList, negativeTweetObservableList, lvTweetText, lvNegativeTweets, "-1");
                break;
            case "btnNeutral":
                changeTweetTag(tweetObservableList, neutralTweetObservableList, lvTweetText, lvNeutralTweets, "0");
                break;
            case "btnIrrelevant":
                changeTweetTag(tweetObservableList, irrelevantTweetObservableList, lvTweetText, lvIrrelevantTweets, "2");
                break;
            case "btnPositiveNegative":
                changeTweetTag(positiveTweetObservableList, negativeTweetObservableList, lvPositiveTweets, lvNegativeTweets, "-1");
                break;
            case "btnPositiveNeutral":
                changeTweetTag(positiveTweetObservableList, neutralTweetObservableList, lvPositiveTweets, lvNeutralTweets, "0");
                break;
            case "btnPositiveIrrelevant":
                changeTweetTag(positiveTweetObservableList, irrelevantTweetObservableList, lvPositiveTweets, lvIrrelevantTweets, "2");
                break;
            case "btnPositiveEject":
                changeTweetTag(positiveTweetObservableList, tweetObservableList, lvPositiveTweets, lvTweetText, "");
                break;
            case "btnNegativePositive":
                changeTweetTag(negativeTweetObservableList, positiveTweetObservableList, lvNegativeTweets, lvPositiveTweets, "1");
                break;
            case "btnNegativeNeutral":
                changeTweetTag(negativeTweetObservableList, neutralTweetObservableList, lvNegativeTweets, lvNeutralTweets, "0");
                break;
            case "btnNegativeIrrelevant":
                changeTweetTag(negativeTweetObservableList, irrelevantTweetObservableList, lvNegativeTweets, lvIrrelevantTweets, "2");
                break;
            case "btnNegativeEject":
                changeTweetTag(negativeTweetObservableList, tweetObservableList, lvNegativeTweets, lvTweetText, "");
                break;
            case "btnNeutralPositive":
                changeTweetTag(neutralTweetObservableList, positiveTweetObservableList, lvNeutralTweets, lvPositiveTweets, "1");
                break;
            case "btnNeutralNegative":
                changeTweetTag(neutralTweetObservableList, negativeTweetObservableList, lvNeutralTweets, lvNegativeTweets, "-1");
                break;
            case "btnNeutralIrrelevant":
                changeTweetTag(neutralTweetObservableList, irrelevantTweetObservableList, lvNeutralTweets, lvIrrelevantTweets, "2");
                break;
            case "btnNeutralEject":
                changeTweetTag(neutralTweetObservableList, tweetObservableList, lvNeutralTweets, lvTweetText, "");
                break;
            case "btnIrrelevantPositive":
                changeTweetTag(irrelevantTweetObservableList, positiveTweetObservableList, lvIrrelevantTweets, lvPositiveTweets, "1");
                break;
            case "btnIrrelevantNegative":
                changeTweetTag(irrelevantTweetObservableList, negativeTweetObservableList, lvIrrelevantTweets, lvNegativeTweets, "-1");
                break;
            case "btnIrrelevantNeutral":
                changeTweetTag(irrelevantTweetObservableList, neutralTweetObservableList, lvIrrelevantTweets, lvNeutralTweets, "0");
                break;
            case "btnIrrelevantEject":
                changeTweetTag(irrelevantTweetObservableList, tweetObservableList, lvIrrelevantTweets, lvTweetText, "");
                break;
        }

        updateTagCountLabels();

    }

    public void changeTweetTag(ObservableList<Tweet> removedTweetObservableList, ObservableList<Tweet> addedTweetObservableList,
                               ListView<Tweet> removedTweetListView, ListView<Tweet> addedTweetListView, String sentiment) {
        Tweet selectedTweet = removedTweetListView.getSelectionModel().getSelectedItem();
        selectedTweet.setSentiment(sentiment);
        addedTweetObservableList.add(selectedTweet);
        addedTweetListView.setItems(addedTweetObservableList);
        removedTweetObservableList.remove(removedTweetListView.getSelectionModel().getSelectedIndex());
        removedTweetListView.setItems(removedTweetObservableList);
    }

    public void onActionBtnKaydet(ActionEvent actionEvent) {
        csvFile.writeCsvFile( SAVED_FILE_PATH,
                new String[]{"id", "user", "fullname", "url", "timestamp", "replies", "likes", "retweets", "text", "sentiment"},
                formatter,tweetObservableList,positiveTweetObservableList,negativeTweetObservableList,neutralTweetObservableList,
                irrelevantTweetObservableList);
    }

    public void addTweetList(List<Tweet> tweetObservableList, List<String> line) {
        String sentiment;

        if (line.size() == 9) {
            sentiment = "";
        } else {
            sentiment = line.get(9).replace("\"", "");
        }

        tweetObservableList.add(new Tweet(line.get(0).replace("\"", ""), line.get(1).replace("\"", ""),
                line.get(2).replace("\"", ""), line.get(3).replace("\"", ""),
                ZonedDateTime.parse(line.get(4).replace("\"", ""), formatter),
                Integer.parseInt(line.get(5).replace("\"", "")),
                Integer.parseInt(line.get(6).replace("\"", "")),
                Integer.parseInt(line.get(7).replace("\"", "")),
                line.get(8).replace("\"", ""), sentiment)
        );
    }

    public int characterCountAtString(String word, char character) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == character) {
                count++;
            }
        }
        return count;
    }

}
