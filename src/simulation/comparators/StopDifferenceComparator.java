package simulation.comparators;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class StopDifferenceComparator extends LabeledComparator implements Serializable {
    public StopDifferenceComparator() {
        super("NUMBER-OF-NONSENSE-MUTATION", "Numero di stop creati");
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        int numberOfStop = 0;
        ArrayList<AminoAcid> aminoAcids1 = fragment1.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = fragment2.getAminoAcids();

        int min=Math.min(aminoAcids1.size(), aminoAcids2.size());

        for(int i=0;i<min; i++){
            if(aminoAcids2.get(i) == AminoAcid.STOP && aminoAcids1.get(i)!=aminoAcids2.get(i)) {
                numberOfStop++;
            }
        }

        return numberOfStop;
    }
}
