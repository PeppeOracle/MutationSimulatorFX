package simulation.logic;

import simulation.statistics.ParameterIndex;
import simulation.wrapper.Simulation;

import java.util.ArrayList;

public class VariableSimulation {
    private int numberOfSimulation;
    private double[][][] scale;
    private ArrayList<Mutator> listOfMutator;
    private ArrayList<Simulation> listOfSimulation;
    private Simulation simulation;
    private MutationSimulator mutationSimulator;
    private int pivot;

    private ParameterIndex[] parameterIndices;

    public VariableSimulation(int numberOfSimulation, double[][][] scale, Simulation simulation, MutationSimulator mutationSimulator) {
        this.numberOfSimulation = numberOfSimulation;
        listOfMutator = new ArrayList<>();
        listOfSimulation = new ArrayList<>();
        this.simulation = simulation;
        this.mutationSimulator = mutationSimulator;
        this.scale = scale;
        this.parameterIndices = new ParameterIndex[numberOfSimulation];
    }

    private int getPivot(){
        if( (numberOfSimulation % 2) == 0 ){
            return numberOfSimulation / 2;
        }else {
            return (numberOfSimulation / 2) + 1;
        }
    }

    /**
     * Va invocato dopo getSimulation()
     * @param parameter
     * @return
     */
    public ParameterIndex[] getParameterIndicesByParameter(String parameter){
        for(int i = 0; i < numberOfSimulation; i++){
            this.parameterIndices[i] = new ParameterIndex(parameter,listOfSimulation.get(i).getListOfSimulationResults());
        }
        return this.parameterIndices;
    }

    public void executeVariationOfOperation(String operation){

        int variationOfInt = getIntByNameOperation(operation);
        if(variationOfInt == -1){
            return;
        }

        pivot = getPivot();

        for(int i = 1; i <= numberOfSimulation; i++){
            Simulation sim = new Simulation();
            Mutator mutator = this.mutationSimulator.getMutator();
            double p[][][] = new double[4][4][3];
            for(int x=0 ; x<4 ; x++){
                for(int z=0; z<3 ; z++){
                    double pPivot = mutationSimulator.getMutator().getMutationProbabilities()[x][variationOfInt][z];
                    if (i < pivot) {
                        pPivot = pPivot - (scale[x][variationOfInt][z] * pPivot * i);
                    } else if (i > pivot) {
                        pPivot = pPivot + (scale[x][variationOfInt][z] * pPivot * i);
                    }
                    p[x][variationOfInt][z] = pPivot;
                    p[x][3][z] = 1-(p[x][0][z]+p[x][1][z]+p[x][2][z]);
                }
            }

            if (i < pivot) {
                sim.setName(simulation.getName() + " -" + (i % pivot));
                mutator.setMutationProbabilities(p);
                listOfSimulation.add(sim);
                listOfMutator.add(mutator);
            } else if (i > pivot) {
                sim.setName(simulation.getName() + " +" + (i % pivot));
                mutator.setMutationProbabilities(p);
                listOfSimulation.add(sim);
                listOfMutator.add(mutator);
            }else{
                listOfSimulation.add(this.simulation);
                listOfMutator.add(this.mutationSimulator.getMutator());
            }
        }
    }

    /**
     * Se non viene invocato prima executeVariationOfOperation(String operation)
     * non vengono modificate le probabilità
     * @return
     */
    public  ArrayList<Simulation> getSimulation(){
        for(int i = 0 ; i < numberOfSimulation; i++){
            Mutator mut = listOfMutator.get(i);
            Simulation sim = listOfSimulation.get(i);
            sim.setListOfSimulationResults(mutationSimulator.simulate(mut));
            listOfSimulation.set(i, sim);
        }
        return listOfSimulation;
    }

    private int getIntByNameOperation(String name){
        switch (name){
            case "inserimento":
                return 0;
            case "cancellazione":
                return 1;
            case "sostituzione":
                return 2;
        }
        return -1;
    }


}