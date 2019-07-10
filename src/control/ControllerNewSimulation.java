package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerNewSimulation extends ControllerMenu implements Initializable {

    @FXML
    AnchorPane newSimulationAP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane=newSimulationAP;
    }


    public void nextPage(ActionEvent actionEvent) throws IOException {
        AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("graphics/NewSimulationNext.fxml"));
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
        mainPane=root;
    }
}
