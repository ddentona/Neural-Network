import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Teacher church = new Teacher("data/emails.csv");

        Maker turing = new Maker(21, Dictionary.loc);

        turing.allStatCUD(21, -10, 10);

        System.out.println("Generation 1");
        Brain[] bots = turing.getBots();
        double[] input = new double[]{1,0,1};
        double[] output = new double[bots.length];
        for (int i = 0; i < bots.length; i++) {
            double out = church.testBrain(bots[i]);
            System.out.println(out);
            output[i] = out;
        }
        double sd = 1; // S.std_dev(output);
        int minPoint = S.minPoint(output);
        System.out.print("μ = "); System.out.println(S.avg(output));
        System.out.print("σ = "); System.out.println(S.std_dev(output));

        System.out.println();
        System.out.println("Generation 2");
        double[][][][] brains = turing.getBrains();
        double base = brains[minPoint][0][0][0];
        turing.allStatCUD(brains.length, base, base);
        double bestError = output[minPoint];
        // Run through the entire set of bot stats
        for (int p = 0; p < 100; p++) {
            for (int i = 0; i < brains[0].length; i++) {
                for (int j = 0; j < brains[0][i].length; j++) {
                    for (int k = 0; k < brains[0][i][j].length; k++) {
                        base = brains[minPoint][i][j][k];
                        turing.oneStatCUD(new int[]{i, j, k}, base - sd, base + sd);
                        for (int l = 0; l < turing.getBots().length; l++) {
                            double out = church.testBrain(turing.getBots()[l]);
                            output[l] = out;
                        }
                        minPoint = S.minPoint(output);
                        if (output[minPoint] < bestError)
                            bestError = output[minPoint];
                        else
                            base = brains[minPoint][i][j][k];
                        turing.oneStatCUD(new int[]{i, j, k}, base, base);
                        brains = turing.getBrains();
                    }
                }
            }
            sd *= 0.95;
            if(sd < 0.01) sd = 0.01;
            System.out.println(p + ": " + output[minPoint] + "; " + sd);
        }
        System.out.println();
        for (int i = 0; i < turing.getBots().length; i++) {
            double out = church.testBrain(turing.getBots()[i]);
            System.out.println(out);
            output[i] = out;
        }
        System.out.print("μ = "); System.out.println(S.avg(output));
        System.out.print("σ = "); System.out.println(S.std_dev(output));

        System.out.println();
        System.out.println("Enter an email title:");
        String in = "";
        Scanner scanner = new Scanner(System.in);
        while (!in.equals("quit")) {
            in = scanner.nextLine();
            double[] email = Parser.parseWords(in);
            double chance = turing.getBots()[0].process(email);
            System.out.println((chance * 100) + "% chance this is spam.");
        }
    }
}