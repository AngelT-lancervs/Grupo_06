package general;

import TDAS.Trie;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import utils.Alertas;
import utils.trieSerialization;

public class DictionaryController implements Initializable {

    @FXML
    private Label lbNote;

    @FXML
    private Label lbPrefix;

    @FXML
    private Label lbResult;

    @FXML
    private Label lbReverse;

    @FXML
    private Label lbSimilars;

    @FXML
    private VBox vbInfo;

    @FXML
    private VBox vbPrefix;

    @FXML
    private VBox vbReverse;

    @FXML
    private VBox vbSimilars;

    @FXML
    private TextField tfSearch;

    public static Trie dictionary;

    private AutoCompletionBinding<String> autoComplete;

    private final Alertas alert = new Alertas();

    private final FileChooser fc = new FileChooser();

    @FXML
    private void closeWindow() {
        trieSerialization.serialize(dictionary, "dictionary");
        System.exit(0);
    }

    @FXML
    private void showStadistics() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeAutoComplete();
    }

    private void initializeAutoComplete() {
        autoComplete = TextFields.bindAutoCompletion(tfSearch, dictionary.getLeaves());
        autoComplete.setVisibleRowCount(5);
        autoComplete.setPrefWidth(tfSearch.getPrefWidth() - 10);
    }

    @FXML
    private void addWord() {
        String currentWord = tfSearch.getText();
        if (currentWord.equals("")) {
            alert.AlertError("Por favor, ingrese una palabra valida");
        } else if (dictionary.search(currentWord)) {
            alert.AlertWarning("La palabra '" + currentWord + "' ya existe en el diccionario");
        } else if (alert.AlertConfirmation("Esta seguro de que desea agregar '" + currentWord + "' al diccionario")) {
            dictionary.add(currentWord);
            if (autoComplete != null) {
                autoComplete.dispose();
            }
            initializeAutoComplete();
            alert.AlertInfo("Palabra agregada Correctamente");
        }
    }

    @FXML
    private void deleteWord() {
        String currentWord = tfSearch.getText();
        if (currentWord.equals("")) {
            alert.AlertError("Por favor, ingrese una palabra a valida");
        } else if (!dictionary.getLeaves().contains(currentWord)) {
            alert.AlertWarning("La palabra '" + currentWord + "' NO existe en el diccionario");

        } else if (alert.AlertConfirmation("Esta seguro de que desea eliminar '" + currentWord + "' al diccionario")) {
            dictionary.remove(currentWord);
            if (autoComplete != null) {
                autoComplete.dispose();
            }
            initializeAutoComplete();
            alert.AlertInfo("Palabra eliminada Correctamente");

        }
    }

    @FXML
    private void searchWord() {
        String wordToSearch = tfSearch.getText().trim();
        if (wordToSearch.equals("")) {
            alert.AlertError("Ingrese una palabra para buscar");
        }
        if (wordToSearch.contains(" ")) {
            alert.AlertError("Ingrese solo UNA palabra");
        } else {
            setResultComponents(dictionary.search(wordToSearch), wordToSearch);
            vbInfo.setVisible(true);
        }
    }

    @FXML
    void uploadWords() {
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Archivo txt", "*.txt");
        fc.getExtensionFilters().addAll(txtFilter);

        File file = fc.showOpenDialog(new Stage());
        if (file != null && file.isFile()) {

            try {
                String txtPath = file.getCanonicalPath();
                readFile(txtPath);
                if (autoComplete != null) {
                    autoComplete.dispose();
                }
                initializeAutoComplete();
                alert.AlertInfo("Archivo cargado existosamente");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.AlertInfo("Ningun archivo seleccionado");
        }

        fc.getExtensionFilters().clear();

    }

    @FXML
    void downloadWords() {
        if (!dictionary.isEmpty()) {

            fc.setInitialFileName("Diccionario(" + LocalDateTime.now().getHour() + "-" + LocalDateTime.now().getMinute() + "-" + LocalDateTime.now().getSecond() + ")");
            FileChooser.ExtensionFilter filesFilter = new FileChooser.ExtensionFilter("Carpetas", "*.");
            fc.getExtensionFilters().add(filesFilter);
            File file = fc.showSaveDialog(new Stage());
            if (file != null) {
                try {
                    String filePath = file.getCanonicalPath();
                    for (String word : dictionary.getLeaves()) {
                        writteFile(filePath + ".txt", word);
                    }
                    alert.AlertInfo("Archivo guardado exitosamente");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fc.getExtensionFilters().clear();
        }
    }

    @FXML
    void playGame() throws IOException {

        App.setRoot("Game");

    }

    private void readFile(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                if (!dictionary.search(linea)) {
                    dictionary.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writteFile(String path, String toWritte) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
            bufferedWriter.write(toWritte + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setResultComponents(boolean result, String wordToSearch) {
        if (result) {

            lbNote.setText("EXITO!");
            lbNote.setTextFill(new Color(0.299, 0.7158, 0.2439, 1.0));
            lbResult.setPrefHeight(60);
            lbResult.setText("La palabra \"" + wordToSearch + "\"  se encuentra agregada dentro de su diccionario");
        } else {
            lbNote.setText("ERROR!");
            lbNote.setTextFill(new Color(0.7368, 0.2129, 0.2129, 1.0));
            lbResult.setPrefHeight(100);
            lbResult.setText("La palabra \"" + wordToSearch + "\"  NO se encuentra agregada dentro de su diccionario\n"
                    + "De en el boton \"Añadir\" si desea guardar esta palabra");
        }
        updateSearchComponents(wordToSearch);
    }

    private void updateSearchComponents(String wordToSearch) {
        lbSimilars.setText("Palabras similares a \"" + wordToSearch + "\":");
        createVBoxList(vbSimilars, dictionary.searchSimilar(wordToSearch));
        lbPrefix.setText("Palabras que inician con  \"" + wordToSearch + "\":");
        createVBoxList(vbPrefix, dictionary.searchByPrefix(wordToSearch));
        lbReverse.setText("Palabras que terminan con  \"" + wordToSearch + "\":");
        createVBoxList(vbReverse, dictionary.searchReverse(wordToSearch));

    }

    private void createVBoxList(VBox searchVBox, List<String> searchList) {
        searchVBox.getChildren().clear();
        if (searchList == null || searchList.isEmpty()) {
            Label withoutElements = new Label("No hay resultados");
            withoutElements.setFont(new Font(14));
            searchVBox.getChildren().add(withoutElements);
        } else {
            for (String word : searchList) {
                Label wordLabel = new Label("• " + word);
                wordLabel.setFont(new Font(14));
                searchVBox.getChildren().add(wordLabel);

            }
        }

    }

    @FXML
    void capitalize(KeyEvent event) {
        String currentText = tfSearch.getText().trim();
        if (currentText.length() == 1) {
            tfSearch.setText(currentText.toUpperCase());
            tfSearch.positionCaret(1);
        }
        if (currentText.equals("")) {
            vbInfo.setVisible(false);
        }

    }

    @FXML
    private void keyboardShortcuts(KeyEvent event) {
        String textWithoutSpaces = tfSearch.getText().trim();
        int wordSize = textWithoutSpaces.length();

        if (event.getCode() == KeyCode.ENTER) {
            searchWord();
        }
        if (event.getCode() == KeyCode.SPACE) {
            tfSearch.setText(textWithoutSpaces);
            tfSearch.positionCaret(wordSize);

        }
    }
}
