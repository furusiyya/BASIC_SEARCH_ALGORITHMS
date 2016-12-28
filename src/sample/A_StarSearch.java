package sample;


import java.util.*;

public class A_StarSearch  {
    private ArrayList<String> resultList;

    class Edges {
        Node edge;
        int cost;
        public Edges(Node edge, int cost) {
            this.edge = edge;
            this.cost = cost;
        }
    }
    private LinkedList<Edges> queue;
    Controller control;
    String identifier,goal;
    int time;
    private HashMap<String,Integer> heuristic;
    private HashMap<String,Node> tree;
    public A_StarSearch(Controller control,HashMap<String, Node> tree, HashMap<String,Integer> heuristic,String identifier,String goal,int time) {
        resultList = new ArrayList();
        this.control = control;
        this.time = time;
        this.heuristic = heuristic;
        queue = new LinkedList<>();
        this.tree = tree;
        this.goal = goal;
        this.identifier = identifier;
        traverse();
        if(fatal)control.graphPanel.showAlert("Infinite execution state detected!");
        control.paintPath(resultList,counter*time);
    }


    int counter = 0;
    int enqueue = 0,path_nodes = 0 ,cost = 0;
    int first_smallest = 1000000;
    int second_smallest = 1000000;
    boolean fatal = false;
    String previouse = null;
    Node winner = null;
    Node node = null;
    public void traverse() {
        if (tree.containsKey(identifier)) {
            queue.add(new Edges(tree.get(identifier), 0));
            enqueue++;
            path_nodes++;
            call_back();
            while (!queue.isEmpty()) {
                if(cost > 4* heuristic.get(identifier)){
                    fatal = true;
                    return;
                }
                sort();
                node = queue.poll().edge;
                if(goalTest(node))return;

                node.getchild().forEach((item, value) -> {
                    control.paint(item,node.getParent(),counter*time);
                    System.out.println("Heuristic: "+item);
                    if(first_smallest<(value+heuristic.get(item)+cost))first_smallest  = value;
                });

                if(queue.size()!=0) {
                    // Comparing with 2nd shortest one
                    Node competitor = queue.poll().edge;
                    if(goalTest(competitor))return;
                    control.paint(competitor.getParent(),previouse, counter * time);
                    competitor.getchild().forEach((item, value) -> {
                        control.paint(item, competitor.getParent(), counter * time);
                        if (second_smallest < (value + heuristic.get(item)+cost)) {
                            winner = tree.get(item);
                            second_smallest = value;
                        }
                    });
                    if (second_smallest < first_smallest) {
                        previouse = winner.getParent();
                    } else {
                        winner = node;
                    }
                } else{
                    winner = node;
                }

                if(previouse != null)cost += tree.get(previouse).getchild().get(winner.getParent());

                path_nodes++;
                call_back();
                resultList.add(winner.getParent());
                previouse = winner.getParent();
                winner.getchild().forEach((item, value) -> {
                        queue.add(new Edges(tree.get(item), (heuristic.get(item)+value)));
                        enqueue++;
                        call_back();
                });
                counter++;
            }
        }
        else{
            System.out.println("Invalid Entry");
        }
    }

    boolean goalTest(Node test){
        if (test.getParent() == goal){
            cost += tree.get(previouse).getchild().get(test.getParent());
            path_nodes++;
            resultList.add(test.getParent());
            call_back();
            return true;
        }
        return false;
    }


    void sort() {
        Edges obj;
        for (int i = 0; i < queue.size(); i++) {
            for (int j = i; j < queue.size(); j++) {
                if (queue.get(i).cost > queue.get(j).cost){
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

}