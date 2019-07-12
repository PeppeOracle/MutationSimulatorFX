package control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import simulation.logic.DNAFragment;
import simulation.comparators.NucleotidesDifferenceComparator;
import simulation.logic.MutationSimulator;
import simulation.logic.Mutator;
import simulation.utils.StringConverter;
import simulation.wrapper.MutationResults;

public class Main extends Application {

    Stage actualStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("graphics/GestioneSimulazioni.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/GestioneSimulazioni.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerGestioneSimulazioni controllerNewSimulation = loader.getController();
        controllerNewSimulation.mainPane = root;

        //controllerNewSimulation.mainPane.getChildren().clear();
        //controllerNewSimulation.mainPane.getChildren().setAll(root.getChildren());

        this.actualStage=primaryStage;
        primaryStage.setTitle("Mutation Simulator");
        primaryStage.setScene(new Scene(root, 1080, 768));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //launch(args);

        Mutator mutator = new Mutator(new DNAFragment(15));

        System.out.println(mutator.getFragmentToMutate());

        MutationResults mutationResults = mutator.mutate();

        System.out.println(StringConverter.convertListToString(mutator.getFragmentToMutate().getNucleotides()));

        System.out.println(mutationResults.getNumberOfEntries());
        System.out.println(mutationResults.getNumberOfInvariances());
        System.out.println(mutationResults.getNumberOfRemovals());
        System.out.println(mutationResults.getNumberOfReplacements());
        System.out.println(StringConverter.convertListToString(mutationResults.getDnaFragmentMutated().getNucleotides()));


        int numberOfSimulation = 11;
        if( (numberOfSimulation % 2) == 0 ){
            System.out.println("perno " + numberOfSimulation / 2);
        }else {
            System.out.println("perno " + ( (numberOfSimulation / 2) + 1) );
        }
    }
}

