/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

/**
 *
 * @author Usuario
 */
public class Alertas {

    private String css = getClass().getResource("/style/alertas.css").toExternalForm();

    @FXML
    public  void AlertError(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(texto);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

    @FXML
    public void AlertInfo(String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(texto);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

    @FXML
    public void AlertWarning(String texto) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(texto);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

    @FXML
    public boolean AlertConfirmation(String texto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmacion");
        alert.setContentText(texto);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("custom-alert");

        Optional<ButtonType> result = alert.showAndWait();
        System.out.println(result);
        return result.isPresent() && result.get() == ButtonType.OK;

    }

}
