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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
        rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(50);
        //rowConstraints.setPrefHeight(100);

        int j;

        for(gridRows=0; gridRows < 9 ; gridRows++){
            for(j=0;j<3;j++){
                Text node = null;
                switch (j){
                    case 0:
                        node = new Text("Nome Simulazione "+ (gridRows+1));
                        break;
                    case 1:
                        node = new Text("GG/MM/YYYY " + (gridRows+1));
                        break;
                    case 2:
                        node = new Text("Descrizione Simulazione " + (gridRows+1));
                        break;
                }

                simulationsGrid.setConstraints(node,j,gridRows);
                simulationsGrid.getChildren().add(node);
            }

            HBox optionImages = null;
            try {
                optionImages = FXMLLoader.load(getClass().getResource("graphics/SimulationOptions.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(optionImages);
            simulationsGrid.setConstraints(optionImages,j,gridRows);
            simulationsGrid.getChildren().add(optionImages);

            simulationsGrid.getRowConstraints().add(gridRows, rowConstraints);
        }
    }

    public void resizeGrid(){
        int j;

        for(int i=0;i<3;i++){
            for(j=0;j<3;j++){
                Text node = null;
                switch (j){
                    case 0:
                        node = new Text("Nome Simulazione "+ (gridRows+1));
                        break;
                    case 1:
                        node = new Text("GG/MM/YYYY " + (gridRows+1));
                        break;
                    case 2:
                        node = new Text("Descrizione Simulazione " + (gridRows+1));
                        break;
                }

                simulationsGrid.setConstraints(node,j,gridRows);
                simulationsGrid.getChildren().add(node);
            }
            HBox optionImages = null;
            try {
                optionImages = FXMLLoader.load(getClass().getResource("graphics/SimulationOptions.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(optionImages);
            simulationsGrid.setConstraints(optionImages,j,gridRows);
            simulationsGrid.getChildren().add(optionImages);

            simulationsGrid.getRowConstraints().add(gridRows, rowConstraints);
            gridRows++;
        }
    }
}
