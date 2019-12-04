package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader((getClass().getResource("menu.fxml")));
        Parent root = loader.load();
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(new Scene(root, 429, 499));
        primaryStage.show();
        GameSettings.OFF_X = primaryStage.getWidth() - 429;
        GameSettings.OFF_Y = primaryStage.getHeight() - 499;
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
