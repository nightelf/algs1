package graph;

import java.util.ArrayList;

import edu.princeton.cs.introcs.In;

/**
 * Word Net.
 */
public class WordNet {

    private ArrayList<String> theSynsets;
    /**
     * constructor takes the name of the two input files.
     * @param synsets a csv file
     * @param hypernyms a csv file
     */
    public WordNet(String synsets, String hypernyms) {
        
        theSynsets = new ArrayList<String>();
        In inSyn = new In(synsets);
        String line = inSyn.readLine();
        String[] fields;

        while (null != line) {
            fields = line.split(",");
            theSynsets.add(Integer.parseInt(fields[0]), fields[1]);
            line = inSyn.readLine();
        }
    }

    /**
     * the set of nouns (no duplicates), returned as an Iterable.
     */
    public Iterable<String> nouns() {
        
        return theSynsets;
    }

    /**
     * is the word a WordNet noun?
     * @param word
     * @return
     */
    public boolean isNoun(String word) {
        return false;
    }

    /**
     * distance between nounA and nounB (defined below).
     * @param nounA
     * @param nounB
     * @return
     */
    public int distance(String nounA, String nounB) {
        return 0;
    }

    /**
     * a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     * in a shortest ancestral path (defined below).
     * @param nounA
     * @param nounB
     * @return
     */
    public String sap(String nounA, String nounB) {
        return "";
    }

    /**
     * For unit testing of this class.
     * @param args
     */
    public static void main(String[] args) {
        
        WordNet w = new WordNet(args[0], args[1]);
        for (String foo : w.nouns()) {
            System.out.println(foo);
        }
    }
}
