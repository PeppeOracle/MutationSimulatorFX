package simulation.wrapper;

import simulation.enums.AminoAcid;
import simulation.logic.DNAFragment;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class SimulationResults implements Serializable {
    private MutationResults mutationResults;
    private HashMap<String,Integer> hashMapOfLabeledComparator;
    private int[] mutazioniMissensoBernoulli;
    private int[] mutazioniSilenceBernoulli;
    private int[] mutazioniImmutatoBernoulli;

    public SimulationResults(MutationResults mutationResults, HashMap<String, Integer> hashMapOfLabeledComparator) {
        this.mutationResults = mutationResults;
        this.hashMapOfLabeledComparator = hashMapOfLabeledComparator;
    }

    public MutationResults getMutationResults() {
        return mutationResults;
    }

    public HashMap<String, Integer> getHashMapOfLabeledComparator() {
        return hashMapOfLabeledComparator;
    }

    @Override
    public String toString() {
        return getClass().getName() +
                "mutationResults=" + mutationResults +
                ", hashMapOfLabeledComparator=" + hashMapOfLabeledComparator +
                '}';
    }


    public int getNumberOfEntries() {
        return mutationResults.getNumberOfEntries();
    }


    public int getNumberOfReplacements() {
        return mutationResults.getNumberOfReplacements();
    }


    public int getNumberOfRemovals() {
        return mutationResults.getNumberOfRemovals();
    }


    public int getNumberOfInvariances() {
        return mutationResults.getNumberOfInvariances();
    }

    public DNAFragment getDnaFragmentMutated() {
        return mutationResults.getDnaFragmentMutated();
    }

    public int getSize(){
        return mutationResults.getSize();
    }

    public void setMutazioniMissensoBernoulli(DNAFragment fr, DNAFragment frMutated){
        ArrayList<AminoAcid> aminoAcids1 = fr.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = frMutated.getAminoAcids();

        int n = aminoAcids1.size();
        int m = aminoAcids2.size();

        mutazioniMissensoBernoulli = new int[n];

        int min = Math.min(m,n);

        for(int i = 0; i < min; i++){
            if(aminoAcids1.get(i) != aminoAcids2.get(i)){
                mutazioniMissensoBernoulli[i] = 1;
            }
        }

        if(n>m){
            for(int i = m; i < n; i++){
                 mutazioniMissensoBernoulli[i] = 1;
            }
        }
    }

    public void setMutazioniSilenceBernoulli(DNAFragment fr, DNAFragment frMutated){
        ArrayList<AminoAcid> aminoAcids1 = fr.getAminoAcids();
        ArrayList<AminoAcid> aminoAcids2 = frMutated.getAminoAcids();

        int n = aminoAcids1.size();
        int m = aminoAcids2.size();

        mutazioniSilenceBernoulli = new int[n];

        int min = Math.min(m,n);

        for(int i=0;i<min; i++){
            if(aminoAcids1.get(i) == aminoAcids2.get(i)) {
                int offset = 3*i;
                if(fr.get(offset) != frMutated.get(offset) || fr.get(offset+1) != frMutated.get(offset+1) || fr.get(offset+2) != frMutated.get(offset+2)) {
                    mutazioniSilenceBernoulli[i]=1;
                }
            }
        }

    }

    public void setMutazioniImmutatoBernoulli(DNAFragment fr, DNAFragment frMutated){
        mutazioniImmutatoBernoulli = new int[fr.getNumberOfCodon()];

        int n = fr.getNumberOfCodon();
        int m = frMutated.getNumberOfCodon();

        int min = Math.min(n,m);

        for(int i=0;i<min; i++){
            int offset = 3*i;
            if(fr.get(offset) == frMutated.get(offset) && fr.get(offset+1) == frMutated.get(offset+1) && fr.get(offset+2) == frMutated.get(offset+2)) {
                mutazioniImmutatoBernoulli[i]=1;
            }
        }
    }

    public int[] getMutazioniMissensoBernoulli() {
        return mutazioniMissensoBernoulli;
    }

    public int[] getMutazioniSilenceBernoulli() {
        return mutazioniSilenceBernoulli;
    }

    public int[] getMutazioniImmutatoBernoulli() {
        return mutazioniImmutatoBernoulli;
    }
}
