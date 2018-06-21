package it.polimi.se2018.view.GUI;

import it.polimi.se2018.view.GUI.Notifiers.GUIReplies.*;
import it.polimi.se2018.view.GUI.Notifiers.GameBoardNotifier;
import it.polimi.se2018.view.GUI.Notifiers.LobbyNotifier;
import it.polimi.se2018.view.GUI.Notifiers.RankingPaneNotifier;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceNotifier;
import it.polimi.se2018.view.View;
import it.polimi.se2018.commands.server_to_client_command.RefreshBoardCommand;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;
import java.util.List;

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
        wpcChoiceNotifier.updateGui(new GUIViewSetting(this));
        wpcChoiceNotifier.updateGui(new WPCChoice(cardNames, cardDifficulties));
    }

    public void startTurnMenu() {
        System.out.println("start turn");
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new TurnStart(null));
        // Abilito bottoni draftpool, toolcard, pass. fatto
        // Scrivo nel riquadro eventi " It's your turn" fatto

        //3 casi:
        //1) clicco un dado dells draftpool -> Inizio mossa
        //se faccio una mossa -> tutti bottoni disattivati
        ///notify(new MoveChoiceDicePlacement( indice draft, indice schema)
        //Disattivo tutti i bottoni


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
        //se move = false, draftpool disattivata, se true attivata fatto
        //tool "" fatto


        //notify( new MOVE / new TOOLUSE / new PASSTURN )
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
    public void updateWpc(ArrayList<String> myWpc, ArrayList<ArrayList<String>> otherWpcs) {
        GameBoardNotifier gameBoardNotifier = GameBoardNotifier.getInstance();
        gameBoardNotifier.updateGui(new WPCUpdate(myWpc, otherWpcs));
    }

    @Override
    public void updateTokens() {
        //DO LATER

    }

    @Override
    public void updateRoundTrack() {
        //DO LATER
    }

    @Override
    public void updateDraftPool() {
        //DO LATER
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
            currentModelPathRepresentation = command; //posso sempre accedere alle informazioni del model facendo currentModelPathRepresentation.get....()
            //System.out.println(command.getDraftpool().get(0)); //example
            //TODO NIVES: da command prendo tutte le informazioni come ho fatto per la classe di prova
            //es. command.getDraftPool,... Oss: ho aggiunto anche le descrizioni
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