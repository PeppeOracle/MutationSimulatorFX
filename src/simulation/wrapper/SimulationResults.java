package simulation.wrapper;

import simulation.logic.DNAFragment;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class SimulationResults implements Serializable {
    private MutationResults mutationResults;
    private HashMap<String,Integer> hashMapOfLabeledComparator;

    public SimulationResults(MutationResults mutationResults, HashMap<String, Integer> hashMapOfLabeledComparator) {
        this.mutationResults = mutationResults;
        this.hashMapOfLabeledComparator = hashMapOfLabeledComparator;
    }

    public MutationResults getMutationResults() {
        return mutationResults;
    }

    public HashMap<String, Integer> getHashMapOfLabeledComparator() {
        return hashMapOfLabeledComparator;
    }

    @Override
    public String toString() {
        return getClass().getName() +
                "mutationResults=" + mutationResults +
                ", hashMapOfLabeledComparator=" + hashMapOfLabeledComparator +
                '}';
    }


    public int getNumberOfEntries() {
        return mutationResults.getNumberOfEntries();
    }


    public int getNumberOfReplacements() {
        return mutationResults.getNumberOfReplacements();
    }


    public int getNumberOfRemovals() {
        return mutationResults.getNumberOfRemovals();
    }


    public int getNumberOfInvariances() {
        return mutationResults.getNumberOfInvariances();
    }

    public DNAFragment getDnaFragmentMutated() {
        return mutationResults.getDnaFragmentMutated();
    }

    public int getSize(){
        return mutationResults.getSize();
    }

}
