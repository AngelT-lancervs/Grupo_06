module general {
    requires javafx.controls;
    requires javafx.fxml;

    opens general to javafx.fxml;
    exports general;
}
