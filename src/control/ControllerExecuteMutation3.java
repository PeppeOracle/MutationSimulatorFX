package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import simulation.comparators.LabeledComparator;
import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;
import simulation.logic.Mutator;
import simulation.wrapper.MutationResults;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerExecuteMutation3 extends ControllerMenu implements Initializable {

    @FXML
    GridPane comparisonGrid;

    Mutator mutator;
    ArrayList<LabeledComparator> comparators;

    private MutationResults results;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        results = mutator.mutate();
        inizializeGridComparison();
    }

    private void inizializeGridComparison(){
        ArrayList<AminoAcid> aminoAcidsToMutate= mutator.getFragmentToMutate().getAminoAcids();
        ArrayList<AminoAcid> aminoAcidsMutated= results.getDnaFragmentMutated().getAminoAcids();
        int fragmentSize = Math.min(aminoAcidsToMutate.size(),aminoAcidsMutated.size());

        for(int i=0 ; i<fragmentSize ; i++){
            Label aminoAcidToMutate = new Label(""+aminoAcidsToMutate.get(i));
            Label aminoAcidMutated = new Label(""+aminoAcidsMutated.get(i));
            Pane coloredPane = new Pane();



            String red = "Red";
            String green = "Green";

            if(aminoAcidsToMutate.get(i)==aminoAcidsMutated.get(i)){
                coloredPane.setStyle("-fx-background-color: #" + green);
            } else {
                coloredPane.setStyle("-fx-background-color: #" + red);
            }

            comparisonGrid.setConstraints(aminoAcidToMutate,i,0);
            comparisonGrid.setConstraints(coloredPane,i,2);
            comparisonGrid.setConstraints(aminoAcidMutated,i,1);

            comparisonGrid.getChildren().addAll(aminoAcidMutated,coloredPane,aminoAcidToMutate);

        }
    }

    public void setMutator(Mutator mutator){
        this.mutator=mutator;
    }

    public void setComparators(ArrayList<LabeledComparator> comparators){
        this.comparators=comparators;
    }
}
