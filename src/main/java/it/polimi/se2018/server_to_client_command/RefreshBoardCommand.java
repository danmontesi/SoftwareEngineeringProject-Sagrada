package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.Model;

public class RefreshBoardCommand extends ServerToClientCommand{
            /**
             * That's the unique command that contains an object different from string and integer
             * Contains each player's view of the board.
             */
            private final Model model;
            private String message;

            public RefreshBoardCommand(Model model, String message){
                this.model = model;
                this.message = message;
            }
        }
