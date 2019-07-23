package simulation.statistics;


import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import simulation.wrapper.MutationResults;
import simulation.wrapper.Simulation;
import simulation.wrapper.SimulationResults;

import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class ParameterIndex {
    private String name;
    private DescriptiveStatistics descriptiveStatistics;
    private DataSort dataSort;

    private ArrayList<SimulationResults> listOfSimulationResults;

    public ParameterIndex(String name, ArrayList<SimulationResults> listOfSimulationResults) {
        this.descriptiveStatistics=new DescriptiveStatistics();
        this.name = name;
        this.listOfSimulationResults = listOfSimulationResults;
        dataSort = new DataSort(listOfSimulationResults.get(0).getSize() + 1);

        if(isFieldOfMutationResults(name)){
            loadDSMutation();
        }else if(listOfSimulationResults.get(0).getHashMapOfLabeledComparator().get(name) != null){
            loadDSComparator();
        }
    }

    private void loadDSComparator() {
        for(SimulationResults simulationResults : listOfSimulationResults){
            HashMap<String,Integer> map = simulationResults.getHashMapOfLabeledComparator();
            int value = map.get(name);
            descriptiveStatistics.addValue((double)value);
            dataSort.addValue(value);
        }
    }

    private void loadDSMutation(){
        for(SimulationResults simulationResults : listOfSimulationResults) {
            MutationResults mutationResults = simulationResults.getMutationResults();
            int value = (int)invokeGetter(mutationResults,name);
            descriptiveStatistics.addValue((double)value);
            dataSort.addValue(value);
        }
    }

    private boolean isFieldOfMutationResults(String name){
        Field[] fields = MutationResults.class.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if(fields[i].getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    private Object invokeGetter(Object obj, String fieldName) {
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
            return pd.getReadMethod().invoke(obj);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public double getMean() {
        return descriptiveStatistics.getMean();
    }

    public double getMedian() {
        return descriptiveStatistics.getPercentile(50);
    }

    public double getVariance() {
        return descriptiveStatistics.getVariance();
    }

    public double getStandardDeviation() {
        return descriptiveStatistics.getStandardDeviation();
    }

    public ArrayList<Point> getPointsFrequency(){
        return dataSort.getArrayListPoint();
    }

    public void count(){
        getPointsFrequency();
        getMean();
        getMedian();
        getStandardDeviation();
        getVariance();
    }
}
