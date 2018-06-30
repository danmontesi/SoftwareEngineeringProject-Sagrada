package it.polimi.se2018.view.cli.cliState;

public enum INPUT_STATE {
    YOUR_TURN,
    NOT_YOUR_TURN,
    CHOOSE_WPC,
    PLACE_DIE_INDEX,
    PLACE_DIE_ROW_COLUMN,
    TOOLCARD_CHOICE,
    REPLY_ANOTHER_ACTION,
    REPLY_DIE_VALUE,
    REPLY_INCREASE_DECREASE,
    REPLY_PICK_DIE,
    REPLY_PLACE_DIE,
    END_GAME;

    public static INPUT_STATE nextState(INPUT_STATE currentState, String input){
        INPUT_STATE nextState;

        //undo action
        if (input.equals("u")){
            //edit @dan(prima c'era Action interrupted, ma verrà scritto dopo automaticamente. Quindi l'ho tolto altrimenti viene scritto 2 volte
            if(currentState.equals(NOT_YOUR_TURN)){
                nextState = NOT_YOUR_TURN;
            } else {
                nextState = YOUR_TURN;
            }

        } else if((input.equals("d")) && currentState.equals(YOUR_TURN)){
            nextState = PLACE_DIE_INDEX;
            //use toolcard
        } else if(currentState.equals(PLACE_DIE_INDEX)){
            nextState = PLACE_DIE_ROW_COLUMN;
        } else if(currentState.equals(PLACE_DIE_ROW_COLUMN)) {
            nextState = YOUR_TURN;
        } else if ((input.equals("t")) && currentState.equals(YOUR_TURN)){
            nextState = TOOLCARD_CHOICE;
        } else if ((input.equals("p")) && currentState.equals(YOUR_TURN)){
            nextState = NOT_YOUR_TURN;
        }
        else {
            nextState = currentState;
        }
        return nextState;
    }
}
