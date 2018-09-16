package Adventure.triangulation;

/**
 * 2D edge class implementation.
 * 
 * @author Johannes Diemke
 */
public class Edge2D {

    public Vector2D a;
    public Vector2D b;

    /**
     * Constructor of the 2D edge class used to create a new edge instance from
     * two 2D vectors describing the edge's vertices.
     * 
     * @param a
     *            The first vertex of the edge
     * @param b
     *            The second vertex of the edge
     */
    public Edge2D(Vector2D a, Vector2D b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "[" + a + "," + b + "]";
    }

    public boolean equals(Edge2D other) {
        return this.a.equals(other.a) && this.b.equals(other.b) || this.a.equals(other.b) && this.b.equals(other.a);
    }
}