package it.polimi.se2018.client.GUI;

import it.polimi.se2018.client.ClientStarterMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MessageMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client/message.fxml"));
        primaryStage.setTitle("Message");
        primaryStage.setScene(new Scene(root, 500, 350));
        //Font.loadFont(ClientStarterMain.class.getResource("GoudyBookletter1911.ttf").toExternalForm(), 10);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
