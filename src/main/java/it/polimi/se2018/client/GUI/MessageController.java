package it.polimi.se2018.client.GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MessageController {

    @FXML
    private Button ok;

    @FXML
    private Button cancel;

    @FXML
    private TextArea message;

    private DropShadow shadow = new DropShadow();

    public void initialize() {
        setMessage();
        initStyle();
    }

    private void initStyle() {
        ok.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ok.setEffect(shadow);
            }
        });
        ok.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ok.setEffect(null);
            }
        });
        cancel.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                cancel.setEffect(shadow);
            }
        });
        cancel.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                cancel.setEffect(null);
            }
        });
    }

    private void setMessage() {
        this.message.setText("sunt in culpa qui officia deserunt mollit anim id est laborum.");
    }

    @FXML
    public void exit() {
        Stage stage = (Stage)ok.getScene().getWindow();
        stage.close();
    }
}
