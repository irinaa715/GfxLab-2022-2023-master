package xyz.marsavic.gfxlab.graphics3d.mesh;



public class Triangle {
    private final Vertex v0;
    private final Vertex v1;
    private final Vertex v2;

    public Triangle(Vertex v0, Vertex v1, Vertex v2) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertex v0() {
        return v0;
    }

    public Vertex v1() {
        return v1;
    }

    public Vertex v2() {
        return v2;
    }


}
