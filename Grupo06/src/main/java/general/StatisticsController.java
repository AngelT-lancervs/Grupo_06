/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package general;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Label;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class
 *
 * @author kev-roldan
 */
public class StatisticsController implements Initializable {

    
    
    @FXML
    private BarChart<Integer, Integer> barChart;

    @FXML
    private ChoiceBox<String> chLetters1;

    @FXML
    private ChoiceBox<String> chLetters2;

    @FXML
    private Label lbLastWordAdded;

    @FXML
    private Label lbLetterFrequency;

    @FXML
    private Label lbLongerWord;

    @FXML
    private Label lbShorterword;

    @FXML
    private Label lbTotalWordPerLetter;

    @FXML
    private Label lbTotalWords;

    @FXML
    private CategoryAxis xAxis;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
