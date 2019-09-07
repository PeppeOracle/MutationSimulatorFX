package simulation.statistics;

import org.apache.commons.math3.stat.Frequency;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;

public class DataSort {
    private Frequency frequency;
    private int range;

    public DataSort(int range){
        this.frequency = frequency = new Frequency();
        this.range = range;
    }

    public void addValue(int value){
        frequency.addValue(value);
    }


    public int[] getArrayOfNumber() {
        int[] numbers = new int[range];

        for (int i = 0; i < range; i++) {
            numbers[i] = (int) frequency.getCount(i);
        }
        return numbers;
    }

    public int getRange(){
        return range;
    }

    public ArrayList<Point> getArrayListPoint(){

        ArrayList<Point> list = new ArrayList<>();
        for(int i = 0; i < range; i++){
            list.add(new Point(i,(int) frequency.getCount(i)));
        }
        return list;
    }
}
