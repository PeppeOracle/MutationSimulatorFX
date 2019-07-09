package simulation.comparators;

import simulation.logic.DNAFragment;

import java.util.Comparator;

public abstract class LabeledComparator implements Comparator<DNAFragment> {
    static String label;

    public LabeledComparator(String label){
        this.label=label;
    }

    public abstract int compare(DNAFragment fragment1, DNAFragment fragment2);
}
