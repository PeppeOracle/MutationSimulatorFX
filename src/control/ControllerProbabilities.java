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
    AnchorPane probabilitiesAP,probabilitiesBoxAP;

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

    public void setProbabilities(double[][][] probabilities) {

        int r=7,c=4,fx=4,fy=3,fz=3;

        chooseGrid(null);

        if(equalOp.isSelected()){
            r-=2;
            fy=1;
        }
        if(equalNucleotides.isSelected()){
            r-=3;
            c=1;
            fx=1;
        }
        if(equalIndex.isSelected()){
            fz=1;
        }

        for(int x=0 ; x<fx ; x++){
            for(int z=0; z<fz ; z++){
                for(int y=0; y<fy; y++){
                    ((TextField)probabilitiesGridPanes[z].getChildren().get(r+x+(y*c))).setText(""+probabilities[x][y][z]);
                }
            }
        }
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

    public AnchorPane getProbabilitiesBoxAP(){
        return probabilitiesBoxAP;
    }

    public void setProbabilitiesBoxAP(AnchorPane probabilities){
        probabilitiesBoxAP.getChildren().clear();
        probabilitiesBoxAP.getChildren().add(probabilities);
    }

    public GridPane[] getProbabilitiesGridPanes() {
        return probabilitiesGridPanes;
    }

    public void setProbabilitiesGridPanes(GridPane[] probabilitiesGridPanes) {
        this.probabilitiesGridPanes = probabilitiesGridPanes;
    }

    public CheckBox getEqualIndex() {
        return equalIndex;
    }

    public CheckBox getEqualNucleotides() {
        return equalNucleotides;
    }

    public CheckBox getEqualOp() {
        return equalOp;
    }

    public void setEqualIndex(CheckBox equalIndex) {
        this.equalIndex = equalIndex;
    }

    public void setEqualNucleotides(CheckBox equalNucleotides) {
        this.equalNucleotides = equalNucleotides;
    }

    public void setEqualOp(CheckBox equalOp) {
        this.equalOp = equalOp;
    }

    public boolean[] getEqualProbabilities(){
        boolean[] equal=new boolean[3];
        equal[0]=equalOp.isSelected();
        equal[1]=equalNucleotides.isSelected();
        equal[2]=equalIndex.isSelected();

        return equal;
    }

    public void setEqualProbabilities(boolean[] equal){
        equalOp.setSelected(equal[0]);
        equalNucleotides.setSelected(equal[1]);
        equalIndex.setSelected(equal[2]);
    }
}
