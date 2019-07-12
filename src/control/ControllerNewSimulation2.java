package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import simulation.enums.Nucleotide;
import simulation.logic.DNAFragment;
import simulation.logic.Mutator;
import simulation.utils.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerNewSimulation2 extends ControllerMenu implements Initializable {

    @FXML
    AnchorPane executeMutation1AP;

    @FXML
    AnchorPane randomSelectPane;

    @FXML
    Label index;

    @FXML
    AnchorPane probabilitiesAP;

    @FXML
    CheckBox randomCheckBox;

    @FXML
    CheckBox equalIndex,equalNucleotides,equalOp;

    @FXML
    Polyline leftArrow,rightArrow;

    @FXML
    TextField numIterations;

    String name,description;

    TextField campoInserimentoSequenza;

    TextField lunghezzaSequenzaField;

    DNAFragment fragmentToMutate;

    int indexCount;

    double[][][] probabilities;

    GridPane[] probabilitiesGridPanes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indexCount=0;

        probabilitiesGridPanes = new GridPane[3];

        initializeRandomSelectPane();

        chooseGrid();
    }

    public void checkRandom(ActionEvent actionEvent) {

        CheckBox checkedRandom = (CheckBox)actionEvent.getSource();
        randomSelectPane.getChildren().clear();

        if(checkedRandom.isSelected()){
            initializeRandomSelectPane();
        } else {
            VBox inserisciSequenzaBox = new VBox();

            HBox inserisciSequenzaHBox = new HBox();
            Label inserisciSequenzaLabel = new Label("Inserisci sequenza");
            inserisciSequenzaLabel.setFont(Font.font(21.0));

            Button sfogliaFile= new Button("Sfoglia file");

            Button inserisciSequenza= new Button("Inserisci");
            inserisciSequenza.setOnAction(e->{
                if(campoInserimentoSequenza==null){
                    campoInserimentoSequenza = new TextField();
                    inserisciSequenzaBox.setMargin(campoInserimentoSequenza, new Insets(0,0,10,10));
                    inserisciSequenzaBox.getChildren().add(campoInserimentoSequenza);
                }
                else {
                    inserisciSequenzaBox.getChildren().remove(campoInserimentoSequenza);
                    campoInserimentoSequenza=null;
                }
            });

            inserisciSequenzaHBox.setMargin(sfogliaFile, new Insets(3,0,0,7));
            inserisciSequenzaHBox.setMargin(inserisciSequenza, new Insets(3,0,0,7));

            inserisciSequenzaHBox.setPrefWidth(450);
            inserisciSequenzaHBox.setPadding(new Insets(10,0,0,10));
            inserisciSequenzaHBox.getChildren().addAll(inserisciSequenzaLabel,sfogliaFile,inserisciSequenza);
            inserisciSequenzaBox.getChildren().add(inserisciSequenzaHBox);

            inserisciSequenzaBox.setSpacing(10);

            randomSelectPane.getChildren().add(inserisciSequenzaBox);
        }
    }

    private void initializeRandomSelectPane(){
        HBox lunghezzaSequenzaBox = new HBox();
        Label lunghezzaSequenzaLabel = new Label("Lunghezza sequenza di codoni");
        lunghezzaSequenzaLabel.setFont(Font.font(21.0));
        lunghezzaSequenzaField = new TextField();
        lunghezzaSequenzaField.setPrefSize(76.0,25.0);

        lunghezzaSequenzaBox.setPrefWidth(450);
        lunghezzaSequenzaBox.setMargin(lunghezzaSequenzaField, new Insets(3,0,0,7));
        lunghezzaSequenzaBox.setPadding(new Insets(10,0,0,10));
        lunghezzaSequenzaBox.getChildren().addAll(lunghezzaSequenzaLabel,lunghezzaSequenzaField);
        randomSelectPane.getChildren().add(lunghezzaSequenzaBox);
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

    private void chooseGrid(){
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

        probabilities=new double[4][4][3];

        probabilitiesAP.getChildren().clear();
        probabilitiesAP.getChildren().add(probabilitiesGridPanes[indexCount]);

    }

    public void checkEqualOp(ActionEvent actionEvent) {
        chooseGrid();
    }

    public void checkEqualNucleotides(ActionEvent actionEvent) {
        chooseGrid();
    }

    public void checkEqualIndex(ActionEvent actionEvent) {
        chooseGrid();
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {

        if(randomCheckBox.isSelected()){
            fragmentToMutate = new DNAFragment(Integer.valueOf(lunghezzaSequenzaField.getText())*3);
        } else{
            ArrayList<Nucleotide> nucleotides = StringConverter.convertStringToNucleotides(campoInserimentoSequenza.getText());
            fragmentToMutate = new DNAFragment(nucleotides);
        }


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
        Mutator mutator = new Mutator(fragmentToMutate,probabilities);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation3.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation3 controllerNewSimulation3= loader.getController();
        controllerNewSimulation3.mainPane=mainPane;
        controllerNewSimulation3.setMutator(mutator);
        controllerNewSimulation3.setIterations(Integer.valueOf(numIterations.getText()));
        controllerNewSimulation3.setName(name);
        controllerNewSimulation3.setDescription(description);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void setName(String name){
        this.name=name;
    }

    public void setDescription(String description){
        this.description=description;
    }
}

