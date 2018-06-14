package ProvaJavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WPCChoiceMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ProvaJavaFX/resources/wpcchoice.fxml"));
        primaryStage.setTitle("WindowPatternCard Choice");
        primaryStage.setScene(new Scene(root, 730, 570));
        Font.loadFont(ClientStarterMain.class.getResource("GoudyBookletter1911.ttf").toExternalForm(), 20);
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}