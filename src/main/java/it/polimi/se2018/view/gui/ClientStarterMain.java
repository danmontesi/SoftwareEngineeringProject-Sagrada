package it.polimi.se2018.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientStarterMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/client/clientstarter.fxml"));
        primaryStage.setTitle("Sagrada");
        primaryStage.setScene(new Scene(root, 400, 500));
//        Font.loadFont(ClientStarterMain.class.getResource("GoudyBookletter1911.ttf").toExternalForm(), 10);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
