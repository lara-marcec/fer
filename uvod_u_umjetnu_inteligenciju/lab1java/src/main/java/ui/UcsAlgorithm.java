package ui;

import java.util.*;

public class UcsAlgorithm
{

    public static Double ucsAlg(String s0, HashMap<String, HashMap<String, Double>> succ, String[] goalStates, boolean checkingOptimistic)
    {
        //pretvaranje string polja u listu
        List<String> goal = new LinkedList<>(Arrays.asList(goalStates));

        //prioritetni red, prioritet cvorova se odreduje prema cijeni
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparing(Node::getCost));
        HashSet<String> closed = new HashSet<>();

        HashMap<String, Double> currentCost = new HashMap<>();

        //pocetno stanje s0, sa cijenom 0 i bez roditelja
        open.add(new Node(s0, 0.0, null));
        currentCost.put(s0, 0.0);

        while(!open.isEmpty())
        {
            Node nodeFromOpen = open.remove();

            closed.add(nodeFromOpen.getState());

            if(goal.contains(nodeFromOpen.getState()))
            {
                //pronadeno rjesenje

                List<String> path = BfsAlgorithm.calculatePath(nodeFromOpen);
                if(!checkingOptimistic)
                {
                    OutputData.printData("UCS", true, closed.size(), currentCost.get(nodeFromOpen.getState()), path);
                }

                return currentCost.get(nodeFromOpen.getState());
            }

            for(String state : succ.get(nodeFromOpen.getState()).keySet())
            {
                Double nodeFromOpenCost = currentCost.get(nodeFromOpen.getState());
                Double nodeFromOpenSuccesorCost = succ.get(nodeFromOpen.getState()).get(state);

                Double newCost =  nodeFromOpenCost + nodeFromOpenSuccesorCost;

                if(!closed.contains(state) && !currentCost.containsKey(state) || newCost < currentCost.get(state) )
                {
                    Node successor = new Node(state, newCost, nodeFromOpen);
                    open.add(successor);
                    currentCost.put(state, newCost);
                }
            }
        }
        return 0.0;
    }
}
