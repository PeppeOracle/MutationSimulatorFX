package simulation.comparators;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.util.ArrayList;

public class NumberOfImmutateCodon extends LabeledComparator{
    public NumberOfImmutateCodon(){
        super("NUMBER-OF-IMMUTATE-CODON", "Numero di codoni uguali");
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        int differences=0;

        int n = fragment1.getNumberOfCodon();
        int m = fragment2.getNumberOfCodon();

        int min = Math.min(n,m);

        for(int i=0;i<min; i++){
            int offset = 3*i;
            if(fragment1.get(offset) == fragment2.get(offset) && fragment1.get(offset+1) == fragment2.get(offset+1) && fragment1.get(offset+2) == fragment2.get(offset+2)) {
                differences++;
            }
        }

        return differences;
    }
}
