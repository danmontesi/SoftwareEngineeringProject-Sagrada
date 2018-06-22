package it.polimi.se2018.view.GUI;

import it.polimi.se2018.commands.server_to_client_command.*;
import it.polimi.se2018.view.GUI.Notifiers.GameBoardActions.*;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions.*;
import it.polimi.se2018.view.GUI.Notifiers.GameBoardNotifier;
import it.polimi.se2018.view.GUI.Notifiers.LobbyNotifier;
import it.polimi.se2018.view.GUI.Notifiers.RankingPaneNotifier;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions.WGUIViewSetting;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceNotifier;
import it.polimi.se2018.view.View;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;

public class GUIView extends View {
    //TODO          PER CHI FA LA VIEW:
//TODO          OGNI METODO DEVE CHIAMARE LA notify() della view, passandole un EVENTO.
//TODO          ognuno dei metodi quì sotto prima chiede l'input dall'utente, poi fa notify(new chosen
    private RefreshBoardCommand currentModelPathRepresentation;

    public GUIView(Observer observer) {
        register(observer);
    }

    public GUIView() {}

    public void newConnectedPlayer(String username) {
        //piccola label che segnala il giocatore all'interno della lobby
        LobbyNotifier lobbyNotifier = LobbyNotifier.getInstance();
        lobbyNotifier.updateGui(username);
    }

    @Override
    public void playerDisconnection(String username) {
        LobbyNotifier lobbyNotifier = LobbyNotifier.getInstance();
        lobbyNotifier.updateGui(username);
    }

    public void startGame() {
        LobbyNotifier lobbyNotifier = LobbyNotifier.getInstance();
        lobbyNotifier.updateGui();
    }

    public void chooseWindowPatternCardMenu(ArrayList<WindowPatternCard> cards) {
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
        System.out.println("start turn");
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new TurnStart(null));

        //2) se clicco una toolcard, appare una casewllina in cui scrivo "vuoi usare il tool x?"
        // se sì-> notify(new MoveChoiceToolCard(indice tool);

        //notify( new MOVE / new TOOLUSE / new PASSTURN )
    }

    @Override
    public void otherPlayerTurn(String username) {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new TurnStart(username));
    }

    @Override
    public void authenticatedCorrectlyMessage(String message) {

    }

    public void AllowedUseToolMessage(String message) {
        //TODO Questo metodo non invia niente, mostra solo il messaggio
    }

    public void continueTurnMenu(boolean move, boolean tool) {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new TurnUpdate(move, tool));
    }

    public void correctUseTool(int numTool) {
        //Switch che in base al tipo di tool
        //i possibili metodi sono PRIVATI e sono questi quì
    }

    public void firmPastryBrushMenu(int value) {

    }

    public void firmPastryThinnerMenu(String color, int value) {

    }

    public void moveDieNoRestrictionMenu(String cardName) {

    }

    public void changeDieValueMenu(String cardName) {
        //if CardName.equals(Roughing Forceps):
        //1- scrivi nelle notifiche "Scegli un dado draftpool" abilitando solo draftpool
        //2- scegli se aumentare / diminuire (Faccio apparire 2 bottoni + o - )"
        //notify( cardName, Integer draftPoolPosition, Integer schemaPosition, boolean increase, boolean placedDie)
        // -> 2 bottoni

    }

    public void twoDiceMoveMenu(String cardName) {

    }

    public void corkLineMenu() {
        // Attivo draftpool
        // Attivo schema
        //l'utente preme gli indici e invio l'evento
        // notify(new UseToolCorkLine(schemaPos, draftPos);
        //disabiliti tutto

    }


    public void wheelsPincherMenu() {


    }

    public void circularCutter() {

    }


    public void invalidActionMessage(String message) {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new InvalidAction(message));
    }

    public void loseMessage(Integer position, ArrayList<String> scores) {
        scores.add(0, position.toString());
        RankingPaneNotifier rankingPaneNotifier = RankingPaneNotifier.getInstance();
        rankingPaneNotifier.updateGui(scores);
    }

    public void winMessage(ArrayList<String> scores) {
        scores.add(0, "1");
        RankingPaneNotifier rankingPaneNotifier = RankingPaneNotifier.getInstance();
        rankingPaneNotifier.updateGui(scores);
    }

    public void correctAuthenthication(String username) {
        //TODO. non contiene niente, mostra solo i messaggio
    }

    @Override
    public void timeOut() {
        //TODO: interrompe tutte le finestre in corso ed invia un passTurnCommand()
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
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new DraftPoolRoundTrackUpdate("DP", refreshCommand.getDraftpool()));
    }


    // Methods for Obs/Obsvb

    //Sono gli stessi in ogni view
    @Override
    public void notify(Object event) {
        for (Observer observer : observers)
            observer.update(event);
    }

    @Override
    public void messageBox(String message) {

    }

    @Override
    public void update(Object model) {
        //Osserva il Model e con Update, fa l'update del model locale
        //Calls the right method to update the Graphical Board;
        //The model is already updated by the ClientController, no worries about that
        //In case there is a CLI, does anything
        RefreshBoardCommand command = (RefreshBoardCommand) model;
        this.currentModelPathRepresentation=command;
        //PER NIVES: PER ORA LE UPDATE SONO DI 2 TIPI: TU LASCIALI ENTRAMBI E FAI L'AGGIORNAMENTO  DELLA BOARD PRENDENDO QUESTO
        if (command.getMessage()!=null) {
            System.out.println("ricevuto " + command.getMessage()); // DEVE ESSERE USATO ESCLUSIVAMENTE PER L'AGGIORNAMENTO MODEL
        }
        else{
            currentModelPathRepresentation = command;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //TODO: sistemare meglio
            GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
            gameBoardNotifier.updateGui(new GUIViewSetting(this));
            gameBoardNotifier.updateGui(new RefreshBoard(command));
        }
    }

}