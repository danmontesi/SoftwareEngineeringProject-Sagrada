package it.polimi.se2018.model;

public class Action {
    private ACTION_TYPE type;
    private String parameter;
    private String parameter2;

    public Action(ACTION_TYPE type) {
        this.type = type;
    }

    public Action(ACTION_TYPE type, String parameter) {
        this.type = type;
        this.parameter = parameter;
    }

    public Action(ACTION_TYPE type, String parameter, String parameter2) {
        this.type = type;
        this.parameter = parameter;
        this.parameter2 = parameter2;
    }

    public ACTION_TYPE getType() {
        return type;
    }

    public void setType(ACTION_TYPE type) {
        this.type = type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }
}
