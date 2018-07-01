package it.polimi.se2018.view.gui;

import it.polimi.se2018.commands.client_to_server_command.ChosenWindowPatternCard;
import it.polimi.se2018.view.gui.Notifiers.WPCChoiceActions.WGUIViewSetting;
import it.polimi.se2018.view.gui.Notifiers.WPCChoiceActions.WPCChoice;
import it.polimi.se2018.view.gui.Notifiers.WPCChoiceActions.WPCChoiceAction;
import it.polimi.se2018.view.gui.Notifiers.WPCChoiceActions.WPCChoiceVisitor;
import it.polimi.se2018.view.gui.Notifiers.WPCChoiceNotifier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WPCChoiceController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(WPCChoiceController.class.getName());

    private GUIView guiViewT;

    private List<ToggleButton> wpCards;
    private List<Label> wpcNames;
    private List<Label> wpcDifficulties;

    @FXML
    private Label choose;
    @FXML
    private Label wpc1n;
    @FXML
    private Label wpc2n;
    @FXML
    private Label wpc3n;
    @FXML
    private Label wpc4n;
    @FXML
    private Label wpc1d;
    @FXML
    private Label wpc2d;
    @FXML
    private Label wpc3d;
    @FXML
    private Label wpc4d;

    @FXML
    private ToggleButton wpc1;
    @FXML
    private ToggleButton wpc2;
    @FXML
    private ToggleButton wpc3;
    @FXML
    private ToggleButton wpc4;

    @FXML
    private Button start;

    private ToggleGroup wpcGroup;

    private DropShadow shadow = new DropShadow();
    private DropShadow redShadow = new DropShadow();

    private String selectedWPC;

    public WPCChoiceController() {
        wpCards = new ArrayList<>();
        wpcNames = new ArrayList<>();
        wpcDifficulties = new ArrayList<>();
        wpcGroup = new ToggleGroup();
    }

    public void initialize() {
        WPCChoiceNotifier.getInstance().addObserver(this);
        initWPCards();
        initButton();
        setTGroup();
    }

    public void update(Observable o, Object arg) {
        if (arg == null) {
            showGameBoard();
        } else {
            WPCChoiceAction guiReply = (WPCChoiceAction) arg;
            WPCChoiceVisitor wpcChoiceVisitor = new WPCChoiceVisitor() {
                @Override
                public void visitWPCChoiceAction(WGUIViewSetting guiViewSetting) {
                    guiViewT = guiViewSetting.getGuiView();
                }

                @Override
                public void visitWPCChoiceAction(WPCChoice wpcChoice) {
                    setWPCards(wpcChoice.getWpcNames(), wpcChoice.getWpcDifficulties());
                }
            };
            guiReply.acceptWPCChoiceVisitor(wpcChoiceVisitor);
        }
    }

    private void setTGroup() {
        wpcGroup.getToggles().addAll(wpc1, wpc2, wpc3, wpc4);
    }

    private void initWPCards() {
        wpCards.add(wpc1);
        wpCards.add(wpc2);
        wpCards.add(wpc3);
        wpCards.add(wpc4);

        wpcNames.add(wpc1n);
        wpcNames.add(wpc2n);
        wpcNames.add(wpc3n);
        wpcNames.add(wpc4n);

        wpcDifficulties.add(wpc1d);
        wpcDifficulties.add(wpc2d);
        wpcDifficulties.add(wpc3d);
        wpcDifficulties.add(wpc4d);
    }

    private void initButton() {
        start.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
        start.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> start.setEffect(shadow));
        start.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> start.setEffect(null));
    }

    private void setWPCards(ArrayList<String> names, ArrayList<Integer> difficulties) {
        Platform.runLater(() -> {
            for (int i=0; i<names.size(); i++) {
                String img = names.get(i);
                String path = "/client/WPC/" + img + ".jpg";
                Image image = new Image(path);
                ImageView iv = new ImageView(image);
                iv.setFitHeight(184);
                iv.setFitWidth(230);
                wpCards.get(i).setGraphic(iv);
                wpcNames.get(i).setText(img);
                wpcDifficulties.get(i).setText(difficulties.get(i).toString());
            }
            for (ToggleButton wpc : wpCards) {
                wpc.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> wpc.setEffect(shadow));
                wpc.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    if (!wpc.isSelected()) wpc.setEffect(null);
                });
            }
        });
    }

    @FXML
    public void wpc1Action(){
        if (wpc1.isSelected()) {
            wpc2.setDisable(true);
            wpc3.setDisable(true);
            wpc4.setDisable(true);
        }
        if (!wpc1.isSelected()) {
            wpc2.setDisable(false);
            wpc3.setDisable(false);
            wpc4.setDisable(false);
        }
        selectedWPC = wpc1n.getText();
    }

    @FXML
    public void wpc2Action(){
        if (wpc2.isSelected()) {
            wpc1.setDisable(true);
            wpc3.setDisable(true);
            wpc4.setDisable(true);
        }
        if (!wpc2.isSelected()) {
            wpc1.setDisable(false);
            wpc3.setDisable(false);
            wpc4.setDisable(false);
        }
        selectedWPC = wpc2n.getText();
    }

    @FXML
    public void wpc3Action(){
        if (wpc3.isSelected()) {
            wpc2.setDisable(true);
            wpc1.setDisable(true);
            wpc4.setDisable(true);
        }
        if (!wpc3.isSelected()) {
            wpc2.setDisable(false);
            wpc1.setDisable(false);
            wpc4.setDisable(false);
        }
        selectedWPC = wpc3n.getText();
    }

    @FXML
    public void wpc4Action(){
        if (wpc4.isSelected()) {
            wpc2.setDisable(true);
            wpc3.setDisable(true);
            wpc1.setDisable(true);
        }
        if (!wpc4.isSelected()) {
            wpc2.setDisable(false);
            wpc3.setDisable(false);
            wpc1.setDisable(false);
        }
        selectedWPC = wpc4n.getText();
    }

    @FXML
    public void start() {
        if (!wpc1.isSelected() && !wpc2.isSelected() && !wpc3.isSelected() && !wpc4.isSelected()) {
            inputError();
        } else {
            guiViewT.notify(new ChosenWindowPatternCard(selectedWPC));
            showGameBoard();
        }
    }

    private void closeStage() {
        Stage stage = (Stage)start.getScene().getWindow();
        stage.close();
    }

    private void showGameBoard(){
        Platform.runLater(() -> {
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

    private void inputError() {
        redShadow.setColor(new Color(0.7, 0,0,1));
        choose.setEffect(redShadow);
    }
}
