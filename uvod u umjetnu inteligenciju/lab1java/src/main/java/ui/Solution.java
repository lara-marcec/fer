package ui;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

import static ui.LoadData.loadHeuristicData;
import static ui.LoadData.loadStateSpaceData;
import static ui.BfsAlgorithm.bfsAlg;
import static ui.UcsAlgorithm.ucsAlg;
import static ui.AStarAlgorithm.aStarAlg;

//za pisanje laboratorijske vjezbe sam koristila materijale s predmeta

public class Solution {
	private static String alg;
	private static String stateSpaceFile;
	private static String heuristicsFile;
	private static boolean checkOptimistic = false;
	private static boolean checkConsistent = false;

	public static String initialState;
	public static String[] goalStates;

	private static HashMap<String, HashMap<String, Double>> succMap;
	public static HashMap<String, Double> hMap;

	public static void main(String ... args) throws IOException
	{
		int i = 0;

		for (String arg : args)
		{
			if (Objects.equals(arg, "--alg"))
			{
				alg = args[i + 1];
			}
			else if (Objects.equals(arg, "--ss"))
			{
				stateSpaceFile = args[i + 1];
			}
			else if (Objects.equals(arg, "--check-optimistic"))
			{
				checkOptimistic = true;
			}
			else if (Objects.equals(arg, "--check-consistent"))
			{
				checkConsistent = true;
			}
			else if (Objects.equals(arg, "--h"))
			{
				heuristicsFile = args[i + 1];
			}

			i++;
		}

		//pozivanje funkcija za parsiranje/ucitavanja podataka iz datoteka
		if (stateSpaceFile != null)
		{
			succMap = loadStateSpaceData(stateSpaceFile);
		}
		if(heuristicsFile != null)
		{
			hMap = loadHeuristicData(heuristicsFile);
		}

		//pozivanje funkcija za algoritme
		if(Objects.equals(alg,"bfs"))
		{
			bfsAlg(initialState, succMap, goalStates);
		}
		else if(Objects.equals(alg,"ucs"))
		{
			ucsAlg(initialState, succMap, goalStates, false);
		}
		else if(Objects.equals(alg, "astar"))
		{
			aStarAlg(initialState, succMap, goalStates, heuristicsFile);
		}

		//pozivanje funkcija za ispitivanje heuristika
		if(checkConsistent)
		{
			HeuristicTests.checkConsistent(hMap, succMap, heuristicsFile);
		}
		else if(checkOptimistic)
		{
			HeuristicTests.checkOptimistic(hMap, succMap, heuristicsFile, goalStates);
		}
	}
}
