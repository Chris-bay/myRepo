package Adventure.CityMap;

import Adventure.triangulation.DelaunayTriangulator;
import Adventure.triangulation.NotEnoughPointsException;
import Adventure.triangulation.Triangle2D;
import Adventure.triangulation.Vector2D;
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

        try {
            Vector<Vector2D> pointSet = new Vector<>();
            for (int i = 0; i < count; i++) {
                pointSet.add(new Vector2D(Math.random() * width, Math.random() * height));
            }
            DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator(pointSet);
            delaunayTriangulator.triangulate();

            List<Triangle2D> triangleSoup = delaunayTriangulator.getTriangles();
            for (Triangle2D triangle2D : triangleSoup) {
                gc.strokeLine(triangle2D.a.x, triangle2D.a.y, triangle2D.b.x, triangle2D.b.y);
                gc.strokeLine(triangle2D.b.x, triangle2D.b.y, triangle2D.c.x, triangle2D.c.y);
                gc.strokeLine(triangle2D.c.x, triangle2D.c.y, triangle2D.a.x, triangle2D.a.y);
            }
        } catch (NotEnoughPointsException e) {
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

}
