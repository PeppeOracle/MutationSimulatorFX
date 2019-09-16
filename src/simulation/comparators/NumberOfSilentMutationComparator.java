package simulation.comparators;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.util.ArrayList;

public class NumberOfSilentMutationComparator extends LabeledComparator{
    public NumberOfSilentMutationComparator(){
        super("NUMBER-OF-SILENT-MUTATION", "Numero di mutazioni silenti");
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        ArrayList<AminoAcid> aminoAcids1 = fragment1.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = fragment2.getAminoAcids();

        int differences=0;
        int min=Math.min(fragment1.getLength(), fragment2.getLength());

        for(int i=0;i<min; i++){
            if(aminoAcids1.get(i) == aminoAcids2.get(i)) {
                int offset = 3*i;
                if(fragment1.get(offset) != fragment2.get(offset) || fragment1.get(offset+1) != fragment2.get(offset+1) || fragment1.get(offset+2) != fragment2.get(offset+2))
                differences++;
                break;
            }
        }

        return differences;
    }
}
