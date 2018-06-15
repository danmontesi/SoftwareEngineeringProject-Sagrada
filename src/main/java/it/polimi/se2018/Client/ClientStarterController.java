package it.polimi.se2018.Client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.Observable;
import java.util.Observer;

public class ClientStarterController implements Observer {

    @FXML
    private ImageView sagrada;

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

    public void initialize() {
        initStyle();
        setTGroup();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private void initStyle() {
        usernameField.setStyle("-fx-focus-color: transparent");
        ipAddressField.setStyle("-fx-focus-color: transparent");
        rmi.setStyle("-fx-focus-color: transparent");
        socket.setStyle("-fx-focus-color: transparent");
        connect.setStyle("-fx-focus-color: transparent");
    }

    private void setTGroup() {
        connectionType.getToggles().addAll(rmi, socket);
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

    @FXML
    public void startConnection() {

    }

    public void closeScene() {
        // PlayerLoginNotifier.getInstance().deleteObserver(this);
        ClientStarterMain.getStage().close();
    }
}
