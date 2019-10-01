package control;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
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
import java.util.HashMap;
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

    @Override
    public void setResources(HashMap<String, Object> resources) {
        super.setResources(resources);
        if(resources.containsKey("mutationSimulator")){
            mutationSimulator = (MutationSimulator)resources.get("mutationSimulator");
        }
    }

    private void initializeRadio() {
        radioParametersGroup = new ToggleGroup();
        RadioButton radio=null;
        for(LabeledComparator comparator: simulation.getLabeledComparators()){
            printParameters(comparator);
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

    private void printParameters(LabeledComparator comparator){
        simResults = simulation.getListOfSimulationResults();
        parameterIndex = new ParameterIndex(comparator.getLabel(), simResults);
        System.out.println();
        System.out.println(comparator.getLabel());
        System.out.println("MEDIA: " + parameterIndex.getMean());
        System.out.println("MEDIANA: " + parameterIndex.getMedian());

        if(comparator instanceof LabeledCategoryComparator) {
            for (int i = 0; i < ((LabeledCategoryComparator) comparator).getCategoriesCounter(); i++) {
                System.out.println("CATEGORIA "+((LabeledCategoryComparator) comparator).getCategory(i) + ": " + parameterIndex.getPointsFrequency().get(i).getY());
            }
        }
    }

    private void initializeStatistics() {

        /*NumberFormat formatter = NumberFormat.getNumberIstance();
        formatter.setMaximumFractionsDigits(2);
        Double numero = 123.1258965;
        numero = Integer.parseInt(formatter.format(numero));*/

        Label positionIndex = new Label("Indici di posizione:");
        positionIndex.setStyle("-fx-font-weight: bold");
        Label mean = new Label("Media: " + (float)parameterIndex.getMean());
        Label median = new Label("Mediana: " + (float)parameterIndex.getMedian());

        String modas = "";
        for(int i = 0; i < parameterIndex.getModa().size(); i++){
            modas = modas + parameterIndex.getModa().get(i).toString()+"  ";
        }
        Label moda = new Label("Moda: " + modas);

        Label variabilityIndex = new Label("Indici di variabilità:");
        variabilityIndex.setStyle("-fx-font-weight: bold");
        Label standardDeviation = new Label("Deviazione Standard: " + (float)parameterIndex.getStandardDeviation());
        Label variance = new Label("Varianza: " + (float)parameterIndex.getVariance());
        Label scartoMedioAssoluto = new Label("Scarto medio assoluto:" + parameterIndex.getScartoMedioAssoluto());
        Label entropy = new Label("Entropia:" + parameterIndex.getEntropy());
        Label ampiezzaIntervalloVariazione = new Label("Ampiezza intervallo variazione" + parameterIndex.getAmpiezzaIntervalloVariazione());
        Label ceoffiecienteVariazione = new Label("Coefficiente di variazione" + parameterIndex.getCoefficienteDiVariazione());

        Label shapeIndex = new Label("Indici di forma:");
        shapeIndex.setStyle("-fx-font-weight: bold");
        Label skewness = new Label("Indice di asimmetria:" + parameterIndex.getSkewness());
        Label curtosi = new Label("Indice di curtosi:" + parameterIndex.getKurtosis());

        statisticsBox.getChildren().clear();
        statisticsBox.getChildren().addAll(mean,median,moda,standardDeviation,variance,scartoMedioAssoluto,entropy,ampiezzaIntervalloVariazione,ceoffiecienteVariazione,skewness,curtosi);
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

            dataSeries1.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " ", data.pieValueProperty()
                            )
                    )
            );
            ((PieChart)chart).setData(dataSeries1);
        }
        else{
            chart = new BarChart<String,Number>(xAxis,yAxis);

            XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
            dataSeries1.setName(parameterIndex.getName());

            for(Point point : parameterIndex.getPointsFrequency()){
                dataSeries1.getData().add(new XYChart.Data<String, Number>(""+(int)point.getX(),point.getY()));
            }

            ((BarChart)chart).getData().add(dataSeries1);
        }

        chartAP.getChildren().clear();
        chartAP.getChildren().add(chart);
    }

    public void saveSimulation() {
        Simulation simulationToRemove=null;
        for(Simulation item : simulations){
            if(item.getName().equals(simulation.getName())){
                simulations.remove(item);
                break;
            }
        }
        simulations.add(simulation);
        SimulationStore.writeAllItem("simulations",simulations);
    }

    public void chooseParameter(ActionEvent actionEvent){
        //String comparatorLabel=((RadioButton)actionEvent.getSource()).getText();
        String comparatorLabel = comparatorAct.getLabel();
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

        resources=simulation.getResources();
        if(resources==null){
            resources=new HashMap<>();
        }
    }

    public void variateStatistic(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/VariateStatistic1.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerVariateStatistic1 controllerVariateStatistic1 = loader.getController();
        controllerVariateStatistic1.mainPane = mainPane;
        resources.put("mutationSimulator",mutationSimulator);
        controllerVariateStatistic1.setResources(resources);
        if(mutationSimulator==null){
            mutationSimulator=new MutationSimulator((Mutator) resources.get("mutator"),(int) resources.get("iterations"),simulation.getLabeledComparators());
        }
        controllerVariateStatistic1.setMutationSimulator(mutationSimulator);
        controllerVariateStatistic1.setSimulation(simulation);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void previousPage(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation4.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation4 controllerNewSimulation4 = loader.getController();
        controllerNewSimulation4.mainPane = mainPane;
        controllerNewSimulation4.root = root;

        resources.put("comparators",simulation.getLabeledComparators());
        controllerNewSimulation4.setResources((HashMap)resources.clone());

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    @Override
    public void setSimulations(ArrayList<Simulation> simulations) {
        super.setSimulations(simulations);
        simResults = simulation.getListOfSimulationResults();
    }
}
