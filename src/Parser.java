public class Parser {
    public static double[] parseWords(String input) {
        String[] words = input.toLowerCase().split(" ");
        int[] wordLocs = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            wordLocs[i] = Dictionary.getWordLocation(words[i]);
        }

        double[] wordsThere = new double[Dictionary.loc];
        for (int wordLoc : wordLocs) {
            wordsThere[wordLoc] += 1.0;
        }

        return wordsThere;
    }
}
