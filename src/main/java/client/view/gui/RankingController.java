package client.view.gui;

import client.view.gui.notifiers.RankingPaneNotifier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the Ranking Pane window
 *
 * @author Nives Migotto
 */
public class RankingController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(GameBoardController.class.getName());
    private RankingPaneNotifier rankingPaneNotifier = RankingPaneNotifier.getInstance();

    @FXML
    private Label outcome;
    @FXML
    private Label rank;

    @FXML
    private GridPane ranking;

    public void initialize() {
        rankingPaneNotifier.addObserver(this);
        rankingPaneNotifier.setOpen(true);
    }


    public void update(Observable o, Object arg) {
        setContent((List<String>) arg);
    }

    /**
     * Sets outcome message (victory/defeat)
     *
     * @param scores list of players and relative scores
     */
    private void setContent(List<String> scores) {
        Platform.runLater(() -> {
            if (scores.get(0).equals("1")) {
                outcome.setText("You Won!");
                outcome.setTextFill(Color.web("#006b00"));
                rank.setText("You ranked #1");
            } else {
                outcome.setText("You Lost");
                outcome.setTextFill(Color.web("#990000"));
                rank.setText("You ranked #" + scores.get(0));
            }
            scores.remove(0);
            setRanking(scores);
        });
    }

    /**
     * Sets ranking
     *
     * @param scores list of players and relative scores
     */
    private void setRanking(List<String> scores) {
        Platform.runLater(() -> {
            List<String> tmp;
            List<String> singleScores = new ArrayList<>();
            for (String value : scores) {
                tmp = new ArrayList<>(Arrays.asList(value.split("_")));
                singleScores.addAll(tmp);
            }
            int k = -1;
            for (int i = 0; i < singleScores.size(); i++) {
                int h = i % 3;
                if (h == 0) k++;
                Label label = new Label();
                label.setText(singleScores.get(i));
                label.setStyle("-fx-font-size: 22px");
                ranking.add(label, h, k);
            }
            rankingPaneNotifier.setOpen(false);
        });
    }

}