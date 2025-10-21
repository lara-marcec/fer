package ui;

import java.util.*;

public class ID3Utils
{
    //racunanje informacijske dobiti
    public static double getIG(LinkedHashMap<String, List<String>> data, String f)
    {
        double ig = getEntropy(data);

        LinkedList<String> features = new LinkedList<>(data.keySet());
        String finalFeature = features.getLast();
        List<String> finalFeatureValues = data.get(finalFeature);

        List<String> featureValues = data.get(f);
        Map<String, Integer> vCount = new LinkedHashMap<>();

        for (String value : featureValues)
        {
            vCount.put(value, vCount.getOrDefault(value, 0) + 1);
        }

        double n = featureValues.size() * 1.0;

        for (String v : vCount.keySet())
        {
            List<String> filteredFeatureValues = new ArrayList<>();
            List<String> filteredFinalFeatureValues = new ArrayList<>();

            for (int i = 0; i < featureValues.size(); i++)
            {
                if (featureValues.get(i).equals(v))
                {
                    filteredFeatureValues.add(featureValues.get(i));
                    filteredFinalFeatureValues.add(finalFeatureValues.get(i));
                }
            }

            LinkedHashMap<String, List<String>> filteredData = new LinkedHashMap<>();
            filteredData.put(f, filteredFeatureValues);
            filteredData.put(finalFeature, filteredFinalFeatureValues);

            double result = (vCount.get(v) / n) * getEntropy(filteredData);
            ig -= result;
        }

        //System.out.format("ig(" + f + "): %.4f", ig);
        //System.out.println();
        return ig;
    }

    //racunanje entropije
    public static double getEntropy(LinkedHashMap<String, List<String>> data)
    {
        double e = 0.0;

        LinkedList<String> features = new LinkedList<>(data.keySet());
        String finalFeature = features.getLast();

        LinkedHashMap<String, Integer> vCount = ID3.getFeaturesCount(data);

        double n = data.get(finalFeature).size() * 1.0;

        for(String k : vCount.keySet())
        {
            double p = (vCount.get(k) / n);
            double result = p * (Math.log(p) / Math.log(2));
            e+=result;
        }

        e*=(-1.0);
        return e;
    }

    public static void printBranches(Node node, String prefix, int level)
    {
        if (node.getChildren().isEmpty())
        {//kad dodemo do kraja
            System.out.println(prefix + " " + node.getLabel());
        }
        else
        {
            for (Map.Entry<String, Node> entry : node.getChildren().entrySet())
            {
                String featureValue = entry.getKey();
                Node child = entry.getValue();

                String newPrefix = prefix.isEmpty() ? level + ":" + node.getFeature() + "=" + featureValue //ako je pocetak ispisa
                        : prefix + " " + level + ":" + node.getFeature() + "=" + featureValue; //inace

                printBranches(child, newPrefix, level + 1); //rekurzivno, ovaj prefix i new prefix su ko neki string builder
            }
        }
    }

    public static String getMostCommon(LinkedHashMap<String, List<String>> data)
    {
        LinkedHashMap<String, Integer> vCount = ID3.getFeaturesCount(data);

        int maxi = 0;
        String mc = null;

        for(String v : vCount.keySet())
        {
            if(vCount.get(v) > maxi)
            {
                maxi = vCount.get(v);
                mc = v;
            }
            if(vCount.get(v) == maxi && mc != null)
            {
                if(v.compareTo(mc) < 0)
                {
                    maxi = vCount.get(v);
                    mc = v;
                }

            }
        }
        return mc;
    }
}
