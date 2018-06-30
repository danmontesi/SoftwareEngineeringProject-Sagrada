package it.polimi.se2018.network.server;

import it.polimi.se2018.model.Action;
import it.polimi.se2018.model.COLOR;
import it.polimi.se2018.model.Model;

import java.util.List;

public class ToolcardData {
    private String toolcardName;
    private List<Action> toolcardActions;

    private int indexFromDraftpool;
    private int indexFromRoundTrack;
    private int indexFromWPC;
    private int indexToWPC;
    private int dieValue;

    private COLOR savedColor;

    private boolean increaseValue;
    private boolean anotherAction;

    public Model removeOldModel(){
        Model temp = oldModel;
        oldModel = null;
        return temp;
    }

    public void setOldModel(Model oldModel) {
        this.oldModel = oldModel;
    }

    private Model oldModel;

    public ToolcardData() {
    }

    public ToolcardData(String toolcardName, List<Action> toolcardActions) {
        this.toolcardName = toolcardName;
        this.toolcardActions = toolcardActions;
    }

    public int getIndexFromDraftpool() {
        return indexFromDraftpool;
    }

    public void setIndexFromDraftpool(int indexFromDraftpool) {
        this.indexFromDraftpool = indexFromDraftpool;
    }

    public int getIndexFromRoundTrack() {
        return indexFromRoundTrack;
    }

    public void setIndexFromRoundTrack(int indexFromRoundTrack) {
        this.indexFromRoundTrack = indexFromRoundTrack;
    }

    public int getIndexFromWPC() {
        return indexFromWPC;
    }

    public void setIndexFromWPC(int indexFromWPC) {
        this.indexFromWPC = indexFromWPC;
    }

    public int getIndexToWPC() {
        return indexToWPC;
    }

    public void setIndexToWPC(int indexToWPC) {
        this.indexToWPC = indexToWPC;
    }

    public boolean isIncreaseValue() {
        return increaseValue;
    }

    public void setIncreaseValue(boolean increaseValue) {
        this.increaseValue = increaseValue;
    }

    public boolean isAnotherAction() {
        return anotherAction;
    }

    public void setAnotherAction(boolean anotherAction) {
        this.anotherAction = anotherAction;
    }

    public int getDieValue() {
        return dieValue;
    }

    public void setDieValue(int dieValue) {
        this.dieValue = dieValue;
    }

    public List<Action> getToolcardActions() {
        return toolcardActions;
    }

    public void setToolcardActions(List<Action> toolcardActions) {
        this.toolcardActions = toolcardActions;
    }

    public String getToolcardName() {
        return toolcardName;
    }

    public void setToolcardName(String toolcardName) {
        this.toolcardName = toolcardName;
    }

    public COLOR getSavedColor() {
        return savedColor;
    }

    public void setSavedColor(COLOR savedColor) {
        this.savedColor = savedColor;
    }
}
