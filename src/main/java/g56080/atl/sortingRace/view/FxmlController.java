package g56080.atl.sortingRace.view;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.stream.Stream;

import g56080.atl.sortingRace.controller.Controller;
import g56080.atl.sortingRace.dto.SimulationDTO;
import g56080.atl.sortingRace.model.Model;
import g56080.atl.sortingRace.model.Util;
import g56080.atl.sortingRace.repository.SimulationRepository;
import g56080.atl.sortingRace.util.Observable;
import g56080.atl.sortingRace.util.Observer;

import javafx.application.Platform;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleListProperty;

import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Stage;

public class FxmlController implements Observer{

    // TableView
    @FXML
    private TableView<SortingInfo> table;

    @FXML
    private TableColumn<SortingInfo, SortingType> nameCol;

    @FXML
    private TableColumn<SortingInfo, Integer> sizeCol;

    @FXML
    private TableColumn<SortingInfo, Integer> swapCol;

    @FXML
    private TableColumn<SortingInfo, Integer> durationCol;


    // chart
    @FXML
    private LineChart<Number, Number> chart;


    // spinner 
    @FXML
    private Spinner<Integer> threadSpinner;

    
    // dropdowns
    @FXML
    private ChoiceBox<SortingType> sortChoice;

    @FXML
    private ChoiceBox<Integer> configurationChoice;


    // progression
    @FXML
    private ProgressBar progressBar;


    // button
    @FXML
    private Button start;


    // labels
    @FXML
    private Label leftStatus;

    @FXML
    private Label rightStatus;

    
    // menu items
    @FXML
    private MenuItem aboutItem;

    @FXML
    private MenuItem quitItem;


    // others
    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private MenuItem logs;

    private final Model model;
    private final Controller controller;

    public FxmlController(Model model, Controller controller){
        if(model == null || controller == null)
            throw new NullPointerException("Null argument(s)");

        this.model = model;
        this.controller = controller;

        model.addObserver(this);
    }

    @FXML
    public void startClickHandler(ActionEvent ev){
        XYChart.Series<Number, Number> series = chart
            .getData()
            .stream()
            .reduce((left, right) -> left.getName().equals(sortChoice.getValue().toString()) ? left : right)
            .get();
        
        series.getData().clear();
        progressBar.setProgress(0.0);
        controller.sort(sortChoice.getValue(), switch(sortChoice.getValue()){
            case BUBBLE -> Util::bubbleSort;
            case MERGE -> Util::mergeSort;
            case INSERTION -> Util::insertionSort;
        }, configurationChoice.getValue(), Integer::compare, threadSpinner.getValue());

        leftStatus.setText("Active threads: " + threadSpinner.getValue());

        // add log for the current sorting operation
        SimulationRepository srepo = new SimulationRepository(); // repo

        // data
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = ldt.format(pattern);
        SortingType type = sortChoice.getValue();
        int max_size = configurationChoice.getValue();

        SimulationDTO sdto = new SimulationDTO(timestamp, type, max_size); // dto
        srepo.add(sdto); // insertion
    }

    @FXML
    public void logClickHandler(ActionEvent ev) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LogScene.fxml"));
        loader.setController(new LogController());

        Scene logScene = new Scene(loader.load());
        Stage logStage = new Stage();

        logStage.setScene(logScene);
        logStage.setTitle("Simulation Logs");
        logStage.show();
    }
    
    public void initialize(){
        nameCol.setCellValueFactory(cdf -> new ReadOnlyObjectWrapper<SortingType>(cdf.getValue().type())); 
        sizeCol.setCellValueFactory(cdf -> new ReadOnlyObjectWrapper<Integer>(cdf.getValue().size()));
        swapCol.setCellValueFactory(cdf -> new ReadOnlyObjectWrapper<Integer>(cdf.getValue().ops()));
        durationCol.setCellValueFactory(cdf -> new ReadOnlyObjectWrapper<Integer>(cdf.getValue().time()));

        threadSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1, 1));

        sortChoice.getItems().addAll(SortingType.values());
        sortChoice.setValue(sortChoice.getItems().get(0));
        configurationChoice.getItems().addAll(Stream.iterate(100, v -> v <= 1_000_000, v -> v * 10).toArray(size -> new Integer[size]));
        configurationChoice.setValue(configurationChoice.getItems().get(0));

        for(SortingType type : SortingType.values()){
            chart.getData().add(new XYChart.Series<>(type.toString(), FXCollections.observableArrayList()));
        }

        leftStatus.setText("Active threads: 0");
        rightStatus.setText("Last execution | Start: ... - End: ... - Duration: ... ms");
    }

    @Override
    public void update(Observable o, Object arg){
        if(arg instanceof SortingInfo sortingInfo){ // sorting info (thread may still running)
            Platform.runLater(() -> {
                XYChart.Series<Number, Number> series = chart
                    .getData()
                    .stream()
                    .reduce((left, right) -> left.getName().equals(sortingInfo.type().toString()) ? left : right)
                    .get();

                table.getItems().add(sortingInfo);
                series.getData().add(new XYChart.Data<>(sortingInfo.size(), sortingInfo.ops()));
                progressBar.setProgress(progressBar.getProgress() + 0.1);
                rightStatus.setText(String.format("Last execution | Start: %s - End: %s - Duration: %d ms", sortingInfo.startTime(), sortingInfo.endTime(), sortingInfo.time()));
            });
        } else{ // a thread has finished its work 
            Platform.runLater(() -> {
                Integer activeThreads = Integer.valueOf(leftStatus.getText().substring(leftStatus.getText().lastIndexOf(' ') + 1));
                leftStatus.setText("Active threads : " + (activeThreads - 1));
            });
        }
    }
}

