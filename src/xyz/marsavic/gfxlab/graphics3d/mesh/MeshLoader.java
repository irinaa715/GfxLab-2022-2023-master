package xyz.marsavic.gfxlab.graphics3d.mesh;

import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.elements.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MeshLoader {

    public static Mesh loadMeshFromOBJ(String objFilePath) {
        Mesh mesh = new Mesh();

        List<Vec3> vertices = new ArrayList<>();
        List<Vec3> normals = new ArrayList<>();

        InputStream inputStream = MeshLoader.class.getResourceAsStream(objFilePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    // vertices
                    String[] parts = line.split("\\s+");
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    vertices.add(new Vec3(x, y, z));
                } else if (line.startsWith("vn ")) {
                    // normals
                    String[] parts = line.split("\\s+");
                    double nx = Double.parseDouble(parts[1]);
                    double ny = Double.parseDouble(parts[2]);
                    double nz = Double.parseDouble(parts[3]);
                    normals.add(new Vec3(nx, ny, nz));
                } else if (line.startsWith("f ")) {
                    // faces(triangles)
                    String[] parts = line.split("\\s+");
                    int[] vertexIndices = new int[3];
                    int[] normalIndices = new int[3];
                    for (int i = 0; i < 3; i++) {
                        String[] faceData = parts[i + 1].split("/");
                        vertexIndices[i] = Integer.parseInt(faceData[0]) - 1;
                        normalIndices[i] = faceData.length >= 3 ? Integer.parseInt(faceData[2]) - 1 : -1;

                    }

                    Triangle triangle;
                    if (normals.isEmpty()) {
                        Vec3 v0 = vertices.get(vertexIndices[0]);
                        Vec3 v1 = vertices.get(vertexIndices[1]);
                        Vec3 v2 = vertices.get(vertexIndices[2]);
                        Vec3 normal = v1.sub(v0).cross(v2.sub(v0));
                        triangle = new Triangle(
                                new Vertex(v0, normal),
                                new Vertex(v1, normal),
                                new Vertex(v2, normal)
                        );
                    } else {

                        triangle = new Triangle(
                                new Vertex(vertices.get(vertexIndices[0]), normals.get(normalIndices[0])),
                                new Vertex(vertices.get(vertexIndices[1]), normals.get(normalIndices[1])),
                                new Vertex(vertices.get(vertexIndices[2]), normals.get(normalIndices[2]))
                        );
                    }
                    mesh.addTriangle(triangle);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("done");
        return mesh;
    }



}