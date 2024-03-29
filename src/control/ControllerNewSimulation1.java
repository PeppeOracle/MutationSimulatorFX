package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerNewSimulation1 extends ControllerMenu implements Initializable {

    @FXML
    AnchorPane newSimulation1AP;

    @FXML
    TextArea textName, textDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textName.setFont(Font.font(20));
        textDescription.setFont(Font.font(20));
    }

    @Override
    public void setResources(HashMap<String, Object> resources) {
        super.setResources(resources);

        if(resources.containsKey("name")){
            this.textName.setText((String)resources.get("name"));
        }
        if(resources.containsKey("description")){
            this.textDescription.setText((String)resources.get("description"));
        }
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation2.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation2 controllerNewSimulation2 = loader.getController();
        controllerNewSimulation2.mainPane=mainPane;

        resources.put("name",textName.getText());
        resources.put("description",textDescription.getText());

        controllerNewSimulation2.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }
}
