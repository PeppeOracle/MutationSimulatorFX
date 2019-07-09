package simulation.utils;

import simulation.logic.DNAFragment;
import simulation.enums.Nucleotide;

public class NucleotidesUtils {
    public static String convertToString(DNAFragment fragment) {
        String sequence = "";

        for(int i=0 ; i< fragment.getLength() ; i++){
            sequence+=fragment.get(i);
        }

        return sequence;
    }

    public static Nucleotide getNucleotideByInt(int i){
        switch (i) {
            case 0:
                return Nucleotide.T;
            case 1:
                return Nucleotide.C;
            case 2:
                return Nucleotide.A;
            case 3:
                return Nucleotide.G;
        }
        return null;
    }

    public static int getIntByNucleotide(Nucleotide nucleotide){
        switch (nucleotide) {
            case T:
                return 0;
            case C:
                return 1;
            case A:
                return 2;
            case G:
                return 3;
        }
        return -1;
    }
}
