package g56080.atl.sortingRace.repository;

import java.util.List;

import g56080.atl.sortingRace.dao.SimulationDAO;
import g56080.atl.sortingRace.dto.SimulationDTO;

public class SimulationRepository implements Repository<Integer, SimulationDTO>{
    
    private final SimulationDAO dao;

    public SimulationRepository(){
        dao = SimulationDAO.instance();
    }

    @Override
    public SimulationDTO get(Integer key){
        return dao.select(key);
    }

    @Override
    public List<SimulationDTO> getAll(){
        return dao.selectAll();
    }

    @Override
    public Integer add(SimulationDTO sdto){
        Integer key = null;
        if(dao.select(sdto.key()) != null){
            key = dao.update(sdto);
        } else{
            key = dao.insert(sdto);
        }

        return key;
    }
}

