package it.polimi.se2018.view.cli.cliState;

public abstract class State {

    public abstract State transitionFunction(State currentState, String input);
    public abstract void outputFunction(State currentState, String input);

    class YourTurn extends State {
        @Override
        public State transitionFunction(State currentState, String input) {
            return null;
        }

        @Override
        public void outputFunction(State currentState, String input) {

        }
    }
}
