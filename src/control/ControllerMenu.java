package control;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import simulation.wrapper.Simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class ControllerMenu {

    protected AnchorPane mainPane;
    protected AnchorPane root;
    protected static HashMap<String,Object> resources;
    protected static ArrayList<Simulation> simulations;

    public void newSimulation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation1.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation1 controllerNewSimulation1 = loader.getController();
        controllerNewSimulation1.mainPane = mainPane;

        resources = new HashMap<String, Object>();

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = (Parent)loader.load();
        Main controller = (Main)loader.getController();
        controller.
                //Parent root = FXMLLoader.load(getClass().getResource("graphics/NewSimulation.fxml"));
                        actualStage.setScene(new Scene(root,1080,768));
                        */
    }

    public void singleMutation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/ExecuteMutation1.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerExecuteMutation1 controllerExecuteMutation1 = loader.getController();
        controllerExecuteMutation1.mainPane = mainPane;

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void recentSimulations(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/ManageSimulations.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerManageSimulations controllerManageSimulations = loader.getController();
        controllerManageSimulations.mainPane = mainPane;

        resources = new HashMap<String, Object>();
        controllerManageSimulations.setSimulations(simulations);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void setSimulations(ArrayList<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void setResources(HashMap<String, Object> resources) {
        this.resources = resources;
    }
}
