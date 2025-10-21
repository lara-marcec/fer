package ui;

import java.util.*;

public class GeneticAlgorithm
{
    private final int inputSize;
    private final LinkedList<Integer> hiddenLayersSizes;
    private final int popSize;
    private final int elitism;
    private final double mutationP;
    private final double gaussK;
    private final int iterations;
    private final LinkedHashMap<String, List<String>> trainMap;
    private final LinkedHashMap<String, List<String>> testMap;

    public GeneticAlgorithm(LinkedList<Integer> hiddenLayersSizes, int inputSize, int popSize, int elitism, double mutationP, double gaussK, int iterations, LinkedHashMap<String, List<String>> trainMap, LinkedHashMap<String, List<String>> testMap) {

        this.popSize = popSize;
        this.elitism = elitism;
        this.mutationP = mutationP;
        this.gaussK = gaussK;
        this.iterations = iterations;
        this.trainMap = trainMap;
        this.testMap = testMap;
        this.hiddenLayersSizes = hiddenLayersSizes;
        this.inputSize = inputSize;
    }

    public void genAlg()
    {
        LinkedList<NeuralNetwork> startingPopulation = initPopulation();
        LinkedList<Double> fitnessValues = new LinkedList<>();

        for(int i = 1; i <= iterations; i++)
        {
            LinkedList<NeuralNetwork> newPopulation = new LinkedList<>();

            if(i>1)//preskacemo prvi slucaj kad fitnes nije definirano tj kad nije bilo prethodne iteracije
            {
                for (int j = 0; j < elitism; j++)
                {
                    int indexOfBestUnit = fitnessValues.indexOf(Collections.max(fitnessValues));
                    //najbolja jedinka ima max fitness jer ta ima min kvadratno odstupanje
                    newPopulation.add(startingPopulation.get(indexOfBestUnit));
                    fitnessValues.remove(indexOfBestUnit);
                }
            }

            if(i % 2000 == 0)
            {
                double bestMSE = ( 1.0 / Collections.max(fitnessValues) ) ;
                System.out.format("[Train error @%d]: %.5f%n", i, bestMSE);
            }

            fitnessValues = new LinkedList<>();

            for(NeuralNetwork nn : startingPopulation)
            {
               fitnessValues.add(Helpers.calculateFitnes(trainMap, nn)) ;
            }

            while (newPopulation.size() < popSize)
            {
                NeuralNetwork parent1 = getParent(startingPopulation, fitnessValues);
                NeuralNetwork parent2 = getParent(startingPopulation, fitnessValues);
                NeuralNetwork child = crossover(parent1, parent2);
                mutate(child);
                newPopulation.add(child);
            }

            startingPopulation = newPopulation;

        }
        LinkedList<Double> testExpected = Helpers.getExpected(testMap);

        int indexOfBestUnit = fitnessValues.indexOf(Collections.max(fitnessValues));
        NeuralNetwork bestNN = startingPopulation.get(indexOfBestUnit);

        LinkedList<Double> predicted = Helpers.getPredicted(testMap, bestNN);

        double testMSE = bestNN.calculateMSE(predicted, testExpected);
        System.out.format("[Test error]: %.5f%n", testMSE);

    }

    private LinkedList<NeuralNetwork> initPopulation()
    {
        LinkedList<NeuralNetwork> population = new LinkedList<>();
        for(int i = 0; i < popSize; i++)
        {
            population.add(new NeuralNetwork(inputSize, hiddenLayersSizes ));
        }
        return population;
    }

    private NeuralNetwork getParent(LinkedList<NeuralNetwork> pop, LinkedList<Double> fitness)
    {
        Random random = new Random();

        double totalFitnes = 0.0;

        for(Double f : fitness)
        {
            totalFitnes += f;
        }

        double rand = random.nextDouble() * totalFitnes;
        double fitnesSum = 0.0;

        for(int i = 0; i< pop.size(); i++)
        {
            fitnesSum += fitness.get(i);
            if(rand <= fitnesSum)
            {
                return pop.get(i);
            }
        }
        return pop.get(0);
    }
    //fitness proportionate selection pseudokod i objasnjenje - izvor: https://en.wikipedia.org/wiki/Fitness_proportionate_selection

    private NeuralNetwork crossover(NeuralNetwork parent1, NeuralNetwork parent2)
    {
        NeuralNetwork child = new NeuralNetwork();

        for (int i = 0; i < parent1.layers.size(); i++)
        {
            Layer parent1Layer = parent1.layers.get(i);
            Layer parent2Layer = parent2.layers.get(i);

            Layer childLayer = new Layer(parent1Layer.inputSize, parent1Layer.outputSize);

            for (int j = 0; j < parent1Layer.outputSize; j++)
            {
                childLayer.biases[j] = (parent1Layer.biases[j] + parent2Layer.biases[j]) / 2;
                //aritmeticka sredina

                for (int k = 0; k < parent1Layer.inputSize; k++)
                {
                    childLayer.weights[j][k] = (parent1Layer.weights[j][k] + parent2Layer.weights[j][k]) / 2;
                }
            }
            child.layers.add(childLayer);
        }
        return child;
    }

    private void mutate(NeuralNetwork nn)
    {
        //iz uputa: kromosomu tezina pribrojite vektor uzorkovan iz normalne razdiobe sa standardnom devijacijom K
        // svaku tezinu kromosoma mutirajte s vjerojatnoscu p
        Random random = new Random();

        for (Layer l : nn.layers)
        {
            for (int i = 0; i < l.outputSize; i++)
            {
                if (random.nextDouble() < mutationP)
                {
                    l.biases[i] += random.nextGaussian() * gaussK;
                }
                for (int j = 0; j < l.inputSize; j++)
                {
                    if (random.nextDouble() < mutationP)
                    {
                        l.weights[i][j] += random.nextGaussian() * gaussK;
                    }
                }
            }
        }
    }

}
