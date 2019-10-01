package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import simulation.logic.DNAFragment;
import simulation.logic.Mutator;
import simulation.utils.StringConverter;

import javax.print.attribute.IntegerSyntax;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerNewSimulation2 extends ControllerMenu implements Initializable{

    @FXML
    AnchorPane probabilitiesBoxAP;

    ControllerProbabilities controllerProbabilities;

    @FXML
    AnchorPane randomSelectBoxAP;

    ControllerRandom controllerRandom;

    @FXML
    AnchorPane executeMutation1AP;

    @FXML
    TextField numIterations;

    String name,description;

    Mutator mutator;

    DNAFragment fragmentToMutate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeRandomController();
    }

    @Override
    public void setResources(HashMap<String, Object> resources) {
        super.setResources(resources);

        if(resources.containsKey("random")){
            ControllerRandom randomController = (ControllerRandom) resources.get("random");
            controllerRandom.setRandomAP(randomController.getRandomAP());
            controllerRandom.setInserisciSequenzaBox(randomController.getInserisciSequenzaBox());
            controllerRandom.setLunghezzaSequenzaBox(randomController.getLunghezzaSequenzaBox());
            controllerRandom.setSequenceLenght(randomController.getSequenceLenght());
            controllerRandom.setInsertSequence(randomController.getInsertSequence());
        }
        if(resources.containsKey("fragment")){
            fragmentToMutate=(DNAFragment)resources.get("fragment");
        }
        if(resources.containsKey("randomCheck")){
            if((boolean)resources.get("randomCheck")){
                controllerRandom.setRandomCheck(true);
                if(resources.containsKey("fragmentLenght")){
                    controllerRandom.setLenght(((int)resources.get("fragmentLenght")));
                }
            }else{
                controllerRandom.setRandomCheck(false);
                if(resources.containsKey("fragmentSequence")){
                    controllerRandom.setSequence((String)resources.get("fragmentSequence"));
                }
            }
        }
        if(resources.containsKey("iterations")){
            numIterations.setText(""+ resources.get("iterations"));
        }
    }

    private ControllerRandom initializeRandomController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/RandomBox.fxml"));
            randomSelectBoxAP.getChildren().clear();
            randomSelectBoxAP.getChildren().add((AnchorPane) loader.load());
            controllerRandom = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controllerRandom;
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {
        if((resources.containsKey("randomCheck")&& !(fragmentToMutate!=null && (boolean)resources.get("randomCheck") && controllerRandom.getRandomCheck())) || !resources.containsKey("randomCheck")){
            fragmentToMutate = controllerRandom.getDNAFragmentFromInput();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation3.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation3 controllerNewSimulation3 = loader.getController();
        controllerNewSimulation3.mainPane=mainPane;

        //resources.put("random",controllerRandom);
        resources.put("iterations",Integer.valueOf(numIterations.getText()));
        resources.put("fragment",fragmentToMutate);
        resources.put("randomCheck",controllerRandom.getRandomCheck());
        if(controllerRandom.getRandomCheck()){
            try{
                int num = Integer.parseInt(controllerRandom.getSequenceLenght().getText());
                resources.put("fragmentLenght",num);
            } catch (NumberFormatException e) {
            }
        } else{
            resources.put("fragmentSequence",controllerRandom.getSequence());
        }
        //System.out.println(StringConverter.convertListToString(fragmentToMutate.getNucleotides()));

        controllerNewSimulation3.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void previousPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphics/NewSimulation1.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ControllerNewSimulation1 controllerNewSimulation1= loader.getController();
        controllerNewSimulation1.mainPane=mainPane;

        //resources.put("random",controllerRandom);
        resources.put("iterations",numIterations.getText());
        resources.put("fragment",fragmentToMutate);
        resources.put("randomCheck",controllerRandom.getRandomCheck());
        if(controllerRandom.getRandomCheck()){
            try{
                int num = Integer.parseInt(controllerRandom.getSequenceLenght().getText());
                resources.put("fragmentLenght",num);
            } catch (NumberFormatException e) {
            }
        } else{
            resources.put("fragmentSequence",controllerRandom.getSequence());
        }

        controllerNewSimulation1.setResources(resources);

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root.getChildren());
    }

    public void setNumIterations(TextField numIterations) {
        this.numIterations = numIterations;
    }

    public void setFragmentToMutate(DNAFragment fragmentToMutate) {
        this.fragmentToMutate = fragmentToMutate;
    }
}

