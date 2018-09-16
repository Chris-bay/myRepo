package Adventure.CityMap;

import Adventure.triangulation.*;
import com.sun.istack.internal.NotNull;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;


public class CityMapGenerator {

    public GraphicsContext createMap(GraphicsContext gc) {
        int height = (int) gc.getCanvas().getHeight();
        int width = (int) gc.getCanvas().getWidth();
        double diagonal = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
        int count = (height * width)/2500;
        int radius = 10;
        boolean drawStartPoints = false;
        boolean drawPolyPoints = false;
        boolean drawPolyEdges = false;
        boolean drawFillPoly = true;

        //count = 100;

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        ArrayList<Polygon2D> polygons = new ArrayList<>();

        try {
            Vector<Vector2D> pointSet = new Vector<>();
            ///*
            pointSet.add(new Vector2D(0, 0));
            pointSet.add(new Vector2D(0, height));
            pointSet.add(new Vector2D(width, 0));
            pointSet.add(new Vector2D(width, height));
            //*/
            for (int i = 0; i < count; i++) {
                Vector2D newPoint = new Vector2D(2 * Math.random() * width - width, 2 * Math.random() * height - height);
                pointSet.add(newPoint);
                gc.setFill(Color.rgb(240, 200, 200));
                if (drawStartPoints)
                    gc.fillOval(newPoint.x - radius / 2f, newPoint.y - radius / 2f, radius, radius);
            }
            DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator(pointSet);
            delaunayTriangulator.triangulate();

            List<Triangle2D> triangleSoup = delaunayTriangulator.getTriangles();


            //Vector2D randomPoint = pointSet.get((int)(Math.random() * pointSet.size()));
            for (Vector2D p : pointSet) {
                Polygon2D poly = new Polygon2D();
                poly.points = getVoronoiDiagram(p, findConnectedTriangles(p, triangleSoup));
                polygons.add(poly);
            }



            for (Polygon2D polygon : polygons) {
                gc.setFill(Color.GREEN);
                gc.setStroke(Color.GREEN);
                if (polygon.points.size() > 1) {
                    Vector2D prevPoint = polygon.points.get(polygon.points.size() - 1);
                    for (Vector2D v : polygon.points) {
                        if(drawPolyPoints)
                            gc.fillOval(v.x - radius / 2f, v.y - radius / 2f, radius, radius);
                        if(drawPolyEdges)
                            gc.strokeLine(prevPoint.x, prevPoint.y, v.x, v.y);
                        prevPoint = v;
                    }
                    if(drawFillPoly)
                        fillPolygon(polygon.points, gc);
                }
            }

        } catch (NotEnoughPointsException e) {
        }

        return gc;
    }

    private void fillPolygon(ArrayList<Vector2D> points, GraphicsContext gc) {
        double[] x = new double[points.size()];
        double[] y = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
        }
        gc.setFill(Color.gray(Math.random()));
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

}
