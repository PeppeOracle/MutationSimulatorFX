package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import simulation.logic.DNAFragment;
import simulation.logic.Mutator;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerNewSimulation3 extends ControllerMenu implements Initializable{

    @FXML
    AnchorPane probabilitiesBoxAP;

    ControllerProbabilities controllerProbabilities;

    Mutator mutator;

    DNAFragment fragmentToMutate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeProbabilitiesController();
    }

    @Override
    public void setResources(HashMap<String, Object> resources) {
        super.setResources(resources);

        if(resources.containsKey("operationsProbabilities")){
            ControllerProbabilities probabilitiesController = (ControllerProbabilities)resources.get("operationsProbabilities");
            controllerProbabilities.setProbabilitiesBoxAP(probabilitiesController.getProbabilitiesBoxAP());
            controllerProbabilities.setProbabilitiesGridPanes(probabilitiesController.getProbabilitiesGridPanes());
            controllerProbabilities.setEqualIndex(probabilitiesController.getEqualIndex());
            controllerProbabilities.setEqualNucleotides(probabilitiesController.getEqualNucleotides());
            controllerProbabilities.setEqualOp(probabilitiesController.getEqualOp());
        }
        if(resources.containsKey("probabilitiesOP") && resources.containsKey("equalProbabilitiesOP")){
            ControllerProbabilities probabilitiesController = initializeProbabilitiesController();
            probabilitiesController.setEqualProbabilities((boolean[])resources.get("equalProbabilitiesOP"));
            probabilitiesController.setProbabilities((double[][][])resources.get("probabilitiesOP"));
        }
        if(resources.containsKey("fragment")){
            fragmentToMutate=(DNAFragment) resources.get("fragment");
        }
    }

    public ControllerProbabilities initializeProbabilitiesController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/ProbabilitiesBox.fxml"));
            probabilitiesBoxAP.getChildren().clear();
            probabilitiesBoxAP.getChildren().add((AnchorPane) loader.load());
            controllerProbabilities = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controllerProbabilities;
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {
        mutator = new Mutator(fragmentToMutate, controllerProbabilities.readProbabilitiesFromGrid());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation4.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation4 controllerNewSimulation4 = loader.getController();
        controllerNewSimulation4.mainPane=mainPane;
        controllerNewSimulation4.root=root;

        //resources.put("operationsProbabilities",controllerProbabilities);
        double[][][] probabilities = controllerProbabilities.readProbabilitiesFromGrid();
        System.out.println("PROBABILITA' = " + probabilities[2][2][2]);
        resources.put("probabilitiesOP",probabilities);
        resources.put("equalProbabilitiesOP",controllerProbabilities.getEqualProbabilities());
        resources.put("mutator",mutator);

        controllerNewSimulation4.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void previousPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation2.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation2 controllerNewSimulation2= loader.getController();
        controllerNewSimulation2.mainPane=mainPane;

        //resources.put("operationsProbabilities",controllerProbabilities);
        resources.put("probabilitiesOP",controllerProbabilities.readProbabilitiesFromGrid());
        resources.put("equalProbabilitiesOP",controllerProbabilities.getEqualProbabilities());

        controllerNewSimulation2.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }
}

