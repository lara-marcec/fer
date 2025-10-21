package ui;

import java.util.Objects;

public class HeuristicNode extends Node
{
    private Double g;
    private Double f;
    private HeuristicNode parent;

    public HeuristicNode(String state, Double cost, HeuristicNode parent) {
        super(state, cost, parent);

        this.parent = parent;
        this.g = parent != null ? parent.getG() + cost : cost;
        this.f = this.g + Solution.hMap.get(state);
    }

    public String getState() {
        return this.state;
    }

    public Double getG() {
        return g;
    }

    public Double getF(){
        return f;
    }

    public HeuristicNode getParent()
    {
        return parent;
    }

    @Override
    public int compareTo(Node node)
    {
        HeuristicNode node2 = (HeuristicNode) node;
        if (Objects.equals(this.getF(), node2.getF()))
        {
            return this.getState().compareTo(node2.getState());
        }
        else
        {
            return Double.compare(this.getF(), node2.getF());
        }
    }
}
