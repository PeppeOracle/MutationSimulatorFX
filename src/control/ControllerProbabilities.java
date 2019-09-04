package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polyline;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerProbabilities implements Initializable {

    private GridPane[] probabilitiesGridPanes;

    private int indexCount;

    @FXML
    Label index;

    @FXML
    Polyline leftArrow,rightArrow;

    @FXML
    CheckBox equalIndex,equalNucleotides,equalOp;

    @FXML
    AnchorPane probabilitiesAP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indexCount=0;

        probabilitiesGridPanes = new GridPane[3];

        chooseGrid(null);
    }

    double[][][] readProbabilitiesFromGrid(){

        double[][][] probabilities = new double[4][4][3];

        int r=7,c=4;

        if(equalNucleotides.isSelected()){
            r-=3;
            c=1;
        }
        if(equalOp.isSelected()){
            r-=2;
        }

        for(int z=0,i=0; i<3 ; i++){

            if(!equalIndex.isSelected()){
                z=i;
            }
            for(int x=0,n=0 ; n<4 ; n++){
                if(!equalNucleotides.isSelected()){
                    x=n;
                }
                for(int o=0,y=0; y<3; y++){
                    if(!equalOp.isSelected()){
                        o=y;
                    }
                    double d=0;
                    String valueString = ((TextField)probabilitiesGridPanes[z].getChildren().get(r+x+(o*c))).getText();
                    if(!valueString.equals("")){
                        d=Double.valueOf(valueString);
                    }
                    probabilities[n][y][i]= d;
                }
                if((probabilities[n][0][i]+probabilities[n][1][i]+probabilities[n][2][i])<1){
                    probabilities[n][3][i]=1-(probabilities[n][0][i]+probabilities[n][1][i]+probabilities[n][2][i]);
                } else{
                    probabilities[n][3][i]=0;
                };
            }
        }
        return probabilities;
    }

    public void chooseGrid(ActionEvent actionEvent){
        String urlGrid = "graphics/ProbabilitiesGrid.fxml";

        if(equalIndex.isSelected()){
            leftArrow.setVisible(false);
            rightArrow.setVisible(false);
            index.setText("Univoco");
        } else{
            leftArrow.setVisible(true);
            rightArrow.setVisible(true);
            index.setText(""+(indexCount+1));
        }

        if(equalOp.isSelected() && equalNucleotides.isSelected()){

            urlGrid="graphics/ProbabilitiesGridNuc&Op.fxml";

        } else if (equalOp.isSelected()){

            urlGrid="graphics/ProbabilitiesGridOp.fxml";

        } else if(equalNucleotides.isSelected()){

            urlGrid="graphics/ProbabilitiesGridNuc.fxml";

        }

        for(int i=0; i<3;i++) {
            GridPane pane = null;
            try {
                pane = FXMLLoader.load(getClass().getResource(urlGrid));
            } catch (IOException e) {
                e.printStackTrace();
            }
            probabilitiesGridPanes[i] = pane;
        }

        probabilitiesAP.getChildren().clear();
        probabilitiesAP.getChildren().add(probabilitiesGridPanes[indexCount]);
    }


    public void decreaseIndex(MouseEvent mouseEvent) {
        indexCount=(indexCount-1)%3;
        if(indexCount<0){
            indexCount+=3;
        }
        index.setText(""+(indexCount+1));
        probabilitiesAP.getChildren().clear();
        probabilitiesAP.getChildren().add(probabilitiesGridPanes[indexCount]);
    }

    public void increaseIndex(MouseEvent mouseEvent) {
        indexCount=(indexCount+1)%3;
        index.setText(""+(indexCount+1));
        probabilitiesAP.getChildren().clear();
        probabilitiesAP.getChildren().add(probabilitiesGridPanes[indexCount]);
    }

}
