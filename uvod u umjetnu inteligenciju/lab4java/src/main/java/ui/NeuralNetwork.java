package ui;

import java.util.LinkedList;

public class NeuralNetwork
{
    LinkedList<Layer> layers;
    int inputSize;
    int outputSize;

    public NeuralNetwork(int inputSize,  LinkedList<Integer> hiddenLayersSizes )
    {
        this.inputSize = inputSize;
        this.outputSize = 1;
        this.layers = new LinkedList<>();

        layers.add(new Layer(inputSize, hiddenLayersSizes.get(0)));

        for(int i = 0; i < hiddenLayersSizes.size(); i++)
        {
            if(i + 1 >= hiddenLayersSizes.size())
            {
                layers.add(new Layer(hiddenLayersSizes.get(i), outputSize)); //ako je zadnji sloj, output dimenzija je 1
            }
            else
            {
                layers.add(new Layer(hiddenLayersSizes.get(i), hiddenLayersSizes.get(i+1)));
            }

        }//skriveni slojevi

    }

    public NeuralNetwork()
    {
        this.layers = new LinkedList<>();
    }

    public double calculateMSE (LinkedList<Double> predicted, LinkedList<Double> expected)
    {
        //srednje kvadratno odstupanje
        double mse;

        double sum = 0.0;
        for(int i = 0; i < predicted.size(); i++)
        {
            sum += Math.pow((predicted.get(i) - expected.get(i)), 2);
        }
        mse = sum / predicted.size();

        return mse;
    }

    public double[] forwardPass(LinkedList<Double> inputValue)
    {
        double[] output = new double[inputValue.size()];
        int index = 0;

        for (Double value : inputValue)
        {
            output[index] = value;
            index++;
        }//iz liste u array

        int layersSize = layers.size();
        int counter = 0;

        for(Layer l : layers)
        {
            counter++;
            if(counter < layersSize)
            {
                output = l.calculateH(output, false);
            }
            else
            {
                output = l.calculateH(output, true);
            }//na zadnji sloj ne primjenjujemo sigmoid fju
        }
        return output;
    }
}
