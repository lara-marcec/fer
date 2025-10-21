package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataLoader
{
    public static LinkedHashMap<String, List<String>> loadMapFromFile(String filePathString) throws IOException
    {
        Path filePath = Paths.get(filePathString);

        BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()));

        String line = br.readLine();
        List<String> headerValues = List.of(line.split(","));
        line = br.readLine();

        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        for(String hV : headerValues)
        {
            map.put(hV, null);
        }

        while(line != null)
        {
            String[] tokenValues = line.split(",");
            int index = 0;
            for(String mapKey: map.keySet())
            {
                if(map.get(mapKey) == null)
                {
                    List<String> listValue = new ArrayList<>();
                    listValue.add(tokenValues[index]);
                    map.put(mapKey, listValue);
                }
                else
                {
                    List<String> listValue = map.get(mapKey);
                    listValue.add(tokenValues[index]);
                    map.put(mapKey, listValue);
                }
                index++;
            }

            line = br.readLine();

        }

        return map;
    }

}
