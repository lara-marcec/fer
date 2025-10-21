package ui;

import java.util.*;

public class ID3
{
    private Node root;
    private final Integer limit;

    public ID3()
    {
        this.limit = null;
    }

    public ID3(Integer limit)
    {
        this.limit = limit;
    }

    public void fit(LinkedHashMap<String, List<String>> trainData)
    {
        LinkedList<String> features = new LinkedList<>(trainData.keySet());
        features.removeLast(); //uklanjam znacajku koja predstavlja ciljnu znacajku

        this.root = id3Alg(trainData, trainData, features, limit); //pozivam id3 alg koji vraca neki cvor koji predstavlja korijen

        System.out.println("[BRANCHES]:");
        ID3Utils.printBranches(this.root, "", 1); //rekurzivni ispis grana
    }

    public void predict(LinkedHashMap<String, List<String>> testData)
    {
        if(root == null)
        {
            return;
        }

        LinkedList<String> predictions = new LinkedList<>();
        List<String> testFeatures = new ArrayList<>(testData.keySet());
        testFeatures.remove(testFeatures.size() - 1);

        for (int i = 0; i < testData.get(testFeatures.get(0)).size(); i++)
        {
            Map<String, String> instance = new HashMap<>();
            for (String feature : testFeatures)
            {
                instance.put(feature, testData.get(feature).get(i));
            }
            predictions.add(PredictUtils.getPrediction(root, instance, testData));
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[PREDICTIONS]:");

        for(String p : predictions)
        {
            sb.append(" ").append(p);
        }
        System.out.println(sb);

        PredictUtils.getAccuracy(testData, predictions);
        PredictUtils.getConfusionMatrix(testData, predictions);
    }



    public Node id3Alg(LinkedHashMap<String, List<String>> data, LinkedHashMap<String, List<String>> dataParent, List<String> features, Integer limit)
    {
        String v; //najcesca oznaka primjera
        String x; //najdiskriminativnija

        if(data.isEmpty())
        {
            return new Node(argMaxV(dataParent)); //vracamo cvor najcesce oznake primjera u nadcvoru
        }

        if(limit != null) //provjera ako postoji ogranicenje dubine
        {
            if(limit == 0)
            {
                return new Node(argMaxV(data));
            }//vracamo najcescu vrijednost ciljne varijable za skup podataka
        }

        v = argMaxV(data); //najcesca oznaka primjera u cvoru

        LinkedList<String> keys = new LinkedList<>(data.keySet());
        int size = keys.size();
        String finalFeature = keys.get(size - 1);
        List<String> labelsList = data.get(finalFeature);

        boolean allSameLabel = labelsList.stream().allMatch(l -> l.equals(v));

        if(features.isEmpty() || allSameLabel)
        {
            return new Node(v);
        }

        x = argMaxX(data, features); //najdiskriminativnija
        Node newNode = new Node(x, "");//novi cvor!

        LinkedList<String> newFeatures = new LinkedList<>(features);
        newFeatures.remove(x);

        Map<String, LinkedHashMap<String, List<String>>> subsets = getSubsets(data, x);

        for (Map.Entry<String, LinkedHashMap<String, List<String>>> entry : subsets.entrySet())
        {
            String featureValue = entry.getKey();
            LinkedHashMap<String, List<String>> subset = entry.getValue();

            Integer newLimit = null;
            if (limit != null)
            {
                newLimit = limit-1;
            }
            Node child = id3Alg(subset, data, newFeatures, newLimit);
            newNode.getChildren().put(featureValue, child);
        }

        return newNode;

    }

    public static String argMaxV(LinkedHashMap<String, List<String>> data)
    {

        LinkedHashMap<String, Integer> vCount = getFeaturesCount(data);

        String argMaxV = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : vCount.entrySet())
        {
            if (entry.getValue() > maxCount)
            {
                argMaxV = entry.getKey();
                maxCount = entry.getValue();
            }
            else if(entry.getValue() == maxCount && argMaxV != null)
            {
                if(entry.getKey().compareTo(argMaxV) < 0)
                {//provjeri abecedno
                    argMaxV = entry.getKey();
                }
            }
        }

        return argMaxV;
    }

    public static LinkedHashMap<String, Integer> getFeaturesCount(LinkedHashMap<String, List<String>> data)
    {
        //vjerojatnosti vrijednosti ciljne varijable
        LinkedList<String> features = new LinkedList<>(data.keySet());
        String finalFeature = features.getLast();
        List<String> featureValues = data.get(finalFeature);

        LinkedHashMap<String, Integer> vCount = new LinkedHashMap<>();

        for (String value : featureValues)
        {
            vCount.put(value, vCount.getOrDefault(value, 0) + 1);
        }
        return vCount;
    }

    public String argMaxX(LinkedHashMap<String, List<String>> data, List<String> features)
    {
        String argMaxX = null;

        double maxIG = 0.0;

        for(String f : features)
        {
            double ig = ID3Utils.getIG(data, f);

            if(maxIG < ig)
            {
                maxIG = ig;
                argMaxX = f;
            }
            else if(maxIG == ig && argMaxX != null)
            {
                if(f.compareTo(argMaxX) < 0)//provjeri abecedno ako su im iste ig
                {
                    argMaxX = f;
                    maxIG = ig;
                }
            }
        }
        return argMaxX;
    }

    private Map<String, LinkedHashMap<String, List<String>>> getSubsets(LinkedHashMap<String, List<String>> data, String feature)
    {
        //podjela skupa data na temelju predane znacajke feature tj na njenim vrijednostima (for v in V(x))
        Map<String, LinkedHashMap<String, List<String>>> subsets = new HashMap<>();

        for (int i = 0; i < data.get(feature).size(); i++)
        {
            String featureValue = data.get(feature).get(i);
            subsets.putIfAbsent(featureValue, new LinkedHashMap<>());

            for (String key : data.keySet())
            {
                subsets.get(featureValue).putIfAbsent(key, new ArrayList<>());
                subsets.get(featureValue).get(key).add(data.get(key).get(i));
            }
        }

        return subsets;
    }
}
