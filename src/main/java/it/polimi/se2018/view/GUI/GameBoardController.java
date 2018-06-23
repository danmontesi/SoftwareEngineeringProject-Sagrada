package it.polimi.se2018.view.GUI;

import it.polimi.se2018.commands.client_to_server_command.MoveChoiceDicePlacement;
import it.polimi.se2018.commands.client_to_server_command.MoveChoicePassTurn;
import it.polimi.se2018.commands.server_to_client_command.RefreshBoardCommand;
import it.polimi.se2018.view.GUI.Notifiers.GameBoardNotifier;
import it.polimi.se2018.view.GUI.Notifiers.GameBoardActions.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameBoardController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(GameBoardController.class.getName());

    private GUIView guiViewT;

    private RefreshBoardCommand modelRepresentation;

    private List<Circle> tcircles;
    private List<Circle> wcircles;
    private List<Circle> circles;
    private List<ImageView> pubocards;
    private List<ImageView> wpcards;
    private List<ImageView> ivw1;
    private List<ImageView> ivw2;
    private List<ImageView> ivw3;
    private List<List<ImageView>> ivw;
    private List<ToggleButton> tcbuttons;
    private List<ToggleButton> tbuttons;
    private List<ToggleButton> tbd;
    private List<ToggleButton> tbw0;
    private List<ToggleButton> tbr;
    private List<Stage> stages;
    private List<Label> users;
    private List<Label> usersTokens;
    private List<Label> tcTokens;

    @FXML
    private ImageView puboc1;
    @FXML
    private ImageView puboc2;
    @FXML
    private ImageView puboc3;
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
    private ImageView ivw100;
    @FXML
    private ImageView ivw110;
    @FXML
    private ImageView ivw120;
    @FXML
    private ImageView ivw130;
    @FXML
    private ImageView ivw140;
    @FXML
    private ImageView ivw101;
    @FXML
    private ImageView ivw111;
    @FXML
    private ImageView ivw121;
    @FXML
    private ImageView ivw131;
    @FXML
    private ImageView ivw141;
    @FXML
    private ImageView ivw102;
    @FXML
    private ImageView ivw112;
    @FXML
    private ImageView ivw122;
    @FXML
    private ImageView ivw132;
    @FXML
    private ImageView ivw142;
    @FXML
    private ImageView ivw103;
    @FXML
    private ImageView ivw113;
    @FXML
    private ImageView ivw123;
    @FXML
    private ImageView ivw133;
    @FXML
    private ImageView ivw143;
    @FXML
    private ImageView ivw200;
    @FXML
    private ImageView ivw210;
    @FXML
    private ImageView ivw220;
    @FXML
    private ImageView ivw230;
    @FXML
    private ImageView ivw240;
    @FXML
    private ImageView ivw201;
    @FXML
    private ImageView ivw211;
    @FXML
    private ImageView ivw221;
    @FXML
    private ImageView ivw231;
    @FXML
    private ImageView ivw241;
    @FXML
    private ImageView ivw202;
    @FXML
    private ImageView ivw212;
    @FXML
    private ImageView ivw222;
    @FXML
    private ImageView ivw232;
    @FXML
    private ImageView ivw242;
    @FXML
    private ImageView ivw203;
    @FXML
    private ImageView ivw213;
    @FXML
    private ImageView ivw223;
    @FXML
    private ImageView ivw233;
    @FXML
    private ImageView ivw243;
    @FXML
    private ImageView ivw300;
    @FXML
    private ImageView ivw310;
    @FXML
    private ImageView ivw320;
    @FXML
    private ImageView ivw330;
    @FXML
    private ImageView ivw340;
    @FXML
    private ImageView ivw301;
    @FXML
    private ImageView ivw311;
    @FXML
    private ImageView ivw321;
    @FXML
    private ImageView ivw331;
    @FXML
    private ImageView ivw341;
    @FXML
    private ImageView ivw302;
    @FXML
    private ImageView ivw312;
    @FXML
    private ImageView ivw322;
    @FXML
    private ImageView ivw332;
    @FXML
    private ImageView ivw342;
    @FXML
    private ImageView ivw303;
    @FXML
    private ImageView ivw313;
    @FXML
    private ImageView ivw323;
    @FXML
    private ImageView ivw333;
    @FXML
    private ImageView ivw343;

    @FXML
    private Label user1;
    @FXML
    private Label user2;
    @FXML
    private Label user3;
    @FXML
    private Label user0;
    @FXML
    private Label user0tokens;
    @FXML
    private Label user1tokens;
    @FXML
    private Label user2tokens;
    @FXML
    private Label user3tokens;
    @FXML
    private Label tc1tokens;
    @FXML
    private Label tc2tokens;
    @FXML
    private Label tc3tokens;

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

    @FXML
    private Circle tcircle1;
    @FXML
    private Circle tcircle2;
    @FXML
    private Circle tcircle3;
    @FXML
    private Circle wcircle0;
    @FXML
    private Circle wcircle1;
    @FXML
    private Circle wcircle2;
    @FXML
    private Circle wcircle3;

    @FXML
    private TextArea msgbox;

    private DropShadow shadow = new DropShadow();
    private DropShadow redShadow = new DropShadow();

    public GameBoardController() {
        pubocards = new ArrayList<>();
        wpcards = new ArrayList<>();
        tcbuttons = new ArrayList<>();
        tbuttons = new ArrayList<>();
        tbd = new ArrayList<>();
        tbw0 = new ArrayList<>();
        tbr = new ArrayList<>();
        ivw1 = new ArrayList<>();
        ivw2 = new ArrayList<>();
        ivw3 = new ArrayList<>();
        ivw = new ArrayList<>();
        stages = new ArrayList<>();
        users = new ArrayList<>();
        usersTokens = new ArrayList<>();
        tcTokens = new ArrayList<>();
        tcircles = new ArrayList<>();
        wcircles = new ArrayList<>();
        circles = new ArrayList<>();
    }

    public void initialize() {
        GameBoardNotifier.getInstance().addObserver(this);
        initCards();
        initToggleButtons();
        initImageViews();
        initLabels();
        initCircles();
        initButtons();
        initRoundTrack();
        moveDice();
        disableAllButtons(true);
        msgbox.appendText("waiting for other players to choose WPC...\n");
    }

    public void update(Observable o, Object arg) {
        if (arg == null) {
            showRanking();
        } else {
            GameBoardAction guiReply = (GameBoardAction)arg;
            GameBoardVisitor gameBoardVisitor = new GameBoardVisitor() {
                @Override
                public void visitGameBoardAction(GUIViewSetting guiViewSetting) {
                    guiViewT = guiViewSetting.getGuiView();
                }

                @Override
                public void visitGameBoardAction(RefreshBoard refreshBoard) {
                    modelRepresentation = refreshBoard.getModelRepresentation();
                    initPubocards();
                    initTcards();
                    initWpcards();
                    initPersonalWPC();
                    initPersonalPriOC();
                }

                @Override
                public void visitGameBoardAction(TurnStart turnStart) {
                    if (turnStart.getUsername() == null) {
                        disableAllButtons(false);
                        disableTB(tbd, false);
                        disableTB(tcbuttons, false);
                        disableTB(tbw0, false);
                        pass.setDisable(false);
                        msgbox.setText("It's your turn!\n");
                    } else {
                        disableAllButtons(true);
                        msgbox.setText("It's " + turnStart.getUsername() + "'s turn!\n");
                    }
                }

                @Override
                public void visitGameBoardAction(TurnUpdate turnUpdate) {
                    if (!turnUpdate.getMove()) {
                        disableTB(tbw0, false);
                        disableTB(tcbuttons, false);
                        pass.setDisable(false);
                    }
                    if (!turnUpdate.getTool()) {
                        disableTB(tbw0, false);
                        disableTB(tbd, false);
                        pass.setDisable(false);
                    }

                }

                @Override
                public void visitGameBoardAction(InvalidAction invalidAction) {
                    msgbox.appendText(invalidAction.getMessage() + "\n");
                    inputError(true);
                }

                @Override
                public void visitGameBoardAction(WPCUpdate wpcUpdate) {
                    setWpcards(wpcUpdate.getOtherWpcs());
                    setPersonalWPC(wpcUpdate.getMyWpc());
                }

                @Override
                public void visitGameBoardAction(TokensUpdate tokensUpdate) {
                    setTokens(tokensUpdate.getTcTokens(), tokensUpdate.getPlayersTokens(), tokensUpdate.getPersonalTokens());
                }

                @Override
                public void visitGameBoardAction(DraftPoolRoundTrackUpdate draftPoolRoundTrackUpdate) {
                    if (draftPoolRoundTrackUpdate.getType().equals("DP")) {
                        setDrafPool(draftPoolRoundTrackUpdate.getDice());
                    } else if (draftPoolRoundTrackUpdate.getType().equals("RT")) {
                        setRoundTrack(draftPoolRoundTrackUpdate.getDice());
                    }
                }
            };
            guiReply.acceptGameBoardVisitor(gameBoardVisitor);
        }
    }

    private void initCards(){
        pubocards.add(puboc1);
        pubocards.add(puboc2);
        pubocards.add(puboc3);

        wpcards.add(wpc1);
        wpcards.add(wpc2);
        wpcards.add(wpc3);

        tcbuttons.add(tcb1);
        tcbuttons.add(tcb2);
        tcbuttons.add(tcb3);
    }

    private void initToggleButtons() {
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

        for (ToggleButton tb : tbuttons) {
            tb.setStyle("-fx-base: transparent; -fx-focus-color: transparent; -fx-faint-focus-color: transparent");
            tb.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tb.setEffect(shadow));
            tb.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                if (!tb.isSelected()) tb.setEffect(null);
            });
        }
    }

    private void initImageViews() {
        ivw1.add(ivw100);
        ivw1.add(ivw110);
        ivw1.add(ivw120);
        ivw1.add(ivw130);
        ivw1.add(ivw140);
        ivw1.add(ivw101);
        ivw1.add(ivw111);
        ivw1.add(ivw121);
        ivw1.add(ivw131);
        ivw1.add(ivw141);
        ivw1.add(ivw102);
        ivw1.add(ivw112);
        ivw1.add(ivw122);
        ivw1.add(ivw132);
        ivw1.add(ivw142);
        ivw1.add(ivw103);
        ivw1.add(ivw113);
        ivw1.add(ivw123);
        ivw1.add(ivw133);
        ivw1.add(ivw143);

        ivw2.add(ivw200);
        ivw2.add(ivw210);
        ivw2.add(ivw220);
        ivw2.add(ivw230);
        ivw2.add(ivw240);
        ivw2.add(ivw201);
        ivw2.add(ivw211);
        ivw2.add(ivw221);
        ivw2.add(ivw231);
        ivw2.add(ivw241);
        ivw2.add(ivw202);
        ivw2.add(ivw212);
        ivw2.add(ivw222);
        ivw2.add(ivw232);
        ivw2.add(ivw242);
        ivw2.add(ivw203);
        ivw2.add(ivw213);
        ivw2.add(ivw223);
        ivw2.add(ivw233);
        ivw2.add(ivw243);

        ivw3.add(ivw300);
        ivw3.add(ivw310);
        ivw3.add(ivw320);
        ivw3.add(ivw330);
        ivw3.add(ivw340);
        ivw3.add(ivw301);
        ivw3.add(ivw311);
        ivw3.add(ivw321);
        ivw3.add(ivw331);
        ivw3.add(ivw341);
        ivw3.add(ivw302);
        ivw3.add(ivw312);
        ivw3.add(ivw322);
        ivw3.add(ivw332);
        ivw3.add(ivw342);
        ivw3.add(ivw303);
        ivw3.add(ivw313);
        ivw3.add(ivw323);
        ivw3.add(ivw333);
        ivw3.add(ivw343);

        ivw.add(ivw1);
        ivw.add(ivw2);
        ivw.add(ivw3);
    }

    private void initLabels() {
        users.add(user1);
        users.add(user2);
        users.add(user3);

        usersTokens.add(user1tokens);
        usersTokens.add(user2tokens);
        usersTokens.add(user3tokens);

        tcTokens.add(tc1tokens);
        tcTokens.add(tc2tokens);
        tcTokens.add(tc3tokens);
    }

    private void initCircles() {
        tcircles.add(tcircle1);
        tcircles.add(tcircle2);
        tcircles.add(tcircle3);

        wcircles.add(wcircle1);
        wcircles.add(wcircle2);
        wcircles.add(wcircle3);

        circles.addAll(tcircles);
        circles.addAll(wcircles);
        circles.add(wcircle0);

        for (Circle c : circles) {
            c.setVisible(false);
        }
    }

    private void initButtons() {
        pass.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
        quit.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
        pass.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> pass.setEffect(shadow));
        pass.addEventHandler(MouseEvent.MOUSE_EXITED, e -> pass.setEffect(null));
        quit.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> quit.setEffect(shadow));
        quit.addEventHandler(MouseEvent.MOUSE_EXITED, e -> quit.setEffect(null));
    }

    private void initRoundTrack() {
            rt.setImage(new Image("/client/images/RoundTrack.png"));
    }

    private void initPubocards() {
        ArrayList<String> publicOC = modelRepresentation.getPublicObjectiveCards();
        ArrayList<String> publicOCDesc = modelRepresentation.getPublicObjectiveDescription();
        Platform.runLater(() -> {
            for (int i=0; i<publicOC.size(); i++) {
            String img = publicOC.get(i);
                String path = "/client/OC/" + img + ".jpg";
                Image image = new Image(path);
                pubocards.get(i).setImage(image);
                Tooltip t = new Tooltip(publicOCDesc.get(i));
                Tooltip.install(pubocards.get(i), t);
                t.setStyle("-fx-font-size: 15px");
            }
        });
    }

    private void initTcards() {
        ArrayList<String> tCards = modelRepresentation.getToolCards();
        ArrayList<String> tCardsDesc = modelRepresentation.getToolCardDescription();
        Platform.runLater(() -> {
            for (int i=0; i<tCards.size(); i++) {
                String img = tCards.get(i);
                String path = "/client/TC/" + img + ".jpg";
                Image image = new Image(path);
                ImageView iv = new ImageView(image);
                iv.setFitHeight(190);
                iv.setFitWidth(140);
                tcbuttons.get(i).setGraphic(iv);
                Tooltip t = new Tooltip(tCardsDesc.get(i));
                Tooltip.install(tcbuttons.get(i), t);
                t.setStyle("-fx-font-size: 15px");
                tcTokens.get(i).setText(modelRepresentation.getTokensToolCards().get(i).toString());
                tcircles.get(i).setVisible(true);
            }
            for (ToggleButton tc : tcbuttons) {
                tc.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tc.setEffect(shadow));
                tc.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    if (!tc.isSelected()) tc.setEffect(null);
                });
            }
        });
    }

    private void initWpcards() {
        Platform.runLater(() -> {
            for (int i=0; i<modelRepresentation.getOtherPlayersWpcs().size(); i++) {
                String img = modelRepresentation.getOtherPlayersWpcs().get(i).get(0).replace("_", " ");
                String path = "/client/WPC/" + img + ".jpg";
                Image image = new Image(path);
                wpcards.get(i).setImage(image);
                users.get(i).setText(modelRepresentation.getOtherPlayersUsernames().get(i));
                usersTokens.get(i).setText(modelRepresentation.getOtherPlayersTokens().get(i).toString());
                wcircles.get(i).setVisible(true);
            }
            for (int j=modelRepresentation.getOtherPlayersWpcs().size(); j<3; j++) {
                wpcards.get(j).setVisible(false);
                users.get(j).setVisible(false);
                usersTokens.get(j).setVisible(false);
            }
        });
    }

    private void initPersonalWPC() {
        Platform.runLater(() -> {
            String img = modelRepresentation.getPersonalWpc().get(0).replace("_", " ");
            String path = "/client/WPC/" + img + ".jpg";
            Image image = new Image(path);
            wpc0.setImage(image);
            user0.setText(modelRepresentation.getUsername());
            user0tokens.setText(modelRepresentation.getPersonalTokens().toString());
            wcircle0.setVisible(true);
        });
    }

    private void initPersonalPriOC() {
        Platform.runLater(() -> {
            String img = modelRepresentation.getPrivateObjectiveCard();
            String path = "/client/OC/" + img + ".jpg";
            Image image = new Image(path);
            prioc0.setImage(image);
            Tooltip t = new Tooltip(modelRepresentation.getPrivateObjectiveCardDescription());
            Tooltip.install(prioc0, t);
            t.setStyle("-fx-font-size: 15px");
        });
    }

    private void moveDice() {
        for (int i=0; i<20; i++) {
            int h = i;
            tbw0.get(i).setOnAction(event -> {
                for (int j=0; j<9; j++) {
                    if (tbd.get(j).isSelected()) {
                        tbw0.get(h).setGraphic(tbd.get(j).getGraphic());
                        tbd.get(j).setSelected(false);
                        tbw0.get(h).setSelected(false);
                        inputError(false);
                        notifyMove((h+1)%4, (h+1)%5, j);
                    }
                }
            });
        }
    }

    private void setDrafPool(ArrayList<String> dice) {
        for (int i=0; i<dice.size(); i++) {
            String img = dice.get(i);
            if (img.contains("_")) {
                String path = "/client/Dice/" + img + ".jpg";
                Image image = new Image(path);
                ImageView iv = new ImageView(image);
                iv.setFitWidth(40);
                iv.setFitHeight(40);
                tbd.get(i).setGraphic(iv);
            } else {
                tbd.get(i).setDisable(true);
            }
        }
        for (int j=dice.size(); j<9; j++) {
            tbd.get(j).setDisable(true);
        }
    }

    private void setRoundTrack(ArrayList<String> dice) {
        for (int i=0; i<10; i++) {
            String img = dice.get(i);
            if (img.contains("_")) {
                String path = "/client/Dice/" + img + ".jpg";
                Image image = new Image(path);
                ImageView iv = new ImageView(image);
                iv.setFitWidth(40);
                iv.setFitHeight(40);
                tbr.get(i).setGraphic(iv);
            } else {
                tbr.get(i).setDisable(true);
            }
        }
    }

    private void setTokens(ArrayList<Integer> tcTok, ArrayList<Integer> playersTok, Integer personalTok) {
        for (int i=0; i<tcTok.size(); i++) {
            tcTokens.get(i).setText(tcTok.get(i).toString());
        }
        for (int i=0; i<playersTok.size(); i++) {
            usersTokens.get(i).setText(playersTok.get(i).toString());
        }
        user0tokens.setText(personalTok.toString());
    }

    private void setWpcards(ArrayList<ArrayList<String>> wpcards)  {
        Platform.runLater(() -> {
            for (int i=0; i<wpcards.size(); i++) {
                for (int j=0; j<20; j++) {
                    String img = wpcards.get(i).get(j+1);
                    if (img.contains("_")) {
                        String path = "/client/Dice/" + img + ".jpg";
                        Image image = new Image(path);
                        ivw.get(i).get(j).setImage(image);
                    }
                }
            }
        });
    }

    private void setPersonalWPC(ArrayList<String> wpc) {
        Platform.runLater(() -> {
            for (int i=0; i<20; i++) {
                String img = wpc.get(i+1);
                if (img.contains("_")) {
                    String path = "/client/Dice/" + img + ".jpg";
                    Image image = new Image(path);
                    ImageView iv = new ImageView(image);
                    iv.setFitWidth(43);
                    iv.setFitHeight(43);
                    tbw0.get(i).setGraphic(iv);
                }
            }
        });
    }

    private void closeStage() {
        Stage stage = (Stage)pass.getScene().getWindow();
        stage.close();
    }

    private void showRanking() {
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/rankingpane.fxml"));
                Parent root = fxmlLoader.load();
                Stage gameBoardStage = new Stage();
                gameBoardStage.setScene(new Scene(root));
                gameBoardStage.show();
                closeStage();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch ranking pane", e);
            }
        });
    }

    private void showClientStarter() {
        Platform.runLater(() ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientstarter.fxml"));
                Parent root = fxmlLoader.load();
                Stage gameBoardStage = new Stage();
                gameBoardStage.setScene(new Scene(root));
                gameBoardStage.show();
                closeStage();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch ranking pane", e);
            }
        });
    }

    @FXML
    public void passTurn() {
        guiViewT.notify(new MoveChoicePassTurn(user0.getText()));
    }

    @FXML
    public void quit() {
        showClientStarter();
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

    private void disableTB(List<ToggleButton> l, boolean b) {
        if (b) {
            for (ToggleButton t : l) {
                t.setDisable(true);
            }
        } else {
            for (ToggleButton t : l) {
                t.setDisable(false);
            }
        }
    }

    private void disableAllButtons(boolean b) {
        if (b) {
            for (ToggleButton t : tcbuttons) {
                t.setDisable(true);
            }
            for (ToggleButton t : tbuttons) {
                t.setDisable(true);
            }
            pass.setDisable(true);
        } else {
            for (ToggleButton t : tcbuttons) {
                t.setDisable(false);
            }
            for (ToggleButton t : tbuttons) {
                t.setDisable(false);
            }
            pass.setDisable(false);
        }
    }

    private void notifyMove(Integer r, Integer c, Integer d) {
        guiViewT.notify(new MoveChoiceDicePlacement(r, c, d));
        disableAllButtons(true);
    }

    private void inputError(boolean b) {
        redShadow.setColor(new Color(0.7, 0,0,1));
        if (b) {
            msgbox.setEffect(redShadow);
        } else {
            msgbox.setEffect(null);
        }
    }

    private void messages() {
        Platform.runLater(() ->  {
            try {
                for (int i=0; i<5; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProvaJavaFX/resources/message.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage message1Stage = new Stage();
                    message1Stage.setScene(new Scene(root));
                    message1Stage.show();
                    stages.add(message1Stage);
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch message", e);
            }
        });
        Platform.runLater(() ->  {
            try {
                for (int i=stages.size()-1; i>-1; i--) {
                    stages.get(i).close();
                    stages.remove(i);
                }
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProvaJavaFX/resources/message.fxml"));
                Parent root = fxmlLoader.load();
                Stage gameBoardStage = new Stage();
                gameBoardStage.setScene(new Scene(root));
                gameBoardStage.show();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch message", e);
            }
        });
    }
}

