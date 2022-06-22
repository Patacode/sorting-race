package g56080.atl.sortingRace.util;

public interface Observable{

    void addObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObservers(Object arg);
}

