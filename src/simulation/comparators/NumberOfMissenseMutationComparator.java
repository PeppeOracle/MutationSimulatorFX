package simulation.comparators;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class NumberOfMissenseMutationComparator extends LabeledComparator{

    public NumberOfMissenseMutationComparator(){
        super("NUMBER-OF-MISSENSE-MUTATION","Mutazioni missenso (Differenze di amminoacidi)");
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        ArrayList<AminoAcid> aminoAcids1 = fragment1.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = fragment2.getAminoAcids();

        int differences=0;

        int n = aminoAcids1.size();
        int m = aminoAcids2.size();

        int min=Math.min(n,m);

        for(int i=0;i<min; i++){
            if(aminoAcids1.get(i)!=aminoAcids2.get(i)) {
                differences++;
            }
        }

//        if (aminoAcids1.size()-min > 0){
//            differences+=aminoAcids1.size()-min;
//        } else {
//            differences+=aminoAcids2.size()-min;
//        }

        if(n>m){
            differences = differences + (n-m);
        }

        return differences;
    }
}
