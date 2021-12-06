package com.example.bitguess;

import com.example.bitguess.Models.Tweet;
import com.opencsv.CSVWriter;
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

import java.io.*;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TagController implements Initializable {
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
    private Button saveButton;
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
    private Button extractToFilesButton;
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
    private final static String COMMA_DELIMITER = ",";
    private final static String FILE_PATH = Objects.requireNonNull(
            TagController.class.getResource("turkish_tweets.csv")).getPath();
    ObservableList<Tweet> tweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> positiveTweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> negativeTweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> neutralTweetObservableList = FXCollections.observableArrayList();
    ObservableList<Tweet> irrelevantTweetObservableList = FXCollections.observableArrayList();
    NumberFormat numberFormat = NumberFormat.getInstance(new Locale("tr_TR"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeButtonDisables(true);
        try {
            readFile(FILE_PATH);
        } catch (IOException | CsvException ignore) {
        }
    }

    public void readFile(String filePath) throws IOException, CsvException {
        List<List<String>> result = Files.readAllLines(Paths.get(filePath))
                .stream()
                .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                .skip(1)
                .collect(Collectors.toList());
        for (List<String> line : result) {
            if (line.size() >= 10) {
                switch (line.get(9)) {
                    case "1":
                        positiveTweetObservableList.add(new Tweet(line.get(0).replace("\"", ""),line.get(1).replace("\"", ""),line.get(2).replace("\"", ""),line.get(3).replace("\"", ""), ZonedDateTime.parse(line.get(4).replace("\"", ""), formatter),
                                Integer.parseInt(line.get(5).replace("\"", "")),Integer.parseInt(line.get(6).replace("\"", "")),Integer.parseInt(line.get(7).replace("\"", "")),
                                line.get(8).replace("\"", ""),Integer.parseInt(line.get(9).replace("\"", ""))));
                        break;
                    case"-1":
                        negativeTweetObservableList.add(new Tweet(line.get(0).replace("\"", ""),line.get(1).replace("\"", ""),line.get(2).replace("\"", ""),line.get(3).replace("\"", ""),ZonedDateTime.parse(line.get(4).replace("\"", ""), formatter),
                                Integer.parseInt(line.get(5).replace("\"", "")),Integer.parseInt(line.get(6).replace("\"", "")),Integer.parseInt(line.get(7).replace("\"", "")),
                                line.get(8).replace("\"", ""),Integer.parseInt(line.get(9).replace("\"", ""))));
                        break;
                    case "0":
                        neutralTweetObservableList.add(new Tweet(line.get(0).replace("\"", ""),line.get(1).replace("\"", ""),line.get(2).replace("\"", ""),line.get(3).replace("\"", ""),ZonedDateTime.parse(line.get(4).replace("\"", ""), formatter),
                                Integer.parseInt(line.get(5).replace("\"", "")),Integer.parseInt(line.get(6).replace("\"", "")),Integer.parseInt(line.get(7).replace("\"", "")),
                                line.get(8).replace("\"", ""),Integer.parseInt(line.get(9).replace("\"", ""))));
                        break;
                    case "2":
                        irrelevantTweetObservableList.add(new Tweet(line.get(0).replace("\"", ""),line.get(1).replace("\"", ""),line.get(2).replace("\"", ""),line.get(3).replace("\"", ""),ZonedDateTime.parse(line.get(4).replace("\"", ""), formatter),
                                Integer.parseInt(line.get(5).replace("\"", "")),Integer.parseInt(line.get(6).replace("\"", "")),Integer.parseInt(line.get(7).replace("\"", "")),
                                line.get(8).replace("\"", ""),Integer.parseInt(line.get(9).replace("\"", ""))));
                        break;
                    default:
                        tweetObservableList.add(new Tweet(line.get(0).replace("\"", ""),line.get(1).replace("\"", ""),line.get(2).replace("\"", ""),line.get(3).replace("\"", ""),ZonedDateTime.parse(line.get(4).replace("\"", ""), formatter),
                                Integer.parseInt(line.get(5).replace("\"", "")),Integer.parseInt(line.get(6).replace("\"", "")),Integer.parseInt(line.get(7).replace("\"", "")),
                                line.get(8).replace("\"", ""),Integer.parseInt(line.get(9).replace("\"", ""))));
                        break;
                }
            } else if (line.size() > 2) {
                tweetObservableList.add(new Tweet(line.get(0),line.get(1),line.get(2),line.get(3),ZonedDateTime.parse(line.get(4), formatter),
                        Integer.parseInt(line.get(5)),Integer.parseInt(line.get(6)),Integer.parseInt(line.get(7)),
                        line.get(8).replace("\"", ""),3));
            }
        }

        updateTagCountLabels();

        lvTweetText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeButtonDisables(false);
            }
        });
        lvPositiveTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changePositiveButtonDisables(false);
            }
        });
        lvNegativeTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeNegativeButtonDisables(false);
            }
        });
        lvNeutralTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeNeutralButtonDisables(false);
            }
        });
        lvIrrelevantTweets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tweet>() {
            @Override
            public void changed(ObservableValue<? extends Tweet> observableValue, Tweet tweet, Tweet t1) {
                changeIrrelevantButtonDisables(false);
            }
        });
        lvTweetText.setItems(tweetObservableList);
        lvPositiveTweets.setItems(positiveTweetObservableList);
        lvNegativeTweets.setItems(negativeTweetObservableList);
        lvNeutralTweets.setItems(neutralTweetObservableList);
        lvIrrelevantTweets.setItems(irrelevantTweetObservableList);
    }

    private void updateTagCountLabels() {
        lblNotTagCount.setText(numberFormat.format(tweetObservableList.size()));
        lblPositiveCount.setText(numberFormat.format(positiveTweetObservableList.size()));
        lblNegativeCount.setText(numberFormat.format(negativeTweetObservableList.size()));
        lblNeutralCount.setText(numberFormat.format(neutralTweetObservableList.size()));
        lblIrrelevantCount.setText(numberFormat.format(irrelevantTweetObservableList.size()));
    }

    public void changeButtonDisables(Boolean isDisable) {
        btnPositive.setDisable(isDisable);
        btnNeutral.setDisable(isDisable);
        btnNegative.setDisable(isDisable);
        btnIrrelevant.setDisable(isDisable);
    }

    public void changePositiveButtonDisables(Boolean isDisabled) {
        btnPositiveNegative.setDisable(isDisabled);
        btnPositiveNeutral.setDisable(isDisabled);
        btnPositiveIrrelevant.setDisable(isDisabled);
        btnPositiveEject.setDisable(isDisabled);
    }
    public void changeNegativeButtonDisables(Boolean isDisabled) {
        btnNegativePositive.setDisable(isDisabled);
        btnNegativeNeutral.setDisable(isDisabled);
        btnNegativeIrrelevant.setDisable(isDisabled);
        btnNegativeEject.setDisable(isDisabled);
    }
    public void changeNeutralButtonDisables(Boolean isDisabled) {
        btnNeutralPositive.setDisable(isDisabled);
        btnNeutralNegative.setDisable(isDisabled);
        btnNeutralIrrelevant.setDisable(isDisabled);
        btnNeutralEject.setDisable(isDisabled);
    }
    public void changeIrrelevantButtonDisables(Boolean isDisabled) {
        btnIrrelevantPositive.setDisable(isDisabled);
        btnIrrelevantNegative.setDisable(isDisabled);
        btnIrrelevantNeutral.setDisable(isDisabled);
        btnIrrelevantEject.setDisable(isDisabled);
    }

    public void onActionChangeTagTweet(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();

        switch (btn.getId()) {
            case "btnPositive":
                changeTweetTag(tweetObservableList,positiveTweetObservableList,lvTweetText,lvPositiveTweets, 1);
                break;
            case "btnNegative":
                changeTweetTag(tweetObservableList,negativeTweetObservableList,lvTweetText,lvNegativeTweets, -1);
                break;
            case "btnNeutral":
                changeTweetTag(tweetObservableList,neutralTweetObservableList,lvTweetText,lvNeutralTweets, 0);
                break;
            case "btnIrrelevant":
                changeTweetTag(tweetObservableList,irrelevantTweetObservableList,lvTweetText,lvIrrelevantTweets, 2);
                break;

            case "btnPositiveNegative":
                changeTweetTag(positiveTweetObservableList,negativeTweetObservableList,lvPositiveTweets,lvNegativeTweets, -1);
                break;
            case "btnPositiveNeutral":
                changeTweetTag(positiveTweetObservableList,neutralTweetObservableList,lvPositiveTweets,lvNeutralTweets, 0);
                break;
            case "btnPositiveIrrelevant":
                changeTweetTag(positiveTweetObservableList,irrelevantTweetObservableList,lvPositiveTweets,lvIrrelevantTweets, 2);
                break;
            case "btnPositiveEject":
                changeTweetTag(positiveTweetObservableList,tweetObservableList,lvPositiveTweets,lvTweetText, 3);
                break;
            case "btnNegativePositive":
                changeTweetTag(negativeTweetObservableList,positiveTweetObservableList,lvNegativeTweets,lvPositiveTweets, 1);
                break;
            case "btnNegativeNeutral":
                changeTweetTag(negativeTweetObservableList,neutralTweetObservableList,lvNegativeTweets,lvNeutralTweets, 0);
                break;
            case "btnNegativeIrrelevant":
                changeTweetTag(negativeTweetObservableList,irrelevantTweetObservableList,lvNegativeTweets,lvIrrelevantTweets, 2);
                break;
            case "btnNegativeEject":
                changeTweetTag(negativeTweetObservableList,tweetObservableList,lvNegativeTweets,lvTweetText, 3);
                break;
            case "btnNeutralPositive":
                changeTweetTag(neutralTweetObservableList,positiveTweetObservableList,lvNeutralTweets,lvPositiveTweets, 1);
                break;
            case "btnNeutralNegative":
                changeTweetTag(neutralTweetObservableList,negativeTweetObservableList,lvNeutralTweets,lvNegativeTweets, -1);
                break;
            case "btnNeutralIrrelevant":
                changeTweetTag(neutralTweetObservableList,irrelevantTweetObservableList,lvNeutralTweets,lvIrrelevantTweets, 2);
                break;
            case "btnNeutralEject":
                changeTweetTag(neutralTweetObservableList,tweetObservableList,lvNeutralTweets,lvTweetText, 3);
                break;
            case "btnIrrelevantPositive":
                changeTweetTag(irrelevantTweetObservableList,positiveTweetObservableList,lvIrrelevantTweets,lvPositiveTweets, 1);
                break;
            case "btnIrrelevantNegative":
                changeTweetTag(irrelevantTweetObservableList,negativeTweetObservableList,lvIrrelevantTweets,lvNegativeTweets, -1);
                break;
            case "btnIrrelevantNeutral":
                changeTweetTag(irrelevantTweetObservableList,neutralTweetObservableList,lvIrrelevantTweets,lvNeutralTweets, 0);
                break;
            case "btnIrrelevantEject":
                changeTweetTag(irrelevantTweetObservableList,tweetObservableList,lvIrrelevantTweets,lvTweetText, 3);
                break;
        }

        updateTagCountLabels();

    }

    public void changeTweetTag(ObservableList<Tweet> removedTweetObservableList, ObservableList<Tweet> addedTweetObservableList,
                               ListView<Tweet> removedTweetListView, ListView<Tweet> addedTweetListView, int sentiment) {
        Tweet selectedTweet = removedTweetListView.getSelectionModel().getSelectedItem();
        selectedTweet.setSentiment(sentiment);
        addedTweetObservableList.add(selectedTweet);
        addedTweetListView.setItems(addedTweetObservableList);
        removedTweetObservableList.remove(removedTweetListView.getSelectionModel().getSelectedIndex());
        removedTweetListView.setItems(removedTweetObservableList);
    }

    public void onActionBtnKaydet(ActionEvent actionEvent) {
        File file = new File("./src/main/resources/com/example/bitguess/turkish_tweets_guncel.csv");

        try {
            FileWriter output = new FileWriter(file);
            CSVWriter write = new CSVWriter(output);
            String[] header = { "id", "user", "fullname", "url", "timestamp", "replies", "likes", "retweets", "text", "sentiment" };
            write.writeNext(header);
            for (Tweet tweet : tweetObservableList) {
                String[] data = { String.valueOf(tweet.getId()), tweet.getId(), tweet.getFullName(), tweet.getUrl(),
                        tweet.getTimeStamp().format(formatter), String.valueOf(tweet.getReplies()), String.valueOf(tweet.getLikes()),
                        String.valueOf(tweet.getRetweets()), String.valueOf(tweet.getText()), String.valueOf(tweet.getSentiment())
                };
                write.writeNext(data);
            }
            for (Tweet tweet : positiveTweetObservableList)  {
                String[] data = { String.valueOf(tweet.getId()), tweet.getId(), tweet.getFullName(), tweet.getUrl(),
                        tweet.getTimeStamp().format(formatter), String.valueOf(tweet.getReplies()), String.valueOf(tweet.getLikes()),
                        String.valueOf(tweet.getRetweets()), String.valueOf(tweet.getText()), String.valueOf(tweet.getSentiment())
                };
                write.writeNext(data);
            }
            for (Tweet tweet : negativeTweetObservableList)  {
                String[] data = { String.valueOf(tweet.getId()), tweet.getId(), tweet.getFullName(), tweet.getUrl(),
                        tweet.getTimeStamp().format(formatter), String.valueOf(tweet.getReplies()), String.valueOf(tweet.getLikes()),
                        String.valueOf(tweet.getRetweets()), String.valueOf(tweet.getText()), String.valueOf(tweet.getSentiment())
                };
                write.writeNext(data);
            }
            for (Tweet tweet : neutralTweetObservableList)  {
                String[] data = { String.valueOf(tweet.getId()), tweet.getId(), tweet.getFullName(), tweet.getUrl(),
                        tweet.getTimeStamp().format(formatter), String.valueOf(tweet.getReplies()), String.valueOf(tweet.getLikes()),
                        String.valueOf(tweet.getRetweets()), String.valueOf(tweet.getText()), String.valueOf(tweet.getSentiment())
                };
                write.writeNext(data);
            }
            for (Tweet tweet : irrelevantTweetObservableList)  {
                String[] data = { String.valueOf(tweet.getId()), tweet.getId(), tweet.getFullName(), tweet.getUrl(),
                        tweet.getTimeStamp().format(formatter), String.valueOf(tweet.getReplies()), String.valueOf(tweet.getLikes()),
                        String.valueOf(tweet.getRetweets()), String.valueOf(tweet.getText()), String.valueOf(tweet.getSentiment())
                };
                write.writeNext(data);
            }

            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
