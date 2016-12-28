package sample;

import java.util.HashMap;
import java.util.Iterator;

public class Tree {
    String root = null;
    HashMap<String,Node> nodes;

    public Tree(){
        nodes = new HashMap<String, Node>();
    }

    public HashMap<String,Node> getNodes(){return this.nodes;}

    public String getRoot(){return  root;}

    public HashMap<String,Node> addNode(String child){
        root = child;
        return addNode(child,0,null);
    }

    HashMap<String,Node> addNode(String child,int cost,String parent){
        if(!nodes.containsKey(child)){
            Node obj = new Node(child);
            nodes.put(child,obj);
        }
        if (parent != null && nodes.containsKey(parent)){
            nodes.get(parent).addChild(child,cost);
        }else{
            System.out.println("No parent exist : "+parent+" Child: "+child);
        }
        return nodes;
    }
    void addDirectedEdge(String parent,String child,int cost){
        nodes.get(parent).addChild(child,cost);
    }
    void addUndirectedEdge(String parent,String child,int cost){
        nodes.get(parent).addChild(child,cost);
        nodes.get(child).addChild(parent,cost);
    }

}


