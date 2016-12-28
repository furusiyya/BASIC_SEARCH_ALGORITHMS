package sample;
import javafx.event.*;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

public class Graph_Panel {

    private Pane graphPane;     // Object of Pane used for drawing tree or Graph
    public HashMap<String, Circle> shapes;     // Contains circle objects associated with nodes ID's
    public HashMap<String, Group> edges;        // Contains line(edges) objects associated with two nodes
    private Stage dialog = null;
    private Button okBtn;
    Tree tree;

    public Graph_Panel(Pane obj, HashMap<String, Circle> shapes, HashMap<String, Group> edges, Tree tree) {
        this.graphPane = obj;
        this.shapes = shapes;
        this.edges = edges;
        this.tree = tree;
        this.dialog = returnPopUp();
    }

    /**
     * This method will take Node ID as input to create an circle(Node) on the screen.
     * @param x
     * @param y
     */
    void drawNode(double x, double y){
        System.out.println("Drawing node at "+x+" - "+y);
        String id = takeInput("Please enter Node ID: ");
        if(id.trim().length()>0){
            if(shapes.containsKey(id)){
                showAlert("Node already exists!");return;
            }
            // Below Code will add Node with label representing ID 
            Circle circle = new Circle();
            circle.setCenterX(x);
            circle.setCenterY(y);
            circle.setRadius(5.0f);
            circle.setFill(Color.web("#202020"));

            // Label on node representing ID
            Label lbl = new Label(id);
            lbl.setStyle("-fx-forground-color:#12090d;");
            lbl.setTranslateX(x - 7);
            lbl.setTranslateY(y - 20);

            // Add node and label to graph panel
            graphPane.getChildren().add(circle);
            graphPane.getChildren().addAll(lbl);
            shapes.put(id, circle);
            tree.addNode(id);
            return;
      }
    }

    /**
     * Method to load sample problem spaces on graph, this will add nodes on the graph.
     * @param x
     * @param y
     * @param id
     */
    public void drawNode(double x, double y,String id){
            // Below Code will add Node with label representing ID
            Circle circle = new Circle();
            circle.setCenterX(x);
            circle.setCenterY(y);
            circle.setRadius(5.0f);
            circle.setFill(Color.web("#202020"));

            // Label on node representing ID
            Label lbl = new Label(id);
            lbl.setStyle("-fx-forground-color:#12090d;");
            lbl.setTranslateX(x - 7);
            lbl.setTranslateY(y - 20);

            // Add node and label to graph panel
            graphPane.getChildren().add(circle);
            graphPane.getChildren().addAll(lbl);
            shapes.put(id, circle);
            tree.addNode(id);
            return;
    }
    private EventHandler handler;   // Used to handle the action on mouse click release (Drag Released)


