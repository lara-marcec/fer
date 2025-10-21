package ui;

public class Node implements Comparable<Node>
{

    String state;
    Double cost;
    Node parent;

    public Node(String state, Double cost, Node parent)
    {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
    }

    public String getState()
    {
        return state;
    }

    public Double getCost()
    {
        return cost;
    }

    public Node getParent()
    {
        return parent;
    }

    @Override
    public int compareTo(Node node)
    {
        if(this.getCost() == node.getCost())
        {
            return this.getState().compareTo(node.getState());
        }
        else
        {
            return Double.compare(this.cost, node.cost);
        }

    }
}
