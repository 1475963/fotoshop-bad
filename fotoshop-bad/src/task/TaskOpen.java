package task;

import editor.Command;
import editor.Editor;
import image.Image;
import image.ImageCache;
import internationalisation.Internationalisation;

/**
 * Implements command open which open the image and
 * create an image object and store it.
 */
public class TaskOpen extends ATask implements ITask {
    public TaskOpen() {
    }

    public void apply(Command command) {
        if (!this.checkArgs(command, Internationalisation.getInstance().getResources().getString("openWhat")))
                return ;
        
        String inputName = command.getCommandArgs().get(0);
        Image img = Image.loadImage(inputName);
        if (img == null) {
            Editor.getInstance().getUI().currentImageError(command);
        }
        else {
            ImageCache.getInstance().put(img.getName(), img);
            Image currentImage = ImageCache.getInstance().get(img.getName());
            Editor.getInstance().getUI().open(currentImage.getName());
        }
    }
}