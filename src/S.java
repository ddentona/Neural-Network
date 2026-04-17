import java.lang.Math;

/**
 * A class for all my statistics code.
 * Calling this simply S to keep it simple since I expect to use it a lot.
 */
public class S {
    public static int avg(int[] in) {
        int out = 0;
        for (int i : in)
            out += i;
        return out / in.length;
    }

    public static double avg(double[] in) {
        double out = 0;
        for (double i : in)
            out += i;
        return out / in.length;
    }

    public static float avg(float[] in) {
        float out = 0;
        for (float i : in)
            out += i;
        return out / in.length;
    }

    public static double avg_dev(double[] in) {
        double[] deviations = new double[in.length];
        double avg = avg(in);
        for (int i = 0; i < in.length; i++)
            deviations[i] = Math.abs(avg - in[i]);

        return avg(deviations);
    }

    public static double std_dev(double[] in) {
        double[] deviations = new double[in.length];
        double avg = avg(in);
        for (int i = 0; i < in.length; i++)
            deviations[i] = Math.pow(avg - in[i], 2);

        return Math.sqrt(avg(deviations));
    }

    public static double sigmoid(double x) {
        return 1/(1 + Math.exp(x));
    }

    public static int relu(int x) {
        if (x > 0) return x; else return 0;
    }

    public static double relu(double x) {
        if (x > 0) return x; else return 0;
    }

    /**
     * Generates [count] values consistent with a constant density curve.
     * @param count Number of values generated
     * @param min   Lower bound of values
     * @param max   Upper bound of values
     * @return      Array of values consistent with a constant density curve
     */
    public static double[] cd_curve(int count, double min, double max) {
        double[] curve = new double[count];

        for (int i = 0; i < count; i++) {
            curve[i] = (1.0*i / (count - 1)) * (max - min) + min;
        }
        
        return curve;
    }

    public static void main(String[] args) {
//        System.out.println(avg(new double[] {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9}));
        for (double arg : cd_curve(11, 10, 20)) {
            System.out.println(arg);
        }
    }
}
