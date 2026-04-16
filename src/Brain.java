public class Brain {
    Neuron[][] neurons;
    int[] neuron_count;
    int inputs;

    public Brain(int input_count, int layer_count, int[] layer_widths) {
        neuron_count = layer_widths;
        neurons = new Neuron[layer_count + 1][];

        // First hidden set of neurons (n=0)
        neurons[0] = new Neuron[neuron_count[0]];
        for (int j = 0; j < neuron_count[0]; j++) {
            neurons[0][j] = new Neuron(input_count); // Input count of the previous layer's neuron count
        }

        // Following hidden layers of neurons
        for (int i = 1; i < neurons.length - 1; i++) {
            neurons[i] = new Neuron[neuron_count[i]];
            for (int j = 0; j < neuron_count[i]; j++) {
                neurons[i][j] = new Neuron(neuron_count[i-1]); // Input count of the previous layer's neuron count
            }
        }

        // Output neurons
        neurons[layer_count] = new Neuron[1];
        neurons[layer_count][0] = new Neuron(neuron_count[layer_count - 1]);
    }
    public Brain(double[][][] neuron_weights) {
        neuron_count = new int[neuron_weights.length];
        neurons = new Neuron[neuron_weights.length][];
        int row = 0;
        for (double[][] neuronColumn : neuron_weights) {
            neuron_count[row] = neuronColumn.length;
            neurons[row] = new Neuron[neuron_weights[row].length];
            int column = 0;
            for (double[] new_neurons : neuronColumn) {
                neurons[row][column] = new Neuron(new_neurons);
                column++;
            }
            row++;
        }
    }

    boolean expand_inputs(int count) {
        boolean necessary = false;
        for (int i = 0; i < neurons[0].length; i++) {
            necessary = neurons[0][i].add_weights(count) || necessary;
        }
        return necessary;
    }

    /**
     * Get the response by running the data through the brain
     * @param input
     * @return Response from going through the neural network
     */
    double process(double[] input) {
        if (input.length > inputs)
            expand_inputs(input.length);
        double[] data_passed = input;
        for (int i = 0; i < neurons.length - 1; i++) { // For each
            double[] next_layer_data = new double[neuron_count[i]];
            for (int j = 0; j < neuron_count[i]; j++) {
                next_layer_data[j] = neurons[i][j].review(data_passed);
            }
            data_passed = next_layer_data;
        }
        return neurons[neurons.length - 1][0].review(data_passed);
    }
}
