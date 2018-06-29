package it.polimi.se2018.view.gui;

import it.polimi.se2018.commands.client_to_server_command.*;
import it.polimi.se2018.commands.client_to_server_command.new_tool_commands.*;
import it.polimi.se2018.commands.server_to_client_command.RefreshBoardCommand;
import it.polimi.se2018.view.gui.Notifiers.GameBoardNotifier;
import it.polimi.se2018.view.gui.Notifiers.GameBoardActions.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.omg.CORBA.PERSIST_STORE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameBoardController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(WPCChoiceController.class.getName());

    private GUIView guiViewT;
    private RefreshBoardCommand modelRepresentation;

    private List<Circle> tcCircles;
    private List<Circle> wpcCircles;
    private List<Circle> circles;
    private List<ImageView> pubOCards;
    private List<ImageView> wpCards;
    private List<ToggleButton> tcButtons;
    private List<Label> usernames;
    private List<Label> usersTokens;
    private List<Label> tcTokens;
    private List<GridPane> otherWPCsDice;
    private List<Pane> parents;
    private List<Button> buttons;

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
    private ToggleButton toolCard1;
    @FXML
    private ToggleButton toolCard2;
    @FXML
    private ToggleButton toolCard3;
    @FXML
    private Button pass;
    @FXML
    private Button quit;
    @FXML
    private Button yes;
    @FXML
    private Button no;

    @FXML
    private ChoiceBox<Integer> valueChoice;

    @FXML
    private Circle tCircle1;
    @FXML
    private Circle tCircle2;
    @FXML
    private Circle tCircle3;
    @FXML
    private Circle wCircle0;
    @FXML
    private Circle wCircle1;
    @FXML
    private Circle wCircle2;
    @FXML
    private Circle wCircle3;

    @FXML
    private TextArea msgBox;

    private DropShadow shadow = new DropShadow();
    private DropShadow redShadow = new DropShadow();
    private Glow bright = new Glow(0.8);

    public GameBoardController() {
        pubOCards = new ArrayList<>();
        wpCards = new ArrayList<>();
        tcButtons = new ArrayList<>();
        usernames = new ArrayList<>();
        usersTokens = new ArrayList<>();
        tcTokens = new ArrayList<>();
        tcCircles = new ArrayList<>();
        wpcCircles = new ArrayList<>();
        circles = new ArrayList<>();
        otherWPCsDice = new ArrayList<>();
        parents = new ArrayList<>();
        buttons = new ArrayList<>();
    }

    public void initialize() {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.addObserver(this);
        initCards();
        initLabels();
        initCircles();
        initButtons();
        initRoundTrack();
        initParents();
        initChoiceBox();
        //disableAllButtons();
        msgBox.appendText("Waiting for other players to choose their Window Pattern Card...\n");
        gameBoardNotifier.setOpen();
    }

    private void initCards(){
        pubOCards.add(pubOC1);
        pubOCards.add(pubOC2);
        pubOCards.add(pubOC3);

        wpCards.add(wpc1);
        wpCards.add(wpc2);
        wpCards.add(wpc3);

        tcButtons.add(toolCard1);
        tcButtons.add(toolCard2);
        tcButtons.add(toolCard3);
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
        tcCircles.add(tCircle1);
        tcCircles.add(tCircle2);
        tcCircles.add(tCircle3);

        wpcCircles.add(wCircle1);
        wpcCircles.add(wCircle2);
        wpcCircles.add(wCircle3);

        circles.addAll(tcCircles);
        circles.addAll(wpcCircles);
        circles.add(wCircle0);

        for (Circle c : circles) {
            c.setVisible(false);
        }
    }

    private void initButtons() {
        buttons.add(pass);
        buttons.add(quit);
        buttons.add(yes);
        buttons.add(no);

        yes.setVisible(false);
        no.setVisible(false);

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
            //TODO ctrl init buttons wpc
            iv.setFitWidth(43);
            iv.setFitHeight(43);
            tb.setGraphic(iv);
            personalWPCDice.add(tb, h, k);
        }
        initBStyle();
    }

    private void initChoiceBox() {
        valueChoice.setVisible(false);
        valueChoice.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
        valueChoice.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> valueChoice.setEffect(shadow));
        valueChoice.addEventHandler(MouseEvent.MOUSE_EXITED, e -> valueChoice.setEffect(null));
        valueChoice.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6));
        Tooltip tooltip = new Tooltip("Select the value");
        Tooltip.install(valueChoice, tooltip);
        valueChoice.getSelectionModel().selectFirst();
    }

    private void initBStyle() {
        for (Button b : buttons) {
            b.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
            //b.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> b.setEffect(shadow));
            //b.addEventHandler(MouseEvent.MOUSE_EXITED, e -> b.setEffect(null));
        }

        for (ToggleButton tb : tcButtons) {
            tb.setStyle("-fx-base: transparent; -fx-focus-color: transparent; -fx-faint-focus-color: transparent");
            //tb.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tb.setEffect(shadow));
            //tb.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            //    if (!tb.isSelected()) tb.setEffect(null);
            //});
        }

        for (int j=0; j<parents.size(); j++) {
            for (int i = 0; i<parents.get(j).getChildren().size(); i++) {
                int h = i;
                int k=j;
                parents.get(j).getChildren().get(i).setStyle("-fx-base: transparent; -fx-focus-color: transparent; -fx-faint-focus-color: transparent");
                //parents.get(j).getChildren().get(i).addEventHandler(MouseEvent.MOUSE_ENTERED, e -> parents.get(k).getChildren().get(h).setEffect(shadow));
                //parents.get(j).getChildren().get(i).addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                //    if (!((ToggleButton) parents.get(k).getChildren().get(h)).isSelected())
                //        parents.get(k).getChildren().get(h).setEffect(null);
                //});
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
                    initPubOCards();
                    initTCards();
                    initWPCards();
                    initPersonalWPC();
                    initPersonalPriOC();
                    setRoundTrack(modelRepresentation.getRoundTrack());
                    moveDice();
                }

                @Override
                public void visitGameBoardAction(TurnStart turnStart) {
                    if (turnStart.getUsername() == null) {
                        disableTCB(false);
                        disableTB(draftPoolDice, false);
                        disableTB(personalWPCDice, false);
                        pass.setDisable(false);
                        moveDice();
                        msgBox.setText("It's your turn!\n");
                    } else {
                        disableAllButtons();
                        msgBox.setText("It's " + turnStart.getUsername() + "'s turn!\n");
                    }
                }

                @Override
                public void visitGameBoardAction(TurnUpdate turnUpdate) {
                    if (turnUpdate.getMove()) {
                        disableTB(personalWPCDice, false);
                        disableTB(draftPoolDice, false);
                        moveDice();
                    }
                    if (turnUpdate.getTool()) {
                        disableTCB(false);
                    }
                    pass.setDisable(false);
                }

                @Override
                public void visitGameBoardAction(InvalidAction invalidAction) {
                    msgBox.appendText(invalidAction.getMessage() + "\n");
                    inputError(true);
                }

                @Override
                public void visitGameBoardAction(WPCUpdate wpcUpdate) {
                    setWPCards(wpcUpdate.getOtherWpcs());
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

                @Override
                public void visitGameBoardAction(PickDie pickDie) {
                    pickDie(pickDie.getFrom());
                }

                @Override
                public void visitGameBoardAction(PlaceDie placeDie) {
                    selectDie(personalWPCDice, "place");
                }

                @Override
                public void visitGameBoardAction(IncreaseDecrease increaseDecrease) {
                    increaseValue();
                }

                @Override
                public void visitGameBoardAction(DieValue dieValue) {
                    chooseValue();
                }

                @Override
                public void visitGameBoardAction(AnotherAction anotherAction) {
                    chooseAnotherAction();
                }

                @Override
                public void visitGameBoardAction(TimeUp timeUp) {
                    //TODO check put shadow disable
                    for (ToggleButton t: tcButtons) {
                        if (t.isSelected()) {
                            t.setSelected(false);
                        }
                    }
                    for (int i=0; i<draftPoolDice.getChildren().size(); i++) {
                        if (((ToggleButton)draftPoolDice.getChildren().get(i)).isSelected()) {
                            ((ToggleButton)draftPoolDice.getChildren().get(i)).setSelected(false);
                        }
                    }
                    disableAllButtons();
                    resetPostMove();
                    msgBox.setText("Time's Up!\n");
                }

                @Override
                public void visitGameBoardAction(Message message) {
                    msgBox.appendText(message.getMessage()+"\n");
                }
            };
            guiReply.acceptGameBoardVisitor(gameBoardVisitor);
        }
    }

    private void initPubOCards() {
        ArrayList<String> publicOC = modelRepresentation.getPublicObjectiveCards();
        ArrayList<String> publicOCDesc = modelRepresentation.getPublicObjectiveDescription();
        Platform.runLater(() -> {
            for (int i=0; i<publicOC.size(); i++) {
            String img = publicOC.get(i);
                String path = "/client/OC/" + img + ".jpg";
                Image image = new Image(path);
                pubOCards.get(i).setImage(image);
                Tooltip t = new Tooltip(publicOCDesc.get(i));
                t.setStyle("-fx-font-size: 15px");
                t.setPrefWidth(200);
                t.setWrapText(true);
                Tooltip.install(pubOCards.get(i), t);
            }
        });
    }

    private void initTCards() {
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
                t.setStyle("-fx-font-size: 15px");
                t.setPrefWidth(200);
                t.setWrapText(true);
                Tooltip.install(tcButtons.get(i), t);
                tcButtons.get(i).setText(img);
                tcButtons.get(i).setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                tcTokens.get(i).setText(modelRepresentation.getTokensToolCards().get(i).toString());
                tcCircles.get(i).setVisible(true);
            }
            for (ToggleButton tc : tcButtons) {
                tc.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tc.setEffect(shadow));
                tc.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {tc.setEffect(null);
                    if (!tc.isSelected()) tc.setEffect(null);
                });
            }
        });
    }

    private void initWPCards() {
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
            wCircle0.setVisible(true);
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
            for (int i = 0; i < personalWPCDice.getChildren().size(); i++) {
                int h = i;
                ((ToggleButton) personalWPCDice.getChildren().get(i)).setOnAction(event -> {
                    for (int j = 0; j < draftPoolDice.getChildren().size(); j++) {
                        if (((ToggleButton) draftPoolDice.getChildren().get(j)).isSelected()) {
                            ((ToggleButton) draftPoolDice.getChildren().get(j)).setSelected(false);
                            ((ToggleButton) personalWPCDice.getChildren().get(h)).setSelected(false);
                            System.out.println("move");
                            guiViewT.notify(new MoveChoiceDicePlacement(h, j));
                            resetPostMove();
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
                iv.setFitWidth(43);
                iv.setFitHeight(43);
                if (img.contains("_")) {
                    String path = "/client/dice/" + img + ".jpg";
                    Image image = new Image(path);
                    iv.setImage(image);
                    ((ToggleButton)draftPoolDice.getChildren().get(i)).setText(img);
                    ((ToggleButton)draftPoolDice.getChildren().get(i)).setGraphic(iv);
                    ((ToggleButton)draftPoolDice.getChildren().get(i)).setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    draftPoolDice.getChildren().get(i).setDisable(false);
                } else {
                    ((ToggleButton)draftPoolDice.getChildren().get(i)).setGraphic(iv);
                    draftPoolDice.getChildren().get(i).setDisable(true);
                }
            }
            for (int i=dice.size(); i<9; i++) {
                draftPoolDice.getChildren().get(i).setDisable(true);
            }
            for (int i=0; i<dice.size(); i++) {
                int h=i;
                draftPoolDice.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_ENTERED, e -> draftPoolDice.getChildren().get(h).setEffect(shadow));
                draftPoolDice.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_EXITED, e -> {draftPoolDice.getChildren().get(h).setEffect(null);
                    if (!((ToggleButton)draftPoolDice.getChildren().get(h)).isSelected()) draftPoolDice.getChildren().get(h).setEffect(null);
                });
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
        Platform.runLater(() -> {
            for (int i=0; i<tcTok.size(); i++) {
                tcTokens.get(i).setText(tcTok.get(i).toString());
            }
            for (int i=0; i<playersTok.size(); i++) {
                usersTokens.get(i).setText(playersTok.get(i).toString());
            }
            user0tokens.setText(personalTok.toString());
        });
    }

    private void setWPCards(ArrayList<ArrayList<String>> wpcards)  {
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
                    personalWPCDice.getChildren().get(i).setDisable(true);
                } else {
                    ImageView iv = new ImageView();
                    ((ToggleButton)personalWPCDice.getChildren().get(i)).setGraphic(iv);
                }
            }
            for (int i=0; i<20; i++) {
                int h=i;
                personalWPCDice.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_ENTERED, e -> personalWPCDice.getChildren().get(h).setEffect(shadow));
                personalWPCDice.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    if (!((ToggleButton)personalWPCDice.getChildren().get(h)).isSelected()) personalWPCDice.getChildren().get(h).setEffect(null);
                });
            }
        });
    }

    private void pickDie(String from) {
        Pane parent;
        switch (from) {
            case "WPC":
                parent = personalWPCDice;
                break;
            case "RT":
                parent = roundTrackDice;
                break;
            default:
                parent = draftPoolDice;
                break;
        }
        selectDie(parent, "pick");
    }

    private void selectDie(Pane parent, String action) {
        Platform.runLater(() -> {
            disableTB(parent, false);
            if (action.equals("pick")) {
                msgBox.appendText("Select the die.\n");
            } else {
                msgBox.appendText("Select the cell.\n");
            }
            for (int i=0; i<parent.getChildren().size(); i++) {
                int h = i;
                ((ToggleButton)parent.getChildren().get(i)).setOnAction(event -> {
                    ((ToggleButton)parent.getChildren().get(h)).setSelected(false);
                    if (action.equals("pick")) {
                        guiViewT.notify(new ReplyPickDie(h));
                    } else {
                        guiViewT.notify(new ReplyPlaceDie(h));
                    }
                    resetPostMove();
                });
            }
        });
    }

    private void increaseValue() {
        Platform.runLater(() -> {
            msgBox.appendText("Do you want to increase or decrease the die value?\n");
            yes.setText("+");
            no.setText("-");
            yes.setVisible(true);
            no.setVisible(true);
            yes.setOnAction(event -> {
                msgBox.appendText("The value will be increased by 1.\n");
                guiViewT.notify(new ReplyIncreaseDecrease(true));
            });
            no.setOnAction(event -> {
                msgBox.appendText("The value will be decreased by 1.\n");
                guiViewT.notify(new ReplyIncreaseDecrease(false));
            });
            resetPostMove();
        });
    }

    private void chooseValue() {
        Platform.runLater(() -> {
            msgBox.appendText("Choose the value.\n");
            valueChoice.setVisible(true);
            valueChoice.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                int v = (int)newValue+1;
                guiViewT.notify(new ReplyDieValue(v));
                resetPostMove();
            });
        });
    }

    private void chooseAnotherAction() {
        Platform.runLater(() -> {
            msgBox.appendText("Do you want to move another die?\n");
            yes.setText("Yes");
            no.setText("No");
            yes.setVisible(true);
            no.setVisible(true);
            yes.setOnAction(event -> guiViewT.notify(new ReplyAnotherAction(true)));
            no.setOnAction(event -> guiViewT.notify(new ReplyAnotherAction(false)));
            resetPostMove();
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/clientstarter.fxml"));
                Parent root = fxmlLoader.load();
                Stage wpcChoiceStage = new Stage();
                wpcChoiceStage.setScene(new Scene(root));
                wpcChoiceStage.show();
                closeStage();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch login pane", e);
            }
        });
    }

    @FXML
    public void tc1Action() {
        if (toolCard1.isSelected()) {
            guiViewT.notify(new MoveChoiceToolCard(0));
            msgBox.appendText("You are using "+toolCard1.getText()+"\n");
        } else {
            guiViewT.notify(new UndoActionCommand());
            disableTCB(false);
            pass.setDisable(false);
            msgBox.appendText("You stopped using "+toolCard1.getText()+"\n");
        }
    }

    @FXML
    public void tc2Action() {
        if (toolCard2.isSelected()) {
            guiViewT.notify(new MoveChoiceToolCard(1));
            msgBox.appendText("You are using "+toolCard2.getText()+"\n");
        } else {
            guiViewT.notify(new UndoActionCommand());
            disableTCB(false);
            pass.setDisable(false);
            msgBox.appendText("You stopped using "+toolCard2.getText()+"\n");
        }
    }

    @FXML
    public void tc3Action() {
        if (toolCard3.isSelected()) {
            guiViewT.notify(new MoveChoiceToolCard(2));
            msgBox.appendText("You are using "+toolCard3.getText()+"\n");
        } else {
            guiViewT.notify(new UndoActionCommand());
            disableTCB(false);
            pass.setDisable(false);
            msgBox.appendText("You stopped using "+toolCard3.getText()+"\n");
        }
    }

    @FXML
    public void passTurn() {
        disableAllButtons();
        guiViewT.notify(new MoveChoicePassTurn(user0.getText()));
    }

    @FXML
    public void quit() {
        showClientStarter();
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
        }
    }

    private void disableTB(Pane p, boolean b) {
        if (b) {
            for (int i=0; i<p.getChildren().size(); i++) {
                p.getChildren().get(i).setDisable(true);
            }
        } else {
            for (int i=0; i<p.getChildren().size(); i++) {
                p.getChildren().get(i).setDisable(false);
            }
        }
    }

    private void disableAllButtons() {
        disableTCB(true);
        disableTB(draftPoolDice, true);
        disableTB(roundTrackDice, true);
        disableTB(personalWPCDice, true);
        pass.setDisable(true);
    }

    private void resetPostMove() {
        inputError(false);
        disableTCB(true);
        disableAllButtons();
        Platform.runLater(() -> {
            for (int i=0; i<parents.size(); i++) {
                for (int j=0; j<parents.get(i).getChildrenUnmodifiable().size(); j++) {
                    ((ToggleButton) parents.get(i).getChildrenUnmodifiable().get(j)).setOnAction(null);
                    ((ToggleButton) parents.get(i).getChildrenUnmodifiable().get(j)).setSelected(false);
                }
            }
            yes.setVisible(false);
            no.setVisible(false);
        });
    }

    private void inputError(boolean b) {
        redShadow.setColor(new Color(0.7, 0,0,1));
        if (b) {
            msgBox.setEffect(redShadow);
            try {
                Thread.sleep(500);
                msgBox.setEffect(null);
                Thread.sleep(500);
                msgBox.setEffect(redShadow);
                Thread.sleep(500);
                msgBox.setEffect(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            msgBox.setEffect(null);
        }
    }
}