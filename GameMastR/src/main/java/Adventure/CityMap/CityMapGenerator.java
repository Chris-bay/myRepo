package Adventure.CityMap;

import Adventure.triangulation.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class CityMapGenerator {

    public GraphicsContext createMap(GraphicsContext gc) {
        int height = (int) gc.getCanvas().getHeight();
        int width = (int) gc.getCanvas().getWidth();
        double diagonal = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
        int count = 20;
        int radius = 10;

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        List<Edge2D> edges = new ArrayList<>();

        try {
            Vector<Vector2D> pointSet = new Vector<>();
            /*
            pointSet.add(new Vector2D(0, 0));
            pointSet.add(new Vector2D(0, height));
            pointSet.add(new Vector2D(width, 0));
            pointSet.add(new Vector2D(width, height));
            //*/
            for (int i = 0; i < count; i++) {
                Vector2D newPoint = new Vector2D(Math.random() * width, Math.random() * height);
                pointSet.add(newPoint);
                gc.setFill(Color.RED);
                gc.fillOval(newPoint.x - radius / 2f, newPoint.y - radius / 2f, radius, radius);
            }
            DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator(pointSet);
            delaunayTriangulator.triangulate();

            List<Triangle2D> triangleSoup = delaunayTriangulator.getTriangles();
            for (Triangle2D triangle2D : triangleSoup) {
                gc.setFill(Color.BLACK);
                Vector2D c2 = circumCircle(triangle2D);
                gc.fillOval(c2.x - radius / 2f, c2.y - radius / 2f, radius, radius);
                for (Triangle2D adj : triangleSoup) {
                    if (!triangle2D.equals(adj)) {
                        if (triangle2D.isNeighbour(new Edge2D(adj.a, adj.b)) ||
                                triangle2D.isNeighbour(new Edge2D(adj.a, adj.c)) ||
                                triangle2D.isNeighbour(new Edge2D(adj.c, adj.b))) {
                            Vector2D c1 = circumCircle(adj);
                            edges.add(new Edge2D(c1, c2));
                        }
                    }
                }
                ///*
                gc.setStroke(Color.rgb(240,200,200));
                gc.strokeLine(triangle2D.a.x, triangle2D.a.y, triangle2D.b.x, triangle2D.b.y);
                gc.strokeLine(triangle2D.b.x, triangle2D.b.y, triangle2D.c.x, triangle2D.c.y);
                gc.strokeLine(triangle2D.c.x, triangle2D.c.y, triangle2D.a.x, triangle2D.a.y);
                //*/
            }
        } catch (NotEnoughPointsException e) {
        }

        for (Edge2D edge2D : edges) {
            gc.setStroke(Color.BLACK);
            gc.strokeLine(edge2D.a.x, edge2D.a.y, edge2D.b.x, edge2D.b.y);
        }

        return gc;
    }

    private void fillPolygon(ArrayList<Point2D> points, GraphicsContext gc) {
        double[] x = new double[points.size()];
        double[] y = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            x[i] = points.get(i).getX();
            y[i] = points.get(i).getY();
        }
        gc.setFill(Color.BLACK);
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

    private Boolean blocked(Node node, Line line, Node newNode) {

        if (node.equals(newNode))
            return true;
        //System.out.println((int) node.x + "," + (int) node.y + " " + (int) newNode.x + "," + (int) newNode.y);
        //System.out.println("Checking Box:" + (int) Math.min(line.p1.x, line.p2.x) + "," + (int) Math.max(line.p1.x, line.p2.x)
        //        + " " + (int) Math.min(line.p1.y, line.p2.y) + "," + (int) Math.max(line.p1.y, line.p2.y));
        if (node.x < Math.min(line.p1.x, line.p2.x) && newNode.x < Math.min(line.p1.x, line.p2.x)) {
            //System.out.println("both points left from line");
            //System.out.println();
            return false;
        }

        if (node.y < Math.min(line.p1.y, line.p2.y) && newNode.y < Math.min(line.p1.y, line.p2.y)) {
            //System.out.println("both points over the line");
            //System.out.println();
            return false;
        }

        if (node.x > Math.max(line.p1.x, line.p2.x) && newNode.x > Math.max(line.p1.x, line.p2.x)) {
            //System.out.println("both points right from line");
            //System.out.println();
            return false;
        }

        if (node.y > Math.max(line.p1.y, line.p2.y) && newNode.y > Math.max(line.p1.y, line.p2.y)) {
            //System.out.println("both points under the line");
            //System.out.println();
            return false;
        }


        Node A = line.p1;
        Node B = line.p2;

        Node lineVector = new Node(B.x - A.x, B.y - A.y);
        Node ANodeVector = new Node(node.x - A.x, node.y - A.y);
        Node BNodeVector = new Node(B.x - node.x, B.y - node.y);
        Node ANewNodeVector = new Node(newNode.x - A.x, newNode.y - A.y);
        Node BNewNodeVector = new Node(B.x - newNode.x, B.y - newNode.y);

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
        double abs1 = A.dist(B);
        double abs2 = A.dist(node);

        a1 = Math.acos((lineVector.x * ANodeVector.x + lineVector.y * ANodeVector.y) / (A.dist(B) * A.dist(node)));
        a2 = Math.acos((lineVector.x * BNodeVector.x + lineVector.y * BNodeVector.y) / (B.dist(A) * B.dist(node)));
        b1 = Math.acos((lineVector.x * ANewNodeVector.x + lineVector.y * ANewNodeVector.y) / (A.dist(B) * A.dist(newNode)));
        b2 = Math.acos((lineVector.x * BNewNodeVector.x + lineVector.y * BNewNodeVector.y) / (B.dist(A) * B.dist(newNode)));

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
