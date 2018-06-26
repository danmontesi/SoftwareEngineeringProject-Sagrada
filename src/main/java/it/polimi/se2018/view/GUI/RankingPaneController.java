package it.polimi.se2018.view.GUI;

import it.polimi.se2018.view.GUI.Notifiers.RankingPaneNotifier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RankingPaneController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(GameBoardController.class.getName());

    private List<Button> buttons;

    @FXML
    private Label outcome;
    @FXML
    private Label rank;

    @FXML
    private GridPane ranking;

    @FXML
    private Button playAgain;
    @FXML
    private Button exit;

    private DropShadow shadow = new DropShadow();

    public RankingPaneController() {
        buttons = new ArrayList<>();
    }

    public void initialize() {
        RankingPaneNotifier.getInstance().addObserver(this);
        initButtons();
    }

    public void update(Observable o, Object arg) {
        setContent((ArrayList<String>)arg);
    }

    private void initButtons() {
        buttons.add(playAgain);
        buttons.add(exit);
        for (Button b : buttons) {
            b.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
            b.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> b.setEffect(shadow));
            b.addEventHandler(MouseEvent.MOUSE_EXITED, e -> b.setEffect(null));
        }
    }

    private void setContent(ArrayList<String> scores) {
        Platform.runLater(() -> {
        if (scores.get(0).equals("1")) {
            outcome.setText("You Won!");
            outcome.setTextFill(Color.web("#006b00"));
            rank.setText("You ranked #1");
        }
        else {
            outcome.setText("You Lost");
            outcome.setTextFill(Color.web("#990000"));
            rank.setText("You ranked #" + scores.get(0));
        }
        scores.remove(0);
        setRanking(scores);
        });
    }

    private void setRanking(ArrayList<String> scores) {
        Platform.runLater(() -> {
            ArrayList<String> tmp;
            ArrayList<String> singleScores = new ArrayList<>();
            for (String value : scores) {
                tmp = new ArrayList<>(Arrays.asList(value.split("_")));
                singleScores.addAll(tmp);
            }
            int k=-1;
            for (int i=0; i<singleScores.size(); i++) {
                int h=i%3;
                if (h==0) k++;
                Label label = new Label();
                label.setText(singleScores.get(i));
                label.setStyle("-fx-font-size: 22px");
                ranking.add(label, h, k);
            }
        });
    }

    private void closeStage() {
        Stage stage = (Stage)outcome.getScene().getWindow();
        stage.close();
    }

    private void showClientStarter() {
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/clientstarter.fxml"));
                Parent root = fxmlLoader.load();
                Stage wpcChoiceStage = new Stage();
                wpcChoiceStage.setScene(new Scene(root));
                wpcChoiceStage.show();
                closeStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void startNewGame() {
        showClientStarter();
    }

    @FXML
    public void exit(){
        closeStage();
    }
}