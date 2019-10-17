package simulation.logic;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import simulation.comparators.LabeledComparator;
import simulation.comparators.NumberOfImmutateCodon;
import simulation.wrapper.MutationResults;
import simulation.wrapper.SimulationResults;

public class MutationSimulator implements Serializable {
    private Mutator mutator;
    private int iterations;
    private ArrayList<LabeledComparator> listOfLabeledComparator;

    public MutationSimulator(Mutator mutator, int iterations, ArrayList<LabeledComparator> listOfLabeledComparator) {
        this.mutator = mutator;
        this.iterations = iterations;
        this.listOfLabeledComparator = listOfLabeledComparator;

        this.listOfLabeledComparator.add(new NumberOfImmutateCodon());
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
        mutationSimulatorResults.setMutazioniMissensoBernoulli(mutator.getFragmentToMutate(),mutationResults.getDnaFragmentMutated());
        mutationSimulatorResults.setMutazioniSilenceBernoulli(mutator.getFragmentToMutate(),mutationResults.getDnaFragmentMutated());
        mutationSimulatorResults.setMutazioniImmutatoBernoulli(mutator.getFragmentToMutate(),mutationResults.getDnaFragmentMutated());
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

        int[][] vettoreMissenso = new int[iterations][results.get(0).getMutationResults().getSize()/3];
        int[][] vettoreSilence = new int[iterations][results.get(0).getMutationResults().getSize()/3];
        int[][] vettoreImmutato = new int[iterations][results.get(0).getMutationResults().getSize()/3];


        for(int i = 0; i < iterations; i++){
            vettoreMissenso[i] = results.get(i).getMutazioniMissensoBernoulli();
            vettoreSilence[i] = results.get(i).getMutazioniSilenceBernoulli();
            vettoreImmutato[i] = results.get(i).getMutazioniImmutatoBernoulli();
        }

        System.out.println("VETTORE MISSENSO");
        double stimeMISS[] = vettoreStime(vettoreMissenso,iterations,results.get(0).getMutationResults().getSize()/3);
        System.out.println("VETTORE SILENCE");
        double stimeSIL[] = vettoreStime(vettoreSilence,iterations,results.get(0).getMutationResults().getSize()/3);
        System.out.println("VETTORE IMMUTATO");
        double stimeIMM[] = vettoreStime(vettoreImmutato,iterations,results.get(0).getMutationResults().getSize()/3);

//        System.out.println("STIME");
//        double stimeCHECK[] = new double[results.get(0).getMutationResults().getSize()/3];
//        for(int i = 0; i < stimeCHECK.length; i++){
//            stimeCHECK[i] = stimeMISS[i] + stimeSIL[i] + stimeIMM[i];
//            System.out.println(stimeCHECK[i]);
//        }


        return results;
    }

    private static double[] vettoreStime(int[][] risultati, int numRighe, int numColonne){
        double[] stime = new double[numColonne];

        int sum;

        for(int j = 0; j < numColonne; j++){
            sum = 0;
            for(int i = 0; i < numRighe; i++){
                sum = sum + risultati[i][j];
            }
            stime[j] = 0;
            System.out.println(sum/(double)numRighe);
        }
        return stime;
    }



}
