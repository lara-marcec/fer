package ui;
import java.util.*;

public class BfsAlgorithm
{
    public static void bfsAlg(String s0, HashMap<String, HashMap<String, Double>> succ, String[] goalStates)
    {
        //pretvaranje string polja u listu
        List<String> goal = new LinkedList<>(Arrays.asList(goalStates));

        //double ended queue
        Deque<Node> open = new LinkedList<>();
        HashSet<String> closed = new HashSet<>();

        //pocetno stanje s0, sa cijenom 0 i bez roditelja
        open.add(new Node(s0, 0.0, null));

        while(!open.isEmpty())
        {
            Node nodeFromOpen = open.removeFirst();
            closed.add(nodeFromOpen.getState());

            if(goal.contains(nodeFromOpen.getState()))
            {
                //pronadeno rjesenje
                List<String> path =  calculatePath(nodeFromOpen);
                Double totalCost = calculateCost(nodeFromOpen);
                OutputData.printData("BFS", true, closed.size(), totalCost, path);
                return;
            }

            List<Node> successorsForSorting = new ArrayList<>();

            for(String state : succ.get(nodeFromOpen.getState()).keySet())
            {
                //za svako stanje u listi nasljednika nodeFromOpen cvora roditelja

                if(!closed.contains(state))
                {
                    //ako stanje vec nije zatvoreno
                    String nodeState = nodeFromOpen.getState();
                    Double successorCost = succ.get(nodeState).get(state);
                    Node successor = new Node(state, successorCost , nodeFromOpen);

                    successorsForSorting.add(successor);
                }
            }
            //sortiramo djecu cvorove prema stanju i dodamo u open listu
            successorsForSorting.sort(Comparator.comparing(Node::getState));
            open.addAll(successorsForSorting);
        }
    }

    //metoda koristena za sva tri algoritma za dohavcanje puta
    public static List<String> calculatePath(Node solution)
    {
        List<String> path = new ArrayList<>();

        Node tempNode = solution;
        // krecemo od rjesenja i dohvacamo roditelja, pa roditelj roditelja itd
        while(tempNode != null){
            path.add(tempNode.getState());
            tempNode = tempNode.getParent();
        }
        //potrebno je obrnuti put
        Collections.reverse(path);

        return path;

    }

    //metoda koristena za bfs i ucs algoritam za izracun konacne cijene puta
    public static Double calculateCost (Node solution)
    {
        Double totalCost = 0.0;

        Node tempNode = solution;

        while(tempNode != null)
        {
            totalCost += tempNode.getCost();
            tempNode = tempNode.getParent();
        }

        return totalCost;
    }

}
