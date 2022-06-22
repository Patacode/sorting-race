package g56080.atl.sortingRace.model;

import java.util.ArrayList;
import java.util.Comparator;

import g56080.atl.sortingRace.util.Observer;
import g56080.atl.sortingRace.util.Observable;
import g56080.atl.sortingRace.util.ObservableConcrete;
import g56080.atl.sortingRace.view.SortingType;

public class AppModel implements Model, Observer{

    private final Observable obsc;

    public AppModel(){
        obsc = new ObservableConcrete();
    }

    @Override
    public void addObserver(Observer obs){
        obsc.addObserver(obs);
    }

    @Override
    public void removeObserver(Observer obs){
        obsc.removeObserver(obs);
    }

    @Override
    public void notifyObservers(Object arg){
        obsc.notifyObservers(arg);
    }

    @Override
    public void update(Observable p, Object arg){
        /* intermediate between threads and view */
        notifyObservers(arg);
    }

    @Override
    public void sort(SortingType sortingType, Sorter<Integer> sorter, int size, Comparator<Integer> comp, int thread_count){
        Data sorting_data = new Data(10, size);
        Pool thread_pool = new Pool(thread_count, sorting_data, sortingType, sorter, comp);
        thread_pool.forEach(thread -> thread.addObserver(this));
        thread_pool.start();
    }
}

