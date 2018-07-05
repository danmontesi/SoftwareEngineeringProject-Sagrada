package server.model;

public class Action {
    private ACTION_TYPE type;
    private String parameter;
    private String parameter2;

    public Action(ACTION_TYPE type, String parameter, String parameter2) {
        this.type = type;
        this.parameter = parameter;
        this.parameter2 = parameter2;
    }

    public ACTION_TYPE getType() {
        return type;
    }

    public String getParameter() {
        return parameter;
    }

    public String getParameter2() {
        return parameter2;
    }

    public boolean hasParameter() {
        return parameter!=null;
    }
}
