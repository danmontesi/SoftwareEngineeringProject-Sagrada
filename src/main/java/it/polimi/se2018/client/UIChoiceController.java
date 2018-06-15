package it.polimi.se2018.client;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

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
        Image image1 = new Image("/client/images/icon_cli.jpg");
        Image image2 = new Image("/client/images/sagrada3.png");
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

    private void closeStage() {
        Stage stage = (Stage)gui.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void goToCLI() {

    }

    @FXML
    public void goToGUI() {
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/lobby.fxml"));
                Parent root = fxmlLoader.load();
                Stage uiChoiceStage = new Stage();
                uiChoiceStage.setScene(new Scene(root));
                uiChoiceStage.show();
                closeStage();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch waiting room", e);
            }
        });
    }
}
