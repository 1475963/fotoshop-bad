package image;

import java.awt.*;
import java.awt.image.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import editor.Editor;

/**
 * This class is an implementation an image which provides a filter feature.
 */
public class Image extends BufferedImage {
    private String          name;
    private List<String>    filters;

    /**
     * Create an image object and initialise the width, height and filters.
     * @param image BufferedImage object.
     * @param name String, Image name.
     */
    public Image(BufferedImage image, String name) {
        super(image.getWidth(), image.getHeight(), TYPE_INT_RGB);
        this.name = name;
        this.filters = new ArrayList<>();
        IntStream.range(0, image.getHeight())
                 .forEach(y -> IntStream.range(0, image.getWidth())
                                        .forEach(x -> this.setRGB(x, y, image.getRGB(x, y))));
    }
    
    /**
     * Create an image object and initialise the width, height and filters.
     * @param image Image object.
     */
    public Image(Image image) {
        super(image.getWidth(), image.getHeight(), TYPE_INT_RGB);
        this.name = image.getName();
        this.filters = new ArrayList<>();
        IntStream.range(0, image.sizeFilter())
                 .forEach(i -> this.filters.add(image.getFilter(i)));
        IntStream.range(0, image.getHeight())
                 .forEach(y -> IntStream.range(0, image.getWidth())
                                        .forEach(x -> this.setRGB(x, y, image.getRGB(x, y))));
    }

    /**
     * Create an image object and initialise the width, height and filters.
     * @param width Image width.
     * @param height Image height.
     * @param image Image object.
     */
    public Image(int width, int height, Image image) {
        super(width, height, TYPE_INT_RGB);
    	this.name = image.getName();
    	this.filters = new ArrayList<>();
        IntStream.range(0, image.sizeFilter())
                 .forEach(i -> this.filters.add(image.getFilter(i)));
    }

    /**
     * Load an image from an image name (path is the project root path).
     * @param name String, image name.
     * @return Image object.
     */
    public static Image loadImage(String name) {
    	BufferedImage image = null;
        try {
            image = ImageIO.read(new File(name));
        }
        catch (IOException e) {
            Editor.getInstance().getUI().imageException(name);
            return null;
        }
        return (new Image(image, name));
    }

    /**
     * Return image name.
     * @return image name.
     */
    public String getName() {
        return (this.name);
    }
    
    /**
     * Add a new filter in the list.
     * @param filter Filter string.
     */
    public void addFilter(String filter) {
        this.filters.add(filter);
    }

    /**
     * Return a list of filters.
     * @param index Integer index.
     * @return Filter string.
     */
    public String getFilter(int index) {
        return (this.filters.get(index));
    }

    /**
     * Returns the number of filters.
     * @return Size of the filter list.
     */
    public int sizeFilter() {
        return (this.filters.size());
    }

    /**
     * Set a given pixel of this image to a specified color.
     * The color is represented as an (r,g,b) value.
     * @param x The x position of the pixel
     * @param y The y position of the pixel
     * @param col The color of the pixel
     */
    public void setPixel(int x, int y, Color col) {
        int pixel = col.getRGB();
        setRGB(x, y, pixel);
    }

    /**
     * Get the color value at a specified pixel position
     * @param x The x position of the pixel
     * @param y The y position of the pixel
     * @return The color of the pixel at the given position
     */
    public Color getPixel(int x, int y) {
        int pixel = getRGB(x, y);
        return new Color(pixel);
    }
}