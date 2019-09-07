package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import simulation.comparators.*;
import simulation.logic.MutationSimulator;
import simulation.logic.Mutator;
import simulation.statistics.ParameterIndex;
import simulation.store.SimulationStore;
import simulation.wrapper.Simulation;
import simulation.wrapper.SimulationResults;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerSimulationResult extends ControllerMenu implements Initializable {

    Mutator mutator;
    int iterations;
    String name,description;

    ToggleGroup radioParametersGroup;
    ArrayList<RadioButton> radioParameters;
    ArrayList<SimulationResults> simResults;

    MutationSimulator mutationSimulator;
    ArrayList<LabeledComparator> comparators;
    ParameterIndex parameterIndex;
    Simulation simulation;

    @FXML
    HBox radioBox;

    @FXML
    VBox statisticsBox;

    @FXML
    AnchorPane chartAP;
    Chart chart;
    LabeledComparator comparatorAct;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioBox.setSpacing(10);
        statisticsBox.setSpacing(5);
    }

    public void initializeLoadedStage(){
        initializeRadio();
    }

    private void initializeRadio() {
        radioParametersGroup = new ToggleGroup();
        RadioButton radio=null;
        for(LabeledComparator comparator: simulation.getLabeledComparators()){
            radio = new RadioButton(comparator.getLabel());
            radio.setOnAction(e->{
                comparatorAct=comparator;
                chooseParameter(e);
            });
            radio.setToggleGroup(radioParametersGroup);
            radioBox.getChildren().add(radio);
        }
        radio.fire();
    }

    private void initializeStatistics() {

        /*NumberFormat formatter = NumberFormat.getNumberIstance();
        formatter.setMaximumFractionsDigits(2);
        Double numero = 123.1258965;
        numero = Integer.parseInt(formatter.format(numero));*/

        Label mean = new Label("Media: " + (float)parameterIndex.getMean());
        Label median = new Label("Mediana: " + (float)parameterIndex.getMedian());
        Label standardDeviation = new Label("Deviazione Standard: " + (float)parameterIndex.getStandardDeviation());
        Label variance = new Label("Varianza: " + (float)parameterIndex.getVariance());
        statisticsBox.getChildren().clear();
        statisticsBox.getChildren().addAll(mean,median,standardDeviation,variance);
    }

    private void initializeChart(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Differenze");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frequenza");

        if(comparatorAct instanceof LabeledCategoryComparator) {
            chart = new PieChart();

            ObservableList<PieChart.Data> dataSeries1 = FXCollections.observableArrayList();
            for(int i = 0; i<((LabeledCategoryComparator) comparatorAct).getCategoriesCounter(); i++){
                PieChart.Data data = new PieChart.Data(((LabeledCategoryComparator) comparatorAct).getCategory(i), parameterIndex.getPointsFrequency().get(i).getY());
                dataSeries1.add(data);
            }
            ((PieChart)chart).setData(dataSeries1);
        }
        else{
            chart = new BarChart<String,Number>(xAxis,yAxis);

            XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
            dataSeries1.setName(parameterIndex.getName());

            for(Point point : parameterIndex.getPointsFrequency()){
                dataSeries1.getData().add(new XYChart.Data<String, Number>(""+point.getX(),point.getY()));
            }

            ((BarChart)chart).getData().add(dataSeries1);
        }

        chartAP.getChildren().clear();
        chartAP.getChildren().add(chart);
    }

    public void saveSimulation() {
        simulation = new Simulation(name, description, simResults,comparators);
        simulations.add(simulation);
        SimulationStore.writeAllItem("simulations",simulations);
    }

    public void chooseParameter(ActionEvent actionEvent){
        String comparatorLabel=((RadioButton)actionEvent.getSource()).getText();
        parameterIndex = new ParameterIndex(comparatorLabel, simResults);
        initializeChart();
        initializeStatistics();
    }

    public void setName(String name){
        this.name=name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMutator(Mutator mutator) {
        this.mutator=mutator;
    }

    public void setMutationSimulator(MutationSimulator mutationSimulator) {
        this.mutationSimulator = mutationSimulator;
    }

    public void setIterations(int iterations) {
        this.iterations=iterations;
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
        simResults = simulation.getListOfSimulationResults();
        parameterIndex = new ParameterIndex(simulation.getComparatorsLabel().get(0), simResults);
    }

    public void variateStatistic(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/VariateStatistic1.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerVariateStatistic1 controllerVariateStatistic1 = loader.getController();
        controllerVariateStatistic1.mainPane = mainPane;
        controllerVariateStatistic1.setMutationSimulator(mutationSimulator);
        controllerVariateStatistic1.setSimulation(simulation);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void previousPage(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation3.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation3 controllerNewSimulation3= loader.getController();
        controllerNewSimulation3.mainPane = mainPane;

        controllerNewSimulation3.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }
}
