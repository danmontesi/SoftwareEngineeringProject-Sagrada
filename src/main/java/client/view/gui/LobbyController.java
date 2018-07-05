package client.view.gui;

import client.view.gui.notifiers.LobbyNotifier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the Lobby window
 * @author Nives Migotto
 */
public class LobbyController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(LobbyController.class.getName());
    private LobbyNotifier lobbyNotifier = LobbyNotifier.getInstance();

    private List<String> playerNames = new ArrayList<>();

    private List<Label> players;

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
        lobbyNotifier.addObserver(this);
        initLabels();
        lobbyNotifier.setOpen(true);
    }

    public void update(Observable o, Object arg) {
        if (arg == null) {
            showWPCChoice();
        } else {
            ArrayList<String> message = (ArrayList<String>)arg;
            if (message.get(1).equals("1")) {
                updatePlayers(message.get(0));
            } else {
                showGameBoard();
            }
        }
    }

    /**
     * Adds all labels to a list
     */
    private void initLabels() {
        players.add(player1);
        players.add(player2);
        players.add(player3);
    }

    /**
     * Shows if a player connected
     * @param player player that connected
     */
    private void updatePlayers(String player) {
        Platform.runLater(() -> {
            if (!playerNames.contains(player)) {
                playerNames.add(player);
            } else {
                playerNames.remove(player);
            }
            if (!playerNames.isEmpty()) {
                for (int i = 0; i < playerNames.size(); i++) {
                    players.get(i).setText(playerNames.get(i) + " joined the game");
                }
                for (int j = playerNames.size(); j < 3; j++) {
                    players.get(j).setText("");
                }
            }
        });
    }

    /**
     * Opens Window Pattern Card Choice window
     */
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
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch window pattern card choice", e);
            }
        });
    }

    /**
     * Opens Game Board window
     */
    private void showGameBoard(){
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/gameboard.fxml"));
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

    /**
     * Closes current stage
     */
    private void closeStage() {
        lobbyNotifier.setOpen(false);
        Stage stage = (Stage)text.getScene().getWindow();
        stage.close();
    }
}
