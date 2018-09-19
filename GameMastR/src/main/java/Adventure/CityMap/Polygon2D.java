package Adventure.CityMap;

import Adventure.triangulation.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Polygon2D {
    public ArrayList<Vector2D> points = new ArrayList<>();
    public ArrayList<Polygon2D> subPolygons = new ArrayList<>();
    public Paint color = Color.gray(Math.random());

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

        return this.subPolygons;
    }

}
