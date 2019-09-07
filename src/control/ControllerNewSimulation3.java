package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.*;
import java.util.List;

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
    AnchorPane barChartAP,radioAP;
    BarChart<String,Number> barChart;

    NucleotidesDifferenceComparator nucleotidesDifferenceComparator;
    NucleotidesBooleanComparator nucleotidesBooleanComparator;
    AminoAcidsDifferenceComparator aminoAcidsDifferenceComparator;
    AminoAcidsBooleanComparator aminoAcidsBooleanComparator;
    LengthMutationComparator lengthComparator;

    ArrayList<String> pieOperationResults;
    ArrayList<String> barOperationResults;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comparators = new ArrayList<>();
        nucleotidesDifferenceComparator = new NucleotidesDifferenceComparator();
        nucleotidesBooleanComparator = new NucleotidesBooleanComparator();
        aminoAcidsDifferenceComparator = new AminoAcidsDifferenceComparator();
        aminoAcidsBooleanComparator = new AminoAcidsBooleanComparator();
        lengthComparator = new LengthMutationComparator();
        pieOperationResults = new ArrayList();
        barOperationResults = new ArrayList();
    }

    @Override
    public void setResources(HashMap<String, Object> resources) {
        super.setResources(resources);

        if(resources.containsKey("name")){
            name = (String)resources.get("name");
        }
        if(resources.containsKey("description")){
            description = (String)resources.get("description");
        }
        if(resources.containsKey("iterations")){
            iterations = (int)resources.get("iterations");
        }
        if(resources.containsKey("mutator")){
            mutator = (Mutator)resources.get("mutator");
        }
        if(resources.containsKey("comparatorsRadio")){
            radioBox.getChildren().clear();
            radioBox.getChildren().add((VBox)resources.get("comparatorsRadio"));
        }
        if(resources.containsKey("comparators")){
            comparators = (ArrayList<LabeledComparator>)resources.get("comparators");
        }
    }

    public void checkNUCLEOTIDESSINGLEDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(nucleotidesBooleanComparator);
        } else{
            comparators.remove(nucleotidesBooleanComparator);
        }}

    public void checkAMINOACIDSSINGLEDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(aminoAcidsBooleanComparator);
        } else{
            comparators.remove(nucleotidesBooleanComparator);
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

    public void checkLENGTHDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(lengthComparator);
        } else{
            comparators.remove(lengthComparator);
        }}

    public void checkInserimentoPie(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            pieOperationResults.add("numberOfEntries");
        } else{
            pieOperationResults.remove("numberOfEntries");
        }}

    public void checkRimozionePie(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            pieOperationResults.add("numberOfRemovals");
        } else{
            pieOperationResults.remove("numberOfRemovals");
        }}

    public void checkInvarianzaPie(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            pieOperationResults.add("numberOfInvariances");
        } else{
            pieOperationResults.remove("numberOfInvariances");
        }}

    public void checkSostituzionePie(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            pieOperationResults.add("numberOfReplacements");
        } else{
            pieOperationResults.remove("numberOfReplacements");
        }}

    public void checkInserimentoBar(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            barOperationResults.add("numberOfEntries");
        } else{
            barOperationResults.remove("numberOfEntries");
        }}

    public void checkRimozioneBar(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            barOperationResults.add("numberOfRemovals");
        } else{
            barOperationResults.remove("numberOfRemovals");
        }}

    public void checkSostituzioneBar(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            barOperationResults.add("numberOfReplacements");
        } else{
            barOperationResults.remove("numberOfReplacements");
        }}

    public void checkInvarianzaBar(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            barOperationResults.add("numberOfInvariances");
        } else{
            barOperationResults.remove("numberOfInvariances");
        }}

    public void nextPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/SimulationResult.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerSimulationResult controllerSimulationResult= loader.getController();
        controllerSimulationResult.mainPane=mainPane;

        resources.put("comparators",comparators);
        resources.put("comparatorsRadio",radioBox);

        mutationSimulator = new MutationSimulator(mutator,iterations,comparators);
        simResults = mutationSimulator.simulate();

        controllerSimulationResult.setMutationSimulator(mutationSimulator);
        simulation=new Simulation(name,description,simResults,comparators);
        controllerSimulationResult.setSimulation(simulation);

        controllerSimulationResult.initializeLoadedStage();
        controllerSimulationResult.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());

        /*
        comparisonGrid= (GridPane)mainPane.getScene().lookup("#comparisonGrid");
        informationsGrid= (VBox)mainPane.getScene().lookup("#informationsGrid");

        results=mutator.mutate();
        inizializeGridInformations();
        */
    }

    public void previousPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation2.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation2 controllerNewSimulation2= loader.getController();
        controllerNewSimulation2.mainPane=mainPane;

        resources.put("comparators",comparators);
        resources.put("comparatorsRadio",radioBox);

        controllerNewSimulation2.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());

        /*
        comparisonGrid= (GridPane)mainPane.getScene().lookup("#comparisonGrid");
        informationsGrid= (VBox)mainPane.getScene().lookup("#informationsGrid");

        results=mutator.mutate();
        inizializeGridInformations();
        */
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
