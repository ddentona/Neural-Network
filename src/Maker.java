/**
 * Maker should modify old bots and create new brains.
 */
public class Maker {
    int generation = 0;
    Brain[] bots;
    int input_count;
    double mean = 0, radius = 1; // Base statistics
    double[][][][] brains;

    public Brain[] getBots() {
        return bots;
    }
    public void setBots(Brain[] bots) {
        this.bots = bots;
    }

    public double[][][][] getBrains() {
        return brains;
    }
    public void setBrains(double[][][][] brains) {
        this.brains = brains;
    }

    public double getMean() {
        return mean;
    }
    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Maker() {
        bots = new Brain[9];
    }
    public Maker(int bot_count) {
        bots = new Brain[bot_count];
    }
    public Maker(double[][][][] brains) {
        this.brains = brains;
        bots = new Brain[brains.length];
        for (int i = 0; i < brains.length; i++) {
            bots[i] = new Brain(brains[i]);
        }
        input_count = brains[0][0].length;
    }

    public Brain[] generate() {
        bots = new Brain[brains.length];
        for (int i = 0; i < brains.length; i++) {
            bots[i] = new Brain(brains[i]);
        }
        return bots;
    }

    /**
     * Generate a series of bots whose stats are all internally identical.
     * Externally, stats represent a series of values of constant density.
     * @param count Number of bots
     * @param min   Minimum value of stats
     * @param max   Maximum value of stats
     * @return      Bots created
     */
    public Brain[] generateConstantCurve(int count, double min, double max) {
        bots = new Brain[count];
        
        double[] stats = Statistics.cd_curve(count, min, max);
        for (int i = 0; i < count; i++) {
            
        }

        return bots;
    }
}
