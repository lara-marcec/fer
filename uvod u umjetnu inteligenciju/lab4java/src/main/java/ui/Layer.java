package ui;

import java.util.Random;

import static ui.Solution.STD_DEV;

public class Layer
{
    double[][] weights;
    double[] biases;

    int inputSize;
    int outputSize;

    Random random;

    public Layer(int inputSize, int outputSize){
        this.inputSize = inputSize;
        this.outputSize = outputSize;

        this.weights = new double[outputSize][inputSize];
        this.biases = new double[outputSize];
        this.random = new Random();

        weightsInit();
    }

    private void weightsInit() //inicijalizacija tezina i pristranosti
    {
        for(int i = 0; i< outputSize; i++)
        {
            for(int j = 0; j < inputSize; j++)
            {
                weights[i][j] = random.nextGaussian() * STD_DEV;
            }
            biases[i] = random.nextGaussian() * STD_DEV;
        }
    }

    public double[] calculateH(double[] inputValue, boolean applySigmoid)
    {
        double[] h = new double[outputSize];//dimenzija h je outputSize sloja

        for(int i = 0; i < outputSize; i++){

            for(int j = 0; j < inputSize; j++)
            {
                h[i] += weights[i][j] * inputValue[j];
            }

            h[i] += biases[i];

            if(!applySigmoid) {
                h[i] = sigmoid(h[i]);
            }
        }
        return h;
    }

    private double sigmoid(double net)
    {
        return 1.0 / (1.0 + Math.exp(-net));
    }
}
