module com.example.bitguess {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.desktop;
    requires java.prefs;


    opens com.example.bitguess to javafx.fxml;
    exports com.example.bitguess;
    exports com.example.bitguess.Controllers;
    opens com.example.bitguess.Controllers to javafx.fxml;
}
