package it.polimi.se2018.MVC;

import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.WindowPatternCard;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.util.ArrayList;
import java.util.List;

public class GUIView extends View {

//TODO          PER CHI FA LA VIEW:
//TODO          OGNI METODO DEVE CHIAMARE LA notify() della view, passandole un EVENTO.
//TODO          ognuno dei metodi quì sotto prima chiede l'input dall'utente, poi fa notify(new chosen
    public void chooseWindowPatternCardMenu(ArrayList<WindowPatternCard> cards){
        //notify(new ChooseWindowPatternCardCommand())
    }

    public void startTurnMenu(){
        //notify( new MOVE / new TOOLUSE / new PASSTURN )
    }

    public void AllowedUseToolMessage(String message){
        //TODO Questo metodo non invia niente, mostra solo il messaggio
    }

    public void continueTurnMenu(boolean move, boolean tool){
        //notify( new MOVE / new TOOLUSE / new PASSTURN )
    }

    public void correctUseTool(int numTool){
        //Switch che in base al tipo di tool
        //i possibili metodi sono PRIVATI e sono questi quì
    }

    public void firmPastryBrushMenu(int value){

    }

    public void firmPastryThinnerMenu(String color, int value){

    }

    public void moveDieNoRestrictionMenu(String cardName){

    }

    public void changeDieValueMenu(String cardName){

    }

    public void twoDiceMoveMenu(String cardName){

    }

    public void corkLineMenu(){

    }

    public void gavelMenu(){

    }

    public void wheelsPincher(){

    }

    public void circularCutter(){

    }


    public void invalidActionMessage(String message){
        //TODO. non contiene niente, mostra solo i messaggio
    }

    public void loseMessage(Integer position, ArrayList<String> scores){
        //TODO. non contiene niente, mostra solo i messaggio. attento a parsare bene gli score
    }

    public void winMessage(List<String> scores){
        //TODO. non contiene niente, mostra solo i messaggio, attento a parsare bene gli scores
    }

    public void correctAuthenthication(String username){
        //TODO. non contiene niente, mostra solo i messaggio
    }


    // Methods for Obs/Obsvb

    //Sono gli stessi in ogni view
    @Override
    public void notify(Object event) {
        for (Observer observer : observers)
            observer.update(event);
    }

    @Override
    public void update(Object event) {
        //Osserva il Model e con Update, fa l'update del model locale
        //Calls the right method to update the Graphical Board;
        //The model is already updated by the ClientController, no worries about that
        //In case there is a CLI, does anything
        ServerToClientCommand command = (ServerToClientCommand) event;
        System.out.println("ricevuto "+ command.getMessage()); // DEVE ESSERE USATO ESCLUSIVAMENTE PER L'AGGIORNAMENTO MODEL
    }

}
