package sample;

import java.util.*;
import java.util.concurrent.Executor;

class IterativeDeppeningSearch implements Iterator<Node> {
    private LinkedList<Node> resultList;
    int maxLevel = 3;
    int time;
    Controller control;
    public IterativeDeppeningSearch(HashMap<String, Node> tree, String identifier, String goal,int time,Controller control) {
        resultList = new LinkedList<Node>();
        this.time = time;
        this.control = control;
        if (tree.containsKey(identifier)) {
                try{
                    maxLevel = Integer.parseInt(control.graphPanel.takeInput("Enter Depth Level for Iteration:"));
                }catch (Exception e){
                    control.graphPanel.showAlert("Invalid input, level should be in digits!");
                    control.search_type = 0;
                    control.type.setText("Type: ");
                    return;
                }
                this.traverse(tree, identifier,0);
        }
    }

    /**
     * Method is the implementation of Iterative Deppening search algorithm.
     * counter variable is used to maintain equal time delay between threads.
     */
    int counter = 0;
    int enqueue = 0,path_nodes = 0 ,cost = 0;
    boolean fatal = false;
    void traverse(HashMap<String,Node> tree,String identifier,int level){
        //Iteration level.. You can adjust according to your need.
        if (level == maxLevel)return;
        resultList.add(tree.get(identifier));
        enqueue++;
        path_nodes++;
        counter++;
        if((enqueue - tree.size())>500){
            fatal = true;
            return;
        }

        tree.get(identifier).getchild().forEach((key,value)-> {
            this.cost+=cost;
            control.paint(key,identifier,counter*time);
            call_back();
            traverse(tree, key, level + 1);
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