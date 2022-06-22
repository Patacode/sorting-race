package g56080.atl.sortingRace.model;

import java.util.Comparator;

import g56080.atl.sortingRace.util.Observable;
import g56080.atl.sortingRace.view.SortingType;

public interface Model extends Observable {
    
    void sort(SortingType sortingType, Sorter<Integer> sorter, int size, Comparator<Integer> comp, int thread_count);
}

