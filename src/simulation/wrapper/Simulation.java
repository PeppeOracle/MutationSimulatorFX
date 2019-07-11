package simulation.wrapper;

import java.io.Serializable;
import java.util.ArrayList;

public class Simulation implements Serializable {
    //se vogliamo fare le statistiche a run time
    private String name;
    private String descr;
    private ArrayList<SimulationResults> listOfSimulationResults;

    public Simulation(String name, String descr, ArrayList<SimulationResults> listOfSimulationResults) {
        this.name = name;
        this.descr = descr;
        this.listOfSimulationResults = listOfSimulationResults;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public ArrayList<SimulationResults> getListOfSimulationResults() {
        return listOfSimulationResults;
    }

    public void setListOfSimulationResults(ArrayList<SimulationResults> listOfSimulationResults) {
        this.listOfSimulationResults = listOfSimulationResults;
    }
}
