package Adventure.CityMap;

import Adventure.triangulation.Edge2D;
import Adventure.triangulation.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

public class Polygon2D {
    private ArrayList<Vector2D> points = new ArrayList<>();
    private ArrayList<Edge2D> edges = new ArrayList<>();
    public ArrayList<Polygon2D> subPolygons = new ArrayList<>();
    public ArrayList<Vector2D> subPoints = new ArrayList<>();
    public Paint color = Color.gray(Math.random());
    public double area;
    public Edge2D largestEdge;

    Polygon2D() {
    }

    public boolean isClockwise() {
        int sum = 0;
        Vector2D v1 = points.get(points.size() - 1);
        for (Vector2D v2 : points) {
            sum += (v2.x-v1.x)*(v2.y+v1.y);
            v1 = v2;
        }
        return sum>0;
    }

    public ArrayList<Polygon2D> subDivide(int size){
        if(largestEdge!=null) {
            double part = largestEdge.length() / size;
            Vector2D unitVector = largestEdge.b.sub(largestEdge.a).add(largestEdge.a);
            unitVector = unitVector.mult(1 / unitVector.mag());
            Vector2D turnedVector = new Vector2D(unitVector.y, -unitVector.x);

            for (int i = 0; i < size; i++) {
                Vector2D cVector = unitVector.mult(i * part);
                for (Edge2D edge : edges) {
                    Optional<Vector2D> junction = junctionOf(edge, turnedVector, cVector);
                    junction.ifPresent(jVector -> subPoints.add(jVector));
                }
            }
            return this.subPolygons;
        }
        return new ArrayList<Polygon2D>();
    }

    public Optional<Vector2D> junctionOf(Edge2D edge, Vector2D dirVector, Vector2D startVector){
        Optional<Vector2D> result = Optional.empty();
        Vector2D startToA = new Vector2D(edge.a.x-startVector.x, edge.a.y-startVector.y);
        Vector2D startToB = new Vector2D(edge.b.x-startVector.x, edge.b.y-startVector.y);
        Vector2D startWithDir = new Vector2D(dirVector.x+startVector.x, dirVector.y+startVector.y);
        double degAB = startToA.getDegr(startToB);
        double degSA = startToB.getDegr(startWithDir);
        double degSB = startToA.getDegr(startWithDir);

        // has a junction?
        if (degAB<degSA && degAB < degSB){
            Vector2D AB = new Vector2D(edge.b.x-edge.a.x, edge.b.y-edge.a.y);
            double degAS = startToB.getDegr(startWithDir.mult(-1));
            double degABB = AB.getDegr(startToA);
            double degAGS = Math.PI - degABB - degAS;

            double lengthOfDevider = startToA.mag()*Math.sin(degABB)/Math.sin(degAGS);

            Vector2D unitVector = startWithDir.mult(lengthOfDevider/startWithDir.mag());

            result = Optional.of(startVector.add(unitVector));
        }

        return result;
    }

    public boolean hasJunction(Edge2D edge, Vector2D directionalVector, Vector2D startVector){
        Vector2D startToA = new Vector2D(edge.a.x-startVector.x, edge.a.y-startVector.y);
        Vector2D startToB = new Vector2D(edge.b.x-startVector.x, edge.b.y-startVector.y);
        Vector2D startWithDir = new Vector2D(directionalVector.x+startVector.x, directionalVector.y+startVector.y);
        double degAB = startToA.getDegr(startToB);
        double degSA = startToB.getDegr(startWithDir);
        double degSB = startToA.getDegr(startWithDir);
        if (degAB<degSA && degAB < degSB){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Edge2D> calculateEdges(){
        double length = Double.MAX_VALUE;
        Optional<Edge2D> longest = Optional.empty();
        edges.clear();
        if (points.size()>1){
            Vector2D prevPoint = points.get(points.size() - 1);
            for (Vector2D v1 : points){
                Edge2D ed = new Edge2D(prevPoint, v1);
                if (!this.edges.contains(ed)){
                    if (ed.length()<length){
                        longest = Optional.of(ed);
                    }
                    this.edges.add(ed);
                }
                prevPoint = v1;
            }
        }
        longest.ifPresent(edge2D -> this.largestEdge = edge2D);
        return this.edges;
    }

    public double calculateArea(){
        double sum1 = 0;
        double sum2 = 0;
        if (points.size()>2) {
            for (int i = 0; i < points.size()-2; i++) {
                Vector2D p1 = points.get(i);
                Vector2D p2 = points.get(i+1);
                sum1 += p1.x*p2.y;
                sum2 += p1.y*p2.y;
            }
        }
        this.area = (sum1-sum2)/2;
        return this.area;
    }

    public Vector2D addPoint(Vector2D point){
        if (!points.contains(point))
            points.add(point);
        //calculateEdges();
        return point;
    }

    public ArrayList<Vector2D> getPoints(){
        return this.points;
    }

    public void setPoints(ArrayList<Vector2D> ps){
        this.points = ps;
        calculateEdges();
        calculateArea();
    }

    public ArrayList<Edge2D> getEdges() {
        return this.edges;
    }

    public void setEdges(ArrayList<Edge2D> edges) {
        this.edges = edges;
    }
}
