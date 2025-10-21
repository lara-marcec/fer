package ui;

import java.util.*;

public class PredictUtils
{
    public static String getPrediction(Node root, Map<String, String> instance, LinkedHashMap<String, List<String>> testData)
    {
        Node node = root;

        while (node.getChildren().size() > 0)
        {
            String feature = node.getFeature();
            String featureValue = instance.get(feature);

            if (node.getChildren().containsKey(featureValue))
            {
                node = node.getChildren().get(featureValue);
            }
            else
            {
                return ID3.argMaxV(testData);
            }
        }

        return node.getLabel();
    }

    public static void getAccuracy(LinkedHashMap<String, List<String>> testData, LinkedList<String> predictions)
    {
        double accuracy;
        double total = predictions.size() * 1.0;

        List<String> testFeatures = new ArrayList<>(testData.keySet());
        String finalFeature = testFeatures.get(testFeatures.size() - 1);
        LinkedList<String> finalValues = new LinkedList<>(testData.get(finalFeature));

        int correct = 0;

        for(int i = 0; i < total; i++)
        {
            if(Objects.equals(finalValues.get(i), predictions.get(i)))
            {
                correct++;
            }
        }

        accuracy = (correct * 1.0) / total;

        System.out.format("[ACCURACY]: %.5f", accuracy);
        System.out.println();
    }

    public static void getConfusionMatrix(LinkedHashMap<String, List<String>> testData, LinkedList<String> predictions)
    {
        List<String> actualLabels = testData.get(testData.keySet().toArray()[testData.keySet().size() - 1]);

        Set<String> labels = new TreeSet<>();
        labels.addAll(actualLabels);
        labels.addAll(predictions);

        List<String> sortedLabels = new ArrayList<>(labels);
        Collections.sort(sortedLabels);

        int[][] confusionMatrix = new int[sortedLabels.size()][sortedLabels.size()];

        for (int i = 0; i < actualLabels.size(); i++)
        {
            String actual = actualLabels.get(i);
            String predicted = predictions.get(i);
            int actualIndex = sortedLabels.indexOf(actual);
            int predictedIndex = sortedLabels.indexOf(predicted);
            confusionMatrix[actualIndex][predictedIndex]++;
        }

        System.out.println("[CONFUSION_MATRIX]:");
        for (int[] row : confusionMatrix)
        {
            for (int v : row)
            {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }
}
