package it.polimi.se2018.client.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class RankingPaneController {

    private List<Button> buttons;

    @FXML
    private Label outcome;
    @FXML
    private Label rank;

    @FXML
    private Button playAgain;
    @FXML
    private Button exit;

    private DropShadow shadow = new DropShadow();

    public RankingPaneController() {
        buttons = new ArrayList<>();
    }

    public void initialize() {
        initButtons();
        initStyle();
    }

    private void initButtons() {
        buttons.add(playAgain);
        buttons.add(exit);
    }

    private void initStyle() {
        if (outcome.getText().equals("You Won!")) {
            outcome.setTextFill(Color.web("#006b00"));
        }
        if (outcome.getText().equals("You Lost")) {
            outcome.setTextFill(Color.web("#990000"));
        }

        for (Button b : buttons) {
            b.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent");
            b.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> b.setEffect(shadow));
            b.addEventHandler(MouseEvent.MOUSE_EXITED, e -> b.setEffect(null));
        }
    }

    private void closeStage() {
        Stage stage = (Stage)outcome.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void startNewGame() {

    }

    @FXML
    public void exit(){

    }
}
