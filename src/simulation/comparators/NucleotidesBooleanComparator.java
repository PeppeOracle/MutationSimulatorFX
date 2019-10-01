package simulation.comparators;

import simulation.comparators.LabeledComparator;
import simulation.logic.DNAFragment;

import java.io.Serializable;

public class NucleotidesBooleanComparator extends LabeledCategoryComparator{

    private static final String[] categories= {"Nucleotidi immutati","Nucleotide mutato"};

    public NucleotidesBooleanComparator(){
        super("NUCLEOTIDES-BOOLEANDIFF", "Numero di sequenze con almeno un Nucleotide diverso",categories);
    }

    @Override
    public int compare(DNAFragment fragment1, DNAFragment fragment2) {
        int different=0;
        int min=Math.min(fragment1.getLength(), fragment2.getLength());

        for(int i=0;i<min; i++){
            if(fragment1.get(i)!=fragment2.get(i)) {
                return 1;
            }
        }

        return 0;
    }
}
