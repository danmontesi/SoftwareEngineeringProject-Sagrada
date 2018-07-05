package server;

import server.model.Action;
import server.model.COLOR;
import server.model.Model;
import server.model.Player;

import java.util.List;

/**
 *
 */
public class ToolcardData {
    private String toolCardName;
    private List<Action> toolCardActions;
    private Player playerUsingTool;

    private int indexFromDraftPool;
    private int indexFromRoundTrack;
    private int indexFromWPC;
    private int indexToWPC;
    private int dieValue;

    private COLOR savedColor;

    private boolean increaseValue;
    private boolean anotherAction;

    private Model oldModel;
    private int requiredTokensForLastToolUse;
    private int lastUsedToolCardNum;

    private boolean hasDoneMove;
    private int indexMovedDie;
    private int indexDieBeforeMoved;
    private String source;

    ToolcardData(String toolCardName, List<Action> toolCardActions, Player playerUsingTool) {
        this.toolCardName = toolCardName;
        this.toolCardActions = toolCardActions;
        this.playerUsingTool = playerUsingTool;
    }

    public Model removeOldModel(){
        Model temp = oldModel;
        oldModel = null;
        return temp;
    }

    void setOldModel(Model oldModel) {
        Model newModel = new Model(oldModel);
        this.oldModel = newModel;
    }

    void setRequiredTokensForLastToolUse(int requiredTokensForLastToolUse) {
        this.requiredTokensForLastToolUse = requiredTokensForLastToolUse;
    }

    void setLastUsedToolCardNum(int lastUsedToolCardNum) {
        this.lastUsedToolCardNum = lastUsedToolCardNum;
    }

    int getRequiredTokensForLastToolUse() {

        return requiredTokensForLastToolUse;
    }

    int getLastUsedToolCardNum() {
        return lastUsedToolCardNum;
    }

    int getIndexFromDraftPool() {
        return indexFromDraftPool;
    }

    void setIndexFromDraftPool(int indexFromDraftPool) {
        this.indexFromDraftPool = indexFromDraftPool;
    }

    int getIndexFromRoundTrack() {
        return indexFromRoundTrack;
    }

    void setIndexFromRoundTrack(int indexFromRoundTrack) {
        this.indexFromRoundTrack = indexFromRoundTrack;
    }

    int getIndexFromWPC() {
        return indexFromWPC;
    }

    void setIndexFromWPC(int indexFromWPC) {
        this.indexFromWPC = indexFromWPC;
    }

    int getIndexToWPC() {
        return indexToWPC;
    }

    void setIndexToWPC(int indexToWPC) {
        this.indexToWPC = indexToWPC;
    }

    boolean isIncreaseValue() {
        return increaseValue;
    }

    void setIncreaseValue(boolean increaseValue) {
        this.increaseValue = increaseValue;
    }

    boolean isAnotherAction() {
        return anotherAction;
    }

    void setAnotherAction(boolean anotherAction) {
        this.anotherAction = anotherAction;
    }

    int getDieValue() {
        return dieValue;
    }

    public void setDieValue(int dieValue) {
        this.dieValue = dieValue;
    }

    List<Action> getToolCardActions() {
        return toolCardActions;
    }

    public String getToolCardName() {
        return toolCardName;
    }

    COLOR getSavedColor() {
        return savedColor;
    }

    void setIndexMovedDie(int indexMovedDie) {
        this.indexMovedDie = indexMovedDie;
    }

    void setIndexDieBeforeMoved(int indexDieBeforeMoved) {
        this.indexDieBeforeMoved = indexDieBeforeMoved;
    }

    void setSource(String source) {
        this.source = source;
    }

    int getIndexMovedDie() {
        return indexMovedDie;
    }

    int getIndexDieBeforeMoved() {
        return indexDieBeforeMoved;
    }

    String getSource() {
        return source;
    }

    boolean hasDoneMove(){
        return hasDoneMove;
    }

    void setSavedColor(COLOR savedColor) {
        this.savedColor = savedColor;
    }

    void setHasDoneMove() {
        hasDoneMove=true;
    }

    public Player getPlayerUsingTool() {
        return playerUsingTool;
    }
}
