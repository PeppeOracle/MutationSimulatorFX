package simulation.logic;

import simulation.utils.AminoAcidUtils;
import simulation.enums.AminoAcid;
import simulation.enums.Nucleotide;

import java.util.ArrayList;
import java.util.Random;

public class DNAFragment implements Cloneable{
    private ArrayList<Nucleotide> nucleotidesSequence;

    public DNAFragment(int length){
        nucleotidesSequence = new ArrayList<Nucleotide>();
        Random randomGenerator = new Random();
        for (int i = 0; i < length; i++) {
            int randomNumber = randomGenerator.nextInt(4);
            switch (randomNumber) {
                case 0:
                    nucleotidesSequence.add(Nucleotide.T);
                    break;
                case 1:
                    nucleotidesSequence.add(Nucleotide.C);
                    break;
                case 2:
                    nucleotidesSequence.add(Nucleotide.A);
                    break;
                case 3:
                    nucleotidesSequence.add(Nucleotide.G);
                    break;
            }
        }
    }

    public DNAFragment(ArrayList<Nucleotide> nucleotidesSequence){
        this.nucleotidesSequence=nucleotidesSequence;
    }

    public void addNucleotide(int position, Nucleotide nucleotide){
        nucleotidesSequence.add(position,nucleotide);
    }

    public void removeNucleotide(int position){
        nucleotidesSequence.remove(position);
    }

    public Nucleotide subNucleotide(int position, Nucleotide nucleotide){
        return nucleotidesSequence.set(position,nucleotide);
    }

    public int getLength() {
        return nucleotidesSequence.size();
    }

    public Nucleotide get(int position) {
        return nucleotidesSequence.get(position);
    }

    public ArrayList<Nucleotide> getNucleotides(){
        return nucleotidesSequence;
    }

    public ArrayList<AminoAcid> getAminoAcids() {
        ArrayList<AminoAcid> sequence = new ArrayList<AminoAcid>();

        for (int i = 0; i < this.getLength(); i += 3) {
            ArrayList<Nucleotide> codon = new ArrayList<Nucleotide>();
            codon.add(this.get(i));
            codon.add(this.get(i+1));
            codon.add(this.get(i+2));
            sequence.add(AminoAcidUtils.convertCodonToAminoAcid(codon));
        }

        return sequence;
    }

    @Override
    public DNAFragment clone() {
        try {
            ArrayList<Nucleotide> list = (ArrayList<Nucleotide>) this.nucleotidesSequence.clone();
            DNAFragment sequenzaNucleotidi = new DNAFragment(list);
            return sequenzaNucleotidi;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}


