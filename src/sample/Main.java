package sample;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("BFS_DFS");
        primaryStage.setScene(new Scene(root, 927, 595));
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);
//        Tree obj = new Tree();
//        obj.addNode("1");
//        obj.addNode("2",5,"1");
//        obj.addNode("3",7,"1");
//        obj.addNode("4",6,"1");
//        obj.addNode("5",2,"2");
//        obj.addNode("6",1,"2");
//        obj.addNode("11",3,"6");
//        obj.addNode("7",13,"4");
//        obj.addNode("8",20,"4");
//        obj.addNode("9",7,"5");
//        obj.addNode("10",16,"5");
//        obj.addNode("11",2,"7");
//        obj.addNode("12",9,"7");

//        obj.addNode("Arad");
//        obj.addNode("Timisoara",118,"Arad");
//        obj.addNode("Sibiu",140,"Arad");
//        obj.addNode("Zerind",75,"Arad");
//
//
//        obj.addNode("Arad",118,"Timisoara");
//        obj.addNode("Lugoj",111,"Timisoara");
//
//        obj.addNode("Timisoara",111,"Lugoj");
//        obj.addNode("Mehadla",70,"Lugoj");
//
//        obj.addNode("Lugoj",70,"Mehadla");
//        obj.addNode("Dobreta",75,"Mehadla");
//
//        obj.addNode("Mehadla",75,"Dobreta");
//        obj.addNode("Cralova",120,"Dobreta");
//
//        obj.addNode("Dobreta",120,"Cralova");
//        obj.addNode("Rimnicu Vilcea",146,"Cralova");
//        obj.addNode("Pitesti",138,"Cralova");
//
//        obj.addNode("Arad",140,"Sibiu");
//        obj.addNode("Oradea",151,"Sibiu");
//        obj.addNode("Fagaras",99,"Sibiu");
//        obj.addNode("Rimnicu Vilcea",80,"Sibiu");
//
//        obj.addNode("Sibiu",151,"Oradea");
//        obj.addNode("Zerind",71,"Oradea");
//
//        obj.addNode("Sibiu",99,"Fagaras");
//        obj.addNode("Bucharest",211,"Fagaras");
//
//        obj.addNode("Fagaras",211,"Bucharest");
//        obj.addNode("Pitesti",101,"Bucharest");
//        obj.addNode("Glurgui",90,"Bucharest");
//        obj.addNode("Urziceni",85,"Bucharest");
//
//
//        obj.addNode("Bucharest",90,"Glurgui");
//
//
//        obj.addNode("Bucharest",85,"Urziceni");
//        obj.addNode("Vaslui",142,"Urziceni");
//        obj.addNode("Hirsova",98,"Urziceni");
//
//        obj.addNode("Urziceni",98,"Hirsova");
//        obj.addNode("Eforle",86,"Hirsova");
//
//        obj.addNode("Hirsova",86,"Eforle");
//
//        obj.addNode("Urziceni",142,"Vaslui");
//        obj.addNode("Lasi",92,"Vaslui");
//
//        obj.addNode("Vaslui",92,"Lasi");
//        obj.addNode("Neamt",87,"Lasi");
//
//        obj.addNode("Lasi",87,"Neamt");
//
//        obj.addNode("Sibiu",80,"Rimnicu Vilcea");
//        obj.addNode("Cralova",146,"Rimnicu Vilcea");
//        obj.addNode("Pitesti",97,"Rimnicu Vilcea");
//
//        obj.addNode("Rimnicu Vilcea",97,"Pitesti");
//        obj.addNode("Cralova",138,"Pitesti");
//        obj.addNode("Bucharest",101,"Pitesti");
//
//        obj.addNode("Arad",75,"Zerind");
//        obj.addNode("Oradea",71,"Zerind");
//
//        A_StarSearch itr = new A_StarSearch(obj.getNodes(),"Arad","Bucharest");
//        itr.airPlaneDistance("Arad",366);
//        itr.airPlaneDistance("Bucharest",0);
//        itr.airPlaneDistance("Cralova",160);
//        itr.airPlaneDistance("Dobreta",142);
//        itr.airPlaneDistance("Eforle",161);
//        itr.airPlaneDistance("Fagaras",176);
//        itr.airPlaneDistance("Glurgui",77);
//        itr.airPlaneDistance("Hirsova",151);
//        itr.airPlaneDistance("Lasi",226);
//        itr.airPlaneDistance("Lugoj",244);
//        itr.airPlaneDistance("Mehadla",241);
//        itr.airPlaneDistance("Neamt",234);
//        itr.airPlaneDistance("Oradea",380);
//        itr.airPlaneDistance("Pitesti",10);
//        itr.airPlaneDistance("Rimnicu Vilcea",193);
//        itr.airPlaneDistance("Sibiu",253);
//        itr.airPlaneDistance("Timisoara",329);
//        itr.airPlaneDistance("Urziceni",80);
//        itr.airPlaneDistance("Vaslui",199);
//        itr.airPlaneDistance("Zerind",374);
//        itr.traverse();
//        HillClimbingSearch itr = new HillClimbingSearch(obj.getNodes(),"1","12");
//       BreadthFirstTreeIterator itr =  new BreadthFirstTreeIterator(obj.getNodes(),"1");
//        System.out.println("Iterator Result: ");
//        itr.getList().forEach(item->{
//            System.out.println(item.getParent());
//        });
//        DepthFirstTreeIterator itr = new DepthFirstTreeIterator(obj.getNodes(),"1");
//        IterativeDeppeningSearch itr = new IterativeDeppeningSearch(obj.getNodes(),"1");
//        while(itr.hasNext())System.out.println(itr.next().getParent());
//        System.exit(0);em.out.println(itr.next().getParent());
//        System.exit(0);
//        while(itr.hasNext())System.out.println(itr.next());

    }

}
