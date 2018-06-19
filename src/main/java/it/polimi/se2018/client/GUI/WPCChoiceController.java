package it.polimi.se2018.client.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WPCChoiceController{

    private static final Logger LOGGER = Logger.getLogger(WPCChoiceController.class.getName());

    private Stage stage;

    private List<ToggleButton> wpcards = new ArrayList<>();
    private List<String> wpcCards;

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

    /*
    public WPCChoiceController(ArrayList<String> wpcCards) {
        wpcards = new ArrayList<>();
        this.wpcCards = wpcCards;
    }

*/
    public void show(ArrayList<String> cardNames) { //NON VIENE FATTA PARTIRE, viene chiamato lo show() del padre (classe Stage)
        try {
            wpcCards = cardNames;
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/client/wpcchoice.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("entra1");
            Stage primaryStage = new Stage();
            System.out.println("entra2");
            stage = primaryStage;
            System.out.println("entra3");
            stage.setTitle("WPC Choice");
            System.out.println("entra4");
            stage.setScene(new Scene(root, 400, 250));
            System.out.println("entra5");
            //Font.loadFont(ClientStarterMain.class.getResource("GoudyBookletter1911.ttf").toExternalForm(), 10);
            System.out.println("entra6");
            stage.show();
            System.out.println("Entra7");
    }

    public void initialize() {
        System.out.println("Prima di init");
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
        /*for (String card : wpcCards) {
            card.replaceAll(" ", "_");
        }*/
        //int i=0;
            for (ToggleButton wpc : wpcards) {
                /*String img = wpcCards.get(i);
                String path = "/client/WPC/" + img + ".jpg";
                Image image = new Image(path);*/
                Image image = new Image("/client/WPC/virtus.jpg");
                ImageView iv = new ImageView(image);
                iv.setFitHeight(184);
                iv.setFitWidth(275);
                wpc.setGraphic(iv);
                wpc.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> wpc.setEffect(shadow));
                wpc.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    if (!wpc.isSelected()) wpc.setEffect(null);
                });
                //i++;
            }
    }

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

    public void closeStage() {
        Platform.runLater(() -> {
            stage.setOnCloseRequest(event -> Platform.exit());
            stage.close();
            System.exit(0);
        });
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
