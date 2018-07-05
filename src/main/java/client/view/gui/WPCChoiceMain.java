package client.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WPCChoiceMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client/wpcchoice.fxml"));
        primaryStage.setTitle("WindowPatternCard Choice");
        primaryStage.setScene(new Scene(root, 900, 610));
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}