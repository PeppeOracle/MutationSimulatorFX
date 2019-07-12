package simulation.comparators;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class AminoAcidsSingleDifferenceComparator extends LabeledComparator implements Serializable {

    public AminoAcidsSingleDifferenceComparator(){
        super("AMINOACIDS-SINGLEDIFF","Numero di sequenze con almeno un Amminoacido diverso");
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        ArrayList<AminoAcid> aminoAcids1 = fragment1.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = fragment2.getAminoAcids();

        int different=0;
        int min=Math.min(aminoAcids1.size(), aminoAcids2.size());

        for(int i=0;i<min; i++){
            if(aminoAcids1.get(i)!=aminoAcids2.get(i)) {
                different=1;
                break;
            }
        }

        return different;
    }
}
