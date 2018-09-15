package Adventure.CityMap;

import java.util.ArrayList;

public class Triangle {
    public ArrayList<Node> nodes = new ArrayList<>();

    Triangle(){}

    Triangle(Node n1, Node n2, Node n3){
        this.nodes.set(0,n1);
        this.nodes.set(1,n2);
        this.nodes.set(2,n3);
    }

}
