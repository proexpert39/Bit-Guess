package com.example.bitguess.Files;

import com.example.bitguess.Models.Tweet;
import com.example.bitguess.Sentiments;
import com.opencsv.CSVWriter;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVFile {

    private List<List<String>> getLines(String filePath, String columnSeparator, Charset charset) throws IOException {
        return Files.readAllLines(Paths.get(filePath), charset)
                .stream()
                .map(line -> Arrays.asList(line.split(columnSeparator)))
                .skip(1)
                .collect(Collectors.toList());
    }

    public List<List<String>> readCsvFile(String filePath, String columnSeparator) throws IOException {
        return getLines(filePath, columnSeparator, Charset.forName("ISO8859_9"));
    }

    public void writeCsvFile(String pathName, String[] titleLine, DateTimeFormatter dateTimeFormatter, ObservableList<Tweet> notTagList,
                             ObservableList<Tweet> positiveTagList, ObservableList<Tweet> negativeTagList,
                             ObservableList<Tweet> irrelevantTagList) {
        File file = new File(pathName);
        try {
            FileWriter output = new FileWriter(file, Charset.forName("ISO8859_9"));
            CSVWriter write = new CSVWriter(output);
            write.writeNext(titleLine);
            writeDataLineToFile(write, notTagList, Sentiments.UNTAGGED, dateTimeFormatter);
            writeDataLineToFile(write, positiveTagList, Sentiments.POSITIVE, dateTimeFormatter);
            writeDataLineToFile(write, negativeTagList, Sentiments.NEGATIVE, dateTimeFormatter);
            writeDataLineToFile(write, irrelevantTagList, Sentiments.IRRELEVANT, dateTimeFormatter);
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void writeDataLineToFile(CSVWriter csvWriter, ObservableList<Tweet> tweetObservableList, String sentiment,
                                    DateTimeFormatter dateTimeFormatter) {
        for (Tweet tweet : tweetObservableList) {
            csvWriter.writeNext(new String[]{tweet.getDate().format(dateTimeFormatter), tweet.getText(), sentiment
            });
        }
    }
}
