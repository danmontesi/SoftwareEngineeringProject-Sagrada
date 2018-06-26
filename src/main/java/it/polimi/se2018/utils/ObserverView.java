package it.polimi.se2018.utils;

import it.polimi.se2018.commands.server_to_client_command.*;

public interface ObserverView extends Observer{
    void updateWpc(RefreshWpcCommand refreshCommand);
    void updateTokens(RefreshTokensCommand refreshCommand);
    void updateRoundTrack(RefreshRoundTrackCommand refreshCommand);
    void updateDraftPool(RefreshDraftPoolCommand refreshCommand);
    void updateBoard(RefreshBoardCommand refreshCommand);
}
