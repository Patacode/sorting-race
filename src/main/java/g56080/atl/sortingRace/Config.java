package g56080.atl.sortingRace;

public enum Config{

    EOTHREAD(-1);

    private final int value;

    private Config(int value){
        this.value = value;
    }

    public int get(){
        return value;
    }
}

