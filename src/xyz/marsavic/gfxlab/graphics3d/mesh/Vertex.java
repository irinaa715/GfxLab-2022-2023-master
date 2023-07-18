package xyz.marsavic.gfxlab.graphics3d.mesh;

import xyz.marsavic.gfxlab.Vec3;

public class Vertex {
    private final Vec3 p; // position
    private final Vec3 n; // normal

    public Vertex(Vec3 p, Vec3 n) {
        this.p = p;
        this.n = n;
    }

    public Vec3 p() {
        return p;
    }

    public Vec3 n() {
        return n;
    }
}