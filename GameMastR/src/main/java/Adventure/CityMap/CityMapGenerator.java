package Adventure.CityMap;

import Adventure.AGUI.AdventureController;
import Adventure.triangulation.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CityMapGenerator {

    private GraphicsContext gc;
    private int height;
    private int width;
    private double diagonal;
    private ArrayList<Polygon2D> polygons = new ArrayList<>();
    private ArrayList<Polygon2D> shrinkedPolygons = new ArrayList<>();
    private Vector<Vector2D> pointSet = new Vector<>();
    private Vector2D middlePoint;

    private int count;
    private final int radius = 5;
    private final double shrinkage = 5;
    private final double size = 120;
    private final double edgeWidth = 1;
    private final int subdevisionEdgeSize = 40;

    private boolean drawStartPoints = AdventureController.drawStartPointsPreset;
    private boolean drawPolyPoints = AdventureController.drawPolyPointsPreset;
    private boolean drawPolyEdges = AdventureController.drawPolyEdgesPreset;
    private boolean drawFillPoly = AdventureController.drawFillPolyPreset;
    private boolean drawFillSubPoly = AdventureController.drawFillSubPoly;
    private boolean drawSubPolyEdges = AdventureController.drawSubPolyEdges;
    private boolean drawSubPolyPoints = AdventureController.drawSubPolyPoints;

    private boolean fillPolyUnicolor = AdventureController.fillPolyUnicolorPreset;
    private boolean drawAll = AdventureController.drawAll;

    private final Paint polyEdgeColor = Color.color(0, 0, 0);
    private final Paint polyPointColor = Color.color(1, 0, 0);
    private final Paint startPointColor = Color.color(0.3, 0.5, 0.3);
    private final Paint subdevisionPointColor = Color.color(0.8, 0.5, 0.3);
    private final Paint subdevisionEdgeColor = polyEdgeColor;
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

            if (false) {
                Polygon2D debugPoly = new Polygon2D();
                debugPoly.getPoints().add(new Vector2D(100, 100));
                debugPoly.getPoints().add(new Vector2D(300, 100));
                debugPoly.getPoints().add(new Vector2D(303, 103));
                debugPoly.getPoints().add(new Vector2D(305, 105));
                debugPoly.getPoints().add(new Vector2D(305, 109));
                debugPoly.getPoints().add(new Vector2D(200, 180));
                debugPoly.getPoints().add(new Vector2D(100, 180));
                //debugPoly.calculateArea();
                //debugPoly.calculateEdges();
                //debugPoly.subDivide(subdevisionEdgeSize);
                shrinkedPolygons.add(debugPoly);
                shrinkedPolygons.add(Calc.shrink(debugPoly, shrinkage));
                System.out.println();
            } else {
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
                    poly.setPoints(Calc.getVoronoiDiagram(p, Calc.findConnectedTriangles(p, triangleSoup)));
                    poly.calculateEdges();
                    poly.calculateArea();
                    polygons.add(poly);
                    Polygon2D shrinkedPoly = Calc.shrink(poly, shrinkage);
                    shrinkedPoly.subDivide(shrinkage);
                    shrinkedPolygons.add(shrinkedPoly);
                }
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
                        if (polygon.getMiddlePoint()!=null) {
                            gc.setFill(subdevisionPointColor);
                            gc.fillOval(polygon.getMiddlePoint().x - radius / 2f, polygon.getMiddlePoint().y - radius / 2f, radius, radius);
                        }
                    }
                    if ((v.sub(middlePoint).mag() < size || drawAll) && drawOnce && drawFillPoly) {
                        gc.setFill(polyFillColor);
                        fillPolygon(polygon, gc);
                        gc.setFill(Color.BLACK);
                        gc.fillText(String.valueOf((int)polygon.getArea()), polygon.getMiddlePoint().x, polygon.getMiddlePoint().y);
                        if (drawPolyEdges) {
                            for (Edge2D edge2D : polygon.getEdges()) {
                                gc.setStroke(polyEdgeColor);
                                gc.setLineWidth(edgeWidth);
                                gc.strokeLine(edge2D.a.x, edge2D.a.y, edge2D.b.x, edge2D.b.y);
                            }
                        }
                        if (drawSubPolyPoints) {
                            for (Vector2D subVector : polygon.getSubPoints()) {
                                gc.setFill(subdevisionPointColor);
                                gc.fillOval(subVector.x - radius / 2f, subVector.y - radius / 2f, radius, radius);
                            }
                        }
                        if (drawSubPolyEdges) {
                            for (Edge2D subEdge : polygon.getSubEdges()) {
                                gc.setStroke(subdevisionEdgeColor);
                                gc.setLineWidth(edgeWidth);
                                gc.strokeLine(subEdge.a.x, subEdge.a.y, subEdge.b.x, subEdge.b.y);
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
        middlePoint = new Vector2D(width / 2d, height / 2d);
        /*
        drawStartPoints = AdventureController.drawStartPointsPreset;
        drawPolyPoints = AdventureController.drawPolyPointsPreset;
        drawPolyEdges = AdventureController.drawPolyEdgesPreset;
        drawFillPoly = AdventureController.drawFillPolyPreset;
        */
    }

    public void clear() {
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
            gc.setFill(polygon.getColor());
        else
            gc.setFill(polyFillColor);

        gc.fillPolygon(x, y, points.size());
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
