package ui;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Helpers
{
    public static LinkedList<Double> getExpected(LinkedHashMap<String, List<String>> dataMap)
    {
        LinkedList<String> keys = new LinkedList<>(dataMap.keySet());
        int size = keys.size();
        String finalFeature = keys.get(size-1);

        LinkedList<String> stringValues = new LinkedList<>(dataMap.get(finalFeature));//stupac ciljne varijable
        LinkedList<Double> expected = new LinkedList<>();

        for(String s : stringValues)
        {
            expected.add(Double.parseDouble(s));
        }

        return expected;
    }

    public static Double calculateFitnes(LinkedHashMap<String, List<String>> trainMap, NeuralNetwork nn)
    {
        LinkedList<Double> expected = Helpers.getExpected(trainMap);

        LinkedList<Double> predicted = getPredicted(trainMap, nn);;

        return (1.0 / nn.calculateMSE(predicted, expected));

    }

    static LinkedList<Double> getPredicted(LinkedHashMap<String, List<String>> trainMap, NeuralNetwork nn)
    {
        int keySize = trainMap.keySet().size();
        int flag = 1;

        List<String> inputValues = new LinkedList<>();
        for(String key : trainMap.keySet())
        {
            if(flag < keySize)
            {
                inputValues = trainMap.get(key);
            }
            flag++;
        }

        LinkedList<Double> predicted = new LinkedList<>();

        for (String input : inputValues)
        {
            LinkedList<Double> inputExample = new LinkedList<>();
            inputExample.add(Double.parseDouble(input));

            double[] forwardOutput = nn.forwardPass(inputExample);
            predicted.add(forwardOutput[0]);
        }
        return predicted;
    }
}
