package ui;

import java.util.HashMap;

public class HeuristicTests
{
    public static void checkConsistent(HashMap<String, Double> heuristicsMap, HashMap<String, HashMap<String, Double>> successorsMap, String heuristicsPath)
    {
        boolean consistent = true;

        StringBuilder sB = new StringBuilder();
        sB.append("# HEURISTIC_CONSISTENT ").append(heuristicsPath).append("\n");
        for( String parent : successorsMap.keySet() )
        {
            for( String successor : successorsMap.get(parent).keySet() )
            {
                Double parentH = heuristicsMap.get(parent);
                Double successorH = heuristicsMap.get(successor);

                if(! (parentH == 0.0 && successorH == 0.0) )
                {
                    sB.append("[CONDITION]: " );
                    if(parentH <= successorH + successorsMap.get(parent).get(successor))
                    {
                        sB.append("[OK] h(").append(parent).append(") <= h(").append(successor)
                                .append(") + c: ").append(parentH).append(" <= ").append(successorH)
                                .append(" + ").append(successorsMap.get(parent).get(successor));
                    }
                    else
                    {
                        consistent = false;
                        sB.append("[ERR] h(").append(parent).append(") <= h(").append(successor)
                                .append(") + c: ").append(parentH).append(" <= ").append(successorH)
                                .append(" + ").append(successorsMap.get(parent).get(successor));
                    }
                    sB.append("\n");
                }
            }
        }
        if( consistent )
        {
            sB.append("[CONCLUSION]: Heuristic is consistent.");
        }
        else
        {
            sB.append("[CONCLUSION]: Heuristic is not consistent.");
        }

        System.out.println(sB);
    }

    public static void checkOptimistic(HashMap<String, Double> heuristicsMap, HashMap<String, HashMap<String, Double>> succMap, String heuristicsPath, String[] goal)
    {
        boolean optimistic = true;

        StringBuilder sB = new StringBuilder();
        sB.append("# HEURISTIC_OPTIMISTIC ").append(heuristicsPath).append("\n");

        for ( String state : heuristicsMap.keySet() )
        {
            Double totalCost = UcsAlgorithm.ucsAlg(state, succMap, goal, true );

            sB.append("[CONDITION]: " );
            if(heuristicsMap.get(state) <= totalCost)
            {
                sB.append("[OK] h(").append(state).append(") <= h*: ").append(heuristicsMap.get(state)).append(" <= ").append(totalCost);
            }
            else
            {
                sB.append("[ERR] h(").append(state).append(") <= h*: ").append(heuristicsMap.get(state)).append(" <= ").append(totalCost);
                optimistic = false;
            }
            sB.append("\n");
        }
        if(optimistic)
        {
            sB.append("[CONCLUSION]: Heuristic is optimistic.");
        }
        else
        {
            sB.append("[CONCLUSION]: Heuristic is not optimistic.");
        }

        System.out.println(sB);
    }
}
