package com.example.bitguess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class BitGuessApplication extends Application {
    private Preferences applicationPreferences;

    @Override
    public void start(Stage stage) throws IOException {
        String dataFilePathPreferenceID = "FILE PATH ID";
        String dataFilePathNotFoundID = "File Path Not Found";

        // Daha Önce Dosya Seçilmiş İse Yolunu Getiren Blok.
        applicationPreferences = Preferences.userRoot().node(this.getClass().getName());
        String filePath = applicationPreferences.get(dataFilePathPreferenceID, dataFilePathNotFoundID);

        if (filePath.equals(dataFilePathNotFoundID)) {
            // Daha Önce Seçilmemiş İse Dosya Yolunu Seçip Kaydetmek.
            chooseDataFile(stage, dataFilePathPreferenceID);
        } else {
            File fileTemp = new File(filePath);

            // Kaydedilmiş dosyanı adı değiştiğinde, dosya silindiğinde veya dosya taşındığında tekrar yolunu kaydeden blok.
            if(fileTemp.exists() && !fileTemp.isDirectory()) {
                Global.FILE_PATH = filePath;// do something
            } else {
                chooseDataFile(stage, dataFilePathPreferenceID);
            }

        }

        FXMLLoader fxmlLoader = new FXMLLoader(BitGuessApplication.class.getResource("tag-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), java.awt.Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        stage.setTitle("Bit Guess");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    private void chooseDataFile(Stage stage, String dataFilePathPreferenceID) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veri Dosyasını Seçiniz");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Global.FILE_PATH = selectedFile.getAbsolutePath();
            applicationPreferences.put(dataFilePathPreferenceID, Global.FILE_PATH);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
