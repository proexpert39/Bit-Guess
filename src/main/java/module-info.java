module com.example.bitguess {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.desktop;


    opens com.example.bitguess to javafx.fxml;
    exports com.example.bitguess;
}