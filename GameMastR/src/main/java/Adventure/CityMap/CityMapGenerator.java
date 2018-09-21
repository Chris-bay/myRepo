package Adventure.CityMap;

import Adventure.AGUI.AdventureController;
import Adventure.triangulation.*;
import com.sun.istack.internal.NotNull;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.omg.CORBA.MARSHAL;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class CityMapGenerator {

    private GraphicsContext gc;
    private int height;
    private int width;
    private double diagonal;
    ArrayList<Polygon2D> polygons = new ArrayList<>();
    ArrayList<Polygon2D> shrinkedPolygons = new ArrayList<>();
    Vector<Vector2D> pointSet = new Vector<>();
    Vector2D middlePoint;

    private int count;
    private final int radius = 5;
    private final double shrinkage = 5;
    private final double size = 120;
    private final double edgeWidth = 4;
    private final int subdevisions = 5;

    boolean drawStartPoints = AdventureController.drawStartPointsPreset;
    boolean drawPolyPoints = AdventureController.drawPolyPointsPreset;
    boolean drawPolyEdges = AdventureController.drawPolyEdgesPreset;
    boolean drawFillPoly = AdventureController.drawFillPolyPreset;

    boolean fillPolyUnicolor = AdventureController.fillPolyUnicolorPreset;

    private final Paint polyEdgeColor = Color.color(0,0,0);
    private final Paint polyPointColor = Color.color(1,0,0);
    private final Paint startPointColor = Color.color(0.3,0.5,0.3);
    //private final Paint polyFillColor = Color.rgb(212, 166, 146);
    private final Paint polyFillColor = Color.rgb(188, 188, 188);
    private final Paint backgroundColor = Color.rgb(248, 248, 248);

    public GraphicsContext createMap() {
        try {
            ///*
            pointSet.add(new Vector2D(0, 0));
            pointSet.add(new Vector2D(0, height));
            pointSet.add(new Vector2D(width, 0));
            pointSet.add(new Vector2D(width, height));
            //*/
            for (int i = 0; i < count; i++) {
                Vector2D newPoint = new Vector2D(2 * Math.random() * width - width, 2 * Math.random() * height - height);
                pointSet.add(newPoint);
            }
            DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator(pointSet);
            delaunayTriangulator.triangulate();

            List<Triangle2D> triangleSoup = delaunayTriangulator.getTriangles();

            for (Vector2D p : pointSet) {
                Polygon2D poly = new Polygon2D();
                poly.setPoints(getVoronoiDiagram(p, findConnectedTriangles(p, triangleSoup)));
                poly.subDivide(subdevisions);
                polygons.add(poly);
                shrinkedPolygons.add(shrink(poly, shrinkage));
            }

        } catch (NotEnoughPointsException e) {
        }

        return gc;
    }

    public void draw() {
        clear();

        for (Polygon2D polygon : shrinkedPolygons) {
            ArrayList<Vector2D> points = polygon.getPoints();
            if (points.size() > 1) {
                boolean drawOnce = true;
                for (Vector2D v : points) {
                    if (drawPolyPoints) {
                        gc.setFill(polyPointColor);
                        gc.fillOval(v.x - radius / 2f, v.y - radius / 2f, radius, radius);
                    }
                    if (v.sub(middlePoint).mag() < size && drawOnce && drawFillPoly) {
                        gc.setFill(polyFillColor);
                        fillPolygon(polygon, gc);
                        if (drawPolyEdges) {
                            for (Edge2D edge2D: polygon.getEdges()) {
                                gc.setStroke(polyEdgeColor);
                                gc.setLineWidth(edgeWidth);
                                gc.strokeLine(edge2D.a.x, edge2D.a.y, edge2D.b.x, edge2D.b.y);
                            }
                            for (Vector2D subVector: polygon.subPoints){
                                gc.setFill(polyEdgeColor);
                                gc.fillOval(subVector.x - radius / 2f, subVector.y - radius / 2f, radius, radius);
                            }
                        }
                        drawOnce = false;
                    }
                }

            }
        }
        for (Vector2D newPoint : pointSet) {
            if (drawStartPoints) {
                gc.setFill(startPointColor);
                gc.fillOval(newPoint.x - radius / 2f, newPoint.y - radius / 2f, radius, radius);
            }
        }
    }

    public void initialize(GraphicsContext gc) {
        this.gc = gc;
        diagonal = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
        height = (int) gc.getCanvas().getHeight();
        width = (int) gc.getCanvas().getWidth();
        clear();
        polygons.clear();
        shrinkedPolygons.clear();
        pointSet.clear();
        count = (height * width) / 2500;
        middlePoint =  new Vector2D(width/2d, height/2d);
        /*
        drawStartPoints = AdventureController.drawStartPointsPreset;
        drawPolyPoints = AdventureController.drawPolyPointsPreset;
        drawPolyEdges = AdventureController.drawPolyEdgesPreset;
        drawFillPoly = AdventureController.drawFillPolyPreset;
        */
    }

    public void clear(){
        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, width, height);
    }

    private void fillPolygon(Polygon2D polygon, GraphicsContext gc) {
        ArrayList<Vector2D> points = polygon.getPoints();
        double[] x = new double[points.size()];
        double[] y = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
        }
        if (fillPolyUnicolor)
            gc.setFill(polygon.color);
        else
            gc.setFill(polyFillColor);

        gc.fillPolygon(x, y, points.size());
    }

    private Vector2D circumCircle(Triangle2D triangle2D) {
        Vector2D a = triangle2D.a;
        Vector2D b = triangle2D.b;
        Vector2D c = triangle2D.c;

        double d = 2 * (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));
        double x = ((sqr(a.x) + sqr(a.y)) * (b.y - c.y) + (sqr(b.x) + sqr(b.y)) * (c.y - a.y) + (sqr(c.x) + sqr(c.y)) * (a.y - b.y)) / d;
        double y = ((sqr(a.x) + sqr(a.y)) * (c.x - b.x) + (sqr(b.x) + sqr(b.y)) * (a.x - c.x) + (sqr(c.x) + sqr(c.y)) * (b.x - a.x)) / d;
        //System.out.println((int) x + ", " + (int) y);
        return new Vector2D(x, y);
    }

    private ArrayList<Vector2D> getVoronoiDiagram(Vector2D middlePoint, ArrayList<Triangle2D> triangles) {
        ArrayList<Vector2D> points = new ArrayList<>();

        Optional<Vector2D> faultyPoint = Optional.empty();

        for (Triangle2D triangle2D : triangles) {
            int countA = 1;
            int countB = 1;
            int countC = 1;

            for (Triangle2D testingTriangle : triangles) {
                if (!testingTriangle.equals(triangle2D)) {
                    if (testingTriangle.hasVertex(triangle2D.a))
                        countA++;
                    if (testingTriangle.hasVertex(triangle2D.b))
                        countB++;
                    if (testingTriangle.hasVertex(triangle2D.c))
                        countC++;
                }
            }

            if (countA != 1) {
                faultyPoint = Optional.of(triangle2D.a);
            }
            if (countB != 1) {
                faultyPoint = Optional.of(triangle2D.b);
            }
            if (countC != 1) {
                faultyPoint = Optional.of(triangle2D.c);
            }

            if (faultyPoint.isPresent()) {
                //System.out.println("found faulty Point: " + faultyPoint);
            }

        }
        Vector2D currentPoint;
        if (faultyPoint.isPresent())
            currentPoint = faultyPoint.get();
        else if (!middlePoint.equals(triangles.get(triangles.size() - 1).a)) {
            currentPoint = triangles.get(triangles.size() - 1).a;
        } else {
            currentPoint = triangles.get(triangles.size() - 1).b;
        }
        Triangle2D currentTriangle = triangles.get(triangles.size() - 1);

        for (int i = 0; i < triangles.size(); i++) {
            for (Triangle2D triangle2D : triangles) {
                if (!triangle2D.equals(currentTriangle) && triangle2D.hasVertex(currentPoint)) {
                    points.add(circumCircle(triangle2D));
                    currentTriangle = triangle2D;
                    currentPoint = currentTriangle.getNoneEdgeVertex(new Edge2D(middlePoint, currentPoint));
                    break;
                }
            }
        }

        return points;
    }

    private ArrayList<Triangle2D> findConnectedTriangles(Vector2D middlePoint, ArrayList<Triangle2D> triangles) {
        ArrayList<Triangle2D> conTriangles = new ArrayList<>();

        for (Triangle2D triangle2D : triangles)
            if (triangle2D.hasVertex(middlePoint))
                conTriangles.add(triangle2D);

        return conTriangles;
    }

    private ArrayList<Triangle2D> findConnectedTriangles(Vector2D middlePoint, List<Triangle2D> triangles) {
        ArrayList<Triangle2D> triangle2DS = new ArrayList<>(triangles);
        return findConnectedTriangles(middlePoint, triangle2DS);
    }

    private Polygon2D shrink(Polygon2D polygon, double px) {
        Polygon2D pol = new Polygon2D();
        ArrayList<Vector2D> points = polygon.getPoints();
        if(points.size()>2) {
            Vector2D p1 = points.get(points.size() - 2);
            Vector2D p2 = points.get(points.size() - 1);
            for (Vector2D p3 : points) {
                Vector2D v1 = new Vector2D(p1.x - p2.x, p1.y - p2.y);
                Vector2D v2 = new Vector2D(p3.x - p2.x, p3.y - p2.y);
                double deg = getDegr(v1, v2);

                Vector2D halfing;
                if (polygon.isClockwise())
                    halfing = turnVector(v1, deg / 2);
                else
                    halfing = turnVector(v2, deg / 2);

                halfing = halfing.mult(px / halfing.mag());

                pol.getPoints().add(p2.add(halfing));

                p1 = p2;
                p2 = p3;
            }
            return pol;
        }
        return polygon;
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

    private Double getDegr(Vector2D v1, Vector2D v2) {
        return Math.acos(v1.dot(v2) / (v1.mag() * v2.mag()));
    }

    private Vector2D turnVector(Vector2D vector, double radians) {
        return new Vector2D(vector.x * Math.cos(radians) - vector.y * Math.sin(radians), vector.x * Math.sin(radians) + vector.y * Math.cos(radians));
    }

    private Boolean blocked(Vector2D node, Edge2D edge2D, Vector2D newNode) {

        if (node.equals(newNode))
            return true;
        //System.out.println((int) node.x + "," + (int) node.y + " " + (int) newNode.x + "," + (int) newNode.y);
        //System.out.println("Checking Box:" + (int) Math.min(edge2D.a.x, edge2D.b.x) + "," + (int) Math.max(edge2D.a.x, edge2D.b.x)
        //        + " " + (int) Math.min(edge2D.a.y, edge2D.b.y) + "," + (int) Math.max(edge2D.a.y, edge2D.b.y));
        if (node.x < Math.min(edge2D.a.x, edge2D.b.x) && newNode.x < Math.min(edge2D.a.x, edge2D.b.x)) {
            //System.out.println("both points left from line");
            //System.out.println();
            return false;
        }

        if (node.y < Math.min(edge2D.a.y, edge2D.b.y) && newNode.y < Math.min(edge2D.a.y, edge2D.b.y)) {
            //System.out.println("both points over the line");
            //System.out.println();
            return false;
        }

        if (node.x > Math.max(edge2D.a.x, edge2D.b.x) && newNode.x > Math.max(edge2D.a.x, edge2D.b.x)) {
            //System.out.println("both points right from line");
            //System.out.println();
            return false;
        }

        if (node.y > Math.max(edge2D.a.y, edge2D.b.y) && newNode.y > Math.max(edge2D.a.y, edge2D.b.y)) {
            //System.out.println("both points under the line");
            //System.out.println();
            return false;
        }


        Vector2D A = edge2D.a;
        Vector2D B = edge2D.b;

        Vector2D lineVector = new Vector2D(B.x - A.x, B.y - A.y);
        Vector2D ANodeVector = new Vector2D(node.x - A.x, node.y - A.y);
        Vector2D BNodeVector = new Vector2D(B.x - node.x, B.y - node.y);
        Vector2D ANewNodeVector = new Vector2D(newNode.x - A.x, newNode.y - A.y);
        Vector2D BNewNodeVector = new Vector2D(B.x - newNode.x, B.y - newNode.y);

        if (node.x < A.x + ((node.y - A.y) / lineVector.y) * lineVector.x && newNode.x < A.x + ((newNode.y - A.y) / lineVector.y) * lineVector.x) {
            //System.out.println("both points right from line");
            //System.out.println();
            return false;
        }

        if (node.x > A.x + ((node.y - A.y) / lineVector.y) * lineVector.x && newNode.x > A.x + ((newNode.y - A.y) / lineVector.y) * lineVector.x) {
            //System.out.println("both points left from line");
            //System.out.println();
            return false;
        }

        if (node.y < A.y + ((node.x - A.x) / lineVector.x) * lineVector.y && newNode.y < A.y + ((newNode.x - A.x) / lineVector.x) * lineVector.y) {
            //System.out.println("both points over the line");
            //System.out.println();
            return false;
        }

        if (node.y > A.y + ((node.x - A.x) / lineVector.x) * lineVector.y && newNode.y > A.y + ((newNode.x - A.x) / lineVector.x) * lineVector.y) {
            //System.out.println("both points under the line");
            //System.out.println();
            return false;
        }

        double a1;
        double a2;
        double b1;
        double b2;

        int scalProd = (int) (lineVector.x * ANodeVector.x + lineVector.y * ANodeVector.y);
        double abs1 = A.sub(B).mag();
        double abs2 = A.sub(node).mag();

        a1 = Math.acos((lineVector.x * ANodeVector.x + lineVector.y * ANodeVector.y) / (abs1 * abs2));
        a2 = Math.acos((lineVector.x * BNodeVector.x + lineVector.y * BNodeVector.y) / (abs1 * B.sub(node).mag()));
        b1 = Math.acos((lineVector.x * ANewNodeVector.x + lineVector.y * ANewNodeVector.y) / (abs1 * A.sub(newNode).mag()));
        b2 = Math.acos((lineVector.x * BNewNodeVector.x + lineVector.y * BNewNodeVector.y) / (abs1 * B.sub(newNode).mag()));

        /*
        System.out.println();
        System.out.println("a1: " + (lineVector.x * ANodeVector.x + lineVector.y * ANodeVector.y) / (A.dist(B) * A.dist(node)));
        System.out.println("a1 raw: " + (lineVector.x * ANodeVector.x + lineVector.y * ANodeVector.y) + " " + (int)(A.dist(B) * A.dist(node)));
        System.out.println("a2: " + (lineVector.x * BNodeVector.x + lineVector.y * BNodeVector.y) / (B.dist(A) * B.dist(node)));
        System.out.println("a2 raw: " + (lineVector.x * BNodeVector.x + lineVector.y * BNodeVector.y) + " " + (int)(B.dist(A) * B.dist(node)));
        System.out.println("b1: " + (lineVector.x * ANewNodeVector.x + lineVector.y * ANewNodeVector.y) / (B.dist(A) * A.dist(newNode)));
        System.out.println("b1 raw: " + (lineVector.x * ANewNodeVector.x + lineVector.y * ANewNodeVector.y) + " " + (int)(B.dist(A) * A.dist(newNode)));
        System.out.println("b2: " + (lineVector.x * BNewNodeVector.x + lineVector.y * BNewNodeVector.y) / (B.dist(A) * B.dist(newNode)));
        System.out.println("b2 raw: " + (int)(lineVector.x * BNewNodeVector.x + lineVector.y * BNewNodeVector.y) + " " + (int)(B.dist(A) * B.dist(newNode)));
        //*/
        //System.out.println("alpha1: " + (int) Math.toDegrees(a1) + " beta1: " + (int) Math.toDegrees(b1) +
        //        " beta2: " + (int) Math.toDegrees(b2) + " alpha2: " + (int) Math.toDegrees(a2));
        //System.out.println((int) node.x + "," + (int) node.y + " " + (int) newNode.x + "," + (int) newNode.y + " : " + (a1 + b1 < Math.PI && a2 + b2 < Math.PI));
        //System.out.println();
        //*/
        return (a1 + b1 < Math.PI && a2 + b2 < Math.PI);
    }

    private Double sqr(Double a) {
        return Math.pow(a, 2);
    }

    public void toggleDrawStartPoints() {
        drawStartPoints = !drawStartPoints;
    }

    public void toggleDrawPolyEdges() {
        drawPolyEdges = !drawPolyEdges;
    }

    public void toggleDrawPolyPoints() {
        drawPolyPoints = !drawPolyPoints;
    }

    public void toggleFillPoly() {
        drawFillPoly = !drawFillPoly;
    }

    public void setDrawStartPoints(boolean value) {
        drawStartPoints = value;
    }

    public void setDrawPolyEdges(boolean value) {
        drawPolyEdges = value;
    }

    public void setDrawPolyPoints(boolean value) {
        drawPolyPoints = value;
    }

    public void setFillPoly(boolean value) {
        drawFillPoly = value;
    }
}
