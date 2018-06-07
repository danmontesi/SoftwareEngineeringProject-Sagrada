package it.polimi.se2018.MVC;

import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.WindowPatternCard;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIView extends View {

    /**
     * CliView receives a clone of current model each time it's Player's turn
     */

    private String playerUsername;

    private Scanner scan = new Scanner(System.in); // Can be replaced with BufferedReader?

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
        //notify( new UseToolFirmyPastryThinner )
    }

    public void firmPastryThinnerMenu(String color, int value){
        //notify( new UseToolFirmyPastryBrush )
    }

    private void copperFoilReamerMenu(){
        //notify( new UseTool[nome_tool] )
    }

    private void corkLineMenu(){
        //notify( new UseTool[nome_tool] )
    }

    private void diamondSwabMenu(){
        //notify( new UseTool[nome_tool] )
    }

    private void eglomiseBrushMenu(){
        //notify( new UseTool[nome_tool] )
    }


    private void gavelMenu(){
        //notify( new UseTool[nome_tool] )
    }

    private void lathekin(){
        //notify( new UseTool[nome_tool] )
    }

    private void manuelCutter(){
        //notify( new UseTool[nome_tool] )
    }

    private void roughingForceps(){
        //notify( new UseTool[nome_tool] )
    }

    private void wheelsPincher(){
        //notify( new UseTool[nome_tool] )
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
