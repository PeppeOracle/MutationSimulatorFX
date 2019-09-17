package simulation.wrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import simulation.comparators.LabeledComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Simulation implements Serializable {
    //se vogliamo fare le statistiche a run time
    private String name;
    private String descr;
    private Date date;
    private HashMap<String,Object> resources;
    private ArrayList<SimulationResults> listOfSimulationResults;
    private ArrayList<LabeledComparator> listOfLabeledComparators;

    public Simulation() {
        name="";
        descr="";
        listOfSimulationResults = new ArrayList<>();
    }

    public Simulation(String name, String descr, Date date, ArrayList<SimulationResults> listOfSimulationResults,ArrayList<LabeledComparator> listOfLabeledComparators) {
        this();
        this.name=name;
        this.descr=descr;
        this.date=date;
        this.listOfSimulationResults = listOfSimulationResults;
        this.listOfLabeledComparators = listOfLabeledComparators;
    }

    public Simulation(String name, String descr, Date date, ArrayList<SimulationResults> listOfSimulationResults,ArrayList<LabeledComparator> listOfLabeledComparators ,HashMap<String,Object> resources) {
        this();
        this.name=name;
        this.descr=descr;
        this.date=date;
        this.listOfSimulationResults = listOfSimulationResults;
        this.listOfLabeledComparators = listOfLabeledComparators;
        this.resources=resources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date=date;
    }

    public HashMap<String, Object> getResources() {
        return resources;
    }

    public void setResources(HashMap<String, Object> resources) {
        this.resources = resources;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr=descr;
    }

    public ArrayList<String> getComparatorsLabel(){
        ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : listOfSimulationResults.get(0).getHashMapOfLabeledComparator().entrySet()){
            list.add(entry.getKey());
        }
        return list;

        /*ArrayList<String> list = new ArrayList<>();
        for(LabeledComparator comparator : listOfLabeledComparators){
            list.add(comparator.getLabel());
        }
        return list;*/
    }

    public void setListOfLabeledComparators(ArrayList<LabeledComparator> listOfLabeledComparators) {
        this.listOfLabeledComparators = listOfLabeledComparators;
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

