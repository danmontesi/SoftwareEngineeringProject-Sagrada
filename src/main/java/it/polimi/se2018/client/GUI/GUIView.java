package it.polimi.se2018.client.GUI;

import it.polimi.se2018.client.GUI.Notifiers.WPCChoiceNotifier;
import it.polimi.se2018.client.View;
import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class GUIView extends View {
//TODO          PER CHI FA LA VIEW:
//TODO          OGNI METODO DEVE CHIAMARE LA notify() della view, passandole un EVENTO.
//TODO          ognuno dei metodi quì sotto prima chiede l'input dall'utente, poi fa notify(new chosen

    public GUIView(Observer observer) {
        register(observer);
    }

    public GUIView() {}

    public void chooseWindowPatternCardMenu(ArrayList<WindowPatternCard> cards) {
        ArrayList<String> cardNames = new ArrayList<>();
        for (WindowPatternCard card : cards) {
            cardNames.add(card.getCardName());
        }
        //notify(new ChooseWindowPatternCardCommand(cardNames.toString()));
        WPCChoiceNotifier wpcChoiceNotifier = WPCChoiceNotifier.getInstance();
        wpcChoiceNotifier.updateGui(cardNames.toString());
    }

    public void newConnectedPlayer(String username) {
        //piccola label che segnala il giocatore all'interno della lobby
    }

    @Override
    public void playerDisconnection(String username) {

    }


    public void startTurnMenu() {
        // Abilito bottoni draftpool, toolcard, pass.
        // Scrivo nel riquadro eventi " It's your turn"

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

    }

    @Override
    public void authenticatedCorrectlyMessage(String message) {

    }

    public void AllowedUseToolMessage(String message) {
        //TODO Questo metodo non invia niente, mostra solo il messaggio
    }

    public void continueTurnMenu(boolean move, boolean tool) {
        //se move = false, draftpool disattivata, se true attivata
        //tool ""


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
        //TODO. non contiene niente, mostra solo i messaggio
    }

    public void loseMessage(Integer position, ArrayList<String> scores) {
        //TODO. non contiene niente, mostra solo i messaggio. attento a parsare bene gli score
    }

    public void winMessage(List<String> scores) {
        //TODO. non contiene niente, mostra solo i messaggio, attento a parsare bene gli scores
    }

    public void correctAuthenthication(String username) {
        //TODO. non contiene niente, mostra solo i messaggio
    }

    @Override
    public void timeOut() {
        //TODO: interrompe tutte le finesdte in corso, ed invia un passTurnCommand()
    }

    @Override
    public void updateWpc(ArrayList<String> myWpc, ArrayList<ArrayList<String>> otherWpcs) {
        //DO LATER
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
    public void update(Object event) {
        //Osserva il Model e con Update, fa l'update del model locale
        //Calls the right method to update the Graphical Board;
        //The model is already updated by the ClientController, no worries about that
        //In case there is a CLI, does anything
        ServerToClientCommand command = (ServerToClientCommand) event;
        System.out.println("ricevuto " + command.getMessage()); // DEVE ESSERE USATO ESCLUSIVAMENTE PER L'AGGIORNAMENTO MODEL
    }

}
