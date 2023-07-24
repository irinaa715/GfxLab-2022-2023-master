package xyz.marsavic.gfxlab.graphics3d.mesh;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Vec3;

public class Vertex {
    private final Vec3 p; // position
    private final Vec3 n; // normal

    private final Vector uv;

    public Vertex(Vec3 p, Vec3 n, Vector uv) {
        this.p = p;
        this.n = n;
        this.uv = uv;
    }

    public Vec3 p() {
        return p;
    }

    public Vec3 n() {
        return n;
    }

    public Vector uv(){return uv;}
}