package control;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import simulation.store.SimulationStore;
import simulation.wrapper.Simulation;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerManageSimulations extends ControllerMenu implements Initializable{

    @FXML
    GridPane simulationsGrid;

    @FXML
    AnchorPane gestioneAP;

    int gridRows;
    String name;
    RowConstraints rowConstraints;
    ColumnConstraints nameColumnCostraints, descriptionColumnCostraints, dateColumnCostraints, optionsColumnCostraints;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void resizeGrid(){
        int j;

        for(int i=0;i<3;i++){
            for(j=0;j<3;j++){
                Label node = null;
                switch (j){
                    case 0:
                        node = new Label("Nome Simulazione ");
                        break;
                    case 1:
                        node = new Label("GG/MM/YYYY ");
                        break;
                    case 2:
                        node = new Label("Descrizione Simulazione " + (gridRows+1));
                        break;
                }

                simulationsGrid.setConstraints(node,j,gridRows);
                simulationsGrid.getChildren().add(node);
            }
            HBox optionImages = null;
            try {
                optionImages = FXMLLoader.load(getClass().getResource("graphics/SimulationOptions.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            simulationsGrid.setConstraints(optionImages,j,gridRows);
            simulationsGrid.getChildren().add(optionImages);

            simulationsGrid.getRowConstraints().add(gridRows, rowConstraints);
            gridRows++;
        }
    }

    @Override
    public void setSimulations(ArrayList<Simulation> simulations) {
        super.setSimulations(simulations);
        simulationsGrid.getChildren().clear();

        rowConstraints = new RowConstraints();
        nameColumnCostraints = new ColumnConstraints();
        descriptionColumnCostraints = new ColumnConstraints();
        dateColumnCostraints = new ColumnConstraints();
        optionsColumnCostraints = new ColumnConstraints();
        nameColumnCostraints.setPercentWidth(29);
        descriptionColumnCostraints.setPercentWidth(45);
        dateColumnCostraints.setPercentWidth(8);
        optionsColumnCostraints.setPercentWidth(18);

        simulationsGrid.getColumnConstraints().clear();
        simulationsGrid.getRowConstraints().clear();
        simulationsGrid.getColumnConstraints().addAll(nameColumnCostraints, descriptionColumnCostraints,dateColumnCostraints,optionsColumnCostraints);

        rowConstraints.setMinHeight(50);
        //rowConstraints.setPrefHeight(100);

        int j;

        for(gridRows=0; gridRows < simulations.size(); gridRows++){
            int gridNumber=gridRows;
            Simulation simulation = simulations.get(gridRows);
            for(j=0;j<3;j++){
                Label node = null;
                switch (j){
                    case 0:
                        node = new Label(simulation.getName());
                        break;
                    case 1:
                        node = new Label(simulation.getDescr());
                        break;
                    case 2:
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = simulation.getDate();
                        node = new Label(formatter.format(date));
                        break;
                }
                node.setPadding(new Insets(0,0,0,10));
                simulationsGrid.setConstraints(node,j,gridRows);
                //simulationsGrid.getColumnConstraints().add(gridRows*4+j, nameColumnCostraints);
                simulationsGrid.getChildren().add(node);
            }
            //simulationsGrid.getColumnConstraints().add(gridRows*4+j, nameColumnCostraints);

            HBox optionImages = null;
            try {
                optionImages = FXMLLoader.load(getClass().getResource("graphics/SimulationOptions.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            optionImages.getChildren().get(0).setOnMouseReleased(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/SimulationResult.fxml"));
                AnchorPane root=null;

                try {
                    root = (AnchorPane) loader.load();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ControllerSimulationResult controllerSimulationResult= loader.getController();
                controllerSimulationResult.mainPane=mainPane;

                controllerSimulationResult.setSimulation(simulation);
                controllerSimulationResult.initializeLoadedStage();

                mainPane.getChildren().clear();
                mainPane.getChildren().setAll(root.getChildren());
            });
            optionImages.getChildren().get(1).setOnMouseReleased(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation1.fxml"));
                AnchorPane root=null;

                try {
                    root = (AnchorPane) loader.load();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ControllerNewSimulation1 controllerNewSimulation1= loader.getController();
                controllerNewSimulation1.mainPane=mainPane;

                controllerNewSimulation1.setResources((HashMap)simulation.getResources().clone());

                mainPane.getChildren().clear();
                mainPane.getChildren().setAll(root.getChildren());
            });
            optionImages.getChildren().get(2).setOnMouseReleased(e->{
                simulations.remove(simulation);
                setSimulations(simulations);
                //simulationsGrid.getChildren().remove(1+(gridNumber)*4,gridNumber*4 + 5);
            });
            simulationsGrid.setConstraints(optionImages,j,gridRows);
            simulationsGrid.getChildren().add(optionImages);

            simulationsGrid.getRowConstraints().add(gridRows, rowConstraints);
        }
        simulationsGrid.setGridLinesVisible(false);
        simulationsGrid.setGridLinesVisible(true);
    }

    public void simulationResult(ActionEvent event){

    }
}
