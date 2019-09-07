package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import simulation.enums.Nucleotide;
import simulation.logic.DNAFragment;
import simulation.logic.Mutator;
import simulation.utils.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerNewSimulation2 extends ControllerMenu implements Initializable{

    @FXML
    AnchorPane probabilitiesBoxAP;

    ControllerProbabilities controllerProbabilities;

    @FXML
    AnchorPane randomSelectBoxAP;

    ControllerRandom controllerRandom;

    @FXML
    AnchorPane executeMutation1AP;

    @FXML
    TextField numIterations;

    String name,description;

    Mutator mutator;

    DNAFragment fragmentToMutate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeRandomController();
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
        if(resources.containsKey("random")){
            ControllerRandom randomController = (ControllerRandom) resources.get("random");
            controllerRandom.setRandomAP(randomController.getRandomAP());
            controllerRandom.setInserisciSequenzaBox(randomController.getInserisciSequenzaBox());
            controllerRandom.setLunghezzaSequenzaBox(randomController.getLunghezzaSequenzaBox());
            controllerRandom.setSequenceLenght(randomController.getSequenceLenght());
            controllerRandom.setInsertSequence(randomController.getInsertSequence());
        }
        if(resources.containsKey("iterations")){
            numIterations.setText(""+ resources.get("iterations"));
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

    private ControllerRandom initializeRandomController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/RandomBox.fxml"));
            randomSelectBoxAP.getChildren().clear();
            randomSelectBoxAP.getChildren().add((AnchorPane) loader.load());
            controllerRandom = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controllerRandom;
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {
        fragmentToMutate = controllerRandom.getDNAFragmentFromInput();
        mutator = new Mutator(fragmentToMutate, controllerProbabilities.readProbabilitiesFromGrid());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation3.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation3 controllerNewSimulation3= loader.getController();
        controllerNewSimulation3.mainPane=mainPane;

        resources.put("operationsProbabilities",controllerProbabilities);
        resources.put("random",controllerRandom);
        resources.put("mutator",mutator);
        resources.put("iterations",Integer.valueOf(numIterations.getText()));

        controllerNewSimulation3.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void previousPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation1.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation1 controllerNewSimulation1= loader.getController();
        controllerNewSimulation1.mainPane=mainPane;

        resources.put("operationsProbabilities",controllerProbabilities);
        resources.put("random",controllerRandom);
        resources.put("iterations",numIterations.getText());

        controllerNewSimulation1.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void setNumIterations(TextField numIterations) {
        this.numIterations = numIterations;
    }

    public void setFragmentToMutate(DNAFragment fragmentToMutate) {
        this.fragmentToMutate = fragmentToMutate;
    }
}

