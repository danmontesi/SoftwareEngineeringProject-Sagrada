package shared.utils;

import shared.commands.server_to_client_command.*;

public interface ObserverView extends Observer{
    void updateWpc(RefreshWpcCommand refreshCommand);
    void updateTokens(RefreshTokensCommand refreshCommand);
    void updateRoundTrack(RefreshRoundTrackCommand refreshCommand);
    void updateDraftPool(RefreshDraftPoolCommand refreshCommand);
    void updateBoard(RefreshBoardCommand refreshCommand);
}
