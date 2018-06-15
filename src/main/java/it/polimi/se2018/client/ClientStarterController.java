package it.polimi.se2018.client;

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

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class ClientStarterController implements Observer {

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
        rmi.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                rmi.setEffect(shadow);
            }
        });
        rmi.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (!rmi.isSelected()) rmi.setEffect(null);
            }
        });
        socket.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                socket.setEffect(shadow);
            }
        });
        socket.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (!socket.isSelected()) socket.setEffect(null);
            }
        });
        connect.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                connect.setEffect(shadow);
            }
        });
        connect.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                connect.setEffect(null);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        boolean successful = (boolean)arg;
        if (successful) {
            showUIChoice();
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

    public String getConnection() {
        ToggleButton selectedButton = (ToggleButton)connectionType.getSelectedToggle();
        String connection = selectedButton.getText();
        return connection;
    }

    @FXML
    public void startConnection() {

    }

    private void showUIChoice() {
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/uichoice.fxml"));
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
        // PlayerLoginNotifier.getInstance().deleteObserver(this);
        ClientStarterMain.getStage().close();
    }

    private void inputError() {
        redShadow.setColor(new Color(0.7, 0,0,1));
        redShadow.setSpread(3);
        usernameField.setEffect(redShadow);
    }
}
