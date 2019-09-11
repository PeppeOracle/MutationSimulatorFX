package simulation.comparators;

import simulation.logic.DNAFragment;

import java.io.Serializable;
import java.util.Comparator;

public abstract class LabeledComparator implements Comparator<DNAFragment>, Serializable {
    private String label;
    private String description;

    public LabeledComparator(String label){
        this.label=label;
        description=label;
    }

    public LabeledComparator(String label, String description){
        this.label=label;
        this.description=description;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public abstract int compare(DNAFragment fragment1, DNAFragment fragment2);

}
