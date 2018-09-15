package Adventure.CityMap;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Node {

    public double x;
    public double y;
    private int connections;
    private ArrayList<Node> connectedNodes = new ArrayList<>();
    private Node nextNode;


    Node(){}
    Node(double x, double y){
        this.x = x;
        this.y = y;
    }
    Node(int x, int y){
        this.x = (double) x;
        this.y = (double) y;
    }

    public Point2D toPoint2D(){
        return new Point2D(this.x, this.y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }

    public ArrayList<Node> getConnectedNodes() {
        return connectedNodes;
    }

    public void setConnectedNodes(ArrayList<Node> connectedNodes) {
        this.connectedNodes = connectedNodes;
    }

    public void addConnection(Node node){
        this.connectedNodes.add(node);
    }

    public Node getNextNode(){
        if (connectedNodes.size()==0){
            return null;
        }
        nextNode = connectedNodes.get(0);
        connectedNodes.remove(nextNode);
        connectedNodes.add(nextNode);
        return nextNode;
    }

    public double dist(Node n){
        return Math.sqrt(Math.pow(this.x-n.x, 2) + Math.pow(this.y-n.y, 2));
    }

    public boolean equals(Node n){
        return this.x == n.x && this.y==n.y;
    }
}
