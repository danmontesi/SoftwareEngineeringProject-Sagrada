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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    private List<Circle> tcCircles;
    private List<Circle> wpcCircles;
    private List<Circle> circles;
    private List<ImageView> pubOCards;
    private List<ImageView> wpCards;
    private List<ToggleButton> tcButtons;
    private List<Stage> stages;
    private List<Label> usernames;
    private List<Label> usersTokens;
    private List<Label> tcTokens;
    private List<GridPane> otherWPCsDice;
    private List<Parent> parents;

    @FXML
    private GridPane personalWPCDice;
    @FXML
    private GridPane wpc1dice;
    @FXML
    private GridPane wpc2dice;
    @FXML
    private GridPane wpc3dice;
    @FXML
    private HBox draftPoolDice;
    @FXML
    private VBox roundTrackDice;

    @FXML
    private ImageView pubOC1;
    @FXML
    private ImageView pubOC2;
    @FXML
    private ImageView pubOC3;
    @FXML
    private ImageView wpc1;
    @FXML
    private ImageView wpc2;
    @FXML
    private ImageView wpc3;
    @FXML
    private ImageView wpc0;
    @FXML
    private ImageView priOC;
    @FXML
    private ImageView roundTrack;

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
        pubOCards = new ArrayList<>();
        wpCards = new ArrayList<>();
        tcButtons = new ArrayList<>();
        stages = new ArrayList<>();
        usernames = new ArrayList<>();
        usersTokens = new ArrayList<>();
        tcTokens = new ArrayList<>();
        tcCircles = new ArrayList<>();
        wpcCircles = new ArrayList<>();
        circles = new ArrayList<>();
        otherWPCsDice = new ArrayList<>();
        parents = new ArrayList<>();
    }

    public void initialize() {
        GameBoardNotifier.getInstance().addObserver(this);
        initCards();
        initLabels();
        initCircles();
        initButtons();
        initRoundTrack();
        initParents();
        disableAllButtons(true);
        msgbox.appendText("waiting for other players to choose their Window Pattern Card...\n");
        //prova();
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
                    setDrafPool(modelRepresentation.getDraftpool());
                    setRoundTrack(modelRepresentation.getRoundTrack());
                    initBStyle();
                    moveDice();
                }

                @Override
                public void visitGameBoardAction(TurnStart turnStart) {
                    if (turnStart.getUsername() == null) {
                        disableTCB(false);
                        disableTB(draftPoolDice, false);
                        disableTB(personalWPCDice, false);
                        pass.setDisable(false);
                        msgbox.setText("It's your turn!\n");
                    } else {
                        //disableAllButtons(true);
                        msgbox.setText("It's " + turnStart.getUsername() + "'s turn!\n");
                    }
                }

                @Override
                public void visitGameBoardAction(TurnUpdate turnUpdate) {
                    if (!turnUpdate.getMove()) {
                        disableTB(personalWPCDice, false);
                        disableTCB(false);
                        pass.setDisable(false);
                    }
                    if (!turnUpdate.getTool()) {
                        disableTB(personalWPCDice, false);
                        disableTB(draftPoolDice, false);
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
                    setWpCards(wpcUpdate.getOtherWpcs());
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
        pubOCards.add(pubOC1);
        pubOCards.add(pubOC2);
        pubOCards.add(pubOC3);

        wpCards.add(wpc1);
        wpCards.add(wpc2);
        wpCards.add(wpc3);

        tcButtons.add(tcb1);
        tcButtons.add(tcb2);
        tcButtons.add(tcb3);
    }

    private void initLabels() {
        usernames.add(user1);
        usernames.add(user2);
        usernames.add(user3);

        usersTokens.add(user1tokens);
        usersTokens.add(user2tokens);
        usersTokens.add(user3tokens);

        tcTokens.add(tc1tokens);
        tcTokens.add(tc2tokens);
        tcTokens.add(tc3tokens);
    }

    private void initCircles() {
        tcCircles.add(tcircle1);
        tcCircles.add(tcircle2);
        tcCircles.add(tcircle3);

        wpcCircles.add(wcircle1);
        wpcCircles.add(wcircle2);
        wpcCircles.add(wcircle3);

        circles.addAll(tcCircles);
        circles.addAll(wpcCircles);
        circles.add(wcircle0);

        for (Circle c : circles) {
            c.setVisible(false);
        }
    }

    private void initButtons() {
        Platform.runLater(() -> {
            for (int i=0; i<10; i++){
                ToggleButton tb = new ToggleButton();
                roundTrackDice.getChildren().add(tb);
            }
            for (int i=0; i<9; i++){
                ToggleButton tb = new ToggleButton();
                draftPoolDice.getChildren().add(tb);
            }
            int k=-1;
            for (int i=0; i<20; i++) {
                int h=i%5;
                if (h==0) k++;
                ToggleButton tb = new ToggleButton();
                ImageView iv = new ImageView();
                iv.setFitWidth(43);
                iv.setFitHeight(43);
                tb.setGraphic(iv);
                personalWPCDice.add(tb, h, k);
            }
        });
    }

    private void initBStyle() {
        pass.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
        quit.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
        pass.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> pass.setEffect(shadow));
        pass.addEventHandler(MouseEvent.MOUSE_EXITED, e -> pass.setEffect(null));
        quit.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> quit.setEffect(shadow));
        quit.addEventHandler(MouseEvent.MOUSE_EXITED, e -> quit.setEffect(null));

        for (ToggleButton tb : tcButtons) {
            tb.setStyle("-fx-base: transparent; -fx-focus-color: transparent; -fx-faint-focus-color: transparent");
            tb.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tb.setEffect(shadow));
            tb.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                if (!tb.isSelected()) tb.setEffect(null);
            });
        }

        for (int j=0; j<parents.size(); j++) {
            for (int i = 0; i<parents.get(j).getChildrenUnmodifiable().size(); i++) {
                int h = i;
                int k=j;
                parents.get(j).getChildrenUnmodifiable().get(i).setStyle("-fx-base: transparent; -fx-focus-color: transparent; -fx-faint-focus-color: transparent");
                parents.get(j).getChildrenUnmodifiable().get(i).addEventHandler(MouseEvent.MOUSE_ENTERED, e -> parents.get(k).getChildrenUnmodifiable().get(h).setEffect(shadow));
                parents.get(j).getChildrenUnmodifiable().get(i).addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    if (!((ToggleButton) parents.get(k).getChildrenUnmodifiable().get(h)).isSelected())
                        parents.get(k).getChildrenUnmodifiable().get(h).setEffect(null);
                });
            }
        }
    }

    private void initParents() {
        otherWPCsDice.add(wpc1dice);
        otherWPCsDice.add(wpc2dice);
        otherWPCsDice.add(wpc3dice);

        parents.add(draftPoolDice);
        parents.add(roundTrackDice);
        parents.add(personalWPCDice);
    }

    private void initRoundTrack() {
            roundTrack.setImage(new Image("/client/images/RoundTrack.png"));
    }

    private void initPubocards() {
        ArrayList<String> publicOC = modelRepresentation.getPublicObjectiveCards();
        ArrayList<String> publicOCDesc = modelRepresentation.getPublicObjectiveDescription();
        Platform.runLater(() -> {
            for (int i=0; i<publicOC.size(); i++) {
            String img = publicOC.get(i);
                String path = "/client/OC/" + img + ".jpg";
                Image image = new Image(path);
                pubOCards.get(i).setImage(image);
                Tooltip t = new Tooltip(publicOCDesc.get(i));
                Tooltip.install(pubOCards.get(i), t);
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
                tcButtons.get(i).setGraphic(iv);
                Tooltip t = new Tooltip(tCardsDesc.get(i));
                Tooltip.install(tcButtons.get(i), t);
                t.setStyle("-fx-font-size: 15px");
                tcTokens.get(i).setText(modelRepresentation.getTokensToolCards().get(i).toString());
                tcCircles.get(i).setVisible(true);
            }
            for (ToggleButton tc : tcButtons) {
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
                wpCards.get(i).setImage(image);
                usernames.get(i).setText(modelRepresentation.getOtherPlayersUsernames().get(i));
                usersTokens.get(i).setText(modelRepresentation.getOtherPlayersTokens().get(i).toString());
                wpcCircles.get(i).setVisible(true);
            }
            for (int j=modelRepresentation.getOtherPlayersWpcs().size(); j<3; j++) {
                wpCards.get(j).setVisible(false);
                usernames.get(j).setVisible(false);
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
            priOC.setImage(image);
            Tooltip t = new Tooltip(modelRepresentation.getPrivateObjectiveCardDescription());
            Tooltip.install(priOC, t);
            t.setStyle("-fx-font-size: 15px");
        });
    }

    private void moveDice() {
        Platform.runLater(() -> {
            for (int i=0; i<20; i++) {
                int h = i;
                System.out.println(h+". col: "+personalWPCDice.getColumnIndex(personalWPCDice.getChildren().get(h)));
                System.out.println(h+". row: "+personalWPCDice.getRowIndex(personalWPCDice.getChildren().get(h)));
                System.out.println("-----------");
                ((ToggleButton)personalWPCDice.getChildren().get(i)).setOnAction(event -> {
                    for (int j=0; j<9; j++) {
                        if (((ToggleButton)draftPoolDice.getChildren().get(j)).isSelected()) {
                            ((ToggleButton)personalWPCDice.getChildren().get(h)).setGraphic(((ToggleButton)draftPoolDice.getChildren().get(j)).getGraphic());
                            ((ToggleButton)draftPoolDice.getChildren().get(j)).setSelected(false);
                            ((ToggleButton)personalWPCDice.getChildren().get(h)).setSelected(false);
                            inputError(false);
                            notifyMove(personalWPCDice.getRowIndex(personalWPCDice.getChildren().get(h)), personalWPCDice.getColumnIndex(personalWPCDice.getChildren().get(h)), j);
                        }
                    }
                });
            }
        });
    }

    private void setDrafPool(ArrayList<String> dice) {
        Platform.runLater(() -> {
            for (int i=0; i<dice.size(); i++) {
                String img = dice.get(i);
                ImageView iv = new ImageView();
                if (img.contains("_")) {
                    String path = "/client/dice/" + img + ".jpg";
                    Image image = new Image(path);
                    iv.setImage(image);
                    iv.setFitWidth(43);
                    iv.setFitHeight(43);
                    ((ToggleButton)draftPoolDice.getChildren().get(i)).setGraphic(iv);
                } else {
                    draftPoolDice.getChildren().get(i).setDisable(true);
                }
            }
            for (int i=dice.size(); i<9; i++) {
                draftPoolDice.getChildren().get(i).setDisable(true);
            }
        });
    }

    private void setRoundTrack(ArrayList<String> dice) {
        Platform.runLater(() -> {
            for (int i=0; i<10; i++) {
                String img = dice.get(i);
                ImageView iv = new ImageView();
                if (img.contains("_")) {
                    String path = "/client/dice/" + img + ".jpg";
                    Image image = new Image(path);
                    iv.setImage(image);
                    iv.setFitWidth(43);
                    iv.setFitHeight(43);
                    ((ToggleButton)roundTrackDice.getChildren().get(i)).setGraphic(iv);
                } else {
                    roundTrackDice.getChildren().get(i).setDisable(true);
                }
            }
        });
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

    private void setWpCards(ArrayList<ArrayList<String>> wpcards)  {
        Platform.runLater(() -> {
            for (int i=0; i<wpcards.size(); i++) {
                int k=-1;
                for (int j=0; j<20; j++) {
                    int h=j%5;
                    if (h==0) k++;
                    String img = wpcards.get(i).get(j+1);
                    ImageView iv = new ImageView();
                    if (img.contains("_")) {
                        String path = "/client/dice/" + img + ".jpg";
                        Image image = new Image(path);
                        iv.setImage(image);
                        iv.setFitHeight(29);
                        iv.setFitWidth(29);
                        otherWPCsDice.get(i).add(iv, h, k);
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
                    String path = "/client/dice/" + img + ".jpg";
                    Image image = new Image(path);
                    ImageView iv = new ImageView(image);
                    iv.setFitWidth(43);
                    iv.setFitHeight(43);
                    ((ToggleButton)personalWPCDice.getChildren().get(i)).setGraphic(iv);
                } else {
                    ImageView iv = new ImageView();
                    ((ToggleButton)personalWPCDice.getChildren().get(i)).setGraphic(iv);
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

    private void disableTCB(boolean b) {
        if (b) {
            for (ToggleButton t : tcButtons) {
                t.setDisable(true);
            }
        } else {
            for (ToggleButton t : tcButtons) {
                t.setDisable(false);
            }
            pass.setDisable(false);
        }
    }

    private void disableTB(Parent p, boolean b) {
        if (b) {
            for (int i=0; i<p.getChildrenUnmodifiable().size(); i++) {
                p.getChildrenUnmodifiable().get(i).setDisable(true);
            }
        } else {
            for (int i=0; i<p.getChildrenUnmodifiable().size(); i++) {
                p.getChildrenUnmodifiable().get(i).setDisable(false);
            }
        }
    }

    private void disableAllButtons(boolean b) {
        disableTCB(b);
        disableTB(draftPoolDice, b);
        disableTB(roundTrackDice, b);
        disableTB(personalWPCDice, b);
        pass.setDisable(true);
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

    private void prova() {
        GridPane gridPane = new GridPane();
        int j=-1;
        for (int i=0; i<20; i++) {
            int h;
            h=i%5;
            if (h==0) j++;
            Label label = new Label();
            label.setText(""+i);
            gridPane.add(label, h, j);
        }

        for (javafx.scene.Node l : gridPane.getChildren()) {
            System.out.println("col: "+gridPane.getColumnIndex(l));
            System.out.println("row: "+gridPane.getRowIndex(l));
            System.out.println("-------");
        }
    }
}