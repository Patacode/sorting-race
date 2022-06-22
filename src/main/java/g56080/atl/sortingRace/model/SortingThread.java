package g56080.atl.sortingRace.model;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import g56080.atl.sortingRace.Config;
import g56080.atl.sortingRace.util.Observable;
import g56080.atl.sortingRace.util.ObservableConcrete;
import g56080.atl.sortingRace.util.Observer;
import g56080.atl.sortingRace.view.SortingInfo;
import g56080.atl.sortingRace.view.SortingType;

public class SortingThread extends Thread implements Observable{

    private final Observable obsc;
    private final Data sortingData;
    private final SortingType sortingType;
    private final Sorter<Integer> sorter;
    private final Comparator<Integer> comp;
    private int ops;

    public SortingThread(String name, Data sortingData, SortingType sortingType, Sorter<Integer> sorter, Comparator<Integer> comp){
        super(name);
        this.sortingData = sortingData;
        this.sortingType = sortingType;
        this.sorter = sorter;
        this.comp = comp;
        obsc = new ObservableConcrete();
    }

    @Override
    public void run(){
        Integer[] data;
        while((data = sortingData.getData()) != null){
            Integer[] cop = data;
            String startTime = String.format("%tH:%<tM:%<tS:%<tL", LocalDateTime.now());
            int time = Util.exec(() -> ops = sorter.sort(cop, comp));
            String endTime = String.format("%tH:%<tM:%<tS:%<tL", LocalDateTime.now());
            SortingInfo sortingInfo = new SortingInfo(sortingType, data.length, ops, time, startTime, endTime);
            notifyObservers(sortingInfo);
        }

        notifyObservers(Config.EOTHREAD.get());
    }

    @Override
    public void notifyObservers(Object arg){
        obsc.notifyObservers(arg);
    }

    @Override
    public void addObserver(Observer obs){
        obsc.addObserver(obs);
    }

    @Override
    public void removeObserver(Observer obs){
        obsc.removeObserver(obs);
    }
}

