package it.polimi.se2018.client.GUI;

import it.polimi.se2018.client.ClientStarterMain;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LobbyController {

    private static final Logger LOGGER = Logger.getLogger(LobbyController.class.getName());

    @FXML
    private Label text;

    private Stage stage;

    public void show() {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client/lobby.fxml"));
        Stage primaryStage = new Stage();
        stage = primaryStage;
        stage.setTitle("Lobby");
        stage.setScene(new Scene(root, 400, 250));
  //      Font.loadFont(ClientStarterMain.class.getResource("GoudyBookletter1911.ttf").toExternalForm(), 10);
        stage.show();
    }

    public void closeStage() {
        Platform.runLater(() -> {
            //Stage stage = (Stage)text.getScene().getWindow();
            stage.setOnCloseRequest(event -> Platform.exit());
            stage.close();
            System.exit(0);
        });
    }


}
