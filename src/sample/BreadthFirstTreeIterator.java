package sample;

import javafx.concurrent.Task;
import java.util.*;

public class BreadthFirstTreeIterator implements Iterator<Node> {
    private LinkedList<Node>resultList;
    private Queue<Node> queue;
    Controller control;
    String goal;
    int time = 0;
    public BreadthFirstTreeIterator(HashMap<String, Node> tree, String identifier, String goal,int time,Controller control) {
        this.control = control;
        this.goal = goal;
        resultList = new LinkedList<Node>();
        queue = new LinkedList<>();
        this.time = time;
        if (tree.containsKey(identifier)) {
            this.traverse(tree, identifier);
        }
    }

    /**
     * Method is the implementation of  breadth first search algorithm.
     * counter variable is used to maintain equal time delay between threads.
     */
    int counter = 0;
    int enqueue = 0,path_nodes = 0 ,cost = 0;
    public void traverse(HashMap<String, Node> tree, String identifier){
        queue.add(tree.get(identifier));
        enqueue++;
        path_nodes++;
        call_back();
        Node node = null;
        while(!queue.isEmpty()){
            node = queue.poll();
            call_back();
            String previouse = node.getParent();
            resultList.add(node);
            if (goal.equals(node.getParent())) {
                call_back();
                break;
            }
            node.getchild().forEach((item,cost)->{
                enqueue++;
                path_nodes++;
                this.cost+=cost;
                call_back();
                queue.add(tree.get(item));
                control.paint(item,previouse,counter*time);
                counter++;
            });
        }
        call_back();
    }

    /**
     * Method used to achieve the abstraction, Call update_statistics method.
     */
    void call_back(){
        control.update_states(enqueue,queue.size(),path_nodes,cost);
    }

    public void draw(LinkedList<Node> resultList) {
        for (int i = 0; i < resultList.size(); i++) {
            String temp = null;
            for (int j = 0; j < resultList.size(); j++) {
                temp = resultList.get(i).getParent() + resultList.get(j).getParent();
                if (control.graphPanel.edges.containsKey(temp))
                    control.graphPanel.paintEdges(temp, true);
            }
        }
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