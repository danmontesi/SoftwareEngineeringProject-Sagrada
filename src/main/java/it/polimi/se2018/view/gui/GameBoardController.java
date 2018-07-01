package it.polimi.se2018.view.gui;

import it.polimi.se2018.commands.client_to_server_command.*;
import it.polimi.se2018.commands.server_to_client_command.RefreshBoardCommand;
import it.polimi.se2018.view.gui.Notifiers.GameBoardActions.*;
import it.polimi.se2018.view.gui.Notifiers.GameBoardNotifier;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
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
import javafx.util.Duration;

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
    private int roundDice;
    private final Object available = new Object();

    private ToggleGroup draftPoolGroup = new ToggleGroup();
    private ToggleGroup roundTrackGroup = new ToggleGroup();

    private List<Circle> tcCircles;
    private List<Circle> wpcCircles;
    private List<Circle> circles;
    private List<ImageView> pubOCards;
    private List<ImageView> wpCards;
    private List<Button> tCards;
    private List<Label> userNames;
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
    private Button toolCard1;
    @FXML
    private Button toolCard2;
    @FXML
    private Button toolCard3;
    @FXML
    private Button pass;
    @FXML
    private Button undo;
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

    public GameBoardController() {
        pubOCards = new ArrayList<>();
        wpCards = new ArrayList<>();
        tCards = new ArrayList<>();
        userNames = new ArrayList<>();
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
        initCardLists();
        initLabels();
        initCircles();
        initButtons();
        initRoundTrack();
        initCards();
        initParents();
        initChoiceBox();
        disableAllButtons();
        msgBox.setText("Waiting for other players to choose their Window Pattern Card...\n");
        gameBoardNotifier.setOpen(true);
    }

    private void initCardLists() {
        pubOCards.add(pubOC1);
        pubOCards.add(pubOC2);
        pubOCards.add(pubOC3);

        wpCards.add(wpc1);
        wpCards.add(wpc2);
        wpCards.add(wpc3);

        tCards.add(toolCard1);
        tCards.add(toolCard2);
        tCards.add(toolCard3);
    }

    private void initLabels() {
        userNames.add(user1);
        userNames.add(user2);
        userNames.add(user3);

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
        buttons.add(undo);
        buttons.add(yes);
        buttons.add(no);
        yes.setVisible(false);
        no.setVisible(false);
        for (Button b : buttons) {
            b.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
            b.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> b.setEffect(shadow));
            b.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> b.setEffect(null));
        }

        for (int i = 0; i < 10; i++) {
            ToggleButton tb = new ToggleButton();
            tb.setPrefSize(43, 43);
            tb.setPadding(Insets.EMPTY);
            roundTrackDice.getChildren().add(tb);
            roundTrackGroup.getToggles().add(tb);
        }
        for (int i = 0; i < 9; i++) {
            ToggleButton tb = new ToggleButton();
            tb.setPrefSize(43, 43);
            tb.setPadding(Insets.EMPTY);
            draftPoolDice.getChildren().add(tb);
            draftPoolGroup.getToggles().add(tb);
        }
        int k = -1;
        for (int i = 0; i < 20; i++) {
            int h = i % 5;
            if (h == 0) k++;
            ToggleButton tb = new ToggleButton();
            tb.setPrefSize(43, 43);
            tb.setPadding(Insets.EMPTY);
            personalWPCDice.add(tb, h, k);
        }
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

    private void initCards() {
        Image prioc = new Image("/client/OC/ocback.jpg");
        Image tc = new Image("/client/TC/tcback.jpg");
        Image wpc = new Image("/client/WPC/wpcback.jpg");
        for (ImageView iv : pubOCards) {
            iv.setImage(prioc);
        }
        for (Button b : tCards) {
            ImageView iv = new ImageView(tc);
            iv.setFitWidth(140);
            iv.setFitHeight(190);
            b.setGraphic(iv);
            b.setPadding(Insets.EMPTY);
        }
        for (ImageView iv : wpCards) {
            iv.setImage(wpc);
        }
        wpc0.setImage(wpc);
        priOC.setImage(prioc);
    }

    private void setShadow(Pane p) {
        for (int i = 0; i < p.getChildren().size(); i++) {
            int h = i;
            p.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_ENTERED, e -> p.getChildren().get(h).setEffect(shadow));
            p.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                if (!((ToggleButton) p.getChildren().get(h)).isSelected())
                    p.getChildren().get(h).setEffect(null);
            });
        }
    }

    public void update(Observable o, Object arg) {
        if (arg == null) {
            showRanking();
        } else {
            GameBoardAction guiReply = (GameBoardAction) arg;
            GameBoardVisitor gameBoardVisitor = new GameBoardVisitor() {
                @Override
                public void visitGameBoardAction(GUIViewSetting guiViewSetting) {
                    guiViewT = guiViewSetting.getGuiView();
                }

                @Override
                public void visitGameBoardAction(RefreshBoard refreshBoard) {
                    modelRepresentation = refreshBoard.getModelRepresentation();
                    setPubOCards();
                    setTCards();
                    setWPCards();
                    setPersonalWPC();
                    setPersonalPriOC();
                    setRoundTrack(modelRepresentation.getRoundTrack());
                    moveDice();
                }

                @Override
                public void visitGameBoardAction(TurnStart turnStart) {
                    if (turnStart.getUsername() == null) {
                        enableTCB(true);
                        enableDraftPool(true);
                        enablePersonalWPC(true, "partial");
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
                    if (turnUpdate.isDieMoved()) {
                        enableTCB(true);
                        undo.setDisable(false);
                    }
                    if (turnUpdate.isToolUsed()) {
                        enablePersonalWPC(true, "partial");
                        enableDraftPool(true);
                        moveDice();
                    }
                    pass.setDisable(false);
                }

                @Override
                public void visitGameBoardAction(InvalidAction invalidAction) {
                    Platform.runLater(() -> msgBox.appendText(invalidAction.getMessage() + "\n"));
                    inputError();
                }

                @Override
                public void visitGameBoardAction(WPCUpdate wpcUpdate) {
                    setWPCardsDice(wpcUpdate.getOtherWpcs());
                    setPersonalWPCDice(wpcUpdate.getMyWpc());
                }

                @Override
                public void visitGameBoardAction(TokensUpdate tokensUpdate) {
                    setTokens(tokensUpdate.getTcTokens(), tokensUpdate.getPlayersTokens(), tokensUpdate.getPersonalTokens());
                }

                @Override
                public void visitGameBoardAction(DraftPoolRoundTrackUpdate draftPoolRoundTrackUpdate) {
                    if (draftPoolRoundTrackUpdate.getType().equals("DP")) {
                        setDraftPool(draftPoolRoundTrackUpdate.getDice());
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
                    resetPostMove();
                }

                @Override
                public void visitGameBoardAction(Message message) {
                    Platform.runLater(() -> msgBox.appendText(message.getMessage() + "\n"));
                }
            };
            guiReply.acceptGameBoardVisitor(gameBoardVisitor);
        }
    }

    private void setPubOCards() {
        ArrayList<String> publicOC = modelRepresentation.getPublicObjectiveCards();
        ArrayList<String> publicOCDesc = modelRepresentation.getPublicObjectiveDescription();
        Platform.runLater(() -> {
            for (int i = 0; i < publicOC.size(); i++) {
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

    private void setTCards() {
        ArrayList<String> tCards = modelRepresentation.getToolCards();
        ArrayList<String> tCardsDesc = modelRepresentation.getToolCardDescription();
        Platform.runLater(() -> {
            for (int i = 0; i < tCards.size(); i++) {
                String img = tCards.get(i);
                String path = "/client/TC/" + img + ".jpg";
                Image image = new Image(path);
                ImageView iv = new ImageView(image);
                iv.setFitHeight(190);
                iv.setFitWidth(140);
                this.tCards.get(i).setGraphic(iv);
                this.tCards.get(i).setPadding(Insets.EMPTY);
                Tooltip t = new Tooltip(tCardsDesc.get(i));
                t.setStyle("-fx-font-size: 15px");
                t.setPrefWidth(200);
                t.setWrapText(true);
                Tooltip.install(this.tCards.get(i), t);
                tcCircles.get(i).setVisible(true);
            }
            for (Button tc : this.tCards) {
                tc.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tc.setEffect(shadow));
                tc.addEventHandler(MouseEvent.MOUSE_EXITED, e -> tc.setEffect(null));
            }
        });
    }

    private void setWPCards() {
        Platform.runLater(() -> {
            for (int i = 0; i < modelRepresentation.getOtherPlayersWpcs().size(); i++) {
                String img = modelRepresentation.getOtherPlayersWpcs().get(i).get(0).replace("_", " ");
                String path = "/client/WPC/" + img + ".jpg";
                Image image = new Image(path);
                wpCards.get(i).setImage(image);
                userNames.get(i).setText(modelRepresentation.getOtherPlayersUsernames().get(i));
                usersTokens.get(i).setText(modelRepresentation.getOtherPlayersTokens().get(i).toString());
                wpcCircles.get(i).setVisible(true);
            }
            for (int j = modelRepresentation.getOtherPlayersWpcs().size(); j < 3; j++) {
                wpCards.get(j).setVisible(false);
                userNames.get(j).setVisible(false);
                usersTokens.get(j).setVisible(false);
            }
        });
    }

    private void setPersonalWPC() {
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

    private void setPersonalPriOC() {
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

    private void setDraftPool(ArrayList<String> dice) {
        Platform.runLater(() -> {
            synchronized (available) {
                roundDice = dice.size();
                for (int i = 0; i < dice.size(); i++) {
                    String img = dice.get(i);
                    if (img.contains("_")) {
                        String path = "/client/dice/" + img + ".jpg";
                        Image image = new Image(path);
                        ImageView iv = new ImageView(image);
                        iv.setFitWidth(43);
                        iv.setFitHeight(43);
                        ((ToggleButton) draftPoolDice.getChildren().get(i)).setGraphic(iv);
                    } else {
                        ((ToggleButton) draftPoolDice.getChildren().get(i)).setGraphic(null);
                        draftPoolDice.getChildren().get(i).setDisable(true);
                    }
                }
                for (int i = dice.size(); i < 9; i++) {
                    draftPoolDice.getChildren().get(i).setDisable(true);
                }
                setShadow(draftPoolDice);
            }
        });
    }

    private void setRoundTrack(ArrayList<String> dice) {
        Platform.runLater(() -> {
            for (int i = 0; i < 10; i++) {
                String img = dice.get(i);
                if (img.contains("_")) {
                    String path = "/client/dice/" + img + ".jpg";
                    Image image = new Image(path);
                    ImageView iv = new ImageView(image);
                    iv.setFitWidth(43);
                    iv.setFitHeight(43);
                    ((ToggleButton) roundTrackDice.getChildren().get(i)).setGraphic(iv);
                } else {
                    ((ToggleButton)roundTrackDice.getChildren().get(i)).setGraphic(null);
                    roundTrackDice.getChildren().get(i).setDisable(true);
                }
            }
        });
    }

    private void setTokens(ArrayList<Integer> tcTok, ArrayList<Integer> playersTok, Integer personalTok) {
        Platform.runLater(() -> {
            for (int i = 0; i < tcTok.size(); i++) {
                tcTokens.get(i).setText(tcTok.get(i).toString());
            }
            for (int i = 0; i < playersTok.size(); i++) {
                usersTokens.get(i).setText(playersTok.get(i).toString());
            }
            user0tokens.setText(personalTok.toString());
        });
    }

    private void setWPCardsDice(ArrayList<ArrayList<String>> wpcards) {
        Platform.runLater(() -> {
            for (int i = 0; i < wpcards.size(); i++) {
                int k = -1;
                for (int j = 0; j < 20; j++) {
                    int h = j % 5;
                    if (h == 0) k++;
                    String img = wpcards.get(i).get(j + 1);
                    ImageView iv = new ImageView();
                    iv.setFitHeight(29);
                    iv.setFitWidth(29);
                    if (img.contains("_")) {
                        String path = "/client/dice/" + img + ".jpg";
                        Image image = new Image(path);
                        iv.setImage(image);
                    }
                    otherWPCsDice.get(i).add(iv, h, k);
                }
            }
        });
    }

    private void setPersonalWPCDice(ArrayList<String> wpc) {
        Platform.runLater(() -> {
            for (int i = 0; i < wpc.size() - 1; i++) {
                String img = wpc.get(i + 1);
                if (img.contains("_")) {
                    String path = "/client/dice/" + img + ".jpg";
                    Image image = new Image(path);
                    ImageView iv = new ImageView(image);
                    iv.setFitWidth(43);
                    iv.setFitHeight(43);
                    ((ToggleButton) personalWPCDice.getChildren().get(i)).setGraphic(iv);
                    personalWPCDice.getChildren().get(i).setDisable(true);
                } else {
                    ((ToggleButton)personalWPCDice.getChildren().get(i)).setGraphic(null);
                }
            }
            setShadow(personalWPCDice);
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
                            guiViewT.notify(new MoveChoiceDiePlacement(h, j));
                            resetPostMove();
                        }
                    }
                });
            }
        });
    }

    private void pickDie(String from) {
        Pane parent;
        switch (from) {
            case "WPC":
                parent = personalWPCDice;
                enablePersonalWPC(true, "complete");
                break;
            case "RT":
                parent = roundTrackDice;
                enableRoundTrack(true);
                break;
            default:
                parent = draftPoolDice;
                enableDraftPool(true);
                break;
        }
        selectDie(parent, "pick");
    }

    private void selectDie(Pane parent, String action) {
        Platform.runLater(() -> {
            if (action.equals("pick")) {
                msgBox.appendText("Select the die.\n");
            } else {
                enablePersonalWPC(true, "partial");
                msgBox.appendText("Select the cell.\n");
            }
            for (int i = 0; i < parent.getChildren().size(); i++) {
                int h = i;
                ((ToggleButton) parent.getChildren().get(i)).setOnAction(event -> {
                    ((ToggleButton) parent.getChildren().get(h)).setSelected(false);
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
                int v = (int) newValue + 1;
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

    @FXML
    public void tc1Action() {
        guiViewT.notify(new MoveChoiceToolCard(0));
    }

    @FXML
    public void tc2Action() {
        guiViewT.notify(new MoveChoiceToolCard(1));
    }

    @FXML
    public void tc3Action() {
        guiViewT.notify(new MoveChoiceToolCard(2));
    }

    @FXML
    public void passTurn() {
        disableAllButtons();
        guiViewT.notify(new MoveChoicePassTurn(user0.getText()));
    }

    @FXML
    public void undo() {
        guiViewT.notify(new UndoActionCommand());
        resetPostMove();
    }

    private void closeStage() {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.setOpen(false);
        Stage stage = (Stage) pass.getScene().getWindow();
        stage.close();
    }

    private void showRanking() {
        Platform.runLater(() -> {
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

    private void enableTCB(boolean b) {
        if (b) {
            for (Button tc : tCards) {
                tc.setDisable(false);
            }
        } else {
            for (Button tc : tCards) {
                tc.setDisable(true);
            }
        }
    }

    private void enableDraftPool(boolean b) {
        Platform.runLater(() -> {
            synchronized (available) {
                if (b) {
                    for (int i = 0; i < roundDice; i++) {
                        if (((ToggleButton) draftPoolDice.getChildren().get(i)).getGraphic() != null) {
                            draftPoolDice.getChildren().get(i).setDisable(false);
                        }
                    }
                } else {
                    for (int i = 0; i < roundDice; i++) {
                        draftPoolDice.getChildren().get(i).setDisable(true);
                    }
                }
            }
        });
    }

    private void enableRoundTrack(boolean b) {
        if (b) {
            for (int i = 0; i < roundTrackDice.getChildren().size(); i++) {
                if (((ToggleButton) roundTrackDice.getChildren().get(i)).getGraphic() != null) {
                    roundTrackDice.getChildren().get(i).setDisable(false);
                }
            }
        } else {
            for (int i = 0; i < roundTrackDice.getChildren().size(); i++) {
                roundTrackDice.getChildren().get(i).setDisable(true);
            }
        }
    }

    private void enablePersonalWPC(boolean b, String extent) {
        if (b) {
            for (int i = 0; i < personalWPCDice.getChildren().size(); i++) {
                if (((ToggleButton) personalWPCDice.getChildren().get(i)).getGraphic() == null && !extent.equals("complete")) {
                    personalWPCDice.getChildren().get(i).setDisable(false);
                }
            }
        } else {
            for (int i = 0; i < personalWPCDice.getChildren().size(); i++) {
                personalWPCDice.getChildren().get(i).setDisable(true);
            }
        }
    }

    private void disableAllButtons() {
        enableTCB(false);
        enableDraftPool(false);
        enableRoundTrack(false);
        enablePersonalWPC(false, "partial");
        pass.setDisable(true);
        undo.setDisable(true);
    }

    private void resetPostMove() {
        Platform.runLater(() -> {
            disableAllButtons();
            for (Pane parent : parents) {
                for (int j = 0; j < parent.getChildren().size(); j++) {
                    ((ToggleButton)parent.getChildren().get(j)).setOnAction(null);
                    ((ToggleButton)parent.getChildren().get(j)).setSelected(false);
                    parent.getChildren().get(j).setEffect(null);
                }
            }
            yes.setVisible(false);
            no.setVisible(false);
        });
    }

    private void inputError() {
        redShadow.setColor(new Color(0.7, 0, 0, 1));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6), evt -> msgBox.setEffect(null)),
                new KeyFrame(Duration.seconds(0.2), evt -> msgBox.setEffect(redShadow)));
        timeline.setCycleCount(3);
        timeline.play();
    }
}