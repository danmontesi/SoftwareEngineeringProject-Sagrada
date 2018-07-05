package client.view.gui;

import client.client_network.rmi.RMIClient;
import client.client_network.socket.SocketClient;
import server.server_network.ServerConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the Login window
 * @author Nives Migotto
 */
public class LoginController implements Observer {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML
    private TextField usernameField;
    @FXML
    private TextField ipAddressField;

    @FXML
    private ToggleButton rmi;
    @FXML
    private ToggleButton socket;
    @FXML
    private Button connect;

    private ToggleGroup connectionType = new ToggleGroup();

    private DropShadow redShadow = new DropShadow();

    public void initialize() {
        connectionType.getToggles().addAll(rmi, socket);
        socket.setSelected(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        /*never used*/
    }

    private String getUsername() {
        return usernameField.getText();
    }

    /**
     * Gets the connection type (socket or RMI)
     * @return
     */
    private String getConnection() {
        ToggleButton selectedButton = (ToggleButton)connectionType.getSelectedToggle();
        return selectedButton.getText();
    }

    /**
     * Starts a connection
     */
    @FXML
    public void startConnection() {
        if (getUsername().equals("")) {
            inputError();
        } else {
            usernameField.setEffect(null);
            ServerConnection server;
            if (getConnection().equals("RMI")) {
                server = new RMIClient(2, ipAddressField.getText());
                server.startConnection(getUsername());
            } else if (getConnection().equals("Socket")) {
                server = new SocketClient(2, ipAddressField.getText());
                server.startConnection(getUsername());
            }
            disableButtons();
            showLobby();
        }
    }

    /**
     * Disables all buttons
     */
    private void disableButtons() {
        connect.setDisable(true);
        rmi.setDisable(true);
        socket.setDisable(true);
    }

    /**
     * Opens Lobby window
     */
    private void showLobby() {
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/lobby.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                closeScene();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch interface choice", e);
            }
        });
    }

    /**
     * Closes current stage
     */
    private void closeScene() {
        Stage stage = (Stage)connect.getScene().getWindow();
        stage.close();
    }

    /**
     * Indicates if an input error occurred (no Username)
     */
    private void inputError() {
        redShadow.setColor(new Color(0.7, 0,0,1));
        usernameField.setEffect(redShadow);
    }
}
