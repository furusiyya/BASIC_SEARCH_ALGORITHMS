package sample;

import java.util.*;

public class HillClimbingSearch implements Iterator<Node> {

    private LinkedList<Node> resultList;

    class Edges {
        Node edge;
        int cost;
        public Edges(Node edge, int cost) {
            this.edge = edge;
            this.cost = cost;
        }
    }
    private LinkedList<Edges> queue;

    String goal,identifier;
    Controller control;
    int time;
    public HillClimbingSearch(HashMap<String, Node> tree, String identifier,String goal,int time,Controller control) {
        resultList = new LinkedList<Node>();
        queue = new LinkedList<>();
        this.time = time;
        this.goal = goal;
        this.control = control;
        this.identifier = identifier;
        if (tree.containsKey(identifier)) {
            this.traverse(tree, identifier);
            if(fatal)control.graphPanel.showAlert("Infinite execution state detected!");
            return;
        }
    }
    /**
     * Method is the implementation of  hill_climb search algorithm.
     * counter variable is used to maintain equal time delay between threads.
     */
    int counter = 0;
    int enqueue = 0,path_nodes = 0 ,cost = 0;
    boolean fatal = false;
    void traverse(HashMap<String, Node> tree, String identifier) {
        queue.add(new Edges(tree.get(identifier), 0));
        enqueue++;
        path_nodes++;
        call_back();
        Node node = null;
        while (!queue.isEmpty()) {
            sort();
            Edges obj = queue.poll();
            node = obj.edge;
            queue =  new LinkedList<>();
            path_nodes++;
            cost+=obj.cost;
            call_back();
            resultList.add(node);
            String previouse = node.getParent();
            if (node.getchild().containsKey(goal)) {
                cost+=node.getCost(goal);
                call_back();
                resultList.add(tree.get(goal));
                System.out.println("Goal Found");
                control.paint(goal,previouse,counter*time);
                return;
            }

            node.getchild().forEach((item, value) -> {
                queue.add(new Edges(tree.get(item), value));
                enqueue++;
                call_back();
                control.paint(item,previouse,counter*time);
                counter++;
            });

            if(enqueue+tree.size() > 500){
                fatal = true;
                break;
            }
        }
        call_back();
    }

    void sort() {
        Edges obj;
        for (int i = 0; i < queue.size(); i++) {
            for (int j = i; j < queue.size(); j++) {
                if (queue.get(i).cost > queue.get(j).cost) {
                    obj = queue.get(i);
                    queue.add(i, queue.get(j));
                    queue.add(j, obj);
                    queue.remove(j);
                    queue.remove(j + 1);
                }
            }
        }
    }

    /**
     * Method used to achieve the abstraction, Call update_statistics method.
     */
    void call_back(){
        control.update_states(enqueue,queue.size(),path_nodes,cost);
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