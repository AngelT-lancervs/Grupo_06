/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package general;

import TDAS.Trie;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.Collator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private Label backButton;
    @FXML
    private ImageView ahorcadoView;
    @FXML
    private Label score;
    @FXML
    private Label highScoreLabel;
    
    private int numScore = 0;

    private int numTry = 7;

    private final Font font = new Font("Regular", 30);

    private String word;

    private List<String> listWords = DictionaryController.dictionary.getLeaves();

    private List<String> choosenWords = new ArrayList<>();

    private final Alertas alertHandler = new Alertas();
    
    private int highScore = loadHighScore();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getRandomWord();
        this.numTryLabel.setText(Integer.toString(this.numTry));
        this.highScoreLabel.setText(Integer.toString(this.highScore));

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

        if (this.choosenWords.size() == this.listWords.size() - 1) {
            this.choosenWords = new ArrayList<>();
        }

        while (this.choosenWords.contains(this.word)) {
            i = getRandomNumber(listWords.size() - 1);
            this.word = listWords.get(i);
        }

        this.choosenWords.add(word);
    }

    @FXML
    private void complete(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {

            check();
            checkWin();
            checkStatus();
        }
    }

    private void check() {
        String letter = this.textField.getText();

        if (letter == null || letter.equals("") || letter.equals(" ")) {
            alertHandler.AlertInfo("Escriba un caracter");
            return;
        }
        
        if (letter.length() > 1){
            alertHandler.AlertInfo("Escriba solo un caracter");
            this.textField.setText("");
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

    private void checkStatus() throws IOException {
        switch (numTry) {
            case 6:
                this.ahorcadoView.setImage(new Image(getClass().getResourceAsStream("/images/ahorcado/2.png")));
                break;
            case 4:
                this.ahorcadoView.setImage(new Image(getClass().getResourceAsStream("/images/ahorcado/3.png")));
                break;
            case 3:
                this.ahorcadoView.setImage(new Image(getClass().getResourceAsStream("/images/ahorcado/4.png")));
                break;
            case 2:
                this.ahorcadoView.setImage(new Image(getClass().getResourceAsStream("/images/ahorcado/5.png")));
                break;
            case 1:
                this.ahorcadoView.setImage(new Image(getClass().getResourceAsStream("/images/ahorcado/6.png")));
                break;
            case 0:
                if(this.numScore > this.highScore){
                    this.saveHighScore(this.numScore);
                }
                alertHandler.AlertInfo("Game Over");
                App.setRoot("dictionary");
                break;
        }

    }

    private void checkWin() {
        boolean win = true;

        for (Node n : this.HBoxLabels.getChildren()) {
            Label l = (Label) n;

            if (l.getText().equals("   ")) {
                win = false;
            }
        }

        if (win) {
            System.out.println("win");
            this.numScore++;
            this.score.setText(Integer.toString(this.numScore));
            newWord();
        }
    }

    private void newWord() {
        wait(200);

        getRandomWord();
        this.HBoxLabels.getChildren().clear();

        for (int i = 0; i < word.length(); i++) {
            Label l = new Label("   ");
            l.setFont(font);
            l.getStyleClass().add("label-with-bottom-border");
            this.HBoxLabels.getChildren().add(l);
        }

        Label label = (Label) this.HBoxLabels.getChildren().get(0);
        label.setText(Character.toString(word.charAt(0)).toUpperCase());
    }

    private static String cleanString(String texto) {
        String texto1 = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto1 = texto1.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        System.out.println(texto1);
        return texto1;
    }

    private void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("error en el wait");
        }
    }

    @FXML
    private void back() throws IOException {
        App.setRoot("dictionary");
    }
    
    private void saveHighScore(int i){
         try {
            FileOutputStream fout = new FileOutputStream("userData/serialized/highscore.ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(i);
            out.flush();

        } catch (FileNotFoundException e) {
             System.out.println("No hay archivo");
        } catch (IOException e) {
             System.out.println("Problema con el saveHighScore");
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    private int loadHighScore(){
        try {
            FileInputStream file = new FileInputStream("userData/serialized/highscore.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            int highS = (int) in.readObject();

            in.close();
            file.close();
            return highS;
        } catch (Exception e) {
            return 0;
        }
    }

}
