package xyz.marsavic.gfxlab.graphics3d.mesh;


import xyz.marsavic.gfxlab.Vec3;

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

    // Calculate barycentric coordinates for a point in this triangle
    public Vec3 getBarycentricCoordinates(Vec3 point) {
        Vec3 v0p = point.sub(v0.p());
        Vec3 v1p = point.sub(v1.p());
        Vec3 v2p = point.sub(v2.p());

        Vec3 v0v1 = v1.p().sub(v0.p());
        Vec3 v0v2 = v2.p().sub(v0.p());

        double dot00 = v0v1.dot(v0v1);
        double dot01 = v0v1.dot(v0v2);
        double dot11 = v0v2.dot(v0v2);
        double dot20 = v0p.dot(v0v1);
        double dot21 = v0p.dot(v0v2);

        double denom = dot00 * dot11 - dot01 * dot01;
        double u = (dot11 * dot20 - dot01 * dot21) / denom;
        double v = (dot00 * dot21 - dot01 * dot20) / denom;
        double w = 1.0 - u - v;

        return new Vec3(u, v, w);
    }

}
