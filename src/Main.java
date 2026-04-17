public class Main {
    public static void main(String[] args) {
        Teacher church = new Teacher("data/emails.csv");

        Maker turing = new Maker();
        turing.setInput_count(Dictionary.loc);

        turing.generateConstantCurve(11, 0, 1);

        Brain[] bots = turing.getBots();
        double[] input = new double[]{1,0,1};
        double[] output = new double[bots.length];
        for (int i = 0; i < bots.length; i++) {
            double out = church.testBrain(bots[i]);
            System.out.println(out);
            output[i] = out;
        }
        System.out.print("μ = "); System.out.println(S.avg(output));
        System.out.print("σ = "); System.out.println(S.std_dev(output));
    }
}