package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

public class ToolCardUse implements GameBoardAction {
    private String cardName;
    private String color;
    private int value;

    public ToolCardUse(String cardName, String color, Integer value) {
        this.cardName = cardName;
        if (color != null ) {
            this.color = color;
        }
        if (value != null) {
            this.value = value;
        }
    }

    public ToolCardUse(String cardName) {
        this.cardName=cardName;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public String getCardName() {
        return cardName;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }
}
