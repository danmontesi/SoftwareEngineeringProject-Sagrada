package it.polimi.se2018.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientStarterMain extends Application {
    private static Stage stage;
    private ClientStarterController clientStarterController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setStage(primaryStage);
        FXMLLoader loaderClientStarter = new FXMLLoader(getClass().getResource("/client/clientstarter.fxml"));
        Parent root = loaderClientStarter.load();
        clientStarterController = loaderClientStarter.getController();
        primaryStage.setTitle("Sagrada");
        primaryStage.setScene(new Scene(root, 400, 500));
        //Font.loadFont(ClientStarterMain.class.getResource("GoudyBookletter1911.ttf").toExternalForm(), 20);
        primaryStage.show();
    }

    private static void setStage(Stage stage) {
        ClientStarterMain.stage = stage;
    }

    @Override
    public void stop() {
        clientStarterController.closeScene();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }
}
