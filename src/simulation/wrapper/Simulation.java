package simulation.wrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import simulation.comparators.LabeledComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Simulation implements Serializable {
    //se vogliamo fare le statistiche a run time
    private String name;
    private String descr;
    private ArrayList<SimulationResults> listOfSimulationResults;
    private ArrayList<LabeledComparator> listOfLabeledComparators;

    public Simulation() {
        name="";
        descr="";
        listOfSimulationResults = new ArrayList<>();
    }

    public Simulation(String name, String descr, ArrayList<SimulationResults> listOfSimulationResults,ArrayList<LabeledComparator> listOfLabeledComparators) {
        this();
        this.name=name;
        this.descr=descr;
        this.listOfSimulationResults = listOfSimulationResults;
        this.listOfLabeledComparators = listOfLabeledComparators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr=descr;
    }

    public ArrayList<String> getComparatorsLabel(){
        /*ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : listOfSimulationResults.get(0).getHashMapOfLabeledComparator().entrySet()){
            list.add(entry.getKey());
        }
        return list;
        */
        ArrayList<String> list = new ArrayList<>();
        for(LabeledComparator comparator : listOfLabeledComparators){
            list.add(comparator.getLabel());
        }
        return list;
    }

    public ArrayList<LabeledComparator> getLabeledComparators(){
        return listOfLabeledComparators;
    }

    public ArrayList<SimulationResults> getListOfSimulationResults() {
        return listOfSimulationResults;
    }

    public void setListOfSimulationResults(ArrayList<SimulationResults> listOfSimulationResults) {
        this.listOfSimulationResults = listOfSimulationResults;
    }
}

