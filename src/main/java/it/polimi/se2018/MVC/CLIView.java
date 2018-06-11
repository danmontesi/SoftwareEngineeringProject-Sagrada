package it.polimi.se2018.MVC;

import it.polimi.se2018.client_to_server_command.ChosenWindowPatternCard;
import it.polimi.se2018.client_to_server_command.MoveChoiceDicePlacement;
import it.polimi.se2018.client_to_server_command.MoveChoicePassTurn;
import it.polimi.se2018.server_to_client_command.ChooseWindowPatternCardCommand;
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

    public CLIView(Observer observer){
        register(observer);
    }
    private String playerUsername;

    private Scanner scan = new Scanner(System.in); // Can be replaced with BufferedReader?

//TODO      PER CHI FA LA VIEW:
//TODO          OGNI METODO DEVE CHIAMARE LA notify() della view, passandole un EVENTO.
//TODO          ognuno dei metodi quì sotto prima chiede l'input dall'utente, poi fa notify(new chosen
    @Override
    public void chooseWindowPatternCardMenu(ArrayList<WindowPatternCard> cards){
        for (WindowPatternCard card : cards) {
            System.out.println(":- Difficulty = "+ card.getDifficulty()+ "\n");
            System.out.println(card.toString());
        }
        System.out.println("\n\n Which one do you chose?");
        //TODO: menu per numero scorretto
        int chosen = scan.nextInt();
        notify(new ChosenWindowPatternCard(cards.get(chosen).getCardName()));
    }

    @Override
    public void startTurnMenu(){
        //notify( new MOVE / new TOOLUSE / new PASSTURN )
        System.out.println("What do you want to do?");
        System.out.println("1- Place die");
        System.out.println("2- Use Tool");
        System.out.println("3- Pass Turn");
        int choice = scan.nextInt();
        switch(choice){
            case 1:
                System.out.println("Inserisci rispettivamente");
                int draftPos = scan.nextInt();
                int schemaRow = scan.nextInt();
                int schemaCol = scan.nextInt();
                notify(new MoveChoiceDicePlacement("",schemaRow,schemaCol,draftPos));
                break;
            case 2:
                System.out.println("Which tool want you to use?");
                break;
            case 3:
                System.out.println("passed turn");
                notify(new MoveChoicePassTurn(""));
        }
    }

    public void AllowedUseToolMessage(String message){
        //TODO Questo metodo non invia niente, mostra solo il messaggio
    }

    @Override
    public void continueTurnMenu(boolean move, boolean tool){
        //notify( new MOVE / new TOOLUSE / new PASSTURN )
        System.out.println("What do you want to do?");
        System.out.println(move? "1- Place die": "");
        System.out.println(tool? "2- Use Tool": "");
        System.out.println("3- Pass Turn");
        int choice = scan.nextInt();
        switch(choice){
            case 1:
                System.out.println("Inserisci rispettivamente");
                int draftPos = scan.nextInt();
                int schemaRow = scan.nextInt();
                int schemaCol = scan.nextInt();
                notify(new MoveChoiceDicePlacement("",schemaRow,schemaCol,draftPos));
                break;
            case 2:
                System.out.println("Which tool want you to use?");
                break;
            case 3:
                System.out.println("passed turn");
                notify(new MoveChoicePassTurn(""));
        }
    }

    @Override
    public void correctUseTool(int numTool){
        //Switch che in base al tipo di tool
        //i possibili metodi sono PRIVATI e sono questi quì
    }

    @Override
    public void firmPastryBrushMenu(int value){
        //notify( new UseToolFirmyPastryThinner )
    }

    @Override
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

    @Override
    public void invalidActionMessage(String message){
        System.out.println(message);
        //TODO. non contiene niente, mostra solo i messaggio
    }

    @Override
    public void loseMessage(Integer position, ArrayList<String> scores){
        //TODO. non contiene niente, mostra solo i messaggio. attento a parsare bene gli score
        System.out.println("You lost! Your rank is " + position + "\n");

        System.out.println("Here other players ordered scores:");
        for (String score : scores){
            System.out.println(score);
        }
        //TODO. non contiene niente, mostra solo i messaggio, attento a parsare bene gli scores
    }

    @Override
    public void winMessage(ArrayList<String> scores){
        System.out.println("Congratulation! You won!");

        System.out.println("Here other players ordered scores:");
        for (String score : scores){
            System.out.println(score);
        }
        //TODO. non contiene niente, mostra solo i messaggio, attento a parsare bene gli scores
    }

    @Override
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
        System.out.println("\n->Updating board<-\n");
        ServerToClientCommand command = (ServerToClientCommand) event;
        System.out.println("ricevuto \n"+ command.getMessage());
    }

}
