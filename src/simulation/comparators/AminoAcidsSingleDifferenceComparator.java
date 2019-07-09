package simulation.comparators;

import simulation.logic.DNAFragment;

public class AminoAcidsSingleDifferenceComparator extends LabeledComparator{

    public AminoAcidsSingleDifferenceComparator(){
        super("AMINOACIDS-SINGLEDIFF");
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        int different=0;
        int min=Math.min(fragment1.getLength(), fragment2.getLength());

        for(int i=0;i<min; i++){
            if(fragment1.get(i)!=fragment2.get(i)) {
                different=1;
                break;
            }
        }

        return different;
    }
}
