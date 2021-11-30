module com.example.bitguess {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bitguess to javafx.fxml;
    exports com.example.bitguess;
}