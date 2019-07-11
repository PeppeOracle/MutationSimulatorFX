package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public abstract class ControllerMenu {

    protected AnchorPane mainPane;

    public void newSimulation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation1.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation1 controllerNewSimulation1 = loader.getController();
        controllerNewSimulation1.mainPane = mainPane;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/GestioneSimulazioni.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        ControllerGestioneSimulazioni controllerGestioneSimulazioni= loader.getController();
        controllerGestioneSimulazioni.mainPane = mainPane;

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }
}
