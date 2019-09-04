package control;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import simulation.store.SimulationStore;
import simulation.wrapper.Simulation;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    ArrayList<Simulation> simulations;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("graphics/GestioneSimulazioni.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/ManageSimulations.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerManageSimulations controllerManageSimulations = loader.getController();
        controllerManageSimulations.mainPane = root;

        simulations = (ArrayList<Simulation>)SimulationStore.readAllItem("simulations");
        if(simulations == null){
            simulations = new ArrayList<>();
            SimulationStore.writeAllItem("simulations",simulations);
        }
        controllerManageSimulations.setSimulations(simulations);

        //controllerNewSimulation.mainPane.getChildren().clear();
        //controllerNewSimulation.mainPane.getChildren().setAll(root.getChildren());

        primaryStage.setTitle("Mutation Simulator");
        primaryStage.setScene(new Scene(root, 1080, 768));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ArrayList<Simulation> simulationsList = new ArrayList<>();
        for(Simulation item : simulations){
            simulationsList.add(item);
        }
        SimulationStore.writeAllItem("simulations",simulationsList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

