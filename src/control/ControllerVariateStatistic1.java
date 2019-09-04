package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import simulation.logic.MutationSimulator;
import simulation.logic.VariableSimulation;
import simulation.statistics.ParameterIndex;
import simulation.wrapper.Simulation;
import simulation.wrapper.SimulationResults;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerVariateStatistic1 extends ControllerMenu implements Initializable {

    @FXML
    AnchorPane probabilitiesBoxAP;

    ControllerProbabilities controllerProbabilities;

    MutationSimulator mutationSimulator;
    Simulation simulation;
    int iterations;
    double variation;
    ArrayList<Simulation> simulationsList;
    ArrayList<ParameterIndex> parameterIndexList;

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/VariateStatistic2.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerVariateStatistic2 controllerVariateStatistic2= loader.getController();
        controllerVariateStatistic2.mainPane=mainPane;

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());

        variation = Double.valueOf(controllerProbabilities.readProbabilitiesFromGrid()[0][0][0]);
        iterations = Integer.valueOf(iterationsField.getText());

        simulationsList = new VariableSimulation(iterations, variation, simulation, mutationSimulator).getSimulation();

        controllerVariateStatistic2.setSimulationsList(simulationsList);

        controllerVariateStatistic2.initializeLoadedStage();

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
