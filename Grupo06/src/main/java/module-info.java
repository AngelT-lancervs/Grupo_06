module general {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens general to javafx.fxml;
    exports general;
}
