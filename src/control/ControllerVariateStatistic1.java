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

    }

    public void decreaseIndex(MouseEvent mouseEvent) {

    }

    public void increaseIndex(MouseEvent mouseEvent) {

    }

    public void nextPage(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/VariateStatistic2.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerVariateStatistic1 controllerVariateStatistic1= loader.getController();
        controllerVariateStatistic1.mainPane=mainPane;

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());

        /*
        comparisonGrid= (GridPane)mainPane.getScene().lookup("#comparisonGrid");
        informationsGrid= (VBox)mainPane.getScene().lookup("#informationsGrid");

        results=mutator.mutate();
        inizializeGridInformations();
        */

        variation = Double.valueOf(variationField.getText());
        variation = variation/100;
        iterations = Integer.valueOf(iterationsField.getText());

        System.out.println(variation+" "+iterations);

        simulationsList = new VariableSimulation(iterations, variation, simulation, mutationSimulator).getSimulation();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Differenze");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frequenza");

        areaChart = new AreaChart<String,Number>(xAxis,yAxis);

        areaChartAP = (AnchorPane)mainPane.getScene().lookup("#areaChartAP");
        radioBox = (VBox)mainPane.getScene().lookup("#radioBox");

        initializeRadio();
        initializeChart();
        initializeStatistics();

        saveSimulation();
    }

    private void saveSimulation() {

    }

    private void initializeStatistics() {

    }

    private void initializeChart() {

        ArrayList<XYChart.Series<String, Number>> dataSeriesList = new ArrayList<>();

        for(Simulation simulationItem: simulationsList){
            XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
            dataSeries.setName(simulationItem.getName());

            System.out.println(simulationItem.getListOfSimulationResults().get(0).getHashMapOfLabeledComparator().get("AMINOACIDS-DIFF"));

            ParameterIndex parameterIndex = new ParameterIndex(simulationItem.getComparatorsLabel().get(0), simulationItem.getListOfSimulationResults());

            System.out.println(simulationItem.getListOfSimulationResults().get(0).getHashMapOfLabeledComparator().get("AMINOACIDS-DIFF"));

            for(Point point : parameterIndex.getPointsFrequency()){
                dataSeries.getData().add(new XYChart.Data<String, Number>(""+point.getX(),point.getY()));
            }

            areaChart.getData().add(dataSeries);
        }

        areaChartAP.getChildren().clear();
        areaChartAP.getChildren().add(areaChart);
    }

    private void initializeRadio() {

    }

    public void setMutationSimulator(MutationSimulator mutationSimulator) {
        this.mutationSimulator = mutationSimulator;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void checkEqualOp(ActionEvent actionEvent) {
    }

    public void checkEqualNucleotides(ActionEvent actionEvent) {
    }

    public void checkEqualIndex(ActionEvent actionEvent) {
    }
}
