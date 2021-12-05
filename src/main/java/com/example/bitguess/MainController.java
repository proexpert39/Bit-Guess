package com.example.bitguess;

import com.example.bitguess.Models.Tweet;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import com.opencsv.CSVReader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MainController implements Initializable {
    @FXML
    private VBox vbTweets;
    @FXML
    private ScrollPane spTweets;
    @FXML
    private ListView<String> lvTweetText;
    List<List<String>> tweets = new ArrayList<>();
    List<Tweet> tweetList = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readFile();
        } catch (IOException | CsvException ignore) {
        }
        loadFile();
    }

    public void readFile() throws IOException, CsvException {
        try (BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(MainController.class.getResource("turkish_tweets.csv")).getPath()))) {
            String line;
            int counter = 0;
            int counter2 = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(line.split(",").length);
                String[] values = line.split(",");
                if (values.length >= 10 && !values[0].equals("id")) {
                    tweetList.add(new Tweet(
                            values[0], values[1], values[2], values[3], LocalDateTime.parse(values[4], formatter),
                            Integer.parseInt(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7]),
                            values[8].replace("\"\"\"", "").replace("\"", ""),
                            Integer.parseInt(values[9])));
                            lvTweetText.getItems().add(values[8]);

                    counter++;
                } else if (values.length >= 9 && !values[0].equals("id")) {
                    tweetList.add(new Tweet(
                            values[0], values[1], values[2], values[3], LocalDateTime.parse(values[4], formatter),
                            Integer.parseInt(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7]),
                            values[8].replace("\"\"\"", "").replace("\"", ""),
                            3));
                            lvTweetText.getItems().add(values[8]);

                    counter++;
                }
                counter2++;
            }
            System.out.println(counter);
            System.out.println(counter2);
        }
    }

    private void loadFile() {
    }

    public void saveCSVFile() {

    }
}
