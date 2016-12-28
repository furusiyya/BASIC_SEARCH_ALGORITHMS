package sample;


public class sampleProblemSpaces {

    Controller controller;

    public sampleProblemSpaces(Controller controler,int search_type){
        this.controller = controler;
        switch (search_type) {
            case 1:
                tree();
                break;
            case 2:

                break;
            case 3:
                a_star();
                break;
            case 4:

                break;
        }
        controler.nodes = controler.tree.getNodes();
        controler.edges = controler.graphPanel.edges;
        controler.shapes = controler.graphPanel.shapes;
    }

    /**
     * Method will load a sample tree for BFS, DFS, Hill_Climb search algorithm
     */
    void tree(){
        controller.graphPanel.drawNode(325,31,"1");
        controller.graphPanel.drawNode(209,104,"2");
        controller.graphPanel.drawNode(325,104,"3");
        controller.graphPanel.drawNode(443,104,"4");
        controller.graphPanel.drawNode(88,193,"5");
        controller.graphPanel.drawNode(205,193,"6");
        controller.graphPanel.drawNode(441,193,"7");
        controller.graphPanel.drawNode(563,198,"8");
        controller.graphPanel.drawNode(11,267,"9");
        controller.graphPanel.drawNode(90,267,"10");
        controller.graphPanel.drawNode(442,267,"11");
        controller.graphPanel.drawNode(554,267,"12");

        controller.graphPanel.drawEdge("1","2",2,true);
        controller.graphPanel.drawEdge("1","3",4,true);
        controller.graphPanel.drawEdge("1","4",1,true);
        controller.graphPanel.drawEdge("2","5",3,true);
        controller.graphPanel.drawEdge("2","6",2,true);
        controller.graphPanel.drawEdge("5","9",5,true);
        controller.graphPanel.drawEdge("5","10",1,true);
        controller.graphPanel.drawEdge("4","7",2,true);
        controller.graphPanel.drawEdge("4","8",3,true);
        controller.graphPanel.drawEdge("7","11",6,true);
        controller.graphPanel.drawEdge("7","12",5,true);
    }

    void graph(){

    }

    void a_star(){
        controller.graphPanel.drawNode(62.0,31.0,"Oradea");
        controller.graphPanel.drawNode(29.0,134.0,"Zerind");
        controller.graphPanel.drawNode(0.0,219.0,"Arad");
        controller.graphPanel.drawNode(1.0,342.0,"Timisoara");
        controller.graphPanel.drawNode(146.0,253.0,"Sibiu");
        controller.graphPanel.drawNode(300.0,264.0,"Fagaras");
        controller.graphPanel.drawNode(164.0,329.0,"Rimnicu Vilcea");
        controller.graphPanel.drawNode(111.0,376.0,"Lugoj");
        controller.graphPanel.drawNode(110.0,456.0,"Mehadia");
        controller.graphPanel.drawNode(95.0,508.0,"Dobreta");
        controller.graphPanel.drawNode(228.0,513.0,"Cralova");
        controller.graphPanel.drawNode(316.0,384.0,"Pitesti");
        controller.graphPanel.drawNode(470.0,421.0,"Bucharest");
        controller.graphPanel.drawNode(416.0,485.0,"Giurgiu");
        controller.graphPanel.drawNode(562.0,355.0,"Urziceni");
        controller.graphPanel.drawNode(674.0,353.0,"Hirsova");
        controller.graphPanel.drawNode(698.0,434.0,"Eforie");
        controller.graphPanel.drawNode(607.0,252.0,"Vaslui");
        controller.graphPanel.drawNode(535.0,156.0,"Lasi");
        controller.graphPanel.drawNode(434.0,128.0,"Neamt");

        controller.graphPanel.drawEdge("Oradea","Zerind",71,false);
        controller.graphPanel.drawEdge("Oradea","Sibiu",151,false);
        controller.graphPanel.drawEdge("Zerind","Arad",75,false);
        controller.graphPanel.drawEdge("Arad","Timisoara",118,false);
        controller.graphPanel.drawEdge("Arad","Sibiu",140,false);
        controller.graphPanel.drawEdge("Timisoara","Lugoj",111,false);
        controller.graphPanel.drawEdge("Lugoj","Mehadia",70,false);
        controller.graphPanel.drawEdge("Mehadia","Dobreta",75,false);
        controller.graphPanel.drawEdge("Dobreta","Cralova",120,false);
        controller.graphPanel.drawEdge("Sibiu","Fagaras",99,false);
        controller.graphPanel.drawEdge("Sibiu","Rimnicu Vilcea",80,false);
        controller.graphPanel.drawEdge("Rimnicu Vilcea","Cralova",146,false);
        controller.graphPanel.drawEdge("Rimnicu Vilcea","Pitesti",97,false);
        controller.graphPanel.drawEdge("Cralova","Pitesti",138,false);
        controller.graphPanel.drawEdge("Pitesti","Bucharest",101,false);
        controller.graphPanel.drawEdge("Fagaras","Bucharest",211,false);
        controller.graphPanel.drawEdge("Bucharest","Giurgiu",90,false);
        controller.graphPanel.drawEdge("Bucharest","Urziceni",85,false);
        controller.graphPanel.drawEdge("Urziceni","Hirsova",98,false);
        controller.graphPanel.drawEdge("Hirsova","Eforie",86,false);
        controller.graphPanel.drawEdge("Urziceni","Vaslui",142,false);
        controller.graphPanel.drawEdge("Vaslui","Lasi",92,false);
        controller.graphPanel.drawEdge("Lasi","Neamt",87,false);


        controller.heuristic_to = "Bucharest";
        controller.heuristic_from.setText("Straight-Line distance to: "+controller.heuristic_to);
        controller.heuristic.put("Bucharest",0);
        controller.heuristic.put("Arad",366);
        controller.heuristic.put("Cralova",160);
        controller.heuristic.put("Dobreta",242);
        controller.heuristic.put("Eforie",161);
        controller.heuristic.put("Fagaras",176);
        controller.heuristic.put("Glurgui",77);
        controller.heuristic.put("Hirsova",151);
        controller.heuristic.put("Lasi",226);
        controller.heuristic.put("Lugoj",244);
        controller.heuristic.put("Mehadia",241);
        controller.heuristic.put("Neamt",234);
        controller.heuristic.put("Oradea",380);
        controller.heuristic.put("Pitesti",100);
        controller.heuristic.put("Rimnicu Vilcea",193);
        controller.heuristic.put("Sibiu",253);
        controller.heuristic.put("Timisoara",329);
        controller.heuristic.put("Urziceni",80);
        controller.heuristic.put("Vaslui",199);
        controller.heuristic.put("Zerind",374);
        controller.add_nodes_to_table(true);
    }
}
