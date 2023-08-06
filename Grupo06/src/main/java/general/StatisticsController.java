/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package general;

import TDAS.Tree;
import TDAS.Trie;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Label;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kev-roldan
 */
public class StatisticsController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

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

    private final Trie currentTrie = DiccionaryController.diccionary;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        barChart.getData().add(generateDataSeries(currentTrie.getAllLeavesLevels()));
        lbTotalWords.setText(Integer.toString(currentTrie.countLeaves()));
        chLetters1.getItems().addAll(getInitialLetters(currentTrie));
        chLetters2.getItems().addAll(getInitialLetters(currentTrie));
        chLetters1.setOnAction(e -> updateTotalWordPerLetter(chLetters1.getValue()));
        chLetters2.setOnAction(e -> updateLetterFrequency(chLetters2.getValue()));
        lbLongerWord.setText(currentTrie.getLeavesAtLevel(currentTrie.countLevel()).toString());

        lbShorterword.setText(currentTrie.getLeavesAtLevel(findMinLevel(currentTrie)).toString());
    }

    private int findMinLevel(Trie trie) {
        if (trie.isEmpty()) {
            return Integer.MAX_VALUE;
        }

        if (trie.getChildren().isEmpty()) {
            return 0;
        }

        int minChildLevel = Integer.MAX_VALUE;
        for (Trie child : trie.getChildren().values()) {
            int childLevel = findMinLevel(child) + 1;
            minChildLevel = Math.min(minChildLevel, childLevel);
        }

        return minChildLevel;
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) barChart.getScene().getWindow();
        stage.close();
    }

    private XYChart.Series<String, Integer> generateDataSeries(List<Integer> list) {
        Map<Integer, Integer> histogram = new HashMap<>();

        for (Integer leaveLevels : list) {
            histogram.put(leaveLevels, histogram.getOrDefault(leaveLevels, 0) + 1);
        }

        XYChart.Series<String, Integer> dataSeries = new XYChart.Series<>();
        for (Map.Entry<Integer, Integer> entry : histogram.entrySet()) {
            dataSeries.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }
        return dataSeries;
    }

    private void updateTotalWordPerLetter(String value) {
        Map<String, Trie> children = currentTrie.getChildren();
        lbTotalWordPerLetter.setText(Integer.toString(children.get(value).countLeaves()));

    }

    private void updateLetterFrequency(String value) {
        lbLetterFrequency.setText(Integer.toString(currentTrie.countLetter(value)));
    }

    private List<String> getInitialLetters(Trie trie) {
        if (trie.isEmpty() && trie.isLeaf()) {
            return null;
        }
        List<String> letters = new LinkedList<>();
        letters.addAll(trie.getChildren().keySet());
        return letters;
    }

}
