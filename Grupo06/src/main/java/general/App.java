package general;

import TDAS.Trie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
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
//        launch();
        Trie arbol = new Trie("");
        arbol.add(new Trie("o"));
        arbol.add(new Trie("l"));
        arbol.add(new Trie("a"));
//        arbol.getRoot().getChildren().get(0).add(new Trie("o"));
        arbol.add(new Trie("b"));

        System.out.println(arbol.countLeaves());
        System.out.println(arbol.searchLeave("b"));
        
    }

}
