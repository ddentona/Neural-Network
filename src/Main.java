import java.io.File;                    // Import the File class
import java.io.FileNotFoundException;   // Import this class to handle errors
import java.util.Scanner;               // Import the Scanner class to read text files
import java.io.FileWriter;

import java.lang.Math;

public class Main {
    public static void main(String[] args) {
        Teacher church = new Teacher("data/emails.csv");

        double[][][][] bot_stats = new double[9][][][];
        for (int i = 0; i < 9; i++) {
            double[][][] bot_stat = new double[3][][];

            // First layer, where input goes
            double[][] layer_stats = new double[3][];
            for (int j = 0; j < 3; j++) {
                double[] multipliers = new double[Dictionary.loc];
                for (int k = 0; k < Dictionary.loc; k++) {
                    multipliers[k] = 0.1 * i - k * (1.0/Dictionary.loc);
                }
                layer_stats[j] = multipliers;
            }
            bot_stat[0] = layer_stats;

            // Next layers
            for (int j = 1; j < 3; j++) {
                layer_stats = new double[3][];
                for (int k = 0; k < 3; k++) {
                    layer_stats[k] = new double[]{0.1*i - 0.6, 0.1*i - 0.5, 0.1*i - 0.4};
                }
                bot_stat[j] = layer_stats;
            }
            bot_stats[i] = bot_stat;
        }
        Maker turing = new Maker(bot_stats);

        Brain[] bots = turing.getBots();
        double[] input = new double[]{1,0,1};
        double[] output = new double[bots.length];
        for (int i = 0; i < bots.length; i++) {
            double out = church.testBrain(bots[i]);
            System.out.println(out);
            output[i] = out;
        }
        System.out.print("μ = "); System.out.println(Statistics.avg(output));
        System.out.print("σ = "); System.out.println(Statistics.std_dev(output));
    }
}