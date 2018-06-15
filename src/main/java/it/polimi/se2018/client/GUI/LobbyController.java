package it.polimi.se2018.client.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class LobbyController extends Observable implements Observer {

    @FXML
    private Label text;

    @Override
    public void update(Observable o, Object arg) {
        showWPCChoice();
    }

    public void showWPCChoice() {
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/wpcchoice.fxml"));
                Parent root = fxmlLoader.load();
                Stage lobbyStage = new Stage();
                lobbyStage.setScene(new Scene(root));
                lobbyStage.show();
                closeStage();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch wpc choice", e);
            }
        });
    }

    private void closeStage() {
        Stage stage = (Stage)text.getScene().getWindow();
        stage.close();
    }


}
