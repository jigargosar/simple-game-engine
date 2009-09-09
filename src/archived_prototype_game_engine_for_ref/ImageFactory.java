package archived_prototype_game_engine_for_ref;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ImageFactory {
    public static final ImageFactory imageFactory = new ImageFactory();
    public HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

    private ImageFactory() {
    }

    private BufferedImage loadImage(String relativePath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(relativePath);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage makeColorTransparent(BufferedImage im) {

        final int transparentColor = im.getRGB(im.getWidth() - 1, im.getHeight() - 1);
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = transparentColor | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        Image imageIn = Toolkit.getDefaultToolkit().createImage(ip);
        BufferedImage bufferedImageOut = new BufferedImage(imageIn.getWidth(null), imageIn.getHeight(null), TYPE_INT_ARGB);
        Graphics2D g = bufferedImageOut.createGraphics();
        g.drawImage(imageIn, 0, 0, null);
        g.dispose();
        return bufferedImageOut;
    }

    public BufferedImage load(String relativePath) {
        BufferedImage image = images.get(relativePath);
        if (image == null) {
            image = makeColorTransparent(loadImage(relativePath));
            images.put(relativePath, image);
        }
        return image;
    }

    public Sprite createSprite(String relativePath) {
        return new Sprite(load(relativePath));
    }
}
