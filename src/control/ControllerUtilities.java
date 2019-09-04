package control;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ControllerUtilities {

    public static HBox getLabeledTextField(String label, String initialText){
        HBox hBox = new HBox();

        return hBox;
    }


    public static HBox getLabeledCheckBox(String label, boolean initialCheck){
        HBox hBox = new HBox();

        return hBox;
    }

    public static Button getButton(String name){
        Button button = new Button();
        button.setText(name);
        return button;
    }
}
