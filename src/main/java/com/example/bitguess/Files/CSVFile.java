package com.example.bitguess.Files;

import com.example.bitguess.Models.Tweet;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVFile {
    public List<List<String>> readCsvFile(String filePath, String columnSeparator) {
        try {
            return Files.readAllLines(Paths.get(filePath))
                    .stream()
                    .map(line -> Arrays.asList(line.split(columnSeparator)))
                    .skip(1)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("CSV dosyası okunurken bir hata oluştu...");
            return null;
        }
    }

    public void writeCsvFile(String pathName, String[] titleLine, DateTimeFormatter dateTimeFormatter, ObservableList<Tweet> notTagList,
                             ObservableList<Tweet> positiveTagList, ObservableList<Tweet> negativeTagList,
                             ObservableList<Tweet> neutralTagList, ObservableList<Tweet> irrelevantTagList) {
        File file = new File(pathName);
        try {
            FileWriter output = new FileWriter(file);
            CSVWriter write = new CSVWriter(output);
            write.writeNext(titleLine);
            writeDataLineToFile(write,notTagList,"",dateTimeFormatter);
            writeDataLineToFile(write,positiveTagList,"1",dateTimeFormatter);
            writeDataLineToFile(write,negativeTagList,"-1",dateTimeFormatter);
            writeDataLineToFile(write,neutralTagList,"0",dateTimeFormatter);
            writeDataLineToFile(write,irrelevantTagList,"2",dateTimeFormatter);
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void writeDataLineToFile(CSVWriter csvWriter, ObservableList<Tweet> tweetObservableList, String sentiment,
                                    DateTimeFormatter dateTimeFormatter) {
        for (Tweet tweet : tweetObservableList) {
            csvWriter.writeNext(new String[]{String.valueOf(tweet.getId()), tweet.getUser(), tweet.getFullName(), tweet.getUrl(),
                    tweet.getTimeStamp().format(dateTimeFormatter), String.valueOf(tweet.getReplies()), String.valueOf(tweet.getLikes()),
                    String.valueOf(tweet.getRetweets()), String.valueOf(tweet.getText()), sentiment
            });
        }
    }
}
