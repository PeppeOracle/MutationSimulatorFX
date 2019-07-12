package simulation.wrapper;


import simulation.logic.DNAFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class MutationResults implements Serializable {
    private int numberOfEntries;
    private int numberOfReplacements;
    private int numberOfRemovals;
    private int numberOfInvariances;
    private DNAFragment dnaFragmentMutated;

    public MutationResults() {
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public void setNumberOfEntries(int numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
    }

    public int getNumberOfReplacements() {
        return numberOfReplacements;
    }

    public void setNumberOfReplacements(int numberOfReplacements) {
        this.numberOfReplacements = numberOfReplacements;
    }

    public int getNumberOfRemovals() {
        return numberOfRemovals;
    }

    public void setNumberOfRemovals(int numberOfRemovals) {
        this.numberOfRemovals = numberOfRemovals;
    }

    public int getNumberOfInvariances() {
        return numberOfInvariances;
    }

    public void setNumberOfInvariances(int numberOfInvariances) {
        this.numberOfInvariances = numberOfInvariances;
    }

    public DNAFragment getDnaFragmentMutated() {
        return dnaFragmentMutated;
    }

    public void setDnaFragmentMutated(DNAFragment dnaFragmentMutated) {
        this.dnaFragmentMutated = dnaFragmentMutated;
    }

    public void increaseEntries(){
        numberOfEntries++;
    }

    public void increaseReplacements(){
        numberOfReplacements++;
    }

    public void increaseRemovals(){
        numberOfRemovals++;
    }

    public void increaseInvariances(){
        numberOfInvariances++;
    }

    @Override
    public String toString() {
        return getClass().getName() +
                "numberOfEntries=" + numberOfEntries +
                ", numberOfReplacements=" + numberOfReplacements +
                ", numberOfRemovals=" + numberOfRemovals +
                ", numberOfInvariances=" + numberOfInvariances +
                ", dnaFragmentMutated=" + dnaFragmentMutated +
                '}';
    }
}
