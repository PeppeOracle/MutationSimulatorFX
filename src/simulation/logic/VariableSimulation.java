package simulation.logic;

import simulation.wrapper.Simulation;

import java.util.ArrayList;

public class VariableSimulation {
    private int numberOfSimulation;
    private double scale;
    private ArrayList<Mutator> listOfMutator;
    private ArrayList<Simulation> listOfSimulation;
    private Simulation simulation;
    private MutationSimulator mutationSimulator;

    private int pivot;

    public VariableSimulation(int numberOfSimulation, double scale, Simulation simulation, MutationSimulator mutationSimulator) {
        this.numberOfSimulation = numberOfSimulation;
        listOfMutator = new ArrayList<>();
        listOfSimulation = new ArrayList<>();
        this.simulation = simulation;
        this.mutationSimulator = mutationSimulator;
        this.scale = scale;

        pivot = getPivot();

        System.out.println("perno " + pivot);
        for(int i = 1; i <= numberOfSimulation; i++){
            Simulation sim = new Simulation();
            Mutator mutator = this.mutationSimulator.getMutator();
            double p[][][] = new double[4][4][3];
            for(int x=0 ; x<4 ; x++){
                for(int z=0; z<3 ; z++){
                    for(int y=0; y<3; y++){
                        double pPivot = mutationSimulator.getMutator().getMutationProbabilities()[x][y][z];

                        if (i < pivot) {
                            pPivot = pPivot - (scale * pPivot * i);
                        } else if (i > pivot) {
                            pPivot = pPivot + (scale * pPivot * i);
                        }
                        p[x][y][z] = pPivot;
                    }
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

    private int getPivot(){
        if( (numberOfSimulation % 2) == 0 ){
            return numberOfSimulation / 2;
        }else {
            return (numberOfSimulation / 2) + 1;
        }
    }


    public  ArrayList<Simulation> getSimulation(){

        for(int i = 0 ; i < numberOfSimulation; i++){
            Mutator mut = listOfMutator.get(i);
            Simulation sim = listOfSimulation.get(i);
            sim.setListOfSimulationResults(mutationSimulator.simulate(mut));
            listOfSimulation.set(i, sim);


        }

        return listOfSimulation;
    }


}