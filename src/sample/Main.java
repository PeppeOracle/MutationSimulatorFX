package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.logic.DNAFragment;
import simulation.comparators.NucleotidesDifferenceComparator;
import simulation.utils.StringConverter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Mutation Simulator");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        DNAFragment fragment1 = new DNAFragment(15);
        System.out.println(StringConverter.convertListToString(fragment1.getNucleotides()));

        DNAFragment fragment2 = new DNAFragment(12);
        System.out.println(StringConverter.convertListToString(fragment2.getNucleotides()));

        System.out.println(new NucleotidesDifferenceComparator().compare(fragment1,fragment2));

        System.out.println(StringConverter.convertListToString(fragment1.getAminoAcids()));
    }
}
