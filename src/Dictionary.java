import java.util.Objects;

public class Dictionary {
    static int size = 0, loc = 0;
    static String[] words = new String[1];
    static int[] wordLocs = new int[1];

    /**
     * Compares two strings.
     * As long as it compares them somehow, it's fine.
     * @param s1
     * @param s2
     * @return A omparison of the two strings
     */
    public static boolean stringGeq(String s1, String s2) {
        int i = 0;
        while (s1.charAt(i) == s2.charAt(i)) {
            i++;
            if (i >= s1.length() && i >= s2.length()) return true;
            else if (i >= s1.length()) return false;
            else if (i >= s2.length()) return true;
        }
        return s1.charAt(i) >= s2.charAt(i);
    }

    /**
     * Expands the dictionary
     */
    public static void expand() {
        String[] newWords = new String[size * 3];
        int[] newLocs = new int[size * 3];
        for (int i = 0; i < size; i++) {
            newWords[i] = words[i];
            newLocs[i] = wordLocs[i];
        }
        for (int i = size; i < 3 * size; i++) {
            newLocs[i] = -1;
        }
        size *= 3;

        words = newWords;
        wordLocs = newLocs;
    }

    /**
     * Get the location of a word in the dictionary
     * @param word
     * @return
     */
    public static int getWordLocation(String word) {
        if (size == 0) {
            words[0] = word;
            wordLocs[0] = 0;
            size = 1;
            loc = 1;
            return 0;
        }
        int i = 0;
        while (!Objects.equals(words[i], word)) {
            if(stringGeq(words[i], word)) i = 2*i + 1;
            else i = 2*i + 2;
            if (i >= size) expand();
            if (wordLocs[i] == -1) {
                words[i] = word;
                wordLocs[i] = loc;
                loc++;
                return loc - 1;
            }
        }
        return wordLocs[i];
    }

    public static void main(String[] args) {
        String[] words = new String[]{"it", "was", "the", "best", "of", "times", "it", "was", "the", "worst", "of", "times"};
        for (String word : words) {
            System.out.println(getWordLocation(word));
        }
    }
}
