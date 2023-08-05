package general;

import TDAS.Trie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 900, 500);
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
        Trie arbol = new Trie();

        arbol.addLeave("casa");
        arbol.addLeave("casanoba");
        arbol.addLeave("cascada");
        arbol.addLeave("mateo");
        arbol.addLeave("mareoq");
        arbol.addLeave("mates");
        arbol.addLeave("martes");
        arbol.addLeave("mateoron");
        arbol.addLeave("mieroron");
        arbol.addLeave("marques");

        System.out.println(arbol.countLeaves());
        System.out.println(arbol.searchLeave("casa"));

        System.out.println(arbol.getLeaves());

        System.out.println(arbol);

        System.out.println(arbol.deleteLeave("mates"));
        System.out.println(arbol.getLeaves());

        System.out.println(arbol);

        System.out.println("search prefix" + arbol.searchByPrefix("ma"));
        System.out.println("search prefix" + arbol.searchByPrefix("a"));
        System.out.println("search prefix" + arbol.searchByPrefix("casa"));
        System.out.println("search prefix" + arbol.searchByPrefix(""));
        System.out.println("search prefix" + arbol.searchByPrefix("cas"));
        System.out.println("");
        System.out.println(arbol.searchReverse("es"));
        System.out.println(arbol.searchReverse("a"));
        System.out.println(arbol.searchReverse("oron"));
        System.out.println(arbol.searchReverse(""));
        System.out.println(arbol.searchReverse(null));
        System.out.println(arbol.searchReverse(" "));
        System.out.println(arbol.searchReverse("yao"));
        System.out.println("");
        System.out.println("Similars");
        System.out.println(arbol.searchSimilar("martes"));
        System.out.println(arbol.searchSimilar("casa"));
        launch();

    }

}
