package simulation.comparators;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.util.ArrayList;

public class AminoAcidsDifferenceComparator extends LabeledComparator{

    public AminoAcidsDifferenceComparator(){
        super("AMINOACIDS-DIFF","Numero di Amminoacidi diversi");
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        ArrayList<AminoAcid> aminoAcids1 = fragment1.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = fragment2.getAminoAcids();

        int differences=0;
        int min=Math.min(aminoAcids1.size(), aminoAcids2.size());

        for(int i=0;i<min; i++){
            if(aminoAcids1.get(i)!=aminoAcids2.get(i)) {
                differences++;
            }
        }

        if (aminoAcids1.size()-min > 0){
            differences+=aminoAcids1.size()-min;
        }

        return differences;
    }
}
