package Adventure.CityMap;

import Adventure.triangulation.Edge2D;
import Adventure.triangulation.Triangle2D;
import Adventure.triangulation.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Calc {
    public static Optional<Vector2D> junctionOf(Edge2D edge, Vector2D dirVector, Vector2D startVector) {
        Optional<Vector2D> result = Optional.empty();
        Vector2D startToA = new Vector2D(edge.a.x - startVector.x, edge.a.y - startVector.y);
        Vector2D startToB = new Vector2D(edge.b.x - startVector.x, edge.b.y - startVector.y);
        Vector2D startWithDir = new Vector2D(dirVector.x + startVector.x, dirVector.y + startVector.y);
        double degAB = startToA.getDegr(startToB);
        double degSA = startToA.getDegr(dirVector);
        double degSB = startToB.getDegr(dirVector);
        int ddegAB = (int) Math.toDegrees(degAB);
        int ddegSA = (int) Math.toDegrees(degSA);
        int ddegSB = (int) Math.toDegrees(degSB);
        // has a junction?
        if ((degAB > degSA && degAB > degSB) && !edge.contains(startVector)) {
            Vector2D AB = new Vector2D(edge.b.x - edge.a.x, edge.b.y - edge.a.y);

            double degSAAB = AB.getDegr(startToA.mult(-1));
            double degAGS = Math.PI - degSAAB - degSA;

            double lengthOfDevider = startToA.mag() * Math.sin(degSAAB) / Math.sin(degAGS);

            Vector2D unitVector = dirVector.mult(lengthOfDevider / dirVector.mag());

            result = Optional.of(startVector.add(unitVector));
        }

        return result;
    }

    public static boolean hasJunction(Edge2D edge, Vector2D directionalVector, Vector2D startVector) {
        Vector2D startToA = new Vector2D(edge.a.x - startVector.x, edge.a.y - startVector.y);
        Vector2D startToB = new Vector2D(edge.b.x - startVector.x, edge.b.y - startVector.y);
        Vector2D startWithDir = new Vector2D(directionalVector.x + startVector.x, directionalVector.y + startVector.y);
        double degAB = startToA.getDegr(startToB);
        double degSA = startToB.getDegr(startWithDir);
        double degSB = startToA.getDegr(startWithDir);
        if (degAB < degSA && degAB < degSB) {
            return true;
        } else {
            return false;
        }
    }

    public static Vector2D circumCircle(Triangle2D triangle2D) {
        Vector2D a = triangle2D.a;
        Vector2D b = triangle2D.b;
        Vector2D c = triangle2D.c;

        double d = 2 * (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));
        double x = ((sqr(a.x) + sqr(a.y)) * (b.y - c.y) + (sqr(b.x) + sqr(b.y)) * (c.y - a.y) + (sqr(c.x) + sqr(c.y)) * (a.y - b.y)) / d;
        double y = ((sqr(a.x) + sqr(a.y)) * (c.x - b.x) + (sqr(b.x) + sqr(b.y)) * (a.x - c.x) + (sqr(c.x) + sqr(c.y)) * (b.x - a.x)) / d;
        //System.out.println((int) x + ", " + (int) y);
        return new Vector2D(x, y);
    }

    public static ArrayList<Vector2D> getVoronoiDiagram(Vector2D middlePoint, ArrayList<Triangle2D> triangles) {
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

    public static ArrayList<Triangle2D> findConnectedTriangles(Vector2D middlePoint, ArrayList<Triangle2D> triangles) {
        ArrayList<Triangle2D> conTriangles = new ArrayList<>();

        for (Triangle2D triangle2D : triangles)
            if (triangle2D.hasVertex(middlePoint))
                conTriangles.add(triangle2D);

        return conTriangles;
    }

    public static ArrayList<Triangle2D> findConnectedTriangles(Vector2D middlePoint, List<Triangle2D> triangles) {
        ArrayList<Triangle2D> triangle2DS = new ArrayList<>(triangles);
        return findConnectedTriangles(middlePoint, triangle2DS);
    }

    public static Polygon2D shrink(Polygon2D polygon, double px) {
        // TODO: shrinking could use some improvement to avoid generating antiparallel roads.
        Polygon2D pol = new Polygon2D();
        ArrayList<Vector2D> points = polygon.getPoints();
        int maxEdges = polygon.getEdges().size();
        int maxEdgesuntouched = polygon.getEdges().size();
        for (int i = 0; i < maxEdges; i++) {
            Edge2D edge = polygon.getEdges().get(i);
            if (edge.length() <= 2 * px) {
                polygon.getEdges().remove(i);
                maxEdges--;
                i--;

                Vector2D newPoint = edge.b.sub(edge.a).mult(edge.length() / (2 * edge.b.sub(edge.a).mag())).add(edge.a);
                int aIndex = polygon.getPoints().indexOf(edge.a);
                polygon.getPoints().add(aIndex, newPoint);
                polygon.getPoints().remove(edge.a);
                polygon.getPoints().remove(edge.b);
                for (int j = 0; j < maxEdges; j++) {
                    Edge2D searchEdge = polygon.getEdges().get(j);
                    if (edge.contains(searchEdge.a))
                        searchEdge.a = newPoint;
                    else if (edge.contains(searchEdge.b))
                        searchEdge.b = newPoint;
                    System.out.println();
                }
            }
        }

        if (points.size() > 2) {
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
            pol.calculateEdges();
            pol.calculateArea();
            return pol;
        }
        pol.calculateEdges();
        pol.calculateArea();
        return polygon;
    }
    public static Polygon2D shrinkTowards(Polygon2D polygon, float percentage) {

        Polygon2D pol = new Polygon2D();
        Vector2D mp = polygon.calculateMiddlePoint();
        ArrayList<Vector2D> points = polygon.getPoints();


        for (Vector2D p : points) {
            Vector2D v1 = new Vector2D(mp.x - p.x, mp.y - p.y);
            v1 = v1.mult(percentage);

            pol.getPoints().add(v1.add(p));
        }
        pol.calculateMiddlePoint();
        pol.calculateArea();
        pol.calculateEdges();
        return pol;
    }

    public static Double getDegr(Vector2D v1, Vector2D v2) {
        return Math.acos(v1.dot(v2) / (v1.mag() * v2.mag()));
    }

    public static Vector2D turnVector(Vector2D vector, double radians) {
        return new Vector2D(vector.x * Math.cos(radians) - vector.y * Math.sin(radians), vector.x * Math.sin(radians) + vector.y * Math.cos(radians));
    }

    public static Boolean blocked(Vector2D node, Edge2D edge2D, Vector2D newNode) {

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

    public static Double sqr(Double a) {
        return Math.pow(a, 2);
    }

    public static Vector2D calculateMiddlePoint(ArrayList<Vector2D> points){
        double sumX = 0;
        double sumY = 0;
        for (Vector2D p: points){
            sumX += p.x;
            sumY += p.y;
        }
        if (points.size()>0){
            return new Vector2D(sumX/points.size(), sumY/points.size());
        }
        return null;
    }
}
