package ui;

import java.util.*;

public class AStarAlgorithm
{
    public static void aStarAlg(String s0, HashMap<String, HashMap<String, Double>> succ, String[] goalStates, String heuristicsFile)
    {
        PriorityQueue<HeuristicNode> open = new PriorityQueue<>();

        HashMap<String, HeuristicNode> closed = new HashMap<>();
        HashMap<String, HeuristicNode> openMap = new HashMap<>();

        //pretvaranje string polja u listu
        List<String> goal = new LinkedList<>(Arrays.asList(goalStates));

        open.add(new HeuristicNode(s0, 0.0, null));
        openMap.put(s0, open.peek());

        while(!open.isEmpty())
        {
            HeuristicNode nodeFromOpen = open.remove();

            closed.put(nodeFromOpen.getState(), nodeFromOpen);

            if( goal.contains(nodeFromOpen.getState()) )
            {
                List<String> path =  calculateHeuristicPath(nodeFromOpen);
                Double totalCost = calculateHeuristicCost(nodeFromOpen);

                OutputData.printData("A_STAR " + heuristicsFile, true, closed.size(), totalCost, path );
                return;
            }

            for(String state : succ.get(nodeFromOpen.getState()).keySet())
            {
                if(openMap.containsKey(state) || closed.containsKey(state))
                {
                    HeuristicNode nodeM = openMap.containsKey(state) ? openMap.get(state) : closed.get(state);
                    if(nodeM.getG() < nodeFromOpen.getG())
                    {
                        continue;
                    }
                    else
                    {
                        if( openMap.containsKey(state) )
                        {
                            open.remove(nodeM);
                            openMap.remove(nodeM.getState());
                        }
                        else
                        {
                            closed.remove(nodeM.getState());
                        }
                    }
                }

                Double cost = succ.get(nodeFromOpen.getState()).get(state);

                open.add(new HeuristicNode(state, cost, nodeFromOpen));
                openMap.put(state, new HeuristicNode(state, cost, nodeFromOpen));
            }
        }
    }

    public static Double calculateHeuristicCost(HeuristicNode node)
    {
        return node.getF();
    }
    public static List<String> calculateHeuristicPath(HeuristicNode node)
    {
        List<String> path = new ArrayList<>();

        Node tempNode = node;

        while(tempNode != null){
            path.add(tempNode.getState());
            tempNode = tempNode.getParent();
        }

        Collections.reverse(path);

        return path;
    }

}
