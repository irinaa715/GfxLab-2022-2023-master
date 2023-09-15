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



    public Mesh(F1<Material, Vector> mapMaterial){
        triangles = new ArrayList<>();
        this.mapMaterial = mapMaterial;
    }

    public void addTriangle(Triangle triangle) {
        triangles.add(triangle);
    }

    @Override
    public Hit firstHit(Ray ray, double afterTime) {

        Triangle triangleMin = null;
        BarycentricAndTime bctMin = new BarycentricAndTime(0,0,Double.POSITIVE_INFINITY);


        for (Triangle triangle : triangles) {

            BarycentricAndTime bct = rayTriangleIntersection(ray,triangle);
            if (bct.t() > afterTime && bct.t() < bctMin.t()) {
                bctMin = bct;
                triangleMin = triangle;
            }
        }

        if (bctMin.t() < Double.POSITIVE_INFINITY) return new HitTriangle(ray, bctMin.t(), triangleMin, bctMin.u(), bctMin.v());

        return Hit.AtInfinity.axisAligned(ray.d(), false);
    }

    record BarycentricAndTime(double u, double v, double t){

    }
    private BarycentricAndTime rayTriangleIntersection(Ray ray, Triangle triangle) {
        Vertex v0 = triangle.v0();
        Vertex v1 = triangle.v1();
        Vertex v2 = triangle.v2();

        Vec3 e1 = v1.p().sub(v0.p());
        Vec3 e2 = v2.p().sub(v0.p());

        Vec3 p = ray.d().cross(e2);
        double det = e1.dot(p);

        if(Math.abs(det) <= 1e-6)  return new BarycentricAndTime(0, 0, Double.POSITIVE_INFINITY);

        double inv_det = 1.0 / det;
        Vec3 t = ray.p().sub(v0.p());
        double u = inv_det * t.dot(p);


        if (u < 0.0 || u > 1.0)  return new BarycentricAndTime(0, 0, Double.POSITIVE_INFINITY);


        Vec3 q = t.cross(e1);
        double v = inv_det * ray.d().dot(q);


        if (v < 0.0 || u + v > 1.0)  return new BarycentricAndTime(0, 0, Double.POSITIVE_INFINITY);

        return new BarycentricAndTime(u,v,inv_det * e2.dot(q));

    }


    class HitTriangle extends Hit.RayT{
        Triangle triangle;
        double barycentricU;
        double barycentricV;
        protected HitTriangle(Ray ray, double t,Triangle minTriangle, double bcu, double bcv){
            super(ray,t);
            this.triangle = minTriangle;
            this.barycentricU = bcu;
            this.barycentricV = bcv;
        }

        @Override
        public Vec3 n() {

            Vec3 n0_ = triangle.v0().n().normalized_();
            Vec3 n1_ = triangle.v1().n().normalized_();
            Vec3 n2_ = triangle.v2().n().normalized_();

            double barycentricW = 1.0 - barycentricU - barycentricV;

            Vec3 interpolatedNormal = n0_.mul(barycentricW)
                    .add(n1_.mul(barycentricU))
                    .add(n2_.mul(barycentricV));

            return interpolatedNormal;
//            Vertex v0 = triangle.v0();
//            Vertex v1 = triangle.v1();
//            Vertex v2 = triangle.v2();
//
//            Vec3 v0v1 = v1.p().sub(v0.p());
//            Vec3 v0v2 = v2.p().sub(v0.p() );
//
//
//            Vec3 normal = v0v1.cross(v0v2);
//
//
//            return normal.normalized_();
        }

        @Override
        public Material material() {
            return Mesh.this.mapMaterial.at(uv());
        }

        @Override
        public Vector uv() {
            if(triangle.v0().uv() == null)return Vector.ZERO;

            double barycentricW = 1.0 - barycentricU - barycentricV;

            Vector uv0 = triangle.v0().uv();
            Vector uv1 = triangle.v1().uv();
            Vector uv2 = triangle.v2().uv();

            double u = barycentricW * uv0.x() + barycentricU * uv1.x() + barycentricV * uv2.x();
            double v = barycentricW * uv0.y() + barycentricU * uv1.y() + barycentricV * uv2.y();

            return new Vector(u, v);

        }


    }

}