package xyz.marsavic.gfxlab.graphics3d.scene;

import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.*;
import xyz.marsavic.gfxlab.graphics3d.mesh.Mesh;
import xyz.marsavic.gfxlab.graphics3d.mesh.MeshLoader;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;
import xyz.marsavic.gfxlab.graphics3d.textures.Grid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MeshTest extends Scene.Base {
    public MeshTest(){


        var materialUVWalls  = Grid.standard(Color.WHITE);
        var materialUVWallsL = Grid.standard(Color.hsb(0.00, 0.5, 1.0));
        var materialUVWallsR = Grid.standard(Color.hsb(0.33, 0.5, 1.0));

        Collection<Solid> solids = new ArrayList<>();
        Collections.addAll(solids,

                HalfSpace.pn(Vec3.xyz(-1,  0,  0), Vec3.xyz( 1,  0,  0), materialUVWallsL),
                HalfSpace.pn(Vec3.xyz( 1,  0,  0), Vec3.xyz(-1,  0,  0), materialUVWallsR),
                HalfSpace.pn(Vec3.xyz( 0, -1,  0), Vec3.xyz( 0,  1,  0), materialUVWalls),
                HalfSpace.pn(Vec3.xyz( 0,  1,  0), Vec3.xyz( 0, -1,  0), materialUVWalls),
                HalfSpace.pn(Vec3.xyz( 0,  0,  1), Vec3.xyz( 0,  0, -1), materialUVWalls)



        );

        String objFilePath = "/xyz/marsavic/gfxlab/graphics3d/objects/pyramid.obj";
        Mesh mesh = MeshLoader.loadMeshFromOBJ(objFilePath, Material.MIRROR);
        solids.add(mesh.transformed(
                Affine.IDENTITY
                        .then(Affine.rotationAboutY(0.3))
                )
        );

//        String objFilePath = "/xyz/marsavic/gfxlab/graphics3d/objects/beetle.obj";
//        Mesh mesh = MeshLoader.loadMeshFromOBJ(objFilePath,Material.matte(Color.hsb(0.15, 0.9, 0.9)).specular(Color.WHITE).shininess(64));
//        solids.add(mesh
//                        .transformed(Affine.IDENTITY
//                                .then(Affine.scaling(2))
//                                   .then(Affine.translation(Vec3.xyz(0,-1,0)))
//                .then(Affine.rotationAboutY(0.5)))
//
//        );

//        String objFilePath = "/xyz/marsavic/gfxlab/graphics3d/objects/spot.obj";
//        Mesh mesh = MeshLoader.loadMeshFromOBJ(objFilePath, Material.matte(Color.hsb(0.85, 0.9, 0.9)).specular(Color.WHITE).shininess(64));
//        solids.add(mesh
//                .transformed(Affine.IDENTITY
//                        .then(Affine.scaling(0.5))
//                        .then(Affine.translation(Vec3.xyz(0,-0.5,0))
//                        .then(Affine.rotationAboutY(0.1))))
//
//        );


        Collections.addAll(lights,
                Light.pc(Vec3.xyz(-0.7, 0.7, -0.7), Color.WHITE),
                Light.pc(Vec3.xyz(-0.7, 0.7,  0.7), Color.WHITE),
                Light.pc(Vec3.xyz( 0.7, 0.7, -0.7), Color.WHITE),
                Light.pc(Vec3.xyz( 0.7, 0.7,  0.7), Color.WHITE)
        );

        solid = Group.of(solids);
    }
}
