package ui;

import java.util.List;

public class OutputData
{
    public static void printData(String alg, boolean foundSolution, int statesVisited, Double totalCost, List<String> path)
    {
        System.out.println("# " + alg);

        System.out.println("[FOUND_SOLUTION]: " + (foundSolution ? "yes" : "no"));

        if(foundSolution)
        {
            System.out.println("[STATES_VISITED]: " + statesVisited);

            System.out.println("[PATH_LENGTH]: " + path.size());

            System.out.println("[TOTAL_COST]: " + totalCost);

            StringBuilder pathSb = new StringBuilder();
            pathSb.append("[PATH]: ");

            for(String state : path)
            {
                pathSb.append(state).append(" => ");
            }

            pathSb.delete(pathSb.length()-4,pathSb.length());

            System.out.println(pathSb);
        }
    }
}
