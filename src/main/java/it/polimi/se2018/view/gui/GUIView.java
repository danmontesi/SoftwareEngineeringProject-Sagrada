package it.polimi.se2018.view.gui;

import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.commands.server_to_client_command.*;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.gui.Notifiers.GameBoardActions.*;
import it.polimi.se2018.view.gui.Notifiers.GameBoardNotifier;
import it.polimi.se2018.view.gui.Notifiers.LobbyNotifier;
import it.polimi.se2018.view.gui.Notifiers.RankingPaneNotifier;
import it.polimi.se2018.view.gui.Notifiers.WPCChoiceActions.WGUIViewSetting;
import it.polimi.se2018.view.gui.Notifiers.WPCChoiceActions.WPCChoice;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.gui.Notifiers.WPCChoiceNotifier;

import java.util.ArrayList;
import java.util.List;

public class GUIView extends View {

    public GUIView(Observer observer) {
        super(observer);
    }

    public void newConnectedPlayer(String username) {
        LobbyNotifier lobbyNotifier = LobbyNotifier.getInstance();
        lobbyNotifier.updateGui(username);
    }

    @Override
    public void playerDisconnection(String username) {
        LobbyNotifier lobbyNotifier = LobbyNotifier.getInstance();
        lobbyNotifier.updateGui(username);
    }

    @Override
    public void timeOut() {
        //TODO: speicficare che c'è stato un timeout
        if(GameBoardNotifier.getInstance().isOpen()){
            GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
            gameBoardNotifier.updateGui(new TimeUp());
        } else {
            WPCChoiceNotifier wpcChoiceNotifier = WPCChoiceNotifier.getInstance();
            wpcChoiceNotifier.updateGui();
        }
    }

    @Override
    public void startGame() {
        LobbyNotifier lobbyNotifier = LobbyNotifier.getInstance();
        lobbyNotifier.updateGui();
    }

    @Override
    public void endGame() {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui();
    }

    @Override
    public void chooseWindowPatternCardMenu(List<WindowPatternCard> cards) {
        ArrayList<String> cardNames = new ArrayList<>();
        ArrayList<Integer> cardDifficulties = new ArrayList<>();
        for (WindowPatternCard card : cards) {
            cardNames.add(card.getCardName());
            cardDifficulties.add(card.getDifficulty());
        }
        WPCChoiceNotifier wpcChoiceNotifier = WPCChoiceNotifier.getInstance();
        wpcChoiceNotifier.updateGui(new WGUIViewSetting(this));
        wpcChoiceNotifier.updateGui(new WPCChoice(cardNames, cardDifficulties));
    }

    public void startTurnMenu() {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new TurnStart(null));
        //casellina in cui scrivo "vuoi usare il tool x?"
    }

    @Override
    public void otherPlayerTurn(String username) {
        while(!GameBoardNotifier.getInstance().isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new TurnStart(username));
    }

    @Override
    public void authenticatedCorrectlyMessage(String message) {
        this.username = message;
    }

    public void continueTurnMenu(boolean move, boolean tool) {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new TurnUpdate(move, tool));
    }

    @Override
    public void firmPastryBrushMenu(int value) {
        GameBoardNotifier gameBoardNotifier  = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new ToolCardUse("Firm Pastry Brush", null, value));
    }

    @Override
    public void firmPastryThinnerMenu(String color, int value) {
        GameBoardNotifier gameBoardNotifier  = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new ToolCardUse("Firm Pastry Thinner", color, value));
    }

    @Override
    public void moveDieNoRestrictionMenu(String cardName) {
        GameBoardNotifier gameBoardNotifier  = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new ToolCardUse(cardName, null, null));
    }

    @Override
    public void changeDieValueMenu(String cardName) {
        GameBoardNotifier gameBoardNotifier  = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new ToolCardUse(cardName, null, null));
    }

    @Override
    public void twoDiceMoveMenu(String cardName) {
        GameBoardNotifier gameBoardNotifier  = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new ToolCardUse(cardName, null, null));
    }

    @Override
    public void corkLineMenu() {
        GameBoardNotifier gameBoardNotifier  = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new ToolCardUse("Cork Line", null, null));
    }

    @Override
    public void wheelsPincherMenu() {
        GameBoardNotifier gameBoardNotifier  = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new ToolCardUse("Wheels Pincher", null, null));
    }

    @Override
    public void circularCutter() {
        GameBoardNotifier gameBoardNotifier  = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new ToolCardUse("Circular Cutter", null, null));
    }

    @Override
    public void invalidActionMessage(String message) {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new InvalidAction(message));
    }

    @Override
    public void loseMessage(Integer position, List<String> scores) {
        while(!GameBoardNotifier.getInstance().isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        scores.add(0, position.toString());
        RankingPaneNotifier rankingPaneNotifier = RankingPaneNotifier.getInstance();
        rankingPaneNotifier.updateGui(scores);
    }

    @Override
    public void winMessage(List<String> scores) {
        while(!GameBoardNotifier.getInstance().isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        scores.add(0, "1");
        RankingPaneNotifier rankingPaneNotifier = RankingPaneNotifier.getInstance();
        rankingPaneNotifier.updateGui(scores);
    }

    @Override
    public void correctAuthenthication(String username) {
        //TODO. non contiene niente, mostra solo i messaggio
    }

    @Override
    public void updateWpc(RefreshWpcCommand refreshCommand) {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new WPCUpdate(refreshCommand.getPersonalWpc(), refreshCommand.getOtherPlayersWpcs()));
    }

    @Override
    public void updateTokens(RefreshTokensCommand refreshCommand) {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new TokensUpdate(refreshCommand.getToolCardsTokens(), refreshCommand.getOtherPlayersTokens(), refreshCommand.getPersonalTokens()));
    }

    @Override
    public void updateRoundTrack(RefreshRoundTrackCommand refreshCommand) {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new DraftPoolRoundTrackUpdate("RT", refreshCommand.getRoundTrack()));
    }

    @Override
    public void updateDraftPool(RefreshDraftPoolCommand refreshCommand) {
        while(!GameBoardNotifier.getInstance().isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new DraftPoolRoundTrackUpdate("DP", refreshCommand.getDraftpool()));
    }

    @Override
    public void updateBoard(RefreshBoardCommand refreshCommand) {
        while(!GameBoardNotifier.getInstance().isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new GUIViewSetting(this));
        gameBoardNotifier.updateGui(new RefreshBoard(refreshCommand));
    }

    @Override
    public void notify(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        command.setUsername(this.username);
        for (Observer observer : observers)
            observer.update(command);
    }

    @Override
    public void messageBox(String message) {}

}