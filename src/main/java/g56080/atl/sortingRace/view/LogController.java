package g56080.atl.sortingRace.view;

import java.util.List;

import g56080.atl.sortingRace.view.SortingType;

import g56080.atl.sortingRace.dto.SimulationDTO;
import g56080.atl.sortingRace.repository.SimulationRepository;

import javafx.beans.property.ReadOnlyObjectWrapper;

import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LogController{
    
    @FXML
    private TableView<SimulationDTO> logTable;

    @FXML
    private TableColumn<SimulationDTO, String> timestampCol;

    @FXML
    private TableColumn<SimulationDTO, SortingType> sortTypeCol;

    @FXML
    private TableColumn<SimulationDTO, Integer> maxSizeCol;

    public void initialize(){
        timestampCol.setCellValueFactory(cdf -> new ReadOnlyObjectWrapper<String>(cdf.getValue().timestamp())); 
        sortTypeCol.setCellValueFactory(cdf -> new ReadOnlyObjectWrapper<SortingType>(cdf.getValue().type()));
        maxSizeCol.setCellValueFactory(cdf -> new ReadOnlyObjectWrapper<Integer>(cdf.getValue().max_size()));

        SimulationRepository srepo = new SimulationRepository();
        List<SimulationDTO> sdtos = srepo.getAll();
        sdtos.forEach(sdto -> logTable.getItems().add(sdto));
    }
}

