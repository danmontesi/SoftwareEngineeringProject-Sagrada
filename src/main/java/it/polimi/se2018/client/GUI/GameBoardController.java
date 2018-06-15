package it.polimi.se2018.client.GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class GameBoardController {

    private List<Circle> tokens;
    private List<ImageView> pubocards;
    private List<ImageView> tcards;
    private List<ImageView> wpcards;
    private List<ToggleButton> tcbuttons;
    private List<ToggleButton> tbuttons;
    private List<ToggleButton> tbd;
    private List<ToggleButton> tbw0;
    private List<ToggleButton> tbr;

    @FXML
    private AnchorPane cards;
    @FXML
    private AnchorPane usersbox;
    @FXML
    private AnchorPane dpbox;
    @FXML
    private AnchorPane yourbox;
    @FXML
    private AnchorPane rtbox;
    @FXML
    private GridPane gwpc1;
    @FXML
    private GridPane gwpc2;
    @FXML
    private GridPane gwpc3;
    @FXML
    private GridPane gwpc0;
    @FXML
    private HBox dpd;
    @FXML
    private VBox rtd;
    @FXML
    private Pane root;

    @FXML
    private ImageView puboc1;
    @FXML
    private ImageView puboc2;
    @FXML
    private ImageView puboc3;
    @FXML
    private ImageView tc1;
    @FXML
    private ImageView tc2;
    @FXML
    private ImageView tc3;
    @FXML
    private ImageView wpc1;
    @FXML
    private ImageView wpc2;
    @FXML
    private ImageView wpc3;
    @FXML
    private ImageView wpc0;
    @FXML
    private ImageView prioc0;
    @FXML
    private ImageView rt;

    @FXML
    private Label user1;
    @FXML
    private Label user2;
    @FXML
    private Label user3;
    @FXML
    private Label user0;
    @FXML
    private Label dp;

    @FXML
    private Button pass;
    @FXML
    private Button quit;
    @FXML
    private ToggleButton tcb1;
    @FXML
    private ToggleButton tcb2;
    @FXML
    private ToggleButton tcb3;
    @FXML
    private ToggleButton tbd1;
    @FXML
    private ToggleButton tbd2;
    @FXML
    private ToggleButton tbd3;
    @FXML
    private ToggleButton tbd4;
    @FXML
    private ToggleButton tbd5;
    @FXML
    private ToggleButton tbd6;
    @FXML
    private ToggleButton tbd7;
    @FXML
    private ToggleButton tbd8;
    @FXML
    private ToggleButton tbd9;
    @FXML
    private ToggleButton tbw000;
    @FXML
    private ToggleButton tbw010;
    @FXML
    private ToggleButton tbw020;
    @FXML
    private ToggleButton tbw030;
    @FXML
    private ToggleButton tbw040;
    @FXML
    private ToggleButton tbw001;
    @FXML
    private ToggleButton tbw011;
    @FXML
    private ToggleButton tbw021;
    @FXML
    private ToggleButton tbw031;
    @FXML
    private ToggleButton tbw041;
    @FXML
    private ToggleButton tbw002;
    @FXML
    private ToggleButton tbw012;
    @FXML
    private ToggleButton tbw022;
    @FXML
    private ToggleButton tbw032;
    @FXML
    private ToggleButton tbw042;
    @FXML
    private ToggleButton tbw003;
    @FXML
    private ToggleButton tbw013;
    @FXML
    private ToggleButton tbw023;
    @FXML
    private ToggleButton tbw033;
    @FXML
    private ToggleButton tbw043;
    @FXML
    private ToggleButton tbr1;
    @FXML
    private ToggleButton tbr2;
    @FXML
    private ToggleButton tbr3;
    @FXML
    private ToggleButton tbr4;
    @FXML
    private ToggleButton tbr5;
    @FXML
    private ToggleButton tbr6;
    @FXML
    private ToggleButton tbr7;
    @FXML
    private ToggleButton tbr8;
    @FXML
    private ToggleButton tbr9;
    @FXML
    private ToggleButton tbr10;

    private DropShadow shadow = new DropShadow();

    public GameBoardController() {
        pubocards = new ArrayList<>();
        tcards = new ArrayList<>();
        wpcards = new ArrayList<>();
        tcbuttons = new ArrayList<>();
        tbuttons = new ArrayList<>();
        tbd = new ArrayList<>();
        tbw0 = new ArrayList<>();
        tbr = new ArrayList<>();
    }

    public void initialize() {
        setCards();
        setButtons();
        initButtons();
        initRoundTrack();
        initPubocards();
        initTcards();
        initWpcards();
        initMyWPC();
        initMyPriOC();
        initDice();
        moveDice();
    }

    private void setCards(){
        pubocards.add(puboc1);
        pubocards.add(puboc2);
        pubocards.add(puboc3);

        tcards.add(tc1);
        tcards.add(tc2);
        tcards.add(tc3);

        wpcards.add(wpc1);
        wpcards.add(wpc2);
        wpcards.add(wpc3);

        tcbuttons.add(tcb1);
        tcbuttons.add(tcb2);
        tcbuttons.add(tcb3);
    }

    private void setButtons() {
        tbd.add(tbd1);
        tbd.add(tbd2);
        tbd.add(tbd3);
        tbd.add(tbd4);
        tbd.add(tbd5);
        tbd.add(tbd6);
        tbd.add(tbd7);
        tbd.add(tbd8);
        tbd.add(tbd9);

        tbw0.add(tbw000);
        tbw0.add(tbw010);
        tbw0.add(tbw020);
        tbw0.add(tbw030);
        tbw0.add(tbw040);
        tbw0.add(tbw001);
        tbw0.add(tbw011);
        tbw0.add(tbw021);
        tbw0.add(tbw031);
        tbw0.add(tbw041);
        tbw0.add(tbw002);
        tbw0.add(tbw012);
        tbw0.add(tbw022);
        tbw0.add(tbw032);
        tbw0.add(tbw042);
        tbw0.add(tbw003);
        tbw0.add(tbw013);
        tbw0.add(tbw023);
        tbw0.add(tbw033);
        tbw0.add(tbw043);

        tbr.add(tbr1);
        tbr.add(tbr2);
        tbr.add(tbr3);
        tbr.add(tbr4);
        tbr.add(tbr5);
        tbr.add(tbr6);
        tbr.add(tbr7);
        tbr.add(tbr8);
        tbr.add(tbr9);
        tbr.add(tbr10);

        tbuttons.addAll(tbd);
        tbuttons.addAll(tbw0);
        tbuttons.addAll(tbr);
    }

    private void initButtons() {
        pass.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                pass.setEffect(shadow);
            }
        });
        pass.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                pass.setEffect(null);
            }
        });
        quit.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                quit.setEffect(shadow);
            }
        });
        quit.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                quit.setEffect(null);
            }
        });
    }

    private void initRoundTrack() {
            String path = "/client/images/RoundTrack.png";
            Image image = new Image(path);
            rt.setImage(image);
    }

    private void initPubocards() {
        String img = "row_color_variety";
        String path = "/client/OC/" + img + ".jpg";
        Image image = new Image(path);

        for (ImageView puboc : pubocards) {
            puboc.setImage(image);
            Tooltip t = new Tooltip("Description: ...");
            Tooltip.install(puboc, t);
            t.setStyle("-fx-font-size: 15px");
        }
    }

    private void initTcards() {
        String img = "circular_cutter";
        String path = "/client/TC/" + img + ".jpg";
        Image image = new Image(path);

        for (ToggleButton tc : tcbuttons) {
            ImageView iv = new ImageView(image);
            iv.setFitHeight(190);
            iv.setFitWidth(140);
            tc.setGraphic(iv);
            Tooltip t = new Tooltip("Description: ...");
            Tooltip.install(tc, t);
            t.setStyle("-fx-font-size: 15px");
            tc.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    tc.setEffect(shadow);
                }
            });
            tc.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (!tc.isSelected()) tc.setEffect(null);
                }
            });
        }
    }

    private void initWpcards() {
        String img = "virtus";
        String path = "/client/WPC/" + img + ".jpg";
        Image image = new Image(path);

        for (ImageView wpc : wpcards) {
            wpc.setImage(image);
        }
    }

    private void initMyWPC() {
        String img = "virtus";
        String path = "/client/WPC/" + img + ".jpg";
        Image image = new Image(path);
        wpc0.setImage(image);
    }

    private void initMyPriOC() {
        String img = "shades_of_blue";
        String path = "/client/OC/" + img + ".jpg";
        Image image = new Image(path);
        prioc0.setImage(image);
        Tooltip t = new Tooltip("Description: ...");
        Tooltip.install(prioc0, t);
        t.setStyle("-fx-font-size: 15px");
    }

    public void initDice() {
        Image image = new Image("/client/dice/violet1.jpg");
        for (ToggleButton tb : tbd) {
            ImageView iv = new ImageView(image);
            iv.setFitWidth(40);
            iv.setFitHeight(40);
            tb.setGraphic(iv);
        }
        for (ToggleButton tb : tbuttons) {
            tb.setStyle("-fx-base: transparent; -fx-focus-color: transparent");
            tb.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    tb.setEffect(shadow);
                }
            });
            tb.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (!tb.isSelected()) tb.setEffect(null);
                }
            });
        }
    }

    public void moveDice() {
        for (ToggleButton tb1 : tbw0) {
            tb1.setOnAction(event -> {
                for (ToggleButton tb2 : tbd) {
                    if (tb2.isSelected()) {
                        tb1.setGraphic(tb2.getGraphic());
                        tb2.setSelected(false);
                        tb1.setSelected(false);
                    }
                }
            });
        }
    }

    @FXML
    public void passTurn() {

    }

    @FXML
    public void quit() {

    }

    @FXML
    public void tc1Action() {

    }

    @FXML
    public void tc2Action() {

    }

    @FXML
    public void tc3Action() {

    }
}

