package simulation.wrapper;

import java.util.HashMap;

public class MutationSimulatorResults {
    private MutationResults mutationResults;
    private HashMap<String,Integer> hashMapOfLabeledComparator;

    public MutationSimulatorResults(MutationResults mutationResults, HashMap<String, Integer> hashMapOfLabeledComparator) {
        this.mutationResults = mutationResults;
        this.hashMapOfLabeledComparator = hashMapOfLabeledComparator;
    }


    public MutationResults getMutationResults() {
        return mutationResults;
    }

    public HashMap<String, Integer> getHashMapOfLabeledComparator() {
        return hashMapOfLabeledComparator;
    }

}
