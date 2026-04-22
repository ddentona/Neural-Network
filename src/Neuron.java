import java.util.Random;

public class Neuron {
    double[] weight = {0.5, 0, -0.5, 0};

    Neuron() {}
    Neuron(int bias_count) {
        weight = new double[bias_count];

        // Random is temporary, only until a makerbot and teacherbot are built
        Random random = new Random();
        for (int i = 0; i < bias_count; i++) {
            weight[i] = random.nextDouble() * 2 - 1;
        }
    }
    Neuron(double[] weights) {
        double base = weight[weight.length - 1];
        weight = new double[weights.length + 1];
        for (int i = 0; i < weights.length; i++) {
            weight[i] = weights[i];
        }
        weight[weight.length-1] = base;
    }

    // Getters and setters
    public double[] getWeight() {
        return weight;
    }
    public void setWeight(double[] weights) {
        double base = weights[weights.length];
        weights = new double[weights.length + 1];
        for (int i = 0; i < weights.length; i++) {
            weight[i] = weights[i];
        }
        weight[weight.length] = base;
    }
    public double getBias() {
        return weight[weight.length];
    }
    public void setBias(double bias) {
        weight[weight.length] = bias;
    }

    boolean add_weights(int final_count) {
        if (final_count <= weight.length) return false;
        double[] new_weights = new double[final_count];
        double sum = 0;
        for (int i = 0; i < weight.length; i++) {
            new_weights[i] = weight[i];
            sum += weight[i]; // Summing up the data
        }
        sum /= weight.length; // Taking the average of the data summed up
        for (int i = weight.length; i < final_count; i++) {
            new_weights[i] = sum; // Setting the new values to be the average of the old values
        }
        weight = new_weights;
        return true;
    }

    double review(double[] input) {
        double sum = 0;
        for (int i = 0; i < weight.length - 1; i++) {
            sum += weight[i] * input[i];
        }
        sum += weight[weight.length - 1];

        return S.sigmoid(sum); // ReLU function
    }
}
