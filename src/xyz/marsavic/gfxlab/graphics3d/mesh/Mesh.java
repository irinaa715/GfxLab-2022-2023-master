package xyz.marsavic.gfxlab.graphics3d.mesh;

import xyz.marsavic.functions.interfaces.F1;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Hit;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.gfxlab.graphics3d.Solid;
import java.util.ArrayList;
import java.util.List;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.geometry.Vector;


public class Mesh implements Solid {
    private List<Triangle> triangles;
    private  F1<Material, Vector> mapMaterial;

    public Mesh() {
        triangles = new ArrayList<>();
    }

    public Mesh(F1<Material, Vector> mapMaterial){
        triangles = new ArrayList<>();
        this.mapMaterial = mapMaterial;
    }



    public void addTriangle(Triangle triangle) {
        triangles.add(triangle);
    }

    @Override
    public Hit firstHit(Ray ray, double afterTime) {

        double tMin = Double.POSITIVE_INFINITY;
        Triangle triangleMin = null;


        for (Triangle triangle : triangles) {
            double t = rayTriangleIntersection(ray,triangle);
            if (t > afterTime && t < tMin) {
                tMin = t;
                triangleMin = triangle;
            }
        }

        if (tMin < Double.POSITIVE_INFINITY) {

            final Vec3 finalNormal = calculateTriangleNormalN(triangleMin);
            return new Hit.RayT(ray, tMin) {
                @Override
                public Vec3 n() {
                    return finalNormal;
                }

                @Override
                public Material material() {
                    return Mesh.this.mapMaterial.at(uv());
                }

                @Override
                public Vector uv() {
                    return Vector.ZERO;
                }
            };
        }

        return Hit.AtInfinity.axisAligned(ray.d(), false);
    }

    private double rayTriangleIntersection(Ray ray, Triangle triangle) {
        Vertex v0 = triangle.v0();
        Vertex v1 = triangle.v1();
        Vertex v2 = triangle.v2();

        Vec3 e1 = v1.p().sub(v0.p());
        Vec3 e2 = v2.p().sub(v0.p());

        Vec3 p = ray.d().cross(e2);
        double det = e1.dot(p);

        if(det == 0) return Double.POSITIVE_INFINITY; // parallel ray

        double inv_det = 1.0 / det;
        Vec3 t = ray.p().sub(v0.p());
        double u = inv_det * t.dot(p);


        if (u < 0.0 || u > 1.0) {
            return Double.POSITIVE_INFINITY;
        }

        Vec3 q = t.cross(e1);
        double v = inv_det * ray.d().dot(q);


        if (v < 0.0 || u + v > 1.0) {
            return Double.POSITIVE_INFINITY;
        }

        return  inv_det * e2.dot(q);


    }
    private Vec3 calculateTriangleNormalN(Triangle triangle) {
        Vertex v0 = triangle.v0();
        Vertex v1 = triangle.v1();
        Vertex v2 = triangle.v2();

        Vec3 v0v1 = v1.p().sub(v0.p());
        Vec3 v0v2 = v2.p().sub(v0.p() );


        Vec3 normal = v0v1.cross(v0v2);


        return normal.normalized_();

    }

}