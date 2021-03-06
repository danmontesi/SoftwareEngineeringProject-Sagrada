package client.view.gui;

import shared.commands.client_to_server_command.ClientToServerCommand;
import shared.commands.server_to_client_command.*;
import shared.utils.Observer;
import shared.View;
import client.view.gui.notifiers.GameBoardNotifier;
import client.view.gui.notifiers.LobbyNotifier;
import client.view.gui.notifiers.RankingPaneNotifier;
import client.view.gui.notifiers.WPCChoiceNotifier;
import client.view.gui.notifiers.gameboardactions.*;
import client.view.gui.notifiers.wpcchoiceactions.WGUIViewSetting;
import client.view.gui.notifiers.wpcchoiceactions.WPCChoice;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages interactions with Controller
 * @author Nives Migotto
 */
public class GUIView extends View {

    private LobbyNotifier lobbyNotifier = LobbyNotifier.getInstance();
    private WPCChoiceNotifier wpcChoiceNotifier = WPCChoiceNotifier.getInstance();
    private GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
    private RankingPaneNotifier rankingPaneNotifier = RankingPaneNotifier.getInstance();

    public GUIView(Observer observer) {
        super(observer);
    }

    @Override
    public void authenticatedCorrectlyMessage(String message) {
        this.username = message;
    }

    @Override
    public void newConnectedPlayer(String username) {
        lobbyNotifier.updateGui(username, 1);
    }

    @Override
    public void playerDisconnection(String username) {
        if(gameBoardNotifier.isOpen()){
            gameBoardNotifier.updateGui(new Message(username + " just disconnected."));
        } else {
            lobbyNotifier.updateGui(username, 1);
        }
    }

    @Override
    public void timeOut() {
        if(gameBoardNotifier.isOpen()){
            gameBoardNotifier.updateGui(new TimeUp());
        } else {
            wpcChoiceNotifier.updateGui();
        }
    }

    @Override
    public void startGame() {
        lobbyNotifier.updateGui();
    }

    @Override
    public void endGame() {
        gameBoardNotifier.updateGui();
    }

    @Override
    public void chooseWindowPatternCardMenu(List<List<String>> cards, String privateObjectiveCard, List<Integer> wpcDifficulties) {
        List<String> cardNames = new ArrayList<>();
        List<Integer> cardDifficulties = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            cardNames.add(cards.get(i).get(0));
            cardDifficulties.add(wpcDifficulties.get(i));
        }
        while(!wpcChoiceNotifier.isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        wpcChoiceNotifier.updateGui(new WGUIViewSetting(this));
        wpcChoiceNotifier.updateGui(new WPCChoice(cardNames, cardDifficulties, privateObjectiveCard));
    }

    @Override
    public void startTurnMenu() {
        gameBoardNotifier.updateGui(new TurnStart(null));
    }

    @Override
    public void otherPlayerTurn(String username) {
        while(!gameBoardNotifier.isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        gameBoardNotifier.updateGui(new TurnStart(username));
    }

    @Override
    public void continueTurnMenu(boolean hasAlreadyMovedDie, boolean hasAlreadyUsedTool) {
        gameBoardNotifier.updateGui(new TurnUpdate(hasAlreadyMovedDie, hasAlreadyUsedTool));
    }

    @Override
    public void invalidActionMessage(String message) {
        gameBoardNotifier.updateGui(new InvalidAction(message));
    }

    @Override
    public void correctAuthenthication(String username) {
        /*nothing to show*/
    }

    @Override
    public void updateWpc(RefreshWpcCommand refreshCommand) {
        gameBoardNotifier.updateGui(new WPCUpdate(refreshCommand.getPersonalWpc(), refreshCommand.getOtherPlayersWpcs()));
    }

    @Override
    public void updateTokens(RefreshTokensCommand refreshCommand) {
        gameBoardNotifier.updateGui(new TokensUpdate(refreshCommand.getToolCardsTokens(), refreshCommand.getOtherPlayersTokens(), refreshCommand.getPersonalTokens()));
    }

    @Override
    public void updateRoundTrack(RefreshRoundTrackCommand refreshCommand) {
        gameBoardNotifier.updateGui(new DraftPoolRoundTrackUpdate("RT", refreshCommand.getRoundTrack()));
    }

    @Override
    public void updateDraftPool(RefreshDraftPoolCommand refreshCommand) {
        while(!gameBoardNotifier.isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        gameBoardNotifier.updateGui(new DraftPoolRoundTrackUpdate("DP", refreshCommand.getDraftPool()));
    }

    @Override
    public void updateBoard(RefreshBoardCommand refreshCommand) {
        while(!gameBoardNotifier.isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        gameBoardNotifier.updateGui(new GUIViewSetting(this));
        gameBoardNotifier.updateGui(new RefreshBoard(refreshCommand));
    }

    @Override
    public void askAnotherAction() {
        gameBoardNotifier.updateGui(new AnotherAction());
    }

    @Override
    public void askIncreaseDecrease() {
        gameBoardNotifier.updateGui(new IncreaseDecrease());
    }

    @Override
    public void askDieValue() {
        gameBoardNotifier.updateGui(new DieValue());
    }

    @Override
    public void askPlaceDie() {
        gameBoardNotifier.updateGui(new PlaceDie());
    }

    @Override
    public void askPickDie(String from) {
        gameBoardNotifier.updateGui(new PickDie(from));
    }

    @Override
    public void messageBox(String message) {
        if(gameBoardNotifier.isOpen()){
            gameBoardNotifier.updateGui(new Message(message));
        } else {
            while(!lobbyNotifier.isOpen()){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            lobbyNotifier.updateGui(message, 0);
        }
    }

    @Override
    public void loseMessage(Integer position, List<String> scores) {
        while(!rankingPaneNotifier.isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        scores.add(0, position.toString());
        rankingPaneNotifier.updateGui(scores);
    }

    @Override
    public void winMessage(List<String> scores) {
        while(!rankingPaneNotifier.isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        scores.add(0, "1");
        rankingPaneNotifier.updateGui(scores);
    }

    @Override
    public void notify(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        command.setUsername(this.username);
        for (Observer observer : observers)
            observer.update(command);
    }
}