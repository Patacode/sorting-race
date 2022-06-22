package g56080.atl.sortingRace.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T>{
    
    private final T[] data;
    private int data_pointer;

    public ArrayIterator(T[] data){
        this.data = data;
    }

    @Override
    public boolean hasNext(){
        return data_pointer < data.length;
    }

    @Override
    public T next(){
        if(!hasNext()){
            throw new NoSuchElementException("No more elements");
        }

        return data[data_pointer++];
    }
}

