package simulation.wrapper;

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
}
