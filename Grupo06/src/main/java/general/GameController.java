/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package general;

import java.net.URL;
import java.text.Collator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import utils.Alertas;

/**
 * FXML Controller class
 *
 * @author lancervs
 */
public class GameController implements Initializable {

    @FXML
    private AnchorPane paneGame;
    @FXML
    private HBox HBoxLabels;
    @FXML
    private TextField textField;
    @FXML
    private Label numTryLabel;

    private int numTry = 6;

    private final Font font = new Font("Regular", 30);

    private String word;

    private List<String> listWords = DictionaryController.dictionary.getLeaves();

    private final Alertas alertHandler = new Alertas();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getRandomWord();

        this.numTryLabel.setText(Integer.toString(this.numTry));

        for (int i = 0; i < word.length(); i++) {
            Label l = new Label("   ");
            l.setFont(font);
            l.getStyleClass().add("label-with-bottom-border");
            this.HBoxLabels.getChildren().add(l);
        }

        Label label = (Label) this.HBoxLabels.getChildren().get(0);
        label.setText(Character.toString(word.charAt(0)).toUpperCase());
    }

    private int getRandomNumber(int max) {
        return (int) (Math.random() * max + 1);
    }

    private void getRandomWord() {
        int i = getRandomNumber(listWords.size() - 1);
        this.word = listWords.get(i);
    }

    @FXML
    private void complete(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            check();
        }
    }

    private void check() {
        String letter = this.textField.getText();
        
        if (letter == null || letter.equals("") || letter.equals(" ")){
            alertHandler.AlertInfo("Escriba un caracter");
            return;
        }

        this.textField.setText("");

        if (cleanString(this.word).contains(letter.toLowerCase())) {

            for (int i = 0; i < word.length(); i++) {

                if (letter.equalsIgnoreCase(cleanString(Character.toString(this.word.charAt(i))))) {
                    Label labelTmp = (Label) this.HBoxLabels.getChildren().get(i);
                    labelTmp.setText(Character.toString(this.word.charAt(i)).toUpperCase());
                }
            }

        } else {
            this.numTryLabel.setText(Integer.toString(--numTry));
        }

    }

    private void checkStatus() {
        
    }
    
    
    public static String cleanString(String texto) {
        String texto1 = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto1 = texto1.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        System.out.println(texto1);
        return texto1;
    }

}
