package simulation.statistics;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import simulation.comparators.LabeledComparator;
import simulation.logic.MutationSimulator;
import simulation.wrapper.MutationResults;
import simulation.wrapper.SimulationResults;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class SimulationStatistics {
    private ArrayList<SimulationResults> listOfSimulatorResults;

    private HashMap<String,DescriptiveStatistics> mapOfDescriptiveStatistics;

    public SimulationStatistics(ArrayList<SimulationResults> listOfSimulatorResults) {
        this.listOfSimulatorResults = listOfSimulatorResults;
        mapOfDescriptiveStatistics = new HashMap<>();

        for(String nameOfField : getNameFields(MutationResults.class)){
            buildDSMutationsResults(nameOfField);
        }

        ///////PRENDO IL PRIMO IN QUANTO IN TEORIA DOVREBBERO AVRERE TUTTI GLI STESSI COMPARATORI... QUESTA COSA VA VISTA D: ??
        ArrayList<LabeledComparator> listOfLabeledComparator = new ArrayList<>();
        for(LabeledComparator labeledComparator : listOfLabeledComparator){
            buildDSComparators(labeledComparator.getLabel());
        }

    }

    private String[] getNameFields(Class aClass) {
        Field[] fields = aClass.getDeclaredFields();
        String[] names = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            names[i] = fields[i].getName();
            System.out.println(names[i]);
        }

        return names;
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

    private void buildDSMutationsResults(String name){
        DescriptiveStatistics ds = new DescriptiveStatistics();

        for(SimulationResults item: listOfSimulatorResults){
            MutationResults mutationSimulator = item.getMutationResults();
            ds.addValue((double)invokeGetter(mutationSimulator,name));
        }
    }

    private void buildDSComparators(String name){
        DescriptiveStatistics ds = new DescriptiveStatistics();

        for(SimulationResults item: listOfSimulatorResults){
            HashMap<String,Integer> map = item.getHashMapOfLabeledComparator();
            ds.addValue(map.get(name));
        }
    }

    public HashMap<String, DescriptiveStatistics> getMapOfDescriptiveStatistics() {
        return mapOfDescriptiveStatistics;
    }
}