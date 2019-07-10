package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public abstract class ControllerMenu {

    protected AnchorPane mainPane;

    public void newSimulation(ActionEvent actionEvent) throws IOException {
        AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("graphics/NewSimulation.fxml"));
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

    public void singleMutation(ActionEvent actionEvent) {
    }

    public void recentSimulations(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("graphics/GestioneSimulazioni.fxml"));
    }
}
