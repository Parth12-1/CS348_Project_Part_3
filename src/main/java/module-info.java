module com.example.part_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.part_3 to javafx.fxml;
    exports com.example.part_3;
}