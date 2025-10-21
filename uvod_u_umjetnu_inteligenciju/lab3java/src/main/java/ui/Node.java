package ui;

import java.util.LinkedHashMap;

public class Node
{
    private String feature;
    private LinkedHashMap<String, Node> children; //value, node feature
    private String label;


    public Node(String label)//when node is leaf
    {
        this.label = label;
        this.children = new LinkedHashMap<>();
    }

    public Node(String feature, String n) //when node is node, n je nepotreban
    {
        this.feature = feature;
        this.children = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, Node> getChildren()
    {
        return children;
    }

    public String getFeature()
    {
        return feature;
    }

    public String getLabel()
    {
        return label;
    }
}
