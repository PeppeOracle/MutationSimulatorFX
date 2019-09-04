package control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import simulation.logic.MutationSimulator;
import simulation.statistics.ParameterIndex;
import simulation.wrapper.Simulation;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerVariateStatistic2 extends ControllerMenu implements Initializable {

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
    }

    public void initializeLoadedStage(){
        initializeRadio();
        initializeStatistics();
        initializeChart();
    }

    private void initializeStatistics() {

    }

    public void initializeChart() {

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Differenze");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frequenza");

        areaChart = new AreaChart<String,Number>(xAxis,yAxis);

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

    public void setSimulationsList(ArrayList<Simulation> simulationsList) {
        this.simulationsList = simulationsList;
    }

    public void setMutationSimulator(MutationSimulator mutationSimulator) {
        this.mutationSimulator = mutationSimulator;
    }
}
