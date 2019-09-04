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
import java.util.ResourceBundle;

public class ControllerExecuteMutation1 extends ControllerMenu implements Initializable{

    @FXML
    AnchorPane probabilitiesBoxAP;

    ControllerProbabilities controllerProbabilities;

    @FXML
    AnchorPane randomSelectBoxAP;

    ControllerRandom controllerRandom;

    @FXML
    AnchorPane executeMutation1AP;

    DNAFragment fragmentToMutate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeProbabilitiesController();
        initializeRandomController();
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

    private void initializeRandomController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/RandomBox.fxml"));
            randomSelectBoxAP.getChildren().clear();
            randomSelectBoxAP.getChildren().add((AnchorPane) loader.load());
            controllerRandom = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {

        fragmentToMutate = controllerRandom.getDNAFragmentFromInput();

        Mutator mutator = new Mutator(fragmentToMutate,controllerProbabilities.readProbabilitiesFromGrid());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/ExecuteMutation2.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerExecuteMutation2 controllerExecuteMutation2= loader.getController();
        controllerExecuteMutation2.mainPane=mainPane;
        controllerExecuteMutation2.setMutator(mutator);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }
}
