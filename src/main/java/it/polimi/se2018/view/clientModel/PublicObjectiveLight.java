package it.polimi.se2018.view.clientModel;

class PublicObjectiveLight {
    private String name;
    private String description;

    public PublicObjectiveLight(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public PublicObjectiveLight() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
