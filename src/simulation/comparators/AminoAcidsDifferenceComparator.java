package simulation.comparators;

import simulation.logic.DNAFragment;

public class AminoAcidsDifferenceComparator extends LabeledComparator{

    public AminoAcidsDifferenceComparator(){
        super("AMINOACIDS-DIFF");
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        int differences=0;
        int min=Math.min(fragment1.getLength(), fragment2.getLength());

        for(int i=0;i<min; i++){
            if(fragment1.get(i)!=fragment2.get(i)) {
                differences++;
            }
        }

        if (fragment1.getLength()-min > 0){
            differences+=fragment1.getLength()-min;
        }

        return differences;
    }
}
