package XML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void Main(String[] args){
        Application.launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPage.fxml"));
        primaryStage.setTitle("EASV Bar");

        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.setFill(javafx.scene.paint.Color.valueOf("#DDDDDD"));

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
