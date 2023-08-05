package general;

import TDAS.Trie;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DiccionaryController {

    public static Trie diccionary;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void closeWindow() throws IOException {
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
}
