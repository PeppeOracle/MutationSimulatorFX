package simulation.utils;

import simulation.enums.AminoAcid;
import simulation.enums.Nucleotide;

import java.util.ArrayList;

public class AminoAcidUtils {
    private static AminoAcid[][] amminoacidi =
            {
                    {
                            AminoAcid
                                    .F,
                            AminoAcid
                                    .F,
                            AminoAcid
                                    .L,
                            AminoAcid
                                    .L,
                            AminoAcid
                                    .L,
                            AminoAcid
                                    .L,
                            AminoAcid
                                    .L,
                            AminoAcid
                                    .L,
                            AminoAcid
                                    .I,
                            AminoAcid
                                    .I,
                            AminoAcid
                                    .I,
                            AminoAcid
                                    .M,
                            AminoAcid
                                    .V,
                            AminoAcid
                                    .V,
                            AminoAcid
                                    .V,
                            AminoAcid
                                    .V
                    },
                    {

                            AminoAcid
                                    .S,
                            AminoAcid
                                    .S,
                            AminoAcid
                                    .S,
                            AminoAcid
                                    .S,
                            AminoAcid
                                    .P,
                            AminoAcid
                                    .P,
                            AminoAcid
                                    .P,
                            AminoAcid
                                    .P,
                            AminoAcid
                                    .T,
                            AminoAcid
                                    .T,
                            AminoAcid
                                    .T,
                            AminoAcid
                                    .T,
                            AminoAcid
                                    .A,
                            AminoAcid
                                    .A,
                            AminoAcid
                                    .A,
                            AminoAcid
                                    .A
                    },

                    {

                            AminoAcid
                                    .Y,
                            AminoAcid
                                    .Y,
                            AminoAcid
                                    .STOP,
                            AminoAcid
                                    .STOP,
                            AminoAcid
                                    .H,
                            AminoAcid
                                    .H,
                            AminoAcid
                                    .Q,
                            AminoAcid
                                    .Q,
                            AminoAcid
                                    .N,
                            AminoAcid
                                    .N,
                            AminoAcid
                                    .K,
                            AminoAcid
                                    .K,
                            AminoAcid
                                    .D,
                            AminoAcid
                                    .D,
                            AminoAcid
                                    .E,
                            AminoAcid
                                    .E
                    },

                    {

                            AminoAcid
                                    .C,
                            AminoAcid
                                    .C,
                            AminoAcid
                                    .STOP,
                            AminoAcid
                                    .W,
                            AminoAcid
                                    .R,
                            AminoAcid
                                    .R,
                            AminoAcid
                                    .R,
                            AminoAcid
                                    .R,
                            AminoAcid
                                    .S,
                            AminoAcid
                                    .S,
                            AminoAcid
                                    .R,
                            AminoAcid
                                    .R,
                            AminoAcid
                                    .G,
                            AminoAcid
                                    .G,
                            AminoAcid
                                    .G,
                            AminoAcid
                                    .G
                    }
            };

    public static AminoAcid convertCodonToAminoAcid (ArrayList<Nucleotide> codon){
        if(codon.size()!=3){
            throw new IllegalArgumentException("Size of the codon must be 3");
        }

        int[] codonIndex = {0, 0 ,0};

        for(int i = 0; i < 3; i++){
            switch (codon.get(i)) {
                case T:
                    codonIndex[i] = 0;
                    break;
                case C:
                    codonIndex[i] = 1;
                    break;
                case A:
                    codonIndex[i] = 2;
                    break;
                case G:
                    codonIndex[i] = 3;
                    break;
                default:
                    return null;
            }
        }

        int index1 = 4*codonIndex[0] + codonIndex[2];
        int index2 = codonIndex[1];

        return amminoacidi[index2][index1];
    }

}