    /**
     * This method draw an edge between two nodes on the base of mouse drag event.
     * @param x shows starting position of drag event on the X coordinate
     * @param y shows starting position of drag event on the Y coordinate
     * @param node is the ID of the node from where drag was started
     */
    void drawEdge(double x,double y,String node){
        handler = (Event e)->{
            MouseEvent event = (MouseEvent)e;
            shapes.forEach((key,value)->{
                double x1= value.getCenterX();
                double y1= value.getCenterY();
                if((event.getX() > (x1-30) && event.getX() < (x1+30))
                        && (event.getY() > (y1-30) && event.getY() < (y1+30))){
                    dialog.show();
                    cost.requestFocus();
                    cost.selectAll();
                    okBtn.setOnAction(e1 -> {
                        dialog.hide();
                        if (cost.getText().trim().length() < 1) {
                            showAlert("Cost should be in digits!");
                            graphPane.removeEventHandler(MouseEvent.MOUSE_RELEASED, handler);
                            return;
                        }
                        int c;
                        try {
                            c = Integer.parseInt(cost.getText());
                            Text text = new Text(c + "");
                            text.setFill(Color.web("000000"));
                            Line line = new Line(x, y, event.getX(), event.getY());
                            line.setStrokeWidth(1);
                            line.setStroke(Color.web("000000"));

                            Group gr = new Group();
                            text.setX((line.getStartX() + line.getEndX()) / 2);
                            text.setY(((line.getStartY() + line.getEndY()) / 2) - 4);
                            gr.getChildren().addAll(line, text);
                            graphPane.getChildren().addAll(gr);
                            edges.put((node + key), gr);
                            if (directed.isSelected()) tree.addDirectedEdge(node, key, c);
                            else{
                                tree.addUndirectedEdge(node, key, c);
                                edges.put((key+ node), gr);
                            }
                            graphPane.removeEventHandler(MouseEvent.MOUSE_RELEASED, handler);
                            return;
                        } catch (Exception e3) {
                            showAlert("Cost in digits is easy to process!");
                            graphPane.removeEventHandler(MouseEvent.MOUSE_RELEASED, handler);
                            return;
                        }
                    });
                }
            });

        };
        graphPane.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);
    }


    /**
     * Method will add edges to the to sample nodes to produce a complete problem space
     * @param start
     * @param end
     * @param cost
     * @param directed
     */
    public void drawEdge(String start,String end,int cost,boolean directed) {
        Text text = new Text(cost + "");
        text.setFill(Color.web("000000"));
        Line line = new Line(shapes.get(start).getCenterX(), shapes.get(start).getCenterY(),
                shapes.get(end).getCenterX(), shapes.get(end).getCenterY());
        line.setStrokeWidth(1);
        line.setStroke(Color.web("000000"));

        Group gr = new Group();
        text.setX((line.getStartX() + line.getEndX()) / 2);
        text.setY(((line.getStartY() + line.getEndY()) / 2) - 4);
        gr.getChildren().addAll(line, text);
        graphPane.getChildren().addAll(gr);
        edges.put((start + end), gr);
        if (directed) tree.addDirectedEdge(start, end, cost);
        else{
            tree.addUndirectedEdge(start, end, cost);
            edges.put((end + start), gr);
        }
    }





    /**
     * This method asks takes user input like Node ID and cost.
     * @param message To help the user to understand the required input.
     * @return
     */
    public String takeInput(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Information Required");
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        return  null;
    }

    /**
     * This method prompts errors messages or information dialogues
     * @param message To help the user to understand the error.
     */
    void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method highlights the path(Edges) from Start to Goal.
     * @param nodes is a concatenated string of two node ID's used to obtain the line object between two nodes.
     * @param paint is flag represents whether the line should be painted to default(black).
     */
    void paintEdges(String nodes,boolean paint){
        javafx.scene.Node line = null;
        try{
            line = edges.get(nodes).getChildren().get(0);
        }catch(Exception e){
            System.out.println("xxxxxxxxxxxxxxx No edge for "+nodes);
            return;
        }
        if (line instanceof Line) {
            if (paint) {
                ((Line) line).setStrokeWidth(4);
            } else{
                ((Line) line).setStroke(Color.web("000000"));
                ((Line) line).setStrokeWidth(1);
            }
        }

    }

    public void paintPath(ArrayList<String> resultList){
        try{
            for(int i = 0; i < resultList.size() ; i++){
                System.out.println(resultList.get(i)+resultList.get(i+1));
                javafx.scene.Node line = edges.get(resultList.get(i)+resultList.get(i+1)).getChildren().get(0);

                if (line instanceof Line) {
                    ((Line) line).setStroke(Color.DARKORANGE);
                    ((Line) line).setStrokeWidth(4);
                }
            }

        }catch(Exception e){
            System.out.println("xxxxxxxxxxxxxxx No edge for ");
        }
    }


    TextField cost;
    RadioButton undirected, directed;

    Stage returnPopUp() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setMinSize(300, 300);
        grid.setVgap(5);
        grid.setHgap(5);
        Text text = new Text("Path cost:");
        grid.add(text, 0, 0);
        cost = new TextField();
        cost.setPrefColumnCount(10);
        grid.add(cost, 1, 0);

        undirected = new RadioButton("Undirected");
        directed = new RadioButton("Directed");
        ToggleGroup group = new ToggleGroup();
        undirected.setToggleGroup(group);
        undirected.setSelected(true);
        directed.setToggleGroup(group);
        grid.add(undirected, 1, 1);
        grid.add(directed, 1, 2);
        okBtn = new Button("OK");
        grid.add(okBtn, 1, 3);

        this.dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        VBox dialogVbox = new VBox(20);
        dialog.setTitle("Information Required");
        dialog.setResizable(false);
        dialog.setWidth(250);
        dialog.setHeight(195);
        dialogVbox.getChildren().add(grid);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        return dialog;
    }
}



























