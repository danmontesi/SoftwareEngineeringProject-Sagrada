package ProvaJavaFX;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UIChoiceController {
    @FXML
    private Button cli;
    @FXML
    private Button gui;

    DropShadow shadow = new DropShadow();

    public void initialize() {
        initStyle();
    }

    private void initStyle() {
        Image image1 = new Image("/ProvaJavaFX/resources/icon_cli.jpg");
        Image image2 = new Image("/ProvaJavaFX/resources/sagrada3.png");
        cli.setGraphic(new ImageView(image1));
        gui.setGraphic(new ImageView(image2));
        cli.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                cli.setEffect(shadow);
            }
        });
        cli.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                cli.setEffect(null);
            }
        });
        gui.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gui.setEffect(shadow);
            }
        });
        gui.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gui.setEffect(null);
            }
        });
    }

    public void goToCLI() {

    }

    public void goToGUI() {
    }
}
