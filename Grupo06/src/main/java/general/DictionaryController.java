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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
    private VBox vbContent;
    
    @FXML
    private TextField tfSearch;
    
    public static Trie dictionary;
    
    private AutoCompletionBinding<String> autoComplete;
    
    private final Alertas alert = new Alertas();
    
    private final FileChooser fc = new FileChooser();

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void closeWindow() {
        trieSerialization.serialize(dictionary,"dictionary");
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
    void playGame() {

    }

    private void readFile(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                if (!dictionary.search(linea)) {
                    dictionary.add(linea);
                }
                System.out.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writteFile(String path, String toWritte) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
            bufferedWriter.write(toWritte + "\n");
            System.out.println("Archivo escrito exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
