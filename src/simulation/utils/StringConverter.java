package simulation.utils;

import simulation.enums.Nucleotide;
import simulation.logic.DNAFragment;

import java.util.ArrayList;
import java.util.List;

public class StringConverter{

    public static String convertListToString (List arrayList){
        String convertedString="";

        for(Object element : arrayList){
            convertedString+=element.toString();
        }

        return convertedString;
    }

    public static ArrayList<Nucleotide> convertStringToNucleotides(String stringToConvert){
        ArrayList<Nucleotide> nucleotides = new ArrayList<>();

        for(int i=0 ; i<stringToConvert.length(); i++){

            if((stringToConvert.length()-i) >4 && stringToConvert.substring(i,i+4).toUpperCase().equals("STOP")){
                nucleotides.add(Nucleotide.valueOf(stringToConvert.substring(i,i+4).toUpperCase()));
                i+=3;
            } else {
                nucleotides.add(Nucleotide.valueOf(stringToConvert.substring(i,i+1).toUpperCase()));
            }
        }

        return nucleotides;
    }
}
