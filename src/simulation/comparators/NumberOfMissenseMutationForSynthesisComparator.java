package simulation.comparators;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class NumberOfMissenseMutationForSynthesisComparator extends LabeledComparator{

    public NumberOfMissenseMutationForSynthesisComparator(){
        super("SYNTHESIS-NUMBER-OF-MISSENSE-MUTATION","Mutazioni missenso (Differenze di amminoacidi) di una sinstesi sequenziale");
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        ArrayList<AminoAcid> aminoAcids1 = fragment1.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = fragment2.getAminoAcids();

        int differences=0;
        int i,j;
        int size1=aminoAcids1.size(),size2=aminoAcids2.size();
        int min=Math.min(aminoAcids1.size(), aminoAcids2.size());

        for(j=0,i=0;i<size1 && j<size2; i++,j++){

            if(aminoAcids1.get(i).toString().equals("STOP")){
                while(j>=size2 && aminoAcids2.get(j).toString().equals("STOP")){
                    differences++;
                }
            } else if (aminoAcids2.get(j).toString().equals("STOP")){
                while(i>=size1 && aminoAcids1.get(i).toString().equals("STOP")){
                    differences++;
                }
            }

            if(aminoAcids1.get(i)!=aminoAcids2.get(j)) {
                differences++;
            }

        }

        if (j==size2){
            differences+=i-size1;
        } else{
            differences+=j-size2;
        }

        return differences;
    }
}
