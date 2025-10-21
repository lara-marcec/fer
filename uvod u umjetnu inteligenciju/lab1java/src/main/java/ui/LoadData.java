package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static ui.Solution.goalStates;
import static ui.Solution.initialState;

public class LoadData
{
    public static HashMap<String, HashMap<String, Double>> loadStateSpaceData(String ssFile) throws IOException
    {
        Path ssFilePath = Paths.get(ssFile);
        BufferedReader br = new BufferedReader(new FileReader(ssFilePath.toFile()));

        String line = br.readLine();

        // mapa gdje su ključevi izvorna stanja, a vrijednosti su mape iducih elemenata i njihovih cijena - nasljednika
        HashMap<String, HashMap<String, Double>> transitionFuncs = new HashMap<>();

        while(line != null)
        {
            if(line.charAt(0) != '#')
            {
                if(initialState == null)
                {
                    // ako pocetno stanje nije definirano
                    initialState = line;
                }
                else
                {
                    // ako je pocetno stanje definirano, provjeri za ciljna stana
                    if(goalStates == null)
                    {
                        // ako ciljna stanja nisu jos definirana
                        goalStates = line.split(" ");
                    }
                    else
                    {
                        // ako su i pocetno stanje i ciljna stanja definirana
                        // preostale linije su zapisi funkcije prijelaza
                        String state = line.split(":")[0];
                        state = state.trim();
                        HashMap<String, Double> tempMap = new HashMap<>();
                        String[] parts = line.split(": ");
                        if (parts.length > 1)
                        {
                            String[] nextStatesArray = parts[1].split(" ");
                            for (String nextState : nextStatesArray)
                            {
                                tempMap.put(nextState.split(",")[0], Double.valueOf(nextState.split(",")[1]));
                            }
                        }
                        else
                        {
                            tempMap.put(line.replace(":",""), 0.0);
                        }
                        transitionFuncs.put(state, tempMap);
                    }
                }
            }
            line = br.readLine();
        }
        return transitionFuncs;
    }

    public static HashMap<String, Double> loadHeuristicData(String hFile) throws IOException
    {
        Path hFilePath = Paths.get(hFile);
        BufferedReader br = new BufferedReader(new FileReader(hFilePath.toFile()));

        String line = br.readLine();

        HashMap<String, Double> heuristicFunc = new HashMap<>();

        while(line != null)
        {
            heuristicFunc.put(line.split(": ")[0], Double.valueOf(line.split(": ")[1]));
            line = br.readLine();
        }
        return heuristicFunc;
    }
}
