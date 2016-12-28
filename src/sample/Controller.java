package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    Pane moniteredPane;
    @FXML
    Button startBtn;
    @FXML
    Button goalBtn;
    @FXML
    public Label type, enqueue, queue_size, path_elements, path_cost,heuristic_from;
    @FXML
    TableView table;

    @FXML  TableColumn<Row,String> heu_node;
    @FXML  TableColumn<Row,String> heu_cost;


    /**
     * search_type -> Type 1 : BFS, type 2: DFS, type 3: Hill Climbing, type 4: A Star.
     */
    public int search_type = 0;
    public Tree tree;

    /**
     * Nodes contains all the nodes of the graph
     */
    public HashMap<String, Node> nodes;

    /**
     * Shapes are the graphical object representing nodes in a graph. Shapes and nodes both maps
     * have same keys with different values.
     * In Edges the key is a concatenated string of names of nodes in between the edges is.
     */
    public HashMap<String, Circle> shapes;
    public HashMap<String, Group> edges;

    /**
     * Heuristics contains straight line distance to facilitate heuristic algorithms like A Star
     */
    public HashMap<String,Integer> heuristic;

    /**
     * Graph panel is a board to draw problem space.
     */
    public Graph_Panel graphPanel;
    static int sleep = 500;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shapes = new HashMap<>();
        edges = new HashMap<>();
        tree = new Tree();
        nodes = new HashMap<>();
        heuristic = new HashMap<>();
        graphPanel = new Graph_Panel(moniteredPane, shapes, edges, tree);
        listenForEvents(this.moniteredPane);

    }

    /**
     * This method listen for mouse events occurred over graph pane
     * @param obj Type Pane
     * @return obj Type Pane
     */
    Pane listenForEvents(Pane obj){
        EventHandler handler = (Event e) ->{
            MouseEvent event = (MouseEvent) e;
            //check if user starts drag from a node
            shapes.forEach((key,value)->{
                double x= value.getCenterX();
                double y= value.getCenterY();
                if((event.getX() > (x-30) && event.getX() < (x+30)) && (event.getY() > (y-30) && event.getY() < (y+30))){
                    graphPanel.drawEdge(event.getX(),event.getY(),key);
                    return;
                }
            });
        };
        /**
         Drag will draw a node on graph pane
         */
        obj.setOnDragDetected(handler);

        /**
         Double Click will draw a node on graph pane
         */
        obj.setOnMouseClicked(e -> {
            if(e.getClickCount()==2){
                    graphPanel.drawNode(e.getX(),e.getY());
                this.nodes = tree.getNodes();
            } else if (e.getClickCount() == 1) {
                if (nodes.size() > 2 && flag != 0) {
                    shapes.forEach((key, value) -> {
                        double x = value.getCenterX();
                        double y = value.getCenterY();
                        if ((e.getX() > (x - 30) && e.getX() < (x + 30)) && (e.getY() > (y - 30) && e.getY() < (y + 30))) {
                            unset_seleted_nodes();
                            if (flag == 1) start = key;
                            if (flag == 2) goal = key;
                            if(flag == 3) {
                                heuristic_to = key;
                                add_nodes_to_table(false);
                                flag = 0;
                                return;
                            }
                            shapes.get(key).setRadius(8.0f);
                            flag = 0;
                            shapes.get(key).setFill(Color.DARKORANGE);
                        }
                        return;
                    });
                } else {
                    flag = 0;
                    obj.removeEventHandler(MouseEvent.MOUSE_RELEASED, handler);
                }
            }
        });
        return obj;
    }


    /**
     * Method will feed a flag to mouse event handler either the selected
     * node is a starting point or goal
     */
    int flag = 0;
    String start = null, goal = null;
    public void setStart() {
        this.nodes = tree.getNodes();
        if (nodes.size() > 0 && edges.size() > 3) flag = 1;
        else graphPanel.showAlert("Incomplete graph!");
    }
    public void setGoal() {
        this.nodes = tree.getNodes();
        if (nodes.size() > 0 && edges.size() > 3) flag = 2;
        else graphPanel.showAlert("Incomplete graph!");
    }


    /**
     * Method will take straight line distances from a specific point
     */
    String heuristic_to = null;
    public void add_Heuristic() {
        this.nodes = tree.getNodes();
        if (nodes.size() > 0 && edges.size() > 3) flag = 3;
        else graphPanel.showAlert("Incomplete graph!");
    }

    /**
     * Method will unset the values of all fields
     */
    public void reset_Pane() {
        start = null;
        goal = null;
        flag = 0;
        tree.nodes = new HashMap<String, Node>();
        shapes = new HashMap<>();
        graphPanel.shapes = new HashMap<>();
        edges = new HashMap<>();
        heuristic = new HashMap<>();
        heuristic_to = "";
        heuristic_from.setText("Straight-Line distance to: ");
        moniteredPane.getChildren().clear();
        search_type = 0;
        data = FXCollections.observableArrayList(new Row("",0+""));
        type.setText("Type: ");
        enqueue.setText("Enqueuing: ");
        queue_size.setText("Queue size:");
        path_elements.setText("Path nodes: ");
        path_cost.setText("Path length:");
    }

    /**
     * Method will unset the previously selected start and goal
     */
    void unset_seleted_nodes() {
        if (start != null && flag == 1) {
            shapes.get(start).setRadius(5.0f);
            shapes.get(start).setFill(Color.web("#202020"));
        }
        if (goal != null && flag == 2) {
            shapes.get(goal).setRadius(5.0f);
            shapes.get(goal).setFill(Color.web("#202020"));
        }
    }

    /**
     * Method will initialize the selected search algorithm on problem space.
     */
    public void start_Search() {
            if (start != null && goal != null) {
                switch (search_type) {
                    case 1:
                        new BreadthFirstTreeIterator(tree.getNodes(), start, goal,time ,this);
                        break;
                    case 2:
                        new DepthFirstTreeIterator(tree.getNodes(), start, goal, time,this);
                        break;
                    case 3:
                        new IterativeDeppeningSearch(tree.getNodes(), start, goal, time,this);
                        break;
                    case 4:
                        new HillClimbingSearch(tree.getNodes(), start, goal, time,this);
                        break;
                    case 5:
                        if(heuristic_to == ""){
                            graphPanel.showAlert("Heuristic distance for Goal is not set!");
                            return;
                        }else{
                            heuristic.forEach((key,value)->{
                                if(key!=heuristic_to && value == 0) {
                                    graphPanel.showAlert("Update zero heuristic distance of nodes!");
                                    return;
                                }
                            });
                            new A_StarSearch(this,tree.getNodes(),heuristic,start, goal,time);
                        }
                        break;
                }
        } else graphPanel.showAlert("Set start and goal first!");
    }

    /**
     * Methods will define sleep time in mili-seconds of running threads
     */
    int time = 150;
    public void time_4x(){time = 150;}
    public void time_3x(){time = 250;}
    public void time_2x(){time = 500;}
    public void time_1x(){time = 800;}

    /**
     * Method will clear previously searched paths from graph
     */
    public void clear_graph(){
        edges.forEach((index,edge)->{
            graphPanel.paintEdges(index,false);
        });
        type.setText("Type: ");
        enqueue.setText("Enqueuing: ");
        queue_size.setText("Queue size:");
        path_elements.setText("Path nodes: ");
        path_cost.setText("Path length:");
    }

    /**
     * Method will update the label showing inside statistics of algorithm
     * @param enqueuings
     * @param queueSize
     * @param path_nodes
     * @param cost
     */
    public void update_states(int enqueuings, int queueSize, int path_nodes,int cost){
            enqueue.setText("Enqueuing: "+enqueuings);
            queue_size.setText("Queue size:"+queueSize);
            path_elements.setText("Path nodes: "+path_nodes);
            path_cost.setText("Path length:"+cost);
    }

    /**
     * Method will generate a thread to paint edges with a custom sleeping time
     * @param item
     * @param previouse
     * @param sleep
     */
    public void paint(String item,String previouse,int sleep) {
        Task<String> sleeper = new Task<String>() {
            @Override
            protected String call() throws Exception {
                try {
                    Thread.sleep(sleep);
                    //updateMessage(previouse + item);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> {
            graphPanel.paintEdges(previouse+item, true);
        });
        new Thread(sleeper).start();
    }




    public void paintPath(ArrayList<String> resutlList, int sleep) {
        Task<String> sleeper = new Task<String>() {
            @Override
            protected String call() throws Exception {
                try {
                    Thread.sleep(sleep);
                    //updateMessage(previouse + item);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> {
            graphPanel.paintPath(resutlList);
        });
        new Thread(sleeper).start();
    }


    /**
     * Method will load a sample tree for BFS, DFS, Hill_Climbing
     */
    public void sample_tree(){
        reset_Pane();
        new sampleProblemSpaces(this,1);
    }

    /**
     * Method will load a sample graph for A Star
     */
    public void sample_a_start_graph(){
        reset_Pane();
        new sampleProblemSpaces(this,3);
    }

    ObservableList<Row> data = null;
    void add_nodes_to_table(boolean sample){
        if(!sample)
        nodes.forEach((id,obj)->{
            heuristic.put(id,0);
        });

        table.setEditable(true);


        heu_node.setCellValueFactory(
                new PropertyValueFactory<>("key"));

        heu_node.setCellFactory(TextFieldTableCell.<Row>forTableColumn());
        heu_node.setOnEditCommit(
                (CellEditEvent<Row, String> t) -> {
                    ((Row) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setKey(t.getNewValue());
                });

        heu_cost.setCellValueFactory(
                new PropertyValueFactory<>("cost"));
        heu_cost.setCellFactory(TextFieldTableCell.<Row>forTableColumn());
        heu_cost.setOnEditCommit(
                (CellEditEvent<Row, String> t) -> {
                    ((Row) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCost(t.getNewValue());
                });
        System.out.println("Setting data");
        data  = FXCollections.observableArrayList(new Row(heuristic_to,0+""));
        table.setItems(data);
        update_heuristic();


    }

    void update_heuristic(){
        heuristic.forEach((id,cost)->{
            if(!id.equals(heuristic_to))
                data.add(new Row(id,cost+""));

        });
    }

     public final class Row {
        private  SimpleStringProperty key;
        private  SimpleStringProperty cost;
        private Row(String key, String cost) {
            this.key = new SimpleStringProperty(key);
            this.cost = new SimpleStringProperty(cost);
        }


        public void setKey(String key) {
            this.key.set(key);
        }
        public String getKey(){
            return key.get();
        }

        public String getCost(){
            return cost.get();
        }
        public void setCost(String cost) {
            try{
                System.out.println("Changing Cost: "+key.get()+" :"+heuristic.get(key.get()));
                if(key.equals(heuristic_to))return;
                this.cost.set(cost);
                heuristic.put(key.get(), Integer.parseInt(cost));
                System.out.println("Changed Cost: "+key.get()+" :"+heuristic.get(key.get()));
                //update_heuristic();
            }catch (Exception e){
                e.printStackTrace();
                graphPanel.showAlert("Invalid input, only digits are allowed!");
                return;
            }
        }

    }



    public void bfs() {
        search_type = 1;
        type.setText("Type: Breadth First");
    }

    public void dfs() {
        search_type = 2;
        type.setText("Type: Depth First");
    }

    public void iterativeDepening() {
        search_type = 3;
        type.setText("Type: Iterative Deepening");
    }

    public void hillClimb() {
        search_type = 4;
        type.setText("Type: Hill Climbing");
    }

    public void aStar() {
        search_type = 5;
        type.setText("Type: A Star");
    }
}