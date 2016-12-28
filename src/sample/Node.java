package sample;

import java.util.HashMap;

public class Node {
    String parent;

    // Map containing children and their cost
    HashMap<String,Integer> child;

    public Node(String parent){
        this.parent = parent;
        this.child = new HashMap<>();
    }

    public void addChild(String child,int cost) {this.child.put(child,cost);}

    public HashMap<String,Integer> getchild(){return child;}

    public String getParent(){return parent;}

    public int getCost(String childName){return child.get(childName);}
}