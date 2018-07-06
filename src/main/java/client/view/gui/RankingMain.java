package client.view.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RankingMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client/ranking.fxml"));
        primaryStage.setTitle("Ranking");
        primaryStage.setScene(new Scene(root, 570, 520));
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}

