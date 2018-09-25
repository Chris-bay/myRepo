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
    private ArrayList<Polygon2D> subPolygons = new ArrayList<>();
    private ArrayList<Edge2D> subEdges = new ArrayList<>();
    private ArrayList<Vector2D> subPoints = new ArrayList<>();
    private Vector2D middlePoint;
    private Paint color = Color.gray(Math.random());
    private double area;
    private Edge2D largestEdge;

    Polygon2D() {
    }

    public boolean isClockwise() {
        int sum = 0;
        Vector2D v1 = points.get(points.size() - 1);
        for (Vector2D v2 : points) {
            sum += (v2.x - v1.x) * (v2.y + v1.y);
            v1 = v2;
        }
        return sum > 0;
    }

    public ArrayList<Polygon2D> subDivide(double size) {
        this.calculateArea();
        this.calculateEdges();
        this.calculateMiddlePoint();
        if (largestEdge != null) {
            Polygon2D subPoly = Calc.shrinkTowards(this, 0.5f);
            this.subPolygons.add(subPoly);
            this.subEdges = subPoly.edges;
            this.subPoints = subPoly.points;
            return this.subPolygons;
        }
        return new ArrayList<Polygon2D>();
    }

    public ArrayList<Edge2D> calculateEdges() {
        double length = 0d;
        Optional<Edge2D> longest = Optional.empty();
        edges.clear();
        if (points.size() > 1) {
            Vector2D prevPoint = points.get(points.size() - 1);
            for (Vector2D v1 : points) {
                Edge2D ed = new Edge2D(prevPoint, v1);
                if (!this.edges.contains(ed)) {
                    if (ed.length() > length) {
                        longest = Optional.of(ed);
                        length = ed.length();
                    }
                    this.edges.add(ed);
                }
                prevPoint = v1;
            }
        }
        longest.ifPresent(edge2D -> this.largestEdge = edge2D);
        return this.edges;
    }

    public double calculateArea() {
        double sum1 = 0;
        double sum2 = 0;
        for (Edge2D e: edges){
            sum1 += e.a.x * e.b.y;
            sum1 += e.a.y * e.b.x;
        }
        this.area = (sum1 - sum2) / 2;
        return this.area;
    }

    public Vector2D calculateMiddlePoint(){
        this.middlePoint = Calc.calculateMiddlePoint(points);
        return this.middlePoint;
    }

    public Vector2D addPoint(Vector2D point) {
        if (!points.contains(point))
            points.add(point);
        //calculateEdges();
        return point;
    }

    public ArrayList<Vector2D> getPoints() {
        return this.points;
    }

    public void setPoints(ArrayList<Vector2D> ps) {
        this.points = ps;
        this.calculateEdges();
        this.calculateArea();
    }

    public ArrayList<Edge2D> getEdges() {
        return this.edges;
    }

    public void setEdges(ArrayList<Edge2D> edges) {
        this.edges = edges;
    }

    public ArrayList<Polygon2D> getSubPolygons() {
        return subPolygons;
    }

    public void setSubPolygons(ArrayList<Polygon2D> subPolygons) {
        this.subPolygons = subPolygons;
    }

    public ArrayList<Edge2D> getSubEdges() {
        return subEdges;
    }

    public void setSubEdges(ArrayList<Edge2D> subEdges) {
        this.subEdges = subEdges;
    }

    public ArrayList<Vector2D> getSubPoints() {
        return subPoints;
    }

    public void setSubPoints(ArrayList<Vector2D> subPoints) {
        this.subPoints = subPoints;
    }

    public Vector2D getMiddlePoint() {
        return middlePoint;
    }

    public void setMiddlePoint(Vector2D middlePoint) {
        this.middlePoint = middlePoint;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Edge2D getLargestEdge() {
        return largestEdge;
    }

    public void setLargestEdge(Edge2D largestEdge) {
        this.largestEdge = largestEdge;
    }
}

/*
            int count = (int) (largestEdge.length() / size);
            switch ((int) (Math.random() * 10)) {
                case 0:
                case 1:
                    count = (int) (count * 0.6);
                    break;
                case 2:
                case 3:
                case 4:
                    count = (int) (count * 0.8);
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    break;
            }
            double part = largestEdge.length() / count;
            Vector2D unitVector = largestEdge.b.sub(largestEdge.a);
            unitVector = unitVector.mult(1 / unitVector.mag());
            Vector2D turnedVector = new Vector2D(-unitVector.y, unitVector.x);
            if (isClockwise())
                turnedVector = turnedVector.mult(-1);

            for (int i = 1; i < count; i++) {
                Vector2D cVector = unitVector.mult(i * part).add(largestEdge.a);
                for (Edge2D edge : edges) {
                    if (!edge.equals(largestEdge)) {
                        Optional<Vector2D> junction = junctionOf(edge, turnedVector, cVector);
                        if (junction.isPresent()) {
                            Vector2D jVector = junction.get();
                            Edge2D cEdge = new Edge2D(cVector, jVector);
                            subEdges.add(cEdge);
                            subPoints.add(cVector);
                            subPoints.add(jVector);
                        }
                    }
                }
            }

 */
