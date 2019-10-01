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

    private void executeVariationOfOperation(){
        pivot = getPivot();
        Mutator mutator = this.mutationSimulator.getMutator();
        double[][][] mutationProbabilities = mutator.getMutationProbabilities();

        for(int i = 1; i <= numberOfSimulation; i++){
            Simulation sim = new Simulation();
            double p[][][] = new double[4][4][3];
            for(int x=0 ; x<4 ; x++){
                for(int z=0; z<3 ; z++){
                    for(int y = 0; y < 3; y++) {
                        double pPivot = mutationProbabilities[x][y][z];
                        if (i < pivot) {
                            pPivot = pPivot - (scale[x][y][z]* (pivot-i));
                        } else if (i > pivot) {
                            pPivot = pPivot + (scale[x][y][z]* (i-pivot));
                        }
                        p[x][y][z] = pPivot;
                    }
                    p[x][3][z] = 1 - (p[x][0][z] + p[x][1][z] + p[x][2][z]);
                    System.out.println(p[x][3][z]);
                }
            }


            if (i < pivot) {
                sim.setName(simulation.getName() + " -" + (i % pivot));
                Mutator mutatorMod = new Mutator(mutator.getFragmentToMutate(),p);
                listOfSimulation.add(sim);
                listOfMutator.add(mutatorMod);
            } else if (i > pivot) {
                sim.setName(simulation.getName() + " +" + (i % pivot));
                Mutator mutatorMod = new Mutator(mutator.getFragmentToMutate(),p);
                listOfSimulation.add(sim);
                listOfMutator.add(mutatorMod);
            }else{
                listOfSimulation.add(this.simulation);
                listOfMutator.add(mutator);
            }
        }
    }

    /**
     * Se non viene invocato prima executeVariationOfOperation(String operation)
     * non vengono modificate le probabilità
     * @return
     */
    public  ArrayList<Simulation> executeSimulations(){
        executeVariationOfOperation();
        for(int i = 0 ; i < numberOfSimulation; i++){
            Mutator mut = listOfMutator.get(i);
            Simulation sim = listOfSimulation.get(i);
            sim.setListOfSimulationResults(mutationSimulator.simulate(mut));
            listOfSimulation.set(i, sim);
        }
        return listOfSimulation;
    }
}