package it.polimi.se2018.view.gui;

import it.polimi.se2018.view.gui.Notifiers.LobbyNotifier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LobbyController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(LobbyController.class.getName());

    private ArrayList<Label> players;

    @FXML
    private Label text;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;

    public LobbyController() {
        players = new ArrayList<>();
    }

    public void initialize() {
        LobbyNotifier.getInstance().addObserver(this);
        initLabels();
    }

    public void update(Observable o, Object arg) {
        if (arg == null) {
            showWPCChoice();
        } else {
            updatePlayers((String)arg);
        }
    }

    private void initLabels() {
        players.add(player1);
        players.add(player2);
        players.add(player3);
    }

    private void updatePlayers(String player) {
        Platform.runLater(() -> {
            ArrayList<String> playerNames = new ArrayList<>();
            if (!playerNames.contains(player)) {
                playerNames.add(player);
            } else {
                playerNames.remove(player);
            }
            if (!playerNames.isEmpty()) {
                for (int i = 0; i < playerNames.size(); i++) {
                    players.get(i).setText(playerNames.get(i) + " just connected");
                }
                for (int j = playerNames.size(); j < 3; j++) {
                    players.get(j).setText("");
                }
            }
        });
    }

    private void showWPCChoice(){
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/wpcchoice.fxml"));
                Parent root = fxmlLoader.load();
                Stage wpcChoiceStage = new Stage();
                wpcChoiceStage.setScene(new Scene(root));
                wpcChoiceStage.show();
                closeStage();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch game board", e);
            }
        });
    }

    private void closeStage() {
        Stage stage = (Stage)text.getScene().getWindow();
        stage.close();
    }
}
