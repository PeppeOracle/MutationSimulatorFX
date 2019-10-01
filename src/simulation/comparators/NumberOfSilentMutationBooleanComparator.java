package simulation.comparators;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.util.ArrayList;

public class NumberOfSilentMutationBooleanComparator  extends LabeledCategoryComparator{

    private static final String[] categories= {"Non Silente","Silente"};

    public NumberOfSilentMutationBooleanComparator(){
            super("NUMBER-OF-SILENT-BOOLEAN-MUTATION","Mutazioni silenti boolean counter(Differenze di amminoacidi)",categories);
        }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        ArrayList<AminoAcid> aminoAcids1 = fragment1.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = fragment2.getAminoAcids();

        int differences=0;
        int min=Math.min(aminoAcids1.size(), aminoAcids2.size());

        for(int i=0;i<min; i++){
            if(aminoAcids1.get(i) == aminoAcids2.get(i)) {
                int offset = 3*i;
                if(fragment1.get(offset) != fragment2.get(offset) || fragment1.get(offset+1) != fragment2.get(offset+1) || fragment1.get(offset+2) != fragment2.get(offset+2))
                    return 1;
            }
        }

        return 0;
    }
}
