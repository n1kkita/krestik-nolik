module com.example.demo14 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo14 to javafx.fxml;
    exports com.example.demo14;
}