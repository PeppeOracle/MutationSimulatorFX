package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import simulation.logic.DNAFragment;
import simulation.utils.StringConverter;

import java.awt.dnd.DnDConstants;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRandom implements Initializable {

    TextField insertSequence;

    TextField sequenceLenght;

    @FXML
    AnchorPane randomSelectPane,randomAP;

    @FXML
    CheckBox randomCheckBox;

    HBox lunghezzaSequenzaBox;
    VBox inserisciSequenzaBox;

    boolean random;
    Button inserisciSequenza;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        randomSelectedTrue();
    }

    public void checkRandom(ActionEvent actionEvent) {

        CheckBox checkedRandom = (CheckBox)actionEvent.getSource();
        randomSelectPane.getChildren().clear();

        if(checkedRandom.isSelected()){
            randomSelectedTrue();
        } else {
            randomSelectedFalse();
        }
    }

    private void randomSelectedTrue(){
        random=true;
        if(lunghezzaSequenzaBox==null) {
            lunghezzaSequenzaBox = new HBox();
            Label lunghezzaSequenzaLabel = new Label("Lunghezza sequenza di codoni");
            lunghezzaSequenzaLabel.setFont(Font.font(21.0));
            sequenceLenght = new TextField();
            sequenceLenght.setPrefSize(76.0, 25.0);

            lunghezzaSequenzaBox.setPrefWidth(450);
            lunghezzaSequenzaBox.setMargin(sequenceLenght, new Insets(3, 0, 0, 7));
            lunghezzaSequenzaBox.setPadding(new Insets(10, 0, 0, 10));
            lunghezzaSequenzaBox.getChildren().addAll(lunghezzaSequenzaLabel, sequenceLenght);
        }
        randomSelectPane.getChildren().add(lunghezzaSequenzaBox);
    }

    private void randomSelectedFalse(){
        random=false;
        if(inserisciSequenzaBox==null) {
            inserisciSequenzaBox = new VBox();

            HBox inserisciSequenzaHBox = new HBox();
            Label inserisciSequenzaLabel = new Label("Inserisci sequenza");
            inserisciSequenzaLabel.setFont(Font.font(21.0));

            Button sfogliaFile= new Button("Sfoglia file");

            inserisciSequenza= new Button("Inserisci");
            inserisciSequenza.setOnAction(e->{
                if(insertSequence==null){
                    insertSequence = new TextField();
                    inserisciSequenzaBox.setMargin(insertSequence, new Insets(0,20,10,10));
                    inserisciSequenzaBox.getChildren().add(insertSequence);
                }
                else {
                    inserisciSequenzaBox.getChildren().remove(insertSequence);
                    insertSequence=null;
                }
            });

            inserisciSequenzaHBox.setMargin(sfogliaFile, new Insets(3,0,0,7));
            inserisciSequenzaHBox.setMargin(inserisciSequenza, new Insets(3,0,0,7));

            inserisciSequenzaHBox.setPrefWidth(450);
            inserisciSequenzaHBox.setPadding(new Insets(10,0,0,10));
            inserisciSequenzaHBox.getChildren().addAll(inserisciSequenzaLabel,sfogliaFile,inserisciSequenza);
            inserisciSequenzaBox.getChildren().add(inserisciSequenzaHBox);

            inserisciSequenzaBox.setSpacing(10);
        }
        randomSelectPane.getChildren().add(inserisciSequenzaBox);
    }

    DNAFragment getDNAFragmentFromInput(){
        DNAFragment fragment;

        if(randomCheckBox.isSelected()){
            fragment = generateRandomDNAFragment();
        } else{
            fragment = readDNAFragmentFromTextField();
        }

        return fragment;
    }

    private DNAFragment readDNAFragmentFromTextField(){
        String sequence = insertSequence.getText();
        return new DNAFragment(StringConverter.convertStringToNucleotides(sequence));
    }

    private DNAFragment generateRandomDNAFragment(){
        return new DNAFragment(Integer.valueOf(sequenceLenght.getText())*3);
    }

    public AnchorPane getRandomAP(){
        return randomAP;
    }

    public void setRandomAP(AnchorPane probabilities){
        randomAP.getChildren().clear();
        randomAP.getChildren().add(probabilities);
    }


    public HBox getLunghezzaSequenzaBox() {
        return lunghezzaSequenzaBox;
    }

    public void setLunghezzaSequenzaBox(HBox lunghezzaSequenzaBox) {
        this.lunghezzaSequenzaBox = lunghezzaSequenzaBox;
    }

    public VBox getInserisciSequenzaBox() {
        return inserisciSequenzaBox;
    }

    public void setInserisciSequenzaBox(VBox inserisciSequenzaBox) {
        this.inserisciSequenzaBox = inserisciSequenzaBox;
    }


    public TextField getSequenceLenght() {
        return sequenceLenght;
    }

    public void setSequenceLenght(TextField sequenceLenght) {
        this.sequenceLenght = sequenceLenght;
    }

    public TextField getInsertSequence() {
        return insertSequence;
    }

    public void setInsertSequence(TextField insertSequence) {
        this.insertSequence = insertSequence;
    }

    public void setRandomCheck(boolean value){
        if(randomCheckBox.isSelected()!=value){
            randomCheckBox.fire();
        }
    }

    public void setSequence(String sequence){
        inserisciSequenza.fire();
        insertSequence.setText(sequence);
    }

    public String getSequence(){
        return insertSequence.getText();
    }

    public void setLenght(int lenght){
        sequenceLenght.setText(""+lenght);
    }

    public boolean getRandomCheck(){
        return random;
    }

    public int getLenght(){
        return Integer.valueOf(sequenceLenght.getText());
    }
}
