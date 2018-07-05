package client.view.gui;

import shared.commands.client_to_server_command.ChosenWindowPatternCard;
import client.view.gui.notifiers.wpcchoiceactions.WGUIViewSetting;
import client.view.gui.notifiers.wpcchoiceactions.WPCChoice;
import client.view.gui.notifiers.wpcchoiceactions.WPCChoiceAction;
import client.view.gui.notifiers.wpcchoiceactions.WPCChoiceVisitor;
import client.view.gui.notifiers.WPCChoiceNotifier;
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

/**
 * Manages the Window Pattern Card window
 * @author Nives Migotto
 */
public class WPCChoiceController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(WPCChoiceController.class.getName());
    private WPCChoiceNotifier wpcChoiceNotifier = WPCChoiceNotifier.getInstance();

    private GUIView guiViewT;

    private List<ToggleButton> wpCards;
    private List<Label> wpcNames;
    private List<Label> wpcDifficulties;

    @FXML
    private Label choose;
    @FXML
    private Label priocname;
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
    private ImageView prioc;

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
        wpcChoiceNotifier.addObserver(this);
        initCards();
        wpcGroup.getToggles().addAll(wpc1, wpc2, wpc3, wpc4);
        start.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
        wpcChoiceNotifier.setOpen(true);
    }

    /**
     * Adds all cards attributes to lists
     */
    private void initCards() {
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
                    setWPCards(wpcChoice.getWpcNames(), wpcChoice.getWpcDifficulties(), wpcChoice.getPrivateOC());
                }
            };
            guiReply.acceptWPCChoiceVisitor(wpcChoiceVisitor);
        }
    }

    /**
     * Assigns to each card the relative information
     * @param names list of Window Pattern Cards names
     * @param difficulties  list of Window Pattern Cards difficulties
     * @param priOC Private Objective Card name
     */
    private void setWPCards(List<String> names, List<Integer> difficulties, String priOC) {
        Platform.runLater(() -> {
            Image image1 = new Image("/client/OC/" + priOC + ".jpg");
            priocname.setText(priOC);
            prioc.setImage(image1);
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

    /**
     * Saves Window Pattern Card name
     */
    @FXML
    public void selectWPC(){
        int s = wpCards.size();
        for (int i=0; i<wpCards.size(); i++) {
            if (wpCards.get(i).isSelected()) {
                disableOtherWPCs(i, true);
                selectedWPC = wpcNames.get(i).getText();
                s--;
            }
        }
        if (s == wpCards.size()) {
            disableOtherWPCs(5, false);
        }
    }

    /**
     * Disables or enables all Window Pattern Cards exept the given one
     * @param i selected Widow Pattern Card index
     * @param b indicates whether the Window Pattern Cards have to be enabled or disabled
     */
    private void disableOtherWPCs(int i, boolean b) {
        for (int j=0; j<wpCards.size(); j++) {
            if (j!=i) {
                wpCards.get(j).setDisable(b);
            }
        }
    }

    /**
     * Starts the game
     */
    @FXML
    public void start() {
        if (!wpc1.isSelected() && !wpc2.isSelected() && !wpc3.isSelected() && !wpc4.isSelected()) {
            inputError();
        } else {
            guiViewT.notify(new ChosenWindowPatternCard(selectedWPC));
            for (ToggleButton tb : wpCards) {
                tb.setDisable(true);
            }
            showGameBoard();
        }
    }

    /**
     * Closes current stage
     */
    private void closeStage() {
        wpcChoiceNotifier.setOpen(false);
        Stage stage = (Stage)start.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens Game Board window
     */
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

    /**
     * Indicates if an input error occurred (no Window Pattern Card selected)
     */
    private void inputError() {
        redShadow.setColor(new Color(0.7, 0,0,1));
        choose.setEffect(redShadow);
    }
}
