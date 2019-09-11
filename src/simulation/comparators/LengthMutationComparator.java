package simulation.comparators;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class LengthMutationComparator extends LabeledCategoryComparator{

    private static final String[] categories= {"Inserzione di frame","Delezione di frame","Lunghezza immutata","Frame-shift con inserzione","Frame-shift con delezione"};

    public LengthMutationComparator(){
        super("LENGTH-DIFF","Numero di sequenze con almeno un Amminoacido diverso",categories);
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        ArrayList<AminoAcid> aminoAcids1 = fragment1.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = fragment2.getAminoAcids();

        int diff = aminoAcids2.size()-aminoAcids1.size();

        if(diff>0){
            if((diff%3)==0){
                return 0;
            }else{
                return 3;
            }
        }else if(diff<0){
            if((diff%3)==0){
                return 1;
            } else{
                return 4;
            }
        }else{
            return 2;
        }
    }
}
