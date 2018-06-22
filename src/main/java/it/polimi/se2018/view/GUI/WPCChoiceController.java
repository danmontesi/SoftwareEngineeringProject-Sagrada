package it.polimi.se2018.view.GUI;

import it.polimi.se2018.commands.client_to_server_command.ChosenWindowPatternCard;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions.WGUIViewSetting;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions.WPCChoice;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions.WPCChoiceAction;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions.WPCChoiceVisitor;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceNotifier;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WPCChoiceController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(WPCChoiceController.class.getName());

    private GUIView guiViewT;

    private List<ToggleButton> wpcards;
    private List<Label> wpcnames;
    private List<Label> wpcdifficulties;

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

    private ToggleGroup wpcs;

    private DropShadow shadow = new DropShadow();
    private DropShadow redShadow = new DropShadow();

    private String selectedWPC;

    public WPCChoiceController() {
        wpcards = new ArrayList<>();
        wpcnames = new ArrayList<>();
        wpcdifficulties = new ArrayList<>();
        wpcs = new ToggleGroup();
    }

    public void initialize() {
        WPCChoiceNotifier.getInstance().addObserver(this);
        initWPCards();
        setTGroup();
    }

    public void update(Observable o, Object arg) {
        WPCChoiceAction guiReply = (WPCChoiceAction)arg;
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

    private void setTGroup() {
        wpcs.getToggles().addAll(wpc1, wpc2, wpc3, wpc4);
    }

    private void initWPCards() {
        wpcards.add(wpc1);
        wpcards.add(wpc2);
        wpcards.add(wpc3);
        wpcards.add(wpc4);

        wpcnames.add(wpc1n);
        wpcnames.add(wpc2n);
        wpcnames.add(wpc3n);
        wpcnames.add(wpc4n);

        wpcdifficulties.add(wpc1d);
        wpcdifficulties.add(wpc2d);
        wpcdifficulties.add(wpc3d);
        wpcdifficulties.add(wpc4d);
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
                wpcards.get(i).setGraphic(iv);
                wpcnames.get(i).setText(img);
                wpcdifficulties.get(i).setText(difficulties.get(i).toString());
            }
            for (ToggleButton wpc : wpcards) {
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

    private void closeStage() {
        Stage stage = (Stage)start.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showGameBoard(){
        if (!wpc1.isSelected() && !wpc2.isSelected() && !wpc3.isSelected() && !wpc4.isSelected()) {
            inputError();
        } else {
            guiViewT.notify(new ChosenWindowPatternCard(selectedWPC));
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
    }

    private void inputError() {
        redShadow.setColor(new Color(0.7, 0,0,1));
        choose.setEffect(redShadow);
    }
}
