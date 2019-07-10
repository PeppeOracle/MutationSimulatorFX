package control;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGestioneSimulazioni extends ControllerMenu implements Initializable{

    @FXML
    Button more;

    @FXML
    VBox gridBox;

    @FXML
    GridPane simulationsGrid;

    @FXML
    AnchorPane gestioneAP;

    int gridRows;
    RowConstraints rowConstraints;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane=gestioneAP;
        rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(50);
        //rowConstraints.setPrefHeight(100);

        for(gridRows=0; gridRows < 9 ; gridRows++){
            for(int j=0;j<3;j++){
                Text node = new Text("Ciao "+ j + " " + gridRows);
                simulationsGrid.setConstraints(node,j,gridRows);
                simulationsGrid.getChildren().add(node);
            }
            simulationsGrid.getRowConstraints().add(gridRows, rowConstraints);
        }
    }

    public void resizeGrid(){

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                Text node = new Text("Ciao "+ j + " " + gridRows);
                simulationsGrid.setConstraints(node,j,gridRows);
                simulationsGrid.getChildren().add(node);
            }
            simulationsGrid.getRowConstraints().add(gridRows, rowConstraints);
            gridRows++;
        }
    }
}
