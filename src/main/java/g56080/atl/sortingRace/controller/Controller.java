package g56080.atl.sortingRace.controller;

import g56080.atl.sortingRace.model.Model;
import g56080.atl.sortingRace.view.SortingType;
import g56080.atl.sortingRace.model.Sorter;
import java.util.Comparator;

public class Controller{

    private final Model model;

    public Controller(Model model){
        this.model = model;
    }

    public void sort(SortingType sortingType, Sorter<Integer> sorter, int size, Comparator<Integer> comp, int thread_count){
        model.sort(sortingType, sorter, size, comp, thread_count);
    }
}

