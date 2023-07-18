package xyz.marsavic.gfxlab.graphics3d.cameras;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Camera;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.utils.Numeric;

public class ProjectiveCamera implements Camera {
    private final double k;
    public ProjectiveCamera(double k){
        this.k = k;
    }
    public static ProjectiveCamera fov(double phi){ //field of vision
        return new ProjectiveCamera(Numeric.tanT(phi/2));
    }
    @Override
    public Ray exitingRay(Vector sensorPosition) {
        return Ray.pd(Vec3.ZERO, Vec3.zp(1/k,sensorPosition));
    }
}
