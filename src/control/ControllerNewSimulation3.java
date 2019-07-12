package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerNewSimulation3 extends ControllerMenu implements Initializable {

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
    VBox radioBox;
    @FXML
    AnchorPane barChartAP;
    BarChart<String,Number> barChart;

    NucleotidesDifferenceComparator nucleotidesDifferenceComparator;
    NucleotidesSingleDifferenceComparator nucleotidesSingleDifferenceComparator;
    AminoAcidsDifferenceComparator aminoAcidsDifferenceComparator;
    AminoAcidsSingleDifferenceComparator aminoAcidsSingleDifferenceComparator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comparators = new ArrayList<>();
        nucleotidesDifferenceComparator = new NucleotidesDifferenceComparator();
        nucleotidesSingleDifferenceComparator = new NucleotidesSingleDifferenceComparator();
        aminoAcidsDifferenceComparator = new AminoAcidsDifferenceComparator();
        aminoAcidsSingleDifferenceComparator = new AminoAcidsSingleDifferenceComparator();
    }

    public void checkNUCLEOTIDESSINGLEDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(nucleotidesSingleDifferenceComparator);
        } else{
            comparators.remove(nucleotidesSingleDifferenceComparator);
        }}

    public void checkAMINOACIDSSINGLEDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(aminoAcidsSingleDifferenceComparator);
        } else{
            comparators.remove(nucleotidesSingleDifferenceComparator);
        }}

    public void checkNUCLEOTIDESDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(nucleotidesDifferenceComparator);
        } else{
            comparators.remove(nucleotidesDifferenceComparator);
        }}

    public void checkAMINOACIDSDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(aminoAcidsDifferenceComparator);
        } else{
            comparators.remove(aminoAcidsDifferenceComparator);
        }}

    public void nextPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation4.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation3 controllerNewSimulation3= loader.getController();
        controllerNewSimulation3.mainPane=mainPane;
        controllerNewSimulation3.setMutator(mutator);

        controllerNewSimulation3.setSimulation(simulation);


        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());

        /*
        comparisonGrid= (GridPane)mainPane.getScene().lookup("#comparisonGrid");
        informationsGrid= (VBox)mainPane.getScene().lookup("#informationsGrid");

        results=mutator.mutate();
        inizializeGridInformations();
        */

        mutationSimulator = new MutationSimulator(mutator,iterations,comparators);

        controllerNewSimulation3.setMutationSimulator(mutationSimulator);

        simResults = mutationSimulator.simulate();

        parameterIndex = new ParameterIndex(comparators.get(0).getLabel(), simResults);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Differenze");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frequenza");

        barChart = new BarChart<String,Number>(xAxis,yAxis);

        barChartAP = (AnchorPane)mainPane.getScene().lookup("#barChartAP");
        radioBox = (VBox)mainPane.getScene().lookup("#radioBox");

        initializeRadio();
        initializeChart();
        initializeStatistics();

        saveSimulation();

        controllerNewSimulation3.setSimulation(simulation);
    }

    private void initializeRadio() {
         radioParametersGroup = new ToggleGroup();

         for(LabeledComparator comparator: comparators){
             RadioButton radio = new RadioButton(comparator.getLabel());
             radio.setOnAction(e->{
                 chooseParameter(e);
             });
             radio.setToggleGroup(radioParametersGroup);
         }
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
        radioBox.getChildren().addAll(mean,median,standardDeviation,variance);
    }

    private void initializeChart(){
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
        dataSeries1.setName(comparators.get(0).getLabel());

        for(Point point : parameterIndex.getPointsFrequency()){
            dataSeries1.getData().add(new XYChart.Data<String, Number>(""+point.getX(),point.getY()));
        }

        barChart.getData().add(dataSeries1);

        barChartAP.getChildren().clear();
        barChartAP.getChildren().add(barChart);
    }

    public void saveSimulation(){
        simulation = new Simulation(name, description, simResults);

        ArrayList<Simulation> simulations = null;
        simulations=(ArrayList<Simulation>)SimulationStore.readAllItem("simulations");
        if(simulations==null){
            simulations=new ArrayList<Simulation>();
        }
        simulations.add(simulation);
        SimulationStore.writeAllItem("simulations",simulations);

    }

    public void chooseParameter(ActionEvent actionEvent){
        //((RadioButton)actionEvent. getSource()). ;
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
}
