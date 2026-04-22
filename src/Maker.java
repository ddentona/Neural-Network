/**
 * Maker should modify old bots and create new brains.
 */
public class Maker {
    int generation = 0;
    Brain[] bots;
    int input_count;
    double mean = 0, radius = 1; // Base statistics
    double[][][][] brains;
    int[] brains_lengths;
    private double[] last_stats;

    public Brain[] getBots() {
        return bots;
    }
    public void setBots(Brain[] bots) {
        this.bots = bots;
    }

    public int getInput_count() {
        return input_count;
    }
    public void setInput_count(int input_count) {
        this.input_count = input_count;
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

    /**
     * Generate 9 default bots
     */
    public Maker() {
        bots = new Brain[9];
        brains = new double[9][][][];
        for (int i = 0; i < bots.length; i++) {
            bots[i] = new Brain(3, 3, new int[]{3,3,3});
            brains[i] = bots[i].getFullStats();
        }
    }

    /**
     * Generates a number of default bots
     * @param bot_count number of default bots generated
     */
    public Maker(int bot_count, int input_count) {
        this.input_count = input_count;
        bots = new Brain[bot_count];
        brains = new double[bot_count][][][];
        for (int i = 0; i < bots.length; i++) {
            bots[i] = new Brain(input_count, 3, new int[]{3,3,3});
            brains[i] = bots[i].getFullStats();
        }
    }

    /**
     * Generates a number of identical bots
     * @param bot_count Number of bots generated
     * @param brain     Stats of the identical brains
     */
    public Maker(int bot_count, double[][][] brain) {
        this.brains = new double[bot_count][][][];
        for (int i = 0; i < bot_count; i++)
            this.brains[i] = brain;
        bots = new Brain[brains.length];
        for (int i = 0; i < brains.length; i++) {
            bots[i] = new Brain(brains[i]);
        }
        input_count = brains[0][0].length;
    }

    /**
     * Generate bots based on a 4D array
     * @param brains Array of Stats of each bot generated
     */
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
     * Generates a giant 1D array only consisting of one value in every spot
     * @param size  Array length. Must have 1 or more values or will return a blank array.
     *              Relu of everything, so you can put negative, but it will end up zero.
     * @param value
     * @return      Array as described
     */
    public static double[] singleValueArray(int size, double value) {
        size = S.relu(size);

        double[] output = new double[size];

        for (int i = 0; i < size; i++) {
            output[i] = value;
        }

        return output;
    }

    /**
     * Generates a giant 3D array only consisting of one value in every spot
     * @param sizes Array of lengths. Must have 3 or more values or will return a blank array.
     *              Relu of everything, so you can put negatives, but they will end up zeroes.
     * @param value
     * @return      Array as described
     */
    public static double[][][] singleValueArray(int[] sizes, double value) {
        if (sizes.length < 3) return new double[0][0][0];

        for (int i = 0; i < sizes.length; i++)
            sizes[i] = S.relu(sizes[i]);

        double[][][] output = new double[sizes[0]][sizes[1]][sizes[2]];

        for (int i = 0; i < sizes[0]; i++) {
            for (int j = 0; j < sizes[1]; j++) {
                for (int k = 0; k < sizes[2]; k++) {
                    output[i][j][k] = value;
                }
            }
        }

        return output;
    }

    /**
     * Generate a series of bots whose stats are all internally identical.
     * Externally, stats represent a series of values of constant density.
     * @param bot_count Number of bots
     * @param min   Minimum value of stats
     * @param max   Maximum value of stats
     * @return      Bots created
     */
    public Brain[] allStatCUD(int bot_count, double min, double max) {
        generation = 0;
        bots = new Brain[bot_count];
        
        double[] stats = S.cd_curve(bot_count, min, max);
        double[][][] neurons;
        for (int i = 0; i < bot_count; i++) {
            neurons = singleValueArray(new int[]{256,16,4,2}, stats[i]);

            // Modifying the first value so it lines up with the number of inputs
            for (int j = 0; j < neurons[0].length; j++) {
                double[] constants = singleValueArray(input_count, stats[i]);
                neurons[0][j] = constants;
            }

            bots[i] = new Brain(neurons);
        }

        return bots;
    }

    /**
     * Changes all bots' stats to
     * @param pos   Array of lengths. Must have 3 or more values or will raise exception.
     *              Relu of everything, so you can put negatives, but they will end up zeroes.
     * @param min
     * @param max
     * @return
     */
    public Brain[] oneStatCUD(int[] pos, double min, double max) throws ArrayIndexOutOfBoundsException {
        if(pos.length < 3)
            throw new ArrayIndexOutOfBoundsException("Location array provided has fewer than three entries.");

        generation++;

        double[] stats = S.cd_curve(bots.length, min, max);
        for (int i = 0; i < bots.length; i++) {
            brains[i][pos[0]][pos[1]][pos[2]] = stats[i];
            // Don't forget a delete point for this for a port to C++
            bots[i] = new Brain(brains[i]);
        }

        return bots;
    }
}
