package g56080.atl.sortingRace.model;

import java.util.Comparator;

public interface Sorter<T>{

    int sort(T[] array, Comparator<T> comp);
}

