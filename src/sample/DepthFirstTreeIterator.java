package sample;

import java.util.*;

public class DepthFirstTreeIterator implements Iterator<Node> {
    private LinkedList<Node> resultList;
    Controller control;
    int time;
    public DepthFirstTreeIterator(HashMap<String, Node> tree, String identifier, String goal,int time,Controller control) {
        this.control = control;
        this.time = time;
        resultList = new LinkedList<Node>();
        if (tree.containsKey(identifier)) {
            this.traverse(tree, identifier);
        if(fatal)control.graphPanel.showAlert("Infinite execution state detected!");
        }
    }

    /**
     * Method is the implementation of depth first search algorithm.
     * counter variable is used to maintain equal time delay between threads.
     */
    int counter = 0;
    int enqueue = 0,path_nodes = 0 ,cost = 0;
    boolean fatal = false;
    void traverse(HashMap<String,Node> tree,String identifier){
        resultList.add(tree.get(identifier));
        enqueue++;
        path_nodes++;
        counter++;
        if((enqueue - tree.size())>500){
            fatal = true;
            return;
        }
        tree.get(identifier).getchild().forEach((child,cost)->{
            this.cost+=cost;
            control.paint(child,identifier,counter*time);
            call_back();
            traverse(tree,child);
        });
    }


    /**
     * Method used to achieve the abstraction, Call update_statistics method.
     */
    void call_back(){
        control.update_states(enqueue,0,path_nodes,cost);
    }

    @Override
    public boolean hasNext() {
        return !resultList.isEmpty();
    }

    @Override
    public Node next() {
        return resultList.poll();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}