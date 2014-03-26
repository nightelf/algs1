package graph;

import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.introcs.In;

/**
 * Word Net.
 */
public class WordNet {

    /**
     * Synsets.
     */
    private String[] synsets;
    
    /**
     * SynsetsLength.
     */
    private int synsetsLength;
    
    /**
     * Hypernyms.
     */
    private int[][] hypernyms;
    
    /**
     * Nouns.
     */
    private ST<String, int[]> nouns;
    
    /**
     * constructor takes the name of the two input files.
     * @param synsets a csv file
     * @param hypernyms a csv file
     */
    public WordNet(String synsets, String hypernyms) {
        
        this.synsets = new String[4];
        nouns = new ST<String, int[]>();
        this.hypernyms = new int[4][1];
        
        In inSyn = new In(synsets);
        In inHyper = new In(hypernyms);
        int synsetId;
        int[] nounIds1;
        int[] nounIds2;
        
        String line = inSyn.readLine();
        String[] fields;

        while (null != line) {
            fields = line.split(",");
            synsetId = Integer.parseInt(fields[0]);
            if (synsetId >= synsetsLength)
                resizeSynsets((synsetId + 1) * 2);
            this.synsets[synsetId] = fields[1];
            fields = fields[1].split(" ");
            
            // TODO put in it's own function
            for (int i = 0; i < fields.length; i++) {
                if (nouns.contains(fields[i])) {
                    nounIds1 = nouns.get(fields[i]);
                    nounIds2 = new int[nounIds1.length + 1];
                    for (int j = 0; j < nounIds1.length; j++) {
                        nounIds2[j] = nounIds1[j];
                    }
                    nounIds2[nounIds2.length - 1] = synsetId;
                } else {
                    nounIds2 = new int[1];
                    nounIds2[0] = synsetId;
                }
                nouns.put(fields[i], nounIds2);
            }
            line = inSyn.readLine();
        }
        
        //hypernyms = new ArrayList<int>();
    }

    /**
     * the set of nouns (no duplicates), returned as an Iterable.
     */
    public Iterable<String> nouns() {
        
        return nouns;
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
     * resize the underlying array holding the elements.
     * @param capacity
     */
    private void resizeSynsets(int capacity) {
        
        assert capacity >= synsetsLength;
        String[] temp = (String[]) new String[capacity];
        for (int i = 0; i < synsetsLength; i++)
            temp[i] = synsets[i];
        synsets = temp;
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
