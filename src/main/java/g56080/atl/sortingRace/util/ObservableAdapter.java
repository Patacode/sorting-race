package g56080.atl.sortingRace.util;

import java.util.List;
import java.util.ArrayList;

public abstract class ObservableAdapter implements Observable{

    private final List<Observer> observers;

    public ObservableAdapter(){
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer obs){
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs){
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(Object arg){
        observers.forEach(obs -> obs.update(this, arg));
    }
}

