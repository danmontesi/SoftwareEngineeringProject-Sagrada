package it.polimi.se2018.client.GUI;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class WPCChoiceController extends Observable implements Observer {

    private List<ToggleButton> wpcards;

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

    private ToggleGroup wpcs = new ToggleGroup();

    private DropShadow shadow = new DropShadow();

    private String selectedWPC = new String();

    public WPCChoiceController() {
        wpcards = new ArrayList<>();
    }

    public void initialize() {
        initWPCards();
        setTGroup();
        initStyle();
    }
    private void setTGroup() {
        wpcs.getToggles().addAll(wpc1, wpc2, wpc3, wpc4);
    }

    private void initWPCards() {
        wpcards.add(wpc1);
        wpcards.add(wpc2);
        wpcards.add(wpc3);
        wpcards.add(wpc4);
    }

    private void initStyle() {
        Image image = new Image("/client/WPC/virtus.jpg");
        for (ToggleButton wpc : wpcards) {
            ImageView iv = new ImageView(image);
            iv.setFitHeight(184);
            iv.setFitWidth(275);
            wpc.setGraphic(iv);
            wpc.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    wpc.setEffect(shadow);
                }
            });
            wpc.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (!wpc.isSelected()) wpc.setEffect(null);
                }
            });
        }
    }

    @Override
    public void update(Observable o, Object args) {

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
    }

    private void closeStage() {
        Stage stage = (Stage)start.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showGameBoard(){
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
}
