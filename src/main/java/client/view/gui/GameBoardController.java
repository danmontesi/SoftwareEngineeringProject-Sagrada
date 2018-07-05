package client.view.gui;

import shared.commands.client_to_server_command.*;
import shared.commands.server_to_client_command.RefreshBoardCommand;
import client.view.gui.notifiers.gameboardactions.*;
import client.view.gui.notifiers.GameBoardNotifier;
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

/**
 * Manages the Game Board window
 * @author Nives Migotto
 */
public class GameBoardController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(WPCChoiceController.class.getName());
    private GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();

    private GUIView guiViewT;
    private RefreshBoardCommand modelRepresentation;
    private int roundDice;

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
    private List<Button> buttons;
    private List<Pane> parents;

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
    private DropShadow greenShadow = new DropShadow();

    private boolean myTurn = false;

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
        buttons = new ArrayList<>();
        parents = new ArrayList<>();
    }

    public void initialize() {
        gameBoardNotifier.addObserver(this);
        initCardLists();
        initLabels();
        initCircles();
        initButtons();
        initRoundTrack();
        initCards();
        initChoiceBox();
        disableAllButtons(true);
        msgBox.setText("Waiting for other players to choose their Window Pattern Card...\n");
        gameBoardNotifier.setOpen(true);
    }

    /**
     * Adds cards to lists
     */
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

        otherWPCsDice.add(wpc1dice);
        otherWPCsDice.add(wpc2dice);
        otherWPCsDice.add(wpc3dice);

        parents.add(draftPoolDice);
        parents.add(roundTrackDice);
        parents.add(personalWPCDice);
    }

    /**
     * Adds labels to lists
     */
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

    /**
     * Adds circles to lists and hides them
     */
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

    /**
     * Initializes service buttons, creates and initializes dice buttons and image views
     */
    private void initButtons() {
        buttons.add(pass);
        buttons.add(undo);
        buttons.add(yes);
        buttons.add(no);
        yes.setVisible(false);
        no.setVisible(false);
        for (Button b : buttons) {
            b.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
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
        for (GridPane gridPane : otherWPCsDice) {
            int j = -1;
            for (int i = 0; i < 20; i++) {
                int h = i % 5;
                if (h == 0) j++;
                ImageView imageView = new ImageView();
                imageView.setFitHeight(29);
                imageView.setFitWidth(29);
                gridPane.add(imageView, h, j);
            }
        }
    }

    /**
     * Initializes choice box valueChoice
     */
    private void initChoiceBox() {
        valueChoice.setVisible(false);
        valueChoice.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: gray; -fx-border-width: 0.3px");
        valueChoice.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6));
        Tooltip tooltip = new Tooltip("Select the value");
        Tooltip.install(valueChoice, tooltip);
    }

    /**
     * Shows Round Track image
     */
    private void initRoundTrack() {
        roundTrack.setImage(new Image("/client/images/RoundTrack.png"));
    }

    /**
     * Initializes cards showing the backside
     */
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

    /**
     * Sets the shadow effect on hover
     * @param p pane whose children will be affected
     */
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
                        myTurn = true;
                        disableAllButtons(false);
                        moveDice();
                        msgBox.setText("It's your turn!\n");
                        greenLight();
                    } else {
                        myTurn = false;
                        disableAllButtons(true);
                        msgBox.setText("It's " + turnStart.getUsername() + "'s turn!\n");
                    }
                }

                @Override
                public void visitGameBoardAction(TurnUpdate turnUpdate) {
                    if (!turnUpdate.isDieMoved()) {
                        moveDice();
                    }
                }

                @Override
                public void visitGameBoardAction(InvalidAction invalidAction) {
                    Platform.runLater(() -> msgBox.appendText(invalidAction.getMessage() + "\n"));
                    redLight();
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
                    selectParent(pickDie.getFrom());
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
                    disableAllButtons(true);
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

    /**
     * Shows Public Objective Cards
     */
    private void setPubOCards() {
        List<String> publicOC = modelRepresentation.getPublicObjectiveCards();
        List<String> publicOCDesc = modelRepresentation.getPublicObjectiveDescription();
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

    /**
     * Shows Tool Cards
     */
    private void setTCards() {
        List<String> toolCards = modelRepresentation.getToolCards();
        List<String> toolCardsDesc = modelRepresentation.getToolCardDescription();
        Platform.runLater(() -> {
            for (int i = 0; i < toolCards.size(); i++) {
                String img = toolCards.get(i);
                String path = "/client/TC/" + img + ".jpg";
                Image image = new Image(path);
                ImageView iv = new ImageView(image);
                iv.setFitHeight(190);
                iv.setFitWidth(140);
                tCards.get(i).setGraphic(iv);
                tCards.get(i).setPadding(Insets.EMPTY);
                Tooltip t = new Tooltip(toolCardsDesc.get(i));
                t.setStyle("-fx-font-size: 15px");
                t.setPrefWidth(200);
                t.setWrapText(true);
                Tooltip.install(tCards.get(i), t);
                tcCircles.get(i).setVisible(true);
            }
            for (Button tc : tCards) {
                tc.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tc.setEffect(shadow));
                tc.addEventHandler(MouseEvent.MOUSE_EXITED, e -> tc.setEffect(null));
            }
        });
    }

    /**
     * Shows other players' Window Pattern Cards
     */
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

    /**
     * Shows player's Window Pattern Card
     */
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

    /**
     * Shows Private Objective Card
     */
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

    /**
     * Shows the dice on the DraftPool
     * @param dice to be shown dice
     */
    private void setDraftPool(List<String> dice) {
        Platform.runLater(() -> {
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
                    draftPoolDice.getChildren().get(i).setDisable(!myTurn);
                } else {
                    ((ToggleButton) draftPoolDice.getChildren().get(i)).setGraphic(null);
                }
            }
            for (int i = dice.size(); i < 9; i++) {
                draftPoolDice.getChildren().get(i).setDisable(true);
            }
            setShadow(draftPoolDice);
        });
    }

    /**
     * Shows the dice ont he RoundTrack
     * @param dice to be shown dice
     */
    private void setRoundTrack(List<String> dice) {
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
                }
            }
            setShadow(roundTrackDice);
        });
    }

    /**
     * Updates Tool Cards and all players' tokens
     * @param tcTok list of Tool Cards tokens numbers
     * @param playersTok player's tokens number
     * @param personalTok list of other players' tokens numbers
     */
    private void setTokens(List<Integer> tcTok, List<Integer> playersTok, Integer personalTok) {
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

    /**
     * Shows the dice on the other players' Window Pattern Cards
     * @param wpcards to be shown dice
     */
    private void setWPCardsDice(List<List<String>> wpcards) {
        Platform.runLater(() -> {
            for (int i = 0; i < wpcards.size(); i++) {
                for (int j = 0; j < 20; j++) {
                    String img = wpcards.get(i).get(j + 1);
                    ImageView iv = new ImageView();
                    iv.setFitHeight(29);
                    iv.setFitWidth(29);
                    if (img.contains("_")) {
                        String path = "/client/dice/" + img + ".jpg";
                        Image image = new Image(path);
                        ((ImageView)otherWPCsDice.get(i).getChildren().get(j)).setImage(image);
                    } else {
                        Image image = new Image("/client/dice/transparent.png");
                        ((ImageView)otherWPCsDice.get(i).getChildren().get(j)).setImage(image);
                    }
                }
            }
        });
    }

    /**
     * Shows the dice in the player's Window Pattern Card
     * @param wpc to be shown dice
     */
    private void setPersonalWPCDice(List<String> wpc) {
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
                } else {
                    ((ToggleButton)personalWPCDice.getChildren().get(i)).setGraphic(null);
                }
            }
            setShadow(personalWPCDice);
        });
    }

    /**
     * Allows to move a die from hte Draft Pool to the Window Pattern Card
     */
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

    /**
     * Selects where the die can be selected from
     * @param from indicates where the dice can be selected from
     */
    private void selectParent(String from) {
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

    /**
     * Allows to select a die
     * @param parent indicates where the dice can be selected from
     * @param action indicates why the dice is selected (pick it or place it)
     */
    private void selectDie(Pane parent, String action) {
        Platform.runLater(() -> {
            if (action.equals("pick")) {
                msgBox.appendText("Select the die.\n");
            } else {
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

    /**
     * Allows to choose whether to increase or decrease the die value
     */
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
                resetPostMove();
            });
            no.setOnAction(event -> {
                msgBox.appendText("The value will be decreased by 1.\n");
                guiViewT.notify(new ReplyIncreaseDecrease(false));
                resetPostMove();
            });
        });
    }

    /**
     * Allows to choose a die value
     */
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

    /**
     * Allows to choose whether to do another action or not
     */
    private void chooseAnotherAction() {
        Platform.runLater(() -> {
            msgBox.appendText("Do you want to move another die?\n");
            yes.setText("Yes");
            no.setText("No");
            yes.setVisible(true);
            no.setVisible(true);
            yes.setOnAction(event -> {
                guiViewT.notify(new ReplyAnotherAction(true));
                resetPostMove();
            });
            no.setOnAction(event -> {
                guiViewT.notify(new ReplyAnotherAction(false));
                resetPostMove();
            });
        });
    }

    /**
     * Notifies use of first Tool Card
     */
    @FXML
    public void tc1Action() {
        guiViewT.notify(new MoveChoiceToolCard(0));
    }

    /**
     * Notifies use of second Tool Card
     */
    @FXML
    public void tc2Action() {
        guiViewT.notify(new MoveChoiceToolCard(1));
    }

    /**
     * Notifies use of second Tool Card
     */
    @FXML
    public void tc3Action() {
        guiViewT.notify(new MoveChoiceToolCard(2));
    }

    /**
     * Allows to pass turn
     */
    @FXML
    public void passTurn() {
        guiViewT.notify(new MoveChoicePassTurn());
        resetPostMove();
    }

    /**
     * Allows to undo the last action
     */
    @FXML
    public void undo() {
        guiViewT.notify(new UndoActionCommand());
    }

    /**
     * Closes current stage
     */
    private void closeStage() {
        gameBoardNotifier.setOpen(false);
        Stage stage = (Stage) pass.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens Ranking Window
     */
    private void showRanking() {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ranking.fxml"));
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

    /**
     * Disables or enables all buttons
     * @param b indicates whether the buttons are to be enabled or disabled
     */
    private void disableAllButtons(boolean b) {
        for (Button tc : tCards) {
            tc.setDisable(b);
        }
        for (int i = 0; i < roundDice; i++) {
            draftPoolDice.getChildren().get(i).setDisable(b);
        }
        for (int i = 0; i < roundTrackDice.getChildren().size(); i++) {
            roundTrackDice.getChildren().get(i).setDisable(b);
        }
        for (int i = 0; i < personalWPCDice.getChildren().size(); i++) {
            personalWPCDice.getChildren().get(i).setDisable(b);
        }
        pass.setDisable(b);
        undo.setDisable(b);
    }

    /**
     * Disables all buttons effects and actions
     */
    private void resetPostMove() {
        Platform.runLater(() -> {
            for (Pane parent : parents) {
                for (int j = 0; j < parent.getChildren().size(); j++) {
                    ((ToggleButton)parent.getChildren().get(j)).setOnAction(null);
                    ((ToggleButton)parent.getChildren().get(j)).setSelected(false);
                    parent.getChildren().get(j).setEffect(null);
                    }
                }
            yes.setVisible(false);
            no.setVisible(false);
            valueChoice.setVisible(false);
        });
    }

    /**
     * Indicates if an input error occurred
     */
    private void redLight() {
        redShadow.setColor(new Color(0.7, 0, 0, 1));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6), evt -> msgBox.setEffect(null)),
                new KeyFrame(Duration.seconds(0.2), evt -> msgBox.setEffect(redShadow)));
        timeline.setCycleCount(3);
        timeline.play();
    }

    /**
     * Indicates the turn is starting
     */
    private void greenLight() {
        greenShadow.setColor(new Color(0, 0.7, 0, 1));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6), evt -> msgBox.setEffect(null)),
                new KeyFrame(Duration.seconds(0.2), evt -> msgBox.setEffect(greenShadow)));
        timeline.setCycleCount(2);
        timeline.play();
    }
}