package g56080.atl.sortingRace.dto;

import g56080.atl.sortingRace.view.SortingType;

public record SimulationDTO(int id, String timestamp, SortingType type, int max_size) implements DTO<Integer>{

    public SimulationDTO(String timestamp, SortingType type, int max_size){
        this(-1, timestamp, type, max_size);
    }
    
    @Override
    public Integer key(){
        return id;
    }

    @Override
    public int hashCode(){
        return key().hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof SimulationDTO sdto){
            return sdto.key().equals(key());
        }

        return false;
    }

}
