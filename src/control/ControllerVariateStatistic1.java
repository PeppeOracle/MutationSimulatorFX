package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import simulation.logic.MutationSimulator;
import simulation.logic.VariableSimulation;
import simulation.statistics.ParameterIndex;
import simulation.wrapper.Simulation;
import simulation.comparators.LabeledComparator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerVariateStatistic1 extends ControllerMenu implements Initializable {

    @FXML
    AnchorPane probabilitiesBoxAP;

    ControllerProbabilities controllerProbabilities;

    MutationSimulator mutationSimulator;
    Simulation simulation;
    int iterations;
    double[][][] variation;
    ArrayList<Simulation> simulationsList;
    ArrayList<ParameterIndex> parameterIndexList;
    VariableSimulation variableSimulation;

    @FXML
    TextField iterationsField,variationField;

    @FXML
    VBox radioBox;
    @FXML
    AnchorPane areaChartAP;
    AreaChart<String,Number> areaChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeProbabilitiesController();
    }

    @Override
    public void setResources(HashMap<String, Object> resources) {
        super.setResources(resources);
        if(resources.containsKey("mutationSimulator")){
            mutationSimulator = (MutationSimulator)resources.get("mutationSimulator");
        }
    }

    private void initializeProbabilitiesController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/ProbabilitiesBox.fxml"));
            probabilitiesBoxAP.getChildren().clear();
            probabilitiesBoxAP.getChildren().add((AnchorPane) loader.load());
            controllerProbabilities = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/VariateStatistic3.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerVariateStatistic3 controllerVariateStatistic3 = loader.getController();
        controllerVariateStatistic3.mainPane=mainPane;

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());

        variation = controllerProbabilities.readProbabilitiesFromGrid();
        iterations = Integer.valueOf(iterationsField.getText());

        variableSimulation= new VariableSimulation(iterations, variation, simulation, mutationSimulator);
        variableSimulation.executeSimulations();

        ArrayList<LabeledComparator> comparators= simulation.getLabeledComparators();;

        controllerVariateStatistic3.setResources(resources);

        controllerVariateStatistic3.setSimulation(simulation);
        controllerVariateStatistic3.setMutationSimulator(mutationSimulator);

        controllerVariateStatistic3.setVariableSimulation(variableSimulation);
        controllerVariateStatistic3.setComparators(comparators);
        controllerVariateStatistic3.initializeLoadedStage();

        //controllerVariateStatistic2.setSimulationsList(simulationsList);

        //controllerVariateStatistic2.initializeLoadedStage();

        /*
        comparisonGrid= (GridPane)mainPane.getScene().lookup("#comparisonGrid");
        informationsGrid= (VBox)mainPane.getScene().lookup("#informationsGrid");

        results=mutator.mutate();
        inizializeGridInformations();
        */


        System.out.println(variation+" "+iterations);


        saveSimulation();
    }

    private void saveSimulation() {

    }

    public void setMutationSimulator(MutationSimulator mutationSimulator) {
        this.mutationSimulator = mutationSimulator;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
}
