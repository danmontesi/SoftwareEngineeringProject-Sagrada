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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RankingPaneController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(GameBoardController.class.getName());

    private List<Button> buttons;
    private List<Label> first;
    private List<Label> second;
    private List<Label> third;
    private List<Label> fourth;
    private List<List<Label>> players;

    @FXML
    private Label outcome;
    @FXML
    private Label rank;
    @FXML
    private Label r1;
    @FXML
    private Label r2;
    @FXML
    private Label r3;
    @FXML
    private Label r4;
    @FXML
    private Label u1;
    @FXML
    private Label u2;
    @FXML
    private Label u3;
    @FXML
    private Label u4;
    @FXML
    private Label p1;
    @FXML
    private Label p2;
    @FXML
    private Label p3;
    @FXML
    private Label p4;

    @FXML
    private Button playAgain;
    @FXML
    private Button exit;

    private DropShadow shadow = new DropShadow();

    public RankingPaneController() {
        buttons = new ArrayList<>();
        first = new ArrayList<>();
        second = new ArrayList<>();
        third = new ArrayList<>();
        fourth = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void initialize() {
        RankingPaneNotifier.getInstance().addObserver(this);
        initButtons();
        initLabels();
    }

    public void update(Observable o, Object arg) {
        setRanking((ArrayList<String>)arg);
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

    private void initLabels() {
        first.add(r1);
        first.add(u1);
        first.add(p1);

        second.add(r2);
        second.add(u2);
        second.add(p2);

        third.add(r3);
        third.add(u3);
        third.add(p3);

        fourth.add(r4);
        fourth.add(u4);
        fourth.add(p4);

        players.add(first);
        players.add(second);
        players.add(third);
        players.add(fourth);
    }

    private void setRanking(ArrayList<String> scores) {
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
        ArrayList<ArrayList<String>> s = extractElements(scores);
        for (int i=0; i<s.size();i++) {
            for (int j=0; j<s.get(i).size(); j++) {
                players.get(i).get(j).setText(s.get(i).get(j));
            }
        }
    }

    private void closeStage() {
        Stage stage = (Stage)outcome.getScene().getWindow();
        stage.close();
    }

    private void showClientStarter() {
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientstarter.fxml"));
                Parent root = fxmlLoader.load();
                Stage gameBoardStage = new Stage();
                gameBoardStage.setScene(new Scene(root));
                gameBoardStage.show();
                closeStage();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch ranking pane", e);
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

    private ArrayList<ArrayList<String>> extractElements(ArrayList<String> s) {
        ArrayList<ArrayList<String>> a = new ArrayList<>();
        for (String value : s) {
            ArrayList<String> a1 = new ArrayList<>(Arrays.asList(value.split("_")));
            a.add(a1);
        }
        return a;
    }
}
