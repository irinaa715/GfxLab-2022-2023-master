package xyz.marsavic.gfxlab.graphics3d.raytracers;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Hit;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;

public class RayTracingTest implements ColorFunctionT {

//    Ball ball = Ball.cr(Vec3.xyz(0,0,2),1, uv -> new Material(Color.hsb(uv.x()*6,0.8,uv.y())));
//    HalfSpace halfSpace = HalfSpace.pn(Vec3.xyz(0,-1,0),Vec3.xyz(0,1,0),uv -> new Material(Color.hsb(uv.x(),0.8,0.8)));

    Group group = Group.of(
            Ball.cr(Vec3.xyz(0,0,2),1, uv -> Material.matte(Color.hsb(uv.x()*6,0.8,uv.y()))),
            HalfSpace.pn(Vec3.xyz(0,-1,0),Vec3.xyz(0,1,0),uv -> Material.matte(Color.hsb(uv.x(),0.8,0.8)))
    );

    @Override
    public Color at(double t, Vector p) {


        Ray ray = Ray.pq(Vec3.ZERO, Vec3.zp(1,p));
//        sa predavanja III
//        Hit hit1 = ball.firstHit(ray);
//        Hit hit2 = halfSpace.firstHit(ray);
//
//        double tMin = Math.min(Hit.t(hit1),Hit.t(hit2)); // vrijeme prvog susreta
//
//
//        return Color.gray(1.0 / (1.0 + tMin));

//        Hit hit = ball.firstHit(ray);
//        Hit hit2 = halfSpace.firstHit(ray);
//
//        if(hit == null)hit = hit2;
//        if (hit2 != null && hit2.t() < hit.t()) hit = hit2;
//        if(hit == null) return Color.BLACK;
//        return hit.material().diffuse().div(1.0 + hit.t());

        Hit hit = group.firstHit(ray);
        if(hit == null)return Color.BLACK;
        return hit.material().diffuse().div(1.0 + hit.t());

    }
}
