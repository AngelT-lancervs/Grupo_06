module general {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.controlsfx.controls;

    opens general to javafx.fxml;
    exports general;
}
