package g56080.atl.sortingRace.model;

import java.util.function.Supplier;
import java.util.List;

public class Data{
    
    private final Integer[][] datas;
    private int data_pointer;

    public Data(int data_count /* number of arrays to sort */, int data_size /* max array size */){
        int eff_size = 0;
        int to_add = data_size / data_count;
        datas = new Integer[data_count + 1][];
        for(int i = 0; i <= data_count; i++){
            datas[i] = new Integer[eff_size];
            Util.fillWith(datas[i], () -> (int) (Math.random() * 1000));
            eff_size += to_add;
        }
    }
    
    public synchronized Integer[] getData(){
        return data_pointer >= datas.length ? null : datas[data_pointer++];
    }
}

