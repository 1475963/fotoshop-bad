package task;

import java.awt.Color;
import editor.Command;
import editor.Editor;
import image.Image;
import image.ImageCache;
import internationalisation.Internationalisation;
import java.util.stream.IntStream;

/**
 * Implements command mono which remove the colors of the image and set
 * it to black and white.
 */
public class TaskMono extends ATask implements ITask {
    public TaskMono() {
    }

    public void apply(Command command) {
        if (ImageCache.getInstance().getCurrent() == null) {
            Editor.getInstance().getUI().currentImageError(command);
            return ;
        }
        Image tmpImage = new Image(ImageCache.getInstance().getCurrent());
        IntStream.range(0, tmpImage.getHeight())
                 .forEach(y -> IntStream.range(0, tmpImage.getWidth())
                                        .forEach(x -> this.processPixel(tmpImage, x, y)));
        tmpImage.addFilter(Internationalisation.getInstance().getResources().getString("mono"));
        ImageCache.getInstance().put(ImageCache.getInstance().getIndex(), tmpImage);
        Editor.getInstance().getUI().mono();
    }
    
    private void processPixel(Image image, int x, int y) {
        Color pix = image.getPixel(x, y);
        int lum = (int) Math.round(0.299 * pix.getRed()
                                   + 0.587 * pix.getGreen()
                                   + 0.114 * pix.getBlue());
        image.setPixel(x, y, new Color(lum, lum, lum));
    }
}