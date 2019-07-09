package simulation.utils;

import java.util.List;

public class StringConverter {
    public static String convertListToString (List arrayList){
        String convertedString="";

        for(Object element : arrayList){
            convertedString+=element.toString();
        }

        return convertedString;
    }
}
