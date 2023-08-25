package general;

import TDAS.Trie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.StageStyle;
import utils.trieSerialization;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("dictionary"), 900, 500);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        if (trieSerialization.unserialize("dictionary") == null) {
            DictionaryController.dictionary = createDefaultDictionary();
        }else{
            DictionaryController.dictionary= (Trie) trieSerialization.unserialize("dictionary");
        }
        launch();
    }

    private static Trie createDefaultDictionary() {
        Trie arbol = new Trie();

        arbol.add("Espol");
        arbol.add("estructura");
        arbol.add("datos");
        arbol.add("árbol");
        arbol.add("diccionario");
        arbol.add("hermana");
        arbol.add("Gonzalo");
        arbol.add("metadato");
        arbol.add("megadato");
        arbol.add("carro");
        arbol.add("gorra");
        arbol.add("atención");
        arbol.add("plena");
        arbol.add("zoom");
        arbol.add("mesa");
        arbol.add("mes");
        arbol.add("tela");
        arbol.add("telegrafo");
        arbol.add("tele");
        arbol.add("televisión");
        arbol.add("gatos");
        arbol.add("novia");
        arbol.add("falta");

        return arbol;

    }

}
