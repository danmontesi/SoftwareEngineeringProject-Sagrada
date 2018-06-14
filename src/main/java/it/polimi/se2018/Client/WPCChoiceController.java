package it.polimi.se2018.Client;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class WPCChoiceController {

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

    DropShadow shadow = new DropShadow();

    public WPCChoiceController() {
        wpcards = new ArrayList<>();
    }

    public void initialize() {
        initWPCards();
        initStyle();
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
            wpc.setStyle("-fx-focus-color: transparent");
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

    @FXML
    public void wpc1Action(){

    }

    @FXML
    public void wpc2Action(){

    }

    @FXML
    public void wpc3Action(){

    }

    @FXML
    public void wpc4Action(){

    }

    @FXML
    public void startGame(){

    }
}
