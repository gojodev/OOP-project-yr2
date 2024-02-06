module org.example.stage1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.example.stage1 to javafx.fxml;
    exports org.example.stage1;
}