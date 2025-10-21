package ui;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Solution
{
	public static final double STD_DEV = 0.01;
	public static void main(String ... args) throws IOException
	{
		final String trainPathC = "--train";
		final String testPathC = "--test";
		final String neuralNetworkC = "--nn";
		final String popSizeC = "--popsize";
		final String elitismC = "--elitism";
		final String mutationPC = "--p";
		final String gaussKC = "--K";
		final String iterationsC = "--iter";

		String arg;

		String trainPath = "";
		String testPath = "";
		String neuralNetwork = "";
		String popSize = "";
		String elitism = "";
		String mutationP = "";
		String gaussK = "";
		String iterations = "";

		for (int i = 0; i < args.length; i+=2)
		{
			arg = args[i];
			switch (arg)
			{
				case trainPathC -> trainPath = args[i + 1];
				case testPathC -> testPath = args[i + 1];
				case neuralNetworkC -> neuralNetwork = args[i + 1];
				case popSizeC -> popSize = args[i + 1];
				case elitismC -> elitism = args[i + 1];
				case mutationPC -> mutationP = args[i + 1];
				case gaussKC -> gaussK = args[i + 1];
				case iterationsC -> iterations = args[i + 1];
			}
		}

		LinkedHashMap<String, List<String>> trainMap = DataLoader.loadMapFromFile(trainPath);
		LinkedHashMap<String, List<String>> testMap = DataLoader.loadMapFromFile(testPath);

		int inputSize = trainMap.keySet().size() - 1;
		String[] nnConfigs = neuralNetwork.split("s");

		LinkedList<Integer> hiddenLayerSizes = new LinkedList<>();

		//hiddenLayerSizes.add(0);

		for(String s : nnConfigs)
		{
			String s1 = "";
			for(int i = 0; i < s.length(); i++)
			{
				if(s.charAt(i) != 's')
				{
					s1 = s1.concat(String.valueOf(s.charAt(i)));
				}
				else
				{
					break;
				}
			}
			int nOfNeurons = Integer.parseInt(s1);
			hiddenLayerSizes.add(nOfNeurons);
		}

		//NeuralNetwork nn = new NeuralNetwork(inputSize, hiddenLayerSizes);

		GeneticAlgorithm ga = new GeneticAlgorithm(hiddenLayerSizes, inputSize, Integer.parseInt(popSize), Integer.parseInt(elitism), Double.parseDouble(mutationP), Double.parseDouble(gaussK), Integer.parseInt(iterations), trainMap, testMap);

		ga.genAlg();
	}

}
