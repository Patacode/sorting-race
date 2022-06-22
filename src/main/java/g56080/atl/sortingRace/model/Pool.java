package g56080.atl.sortingRace.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import g56080.atl.sortingRace.view.SortingType;

public class Pool implements Iterable<SortingThread>{

    private final SortingThread[] threads;

    public Pool(int thread_count, Data sortingData, SortingType sortingType, Sorter<Integer> sorter, Comparator<Integer> comp){
        threads = new SortingThread[thread_count];
        for(int i = 0; i < threads.length; i++){
            threads[i] = new SortingThread("SortingThread " + i, sortingData, sortingType, sorter, comp);
        }
    }

    public void start(){
        Arrays.stream(threads).forEach(th -> th.start());
    }

    @Override
    public Iterator<SortingThread> iterator(){
        return new ArrayIterator<>(threads);
    }
}

