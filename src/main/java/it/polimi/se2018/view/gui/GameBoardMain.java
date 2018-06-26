package it.polimi.se2018.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameBoardMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client/gameboard.fxml"));
        primaryStage.setTitle("Sagrada - Gameboard");
        primaryStage.setScene(new Scene(root, 1254, 630));
        Font.loadFont(ClientStarterMain.class.getResource("GoudyBookletter1911.ttf").toExternalForm(), 10);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
