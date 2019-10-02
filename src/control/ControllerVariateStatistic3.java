package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import simulation.comparators.LabeledCategoryComparator;
import simulation.comparators.LabeledComparator;
import simulation.logic.MutationSimulator;
import simulation.logic.Mutator;
import simulation.logic.VariableSimulation;
import simulation.statistics.ParameterIndex;
import simulation.store.SimulationStore;
import simulation.wrapper.Simulation;
import simulation.wrapper.SimulationResults;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerVariateStatistic3 extends ControllerMenu implements Initializable {

    int iterations;
    String name,description;

    ToggleGroup radioParametersGroup;
    ArrayList<RadioButton> radioParameters;
    ArrayList<SimulationResults> simResults;

    VariableSimulation variableSimulation;
    ArrayList<LabeledComparator> comparators;
    ParameterIndex[] parameterIndexes;
    MutationSimulator mutationSimulator;
    Simulation simulation;
    String index;
    double[] values;


    @FXML
    ComboBox<String> comboIndex;
    @FXML
    HBox radioBox;

    @FXML
    VBox statisticsBox;

    @FXML
    AnchorPane chartAP;
    Chart chart;
    LabeledComparator comparatorAct;
    private static final String mean="Media", median="Mediana", deviation="Deviazione Stardard", variance="Varianza", frequence="Frequenza",scartoMedioAssoluto="Scarto medio assoluto",entropia="Entropia",ampiezzaIntervalloVariazione="Ampiezza intervallo di variazione",coefficienteDiVariazione="Coefficiente di variazione",indiceDiAsimmetria="Indice di asimmetria",indiceDiCurtosi="Indice di curtosi";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioBox.setSpacing(10);
        statisticsBox.setSpacing(5);
        comboIndex.getItems().addAll(mean,median,deviation,variance,scartoMedioAssoluto,entropia,ampiezzaIntervalloVariazione,coefficienteDiVariazione,indiceDiAsimmetria,indiceDiCurtosi,frequence);

        comboIndex.valueProperty().addListener((obs, oldItem, newItem) -> {
            setValues(newItem);
            chooseParameter(null);
        });

    }

    @Override
    public void setResources(HashMap<String, Object> resources) {
        super.setResources(resources);
        if(resources.containsKey("mutationSimulator")){
            mutationSimulator = (MutationSimulator)resources.get("mutationSimulator");
        }
    }

    public void initializeLoadedStage(){
        initializeRadio();
    }

    private void initializeRadio() {
        radioParametersGroup = new ToggleGroup();
        RadioButton radio=null;
        for(LabeledComparator comparator: comparators){
            radio = new RadioButton(comparator.getLabel());
            radio.setOnAction(e->{
                comparatorAct=comparator;
                chooseParameter(e);
            });
            radio.setToggleGroup(radioParametersGroup);
            radioBox.getChildren().add(radio);
            comparatorAct=comparator;
        }
        radio.fire();
    }

    private void initializeStatistics() {

        /*NumberFormat formatter = NumberFormat.getNumberIstance();
        formatter.setMaximumFractionsDigits(2);
        Double numero = 123.1258965;
        numero = Integer.parseInt(formatter.format(numero));*/
/*
        Label mean = new Label("Media: " + (float)parameterIndex.getMean());
        Label median = new Label("Mediana: " + (float)parameterIndex.getMedian());
        Label standardDeviation = new Label("Deviazione Standard: " + (float)parameterIndex.getStandardDeviation());
        Label variance = new Label("Varianza: " + (float)parameterIndex.getVariance());
        statisticsBox.getChildren().clear();
        statisticsBox.getChildren().addAll(mean,median,standardDeviation,variance);
        */
    }



    private void initializeChart(){
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Variazione probabilità");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(index);

        if(comparatorAct instanceof LabeledCategoryComparator) {
            chart = new ScatterChart<Number,Number>(xAxis,yAxis);

            XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
            dataSeries1.setName(parameterIndexes[0].getName());

            for(int i=0; i<parameterIndexes.length;i++){
                dataSeries1.getData().add(new XYChart.Data(i,parameterIndexes[i].getPointsFrequency().get(1).x));
            }
            /*for(Point point : parameterIndex.getPointsFrequency()){
                dataSeries1.getData().add(new XYChart.Data<String, Number>(""+point.getX(),point.getY()));
            }*/

            ((ScatterChart)chart).getData().add(dataSeries1);
        }
        else{
            chart = new ScatterChart<Number,Number>(xAxis,yAxis);

            XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
            dataSeries1.setName(parameterIndexes[0].getName());

            for(int i=0; i<parameterIndexes.length;i++){
                dataSeries1.getData().add(new XYChart.Data(i,values[i]));
            }
            /*for(Point point : parameterIndex.getPointsFrequency()){
                dataSeries1.getData().add(new XYChart.Data<String, Number>(""+point.getX(),point.getY()));
            }*/

            ((ScatterChart)chart).getData().add(dataSeries1);
        }

        chartAP.getChildren().clear();
        chartAP.getChildren().add(chart);
    }

    private void setValues(String index){
        switch (index){
            case mean:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getMean();
                }
                break;
            }
            case median:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getMedian();
                }
                break;
            }
            case deviation:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getStandardDeviation();
                }
                break;
            }
            case variance:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getVariance();
                }
                break;
            }
            case scartoMedioAssoluto:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getScartoMedioAssoluto();
                }
                break;
            }
            case ampiezzaIntervalloVariazione:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getAmpiezzaIntervalloVariazione();
                }
                break;
            }
            case coefficienteDiVariazione:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getCoefficienteDiVariazione();
                }
                break;
            }
            case indiceDiAsimmetria:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getSkewness();
                }
                break;
            }
            case indiceDiCurtosi:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getKurtosis();
                }
                break;
            }
            case entropia:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getEntropy();
                }
                break;
            }
            case frequence:{
                for(int i=0;i<parameterIndexes.length;i++){
                    values[i]=parameterIndexes[i].getPointsFrequency().get(1).getY();
                }
                break;
            }
            default: {
                break;
            }
        }
        this.index=index;
    }

    public void chooseParameter(ActionEvent actionEvent){
        //String comparatorLabel=((RadioButton)actionEvent.getSource()).getText();
        String comparatorLabel = comparatorAct.getLabel();
        if(comparatorAct instanceof LabeledCategoryComparator){
            //comboIndex.getItems().clear();
            //comboIndex.getItems().removeAll(mean,median,deviation,variance);
            //comboIndex.getItems().addAll(frequence);
        } else {
            //comboIndex.removeAll(frequence);
            //comboIndex.getItems().addAll(mean,median,deviation,variance);
        }
        parameterIndexes = variableSimulation.getParameterIndicesByParameter(comparatorLabel);
        if(values==null){
            values=new double[parameterIndexes.length];
        }
        setValues(comboIndex.getValue());

        System.out.println(index);
        for(double value : values){
            System.out.println(value);
        }

        initializeChart();
        initializeStatistics();
    }

    public void setName(String name){
        this.name=name;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void previousPage(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation4.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation4 controllerNewSimulation4 = loader.getController();
        controllerNewSimulation4.mainPane = mainPane;
        controllerNewSimulation4.root = root;

        controllerNewSimulation4.setResources((HashMap)resources.clone());

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    @Override
    public void setSimulations(ArrayList<Simulation> simulations) {
        super.setSimulations(simulations);
        simResults = simulation.getListOfSimulationResults();
    }

    public void setVariableSimulation(VariableSimulation variableSimulation) {
        this.variableSimulation = variableSimulation;
    }

    public void setComparators(ArrayList<LabeledComparator> comparators) {
        this.comparators = comparators;
    }
}
