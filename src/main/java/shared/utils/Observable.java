package shared.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable{

    public List<Observer> getObservers() {
        return observers;
    }

    protected List<Observer> observers = new ArrayList<>();

    public void notify(Object event){
    for (Observer observer : observers)
        observer.update(event);
    }

    public void register(Observer observer){
        observers.add(observer);
    }

    public void deregister(Observer observer){
        observers.remove(observer);
    }
}
