package it.polimi.se2018;

import it.polimi.se2018.MVC.CLIView;
import it.polimi.se2018.MVC.Controller;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println("Hello World di dan");
        Game model = new Game();
        CLIView view1 = new CLIView(model);
        Controller contr1 = new Controller(model, view1);



    }
}
