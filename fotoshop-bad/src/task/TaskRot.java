package task;

import java.awt.Color;
import editor.Command;
import editor.Editor;
import image.Image;
import image.ImageCache;
import internationalisation.Internationalisation;
import java.util.stream.IntStream;

/**
 * Implements command rot90 which rotates the image and store a version in the cache.
 */
public class TaskRot extends ATask implements ITask {
    public TaskRot() {
    }

    public void apply(Command command) {
        // R90 = [0 -1, 1 0] rotates around origin
        // (x,y) -> (-y,x)
        // then transate -> (height-y, x)
        if (ImageCache.getInstance().getCurrent() == null) {
            Editor.getInstance().getUI().currentImageError(command);
            return ;
        }
        Image currentImage = ImageCache.getInstance().getCurrent();
        Image rotImage = new Image(currentImage.getHeight(), currentImage.getWidth(), currentImage);
        IntStream.range(0, currentImage.getHeight())
                 .forEach(y -> IntStream.range(0, currentImage.getWidth())
                                        .forEach(x -> this.processPixel(currentImage, rotImage, x, y)));
        rotImage.addFilter(Internationalisation.getInstance().getResources().getString("rot90"));
        ImageCache.getInstance().put(ImageCache.getInstance().getIndex(), rotImage);
        Editor.getInstance().getUI().rot();
    }
    
    private void processPixel(Image currentImage, Image rotImage, int x, int y) {
        Color pix = currentImage.getPixel(x, y);
        rotImage.setPixel(currentImage.getHeight() - y - 1, x, pix);
    }
}