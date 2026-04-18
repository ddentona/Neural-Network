public class Main {
    public static void main(String[] args) {
        Teacher church = new Teacher("data/emails.csv");

        Maker turing = new Maker(11, Dictionary.loc);

        turing.allStatCUD(11, -1, 1);

        System.out.println("Generation 1");
        Brain[] bots = turing.getBots();
        double[] input = new double[]{1,0,1};
        double[] output = new double[bots.length];
        for (int i = 0; i < bots.length; i++) {
            double out = church.testBrain(bots[i]);
            System.out.println(out);
            output[i] = out;
        }
        double sd = S.std_dev(output);
        int minPoint = S.minPoint(output);
        System.out.print("μ = "); System.out.println(S.avg(output));
        System.out.print("σ = "); System.out.println(S.std_dev(output));

        System.out.println();
        System.out.println("Generation 2");
        double[][][][] brains = turing.getBrains();
        double base = brains[minPoint][0][0][0];
        turing.allStatCUD(brains.length, base, base);
        // Run through the entire set of bot stats
        for (int i = 0; i < brains[0].length; i++) {
            for (int j = 0; j < brains[0][i].length; j++) {
                for (int k = 0; k < brains[0][i][j].length; k++) {
                    base = brains[minPoint][i][j][k];
                    turing.oneStatCUD(new int[]{i,j,k}, base - sd, base + sd);
                    for (int l = 0; l < turing.getBots().length; l++) {
                        double out = church.testBrain(turing.getBots()[i]);
                        output[i] = out;
                    }
                    minPoint = S.minPoint(output);
                    base = brains[minPoint][i][j][k];
                    turing.oneStatCUD(new int[]{i,j,k}, base, base);
                    sd = S.std_dev(output);
                    brains = turing.getBrains();

                    System.out.println(S.avg(output));
                }
            }
        }
        System.out.println();
        for (int i = 0; i < turing.getBots().length; i++) {
            double out = church.testBrain(turing.getBots()[i]);
            System.out.println(out);
            output[i] = out;
        }
        System.out.print("μ = "); System.out.println(S.avg(output));
        System.out.print("σ = "); System.out.println(S.std_dev(output));
    }
}

// TODO: Adapt to include the bias stat