package xyz.marsavic.gfxlab.graphics3d.textures;

import xyz.marsavic.functions.interfaces.F1;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.scene.MeshTest;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class ImageTexture implements F1<Material, Vector>  {

    Color[][] colors;

    public ImageTexture(String fileName){
        this.colors = loadTexture(fileName);
    }

    @Override
    public Material at(Vector uv) {

        double x = Math.abs(uv.x() % 1);
        double y = Math.abs(uv.y() % 1);
        int u = (int) (y * colors.length);
        int v = (int) (x * colors[0].length);
        return Material.matte(colors[u][v]);
    }

    private Color[][] loadTexture(String fn) {
        BufferedImage img = null;
        try {
            InputStream is = MeshTest.class.getResourceAsStream(fn);
            img = ImageIO.read(is);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

        int w = img.getWidth(), h = img.getHeight();

        Color[][] pixels = new Color[h][w];

        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                pixels[y][x] = Color.code(img.getRGB(x,y));

        return pixels;
    }

}
