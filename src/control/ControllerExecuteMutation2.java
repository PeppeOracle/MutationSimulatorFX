package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import simulation.comparators.*;
import simulation.enums.AminoAcid;
import simulation.logic.Mutator;
import simulation.wrapper.MutationResults;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class ControllerExecuteMutation2 extends ControllerMenu implements Initializable {

    @FXML
    GridPane comparisonGrid;

    @FXML
    VBox informationsGrid;

    MutationResults results;

    Mutator mutator;
    ArrayList<LabeledComparator> comparators;

    NucleotidesDifferenceComparator nucleotidesDifferenceComparator;
    NucleotidesSingleDifferenceComparator nucleotidesSingleDifferenceComparator;
    AminoAcidsDifferenceComparator aminoAcidsDifferenceComparator;
    AminoAcidsSingleDifferenceComparator aminoAcidsSingleDifferenceComparator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comparators = new ArrayList<>();
        nucleotidesDifferenceComparator = new NucleotidesDifferenceComparator();
        nucleotidesSingleDifferenceComparator = new NucleotidesSingleDifferenceComparator();
        aminoAcidsDifferenceComparator = new AminoAcidsDifferenceComparator();
        aminoAcidsSingleDifferenceComparator = new AminoAcidsSingleDifferenceComparator();
    }

    public void checkNUCLEOTIDESSINGLEDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(nucleotidesSingleDifferenceComparator);
        } else{
            comparators.remove(nucleotidesSingleDifferenceComparator);
        }}

    public void checkAMINOACIDSSINGLEDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(aminoAcidsSingleDifferenceComparator);
        } else{
            comparators.remove(nucleotidesSingleDifferenceComparator);
        }}

    public void checkNUCLEOTIDESDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(nucleotidesDifferenceComparator);
        } else{
            comparators.remove(nucleotidesDifferenceComparator);
        }}

    public void checkAMINOACIDSDIFF(ActionEvent actionEvent) {
        if(((CheckBox)actionEvent.getSource()).isSelected()){
            comparators.add(aminoAcidsDifferenceComparator);
        } else{
            comparators.remove(aminoAcidsDifferenceComparator);
        }}

    public void nextPage(ActionEvent actionEvent) throws IOException {
        //System.out.println(""+mutator.getFragmentToMutate().getNucleotides());

        /*
        ControllerExecuteMutation3 controllerExecuteMutation3 = new ControllerExecuteMutation3();
        controllerExecuteMutation3.setMutator(mutator);
        controllerExecuteMutation3.setComparators(comparators);

        ControllerMenu controllerTemp = new ControllerMenu(){
        };

        AnchorPane root = (AnchorPane) mainPane.getScene().getRoot();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/ExecuteMutation3.fxml"));
        loader.setController(controllerTemp);

        loader.setController(controllerExecuteMutation3);
        controllerExecuteMutation3.mainPane=mainPane;

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
        */

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/ExecuteMutation3.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerExecuteMutation2 controllerExecuteMutation2= loader.getController();
        controllerExecuteMutation2.mainPane=mainPane;
        controllerExecuteMutation2.setMutator(mutator);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());

        comparisonGrid= (GridPane)mainPane.getScene().lookup("#comparisonGrid");
        informationsGrid= (VBox)mainPane.getScene().lookup("#informationsGrid");

        results=mutator.mutate();
        inizializeGridComparison();
        inizializeGridInformations();
    }

    private void inizializeGridComparison(){
        ArrayList<AminoAcid> aminoAcidsToMutate= mutator.getFragmentToMutate().getAminoAcids();
        ArrayList<AminoAcid> aminoAcidsMutated= results.getDnaFragmentMutated().getAminoAcids();
        int fragmentSize = Math.min(aminoAcidsToMutate.size(),aminoAcidsMutated.size());

        double nodeWidth = comparisonGrid.getMaxWidth()/fragmentSize;
        double nodeMaxWidth = comparisonGrid.getMaxWidth();
        System.out.println("comparisonGrid width " + comparisonGrid.getMaxWidth());

        if(fragmentSize>50) {
            while (comparisonGrid.getRowConstraints().size() > 1) {
                comparisonGrid.getRowConstraints().remove(0);
            }

            for (int i = 0; i < fragmentSize; i++) {
                Label aminoAcidToMutate = new Label("" + aminoAcidsToMutate.get(i));
                Label aminoAcidMutated = new Label("" + aminoAcidsMutated.get(i));
                Pane coloredPane = new Pane();

                String red = "Red";
                String green = "Green";

                if (aminoAcidsToMutate.get(i) == aminoAcidsMutated.get(i)) {
                    coloredPane.setStyle("-fx-background-color: " + green);
                } else {
                    coloredPane.setStyle("-fx-background-color: " + red);
                }

                coloredPane.setMinWidth(0);
                coloredPane.setPrefWidth(nodeMaxWidth);
                coloredPane.setMaxWidth(nodeWidth);

                comparisonGrid.setConstraints(coloredPane, i, 0);
                GridPane.setHalignment(coloredPane, HPos.CENTER);

                comparisonGrid.getChildren().add(coloredPane);
            }
        } else{
            for(int i=0 ; i<fragmentSize ; i++){
                Label aminoAcidToMutate = new Label(""+aminoAcidsToMutate.get(i));
                Label aminoAcidMutated = new Label(""+aminoAcidsMutated.get(i));
                Pane coloredPane = new Pane();

                String red = "Red";
                String green = "Green";

                if(aminoAcidsToMutate.get(i)==aminoAcidsMutated.get(i)){
                    coloredPane.setStyle("-fx-background-color: " + green);
                } else {
                    coloredPane.setStyle("-fx-background-color: " + red);
                }
                aminoAcidMutated.setMinWidth(0);
                aminoAcidToMutate.setMinWidth(0);
                coloredPane.setMinWidth(0);
                aminoAcidToMutate.setPrefWidth(nodeMaxWidth);
                aminoAcidMutated.setPrefWidth(nodeMaxWidth);
                coloredPane.setPrefWidth(nodeMaxWidth);
                aminoAcidToMutate.setMaxWidth(nodeWidth);
                aminoAcidMutated.setMaxWidth(nodeWidth);
                coloredPane.setMaxWidth(nodeWidth);

                comparisonGrid.setConstraints(aminoAcidToMutate,i,0);
                comparisonGrid.setConstraints(coloredPane,i,1);
                comparisonGrid.setConstraints(aminoAcidMutated,i,2);

                comparisonGrid.setHalignment(aminoAcidToMutate, HPos.CENTER);
                aminoAcidToMutate.setAlignment(Pos.CENTER);
                comparisonGrid.setHalignment(coloredPane, HPos.CENTER);
                aminoAcidMutated.setAlignment(Pos.CENTER);
                comparisonGrid.setHalignment(aminoAcidMutated, HPos.CENTER);

                comparisonGrid.getChildren().add(aminoAcidMutated);
                comparisonGrid.getChildren().add(coloredPane);
                comparisonGrid.getChildren().add(aminoAcidToMutate);
            }
        }
    }

    private void inizializeGridInformations(){
        ArrayList<Label> labels = new ArrayList<>();
        Label numOfEntries = new Label("Numero di Inserimenti: "+results.getNumberOfEntries());
        Label numOfRemovals = new Label("Numero di Cancellazioni: "+results.getNumberOfRemovals());
        Label numOfReplacements = new Label("Numero di Sostituzioni: "+results.getNumberOfReplacements());
        Label numOfInvariances = new Label("Numero di Invarianze: "+results.getNumberOfInvariances());

        labels.add(numOfEntries);
        labels.add(numOfRemovals);
        labels.add(numOfReplacements);
        labels.add(numOfInvariances);

        for(LabeledComparator comparator : comparators){
            Label label = new Label(comparator.getDescription() + ": " + comparator.compare(mutator.getFragmentToMutate(),results.getDnaFragmentMutated()));
            labels.add(label);
        }

        for(Label item : labels ){
            item.setFont(Font.font(20));
        }

        informationsGrid.getChildren().addAll(labels);
    }

    public void setMutator(Mutator mutator){
        this.mutator=mutator;
    }
}
