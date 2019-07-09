package simulation.logic;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import simulation.enums.Nucleotide;
import simulation.utils.NucleotidesUtils;

import java.util.ArrayList;

public class Mutator {
    /*
        p[x][y][z]:
            x - Nucleotide
            y - Operation
            z - Index
     */
    private double[][][] mutationProbabilities;
    private DNAFragment fragmentToMutate;

    public Mutator(DNAFragment sequenceToMutate){
        this.fragmentToMutate=sequenceToMutate;
        for(int x=0 ; x<4 ; x++){
            for(int z=0; z<3 ; z++){
                for(int y=0; y<4; y++){
                    mutationProbabilities[x][y][z]=0.25;
                }
            }
        }
    }

    public Mutator(DNAFragment sequenceToMutate, double[][][] mutationProbabilities){
        this.fragmentToMutate=sequenceToMutate;
        this.mutationProbabilities = mutationProbabilities;

        for(int x=0 ; x<4 ; x++){
            for(int z=0; z<3 ; z++){
                double sum=0;

                for(int y=0; y<4; y++){
                    sum+=mutationProbabilities[x][y][z];
                }

                if(sum-1 >= 1e-16){
                    throw new IllegalArgumentException("The sum of probabilities of the operations must be equals to 1");
                }
            }
        }
    }

    public DNAFragment mutate(){
        DNAFragment fragmentMutated = fragmentToMutate.clone();

        for(int i=0 ; i<fragmentMutated.getLength() ; i++){
            muteI(i, fragmentMutated);
        }

        return fragmentMutated;
    }

    private void muteI(int position, DNAFragment fragmentMutated){
        int k = position % 3;

        int nucleotide = NucleotidesUtils.getIntByNucleotide(fragmentMutated.get(position));

        double interval = 0;
        double opreal = Math.random();

        int op = 0;
        for (; op < 4; op++) {
            if (opreal <= mutationProbabilities[nucleotide][op][k]) {
                break;
            } else {
                interval = interval + mutationProbabilities[nucleotide][op][k];
            }
        }

        switch (op) {
            case 0:
                UniformIntegerDistribution pInserimento = new UniformIntegerDistribution(0, 3);
                Nucleotide letteraIns = NucleotidesUtils.getNucleotideByInt(pInserimento.sample());
                fragmentMutated.addNucleotide(k, letteraIns);
                break;
            case 1:
                fragmentMutated.removeNucleotide(k);
                break;
            case 2:
                UniformIntegerDistribution pSostituzione = new UniformIntegerDistribution(0, 3);
                int letteraSostInt = pSostituzione.sample();
                if (letteraSostInt == NucleotidesUtils.getIntByNucleotide(fragmentMutated.get(k)))
                    letteraSostInt = (letteraSostInt + 1) % 4;
                Nucleotide letteraSost = NucleotidesUtils.getNucleotideByInt(letteraSostInt);
                fragmentMutated.subNucleotide(k, letteraSost);
                break;
            case 3:
                break;
            default:
        }
    }
}
