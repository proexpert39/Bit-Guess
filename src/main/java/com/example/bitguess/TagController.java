package com.example.bitguess;

import com.example.bitguess.Files.CSVFile;
import com.example.bitguess.Models.Tweet;
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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TagController implements Initializable {
    private static final String COMMA_DELIMITER = ",";
    private static final String SAVED_FILE_PATH = Global.FILE_PATH;
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(new Locale("tr_TR"));
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    ObservableList<Tweet> tweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> positiveTweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> negativeTweetObservableList = FXCollections.observableArrayList();

    ObservableList<Tweet> irrelevantTweetObservableList = FXCollections.observableArrayList();

    @FXML
    private Label lblUntaggedCount;
    @FXML
    private Label lblPositiveCount;
    @FXML
    private Label lblNegativeCount;
    @FXML
    private Label lblSelectedFilePath;

    @FXML
    private Label lblIrrelevantCount;

    @FXML
    private Button btnPositiveNegative;

    @FXML
    private Button btnPositiveIrrelevant;
    @FXML
    private Button btnPositiveEject;
    @FXML
    private Button btnNegativePositive;

    @FXML
    private Button btnNegativeIrrelevant;
    @FXML
    private Button btnNegativeEject;
    @FXML
    private Button btnIrrelevantPositive;
    @FXML
    private Button btnIrrelevantNegative;
    @FXML
    private Button btnIrrelevantEject;

    @FXML
    private ListView<Tweet> lvPositiveTweets;
    @FXML
    private ListView<Tweet> lvNegativeTweets;
    @FXML
    private ListView<Tweet> lvIrrelevantTweets;
    @FXML
    private ListView<Tweet> lvTweetText;

    private final CSVFile csvFile = new CSVFile();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblSelectedFilePath.setText(String.format("Seçilen Dosya: %s", Global.FILE_PATH));

        readFile(Global.FILE_PATH);
    }

    public void readFile(String filePath) {

        List<List<String>> result = null;
        try {
            result = csvFile.readCsvFile(filePath, COMMA_DELIMITER);
        } catch (IOException e) {
            System.out.printf("CSV Dosyası Okunurken Bir Hata Oluştu: " + e.getMessage());
        }

        if (result != null) {
            for (List<String> line : result) {
                if (line.size() > 2) {
                    switch (line.get(2).replace("\"", "")) {
                        case Sentiments.POSITIVE:
                            addTweetList(positiveTweetObservableList, line);
                            break;
                        case Sentiments.NEGATIVE:
                            addTweetList(negativeTweetObservableList, line);
                            break;
                        case Sentiments.IRRELEVANT:
                            addTweetList(irrelevantTweetObservableList, line);
                            break;
                        default:
                            addTweetList(tweetObservableList, line);
                            break;
                    }
                } else {
                    addTweetList(tweetObservableList, line);
                }
            }
        }

        updateTagCountLabels();
        lvPositiveTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeButtonDisables(new Button[]{btnPositiveNegative, btnPositiveIrrelevant, btnPositiveEject}, false);
            }
        });
        lvNegativeTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeButtonDisables(new Button[]{btnNegativePositive, btnNegativeIrrelevant, btnNegativeEject}, false);
            }
        });
        lvIrrelevantTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeButtonDisables(new Button[]{btnIrrelevantPositive, btnIrrelevantNegative, btnIrrelevantEject}, false);
            }
        });

        lvTweetText.setItems(tweetObservableList);
        lvPositiveTweets.setItems(positiveTweetObservableList);
        lvNegativeTweets.setItems(negativeTweetObservableList);
        lvIrrelevantTweets.setItems(irrelevantTweetObservableList);

    }

    private void updateTagCountLabels() {
        lblUntaggedCount.setText(NUMBER_FORMAT.format(tweetObservableList.size()));
        lblPositiveCount.setText(NUMBER_FORMAT.format(positiveTweetObservableList.size()));
        lblNegativeCount.setText(NUMBER_FORMAT.format(negativeTweetObservableList.size()));
        lblIrrelevantCount.setText(NUMBER_FORMAT.format(irrelevantTweetObservableList.size()));
    }

    public void changeButtonDisables(Button[] buttons, Boolean isDisable) {
        for (Button button : buttons) {
            button.setDisable(isDisable);
        }
    }

    public void onActionChangeTagTweet(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();

        switch (btn.getId()) {
            case "btnPositive":
                changeTweetTag(tweetObservableList, positiveTweetObservableList, lvTweetText, lvPositiveTweets, Sentiments.POSITIVE);
                break;
            case "btnNegative":
                changeTweetTag(tweetObservableList, negativeTweetObservableList, lvTweetText, lvNegativeTweets, Sentiments.NEGATIVE);
                break;
            case "btnIrrelevant":
                changeTweetTag(tweetObservableList, irrelevantTweetObservableList, lvTweetText, lvIrrelevantTweets, Sentiments.IRRELEVANT);
                break;
            case "btnPositiveNegative":
                changeTweetTag(positiveTweetObservableList, negativeTweetObservableList, lvPositiveTweets, lvNegativeTweets, Sentiments.NEGATIVE);
                break;
            case "btnPositiveIrrelevant":
                changeTweetTag(positiveTweetObservableList, irrelevantTweetObservableList, lvPositiveTweets, lvIrrelevantTweets, Sentiments.IRRELEVANT);
                break;
            case "btnPositiveEject":
                changeTweetTag(positiveTweetObservableList, tweetObservableList, lvPositiveTweets, lvTweetText, Sentiments.UNTAGGED);
                break;
            case "btnNegativePositive":
                changeTweetTag(negativeTweetObservableList, positiveTweetObservableList, lvNegativeTweets, lvPositiveTweets, Sentiments.POSITIVE);
                break;
            case "btnNegativeIrrelevant":
                changeTweetTag(negativeTweetObservableList, irrelevantTweetObservableList, lvNegativeTweets, lvIrrelevantTweets, Sentiments.IRRELEVANT);
                break;
            case "btnNegativeEject":
                changeTweetTag(negativeTweetObservableList, tweetObservableList, lvNegativeTweets, lvTweetText, Sentiments.UNTAGGED);
                break;
            case "btnIrrelevantPositive":
                changeTweetTag(irrelevantTweetObservableList, positiveTweetObservableList, lvIrrelevantTweets, lvPositiveTweets, Sentiments.POSITIVE);
                break;
            case "btnIrrelevantNegative":
                changeTweetTag(irrelevantTweetObservableList, negativeTweetObservableList, lvIrrelevantTweets, lvNegativeTweets, Sentiments.NEGATIVE);
                break;
            case "btnIrrelevantEject":
                changeTweetTag(irrelevantTweetObservableList, tweetObservableList, lvIrrelevantTweets, lvTweetText, Sentiments.UNTAGGED);
                break;
        }

        updateTagCountLabels();

        csvFile.writeCsvFile(SAVED_FILE_PATH,
                new String[]{"date", "text", "sentiment"},
                formatter, tweetObservableList, positiveTweetObservableList, negativeTweetObservableList,
                irrelevantTweetObservableList);
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



    public void addTweetList(List<Tweet> tweetObservableList, List<String> line) {
        String sentiment = "";
        if (line.size() > 2)
            sentiment = line.get(2);

        try {
            tweetObservableList.add(new Tweet(LocalDateTime.parse(
                    addZeroToDate(formatDateString(line.get(0).replace("\"", ""))), formatter),
                    line.get(1).replace("\"", ""),
                    sentiment
            ));
        } catch (DateTimeParseException dateTimeParseException) {
            System.out.println(line.get(0) + " Tarihi çevrilirken bir hata oluştu: " + dateTimeParseException);
        }
    }

    private String formatDateString(String date) {
        if (date.charAt(1) == '/' && date.charAt(3) == '/') {
            return date.replace('/', '-');
        }
        if (date.charAt(1) == '.' && date.charAt(3) == '.') {
            return date.replace('.', '-');
        }
        return date;
    }

    private String addZeroToDate(String date) {
        String[] dateSplit = date.split(" ");
        String stringDate = dateSplit[0];
        String hour = dateSplit[1];
        String[] stringDateSplit = stringDate.split("-");
        if (stringDateSplit[0].length() == 1)
            stringDateSplit[0] = "0" + stringDateSplit[0];
        if (stringDateSplit[1].length() == 1)
            stringDateSplit[1] = "0" + stringDateSplit[1];
        if (hour.length() == 5) {
            hour = hour + ":00";
        }
        stringDate = stringDateSplit[0] + "-" + stringDateSplit[1] + "-" +stringDateSplit[2];
        return stringDate + " " + hour;
    }

}
