package simulation.statistics;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import simulation.wrapper.MutationResults;
import simulation.wrapper.Simulation;
import simulation.wrapper.SimulationResults;

import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;


public class ParameterIndex {
    private String name;
    private DescriptiveStatistics descriptiveStatistics;
    private Frequency frequency;
    private Set<Integer> set;

    private ArrayList<SimulationResults> listOfSimulationResults;

    public ParameterIndex(String name, ArrayList<SimulationResults> listOfSimulationResults) {
        this.name = name;
        this.listOfSimulationResults = listOfSimulationResults;
        frequency = new Frequency();

        if(isFieldOfMutationResults(name)){
            loadDSMutation();
        }else if(listOfSimulationResults.get(0).getHashMapOfLabeledComparator().get(name) != null){
            loadDSComparator();
        }
    }

    private void loadDSComparator() {
        for(SimulationResults simulationResults : listOfSimulationResults){
            HashMap<String,Integer> map = simulationResults.getHashMapOfLabeledComparator();
            descriptiveStatistics.addValue((double)map.get(name));
            frequency.addValue((int)map.get(name));
            set.add((int)map.get(name));
        }
    }

    private void loadDSMutation(){
        for(SimulationResults simulationResults : listOfSimulationResults) {
            MutationResults mutationResults = simulationResults.getMutationResults();
            descriptiveStatistics.addValue((double)invokeGetter(mutationResults,name));
            frequency.addValue((int)invokeGetter(mutationResults,name));
            set.add((int)invokeGetter(mutationResults,name));
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
        ArrayList<Point> points = new ArrayList<>();
        Integer[] elements = (Integer[]) set.toArray();

        for(Integer x : elements){
            points.add(new Point(x, (int) frequency.getCount(x)));
        }

        points.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                    int x1 = (int) o1.getX();
                    int x2 = (int) o2.getY();

                    if (x1 == x2)
                        return 0;
                    else if (x1 > x2)
                        return 1;
                    else
                        return -1;

            }
        });
        return points;
    }
}
