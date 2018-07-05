package client.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LobbyMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client/lobby.fxml"));
        primaryStage.setTitle("Lobby");
        primaryStage.setScene(new Scene(root, 400, 330));
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
