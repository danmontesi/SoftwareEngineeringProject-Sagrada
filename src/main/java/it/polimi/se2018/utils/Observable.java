package it.polimi.se2018.utils;

import java.util.ArrayList;

public abstract class Observable{
    public ArrayList<Observer> observers;

    protected void notify(Object event){
    for (Observer observer : observers)
        observer.update(event);
    }

    protected void register(Observer observer){
        observers.add(observer);
    }

    protected void deregister(Observer observer){
        observers.remove(observer);
    }
}
