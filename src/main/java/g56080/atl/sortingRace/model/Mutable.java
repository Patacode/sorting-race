package g56080.atl.sortingRace.model;

public class Mutable<T extends Number>{
    
    private T value;

    public Mutable(T value){
        this.value = value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public T value(){
        return value;
    }
}

