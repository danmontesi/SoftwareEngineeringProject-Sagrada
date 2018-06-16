package it.polimi.se2018.client;

import it.polimi.se2018.network.client.rmi.RMIClient;
import it.polimi.se2018.network.client.socket.SocketClient;
import it.polimi.se2018.network.server.ServerConnection;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientStarterController implements Observer {

    private static final Logger LOGGER = Logger.getLogger(ClientStarterController.class.getName());

    @FXML
    private Label username;
    @FXML
    private Label connection;
    @FXML
    private Label ipAddress;

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

    private DropShadow shadow = new DropShadow();
    private DropShadow redShadow = new DropShadow();

    public void initialize() {
        initStyle();
        initToggleGroup();
    }

    private void initToggleGroup() {
        connectionType.getToggles().addAll(rmi, socket);
    }

    private void initStyle() {
        rmi.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> rmi.setEffect(shadow));
        rmi.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            if (!rmi.isSelected()) rmi.setEffect(null);
        });
        socket.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> socket.setEffect(shadow));
        socket.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            if (!socket.isSelected()) socket.setEffect(null);
        });
        connect.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> connect.setEffect(shadow));
        connect.addEventHandler(MouseEvent.MOUSE_EXITED, e -> connect.setEffect(null));
    }

    @Override
    public void update(Observable o, Object arg) {
        boolean successful = (boolean)arg;
        if (successful) {
            //showUIChoice();
        } else {
            inputError();
        }
    }

    @FXML
    public void showIP() {
        ipAddress.setDisable(false);
        ipAddressField.setDisable(false);
    }

    @FXML
    public void hideIP() {
        ipAddress.setDisable(true);
        ipAddressField.setDisable(true);
    }

    private String getUsername() {
        return usernameField.getText();
    }

    private String getConnection() {
        ToggleButton selectedButton = (ToggleButton)connectionType.getSelectedToggle();
        return selectedButton.getText();
    }

    @FXML
    public void startConnection() {
        ServerConnection server;
        if (getUsername() == null) {
            //TODO scrivere inserire username
        } else if (getConnection() == null) {
            //TODO scrivere scegliere connessione
        } else {
            if (getConnection().equals("RMI")) {
                server = new RMIClient(1, this);
                server.startConnection(getUsername());
            } else if (getConnection().equals("Socket")) {
                //TODO vincolo IP
                server = new SocketClient(2, this);
                server.startConnection(getUsername());
            }
        }
    }

    public void showLobby() {
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


    public void closeScene() {
        //PlayerLoginNotifier.getInstance().deleteObserver(this);
        ClientStarterMain.getStage().close();
    }

    private void inputError() {
        redShadow.setColor(new Color(0.7, 0,0,1));
        redShadow.setSpread(3);
        usernameField.setEffect(redShadow);
    }
}
