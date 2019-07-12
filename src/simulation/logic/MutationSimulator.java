package simulation.logic;


import java.util.ArrayList;
import java.util.HashMap;

import simulation.comparators.LabeledComparator;
import simulation.wrapper.MutationResults;
import simulation.wrapper.SimulationResults;

public class MutationSimulator {
    private Mutator mutator;
    private int iterations;
    private ArrayList<LabeledComparator> listOfLabeledComparator;

    public MutationSimulator(Mutator mutator, int iterations, ArrayList<LabeledComparator> listOfLabeledComparator) {
        this.mutator = mutator;
        this.iterations = iterations;
        this.listOfLabeledComparator = listOfLabeledComparator;
    }

    public Mutator getMutator() {
        return mutator;
    }

    public void setMutator(Mutator mutator) {
        this.mutator = mutator;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public ArrayList<LabeledComparator> getListOfLabeledComparator() {
        return listOfLabeledComparator;
    }

    public void setListOfLabeledComparator(ArrayList<LabeledComparator> listOfLabeledComparator) {
        this.listOfLabeledComparator = listOfLabeledComparator;
    }

    public SimulationResults executeMutation(){
        MutationResults mutationResults = mutator.mutate();
        HashMap<String,Integer> map = new HashMap<>();

        for(LabeledComparator item : listOfLabeledComparator){
            int diff = item.compare(mutator.getFragmentToMutate(), mutationResults.getDnaFragmentMutated());
            map.put(item.getLabel(), diff);
        }

        SimulationResults mutationSimulatorResults = new SimulationResults(mutationResults, map);
        return mutationSimulatorResults;
    }

    public ArrayList<SimulationResults> simulate(Mutator mutator){
        setMutator(mutator);
        ArrayList<SimulationResults> results = new ArrayList<>();

        for(int i = 0; i < iterations; i++){
            results.add(executeMutation());
        }

        return results;
    }

    public ArrayList<SimulationResults> simulate(){
        ArrayList<SimulationResults> results = new ArrayList<>();

        for(int i = 0; i < iterations; i++){
            results.add(executeMutation());
        }

        return results;
    }


}
