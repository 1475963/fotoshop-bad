package task;

import editor.Command;
import editor.Editor;
import image.ImageCache;
import internationalisation.Internationalisation;

/**
 * Implements command put which stores the image into the cache.
 */
public class TaskPut extends ATask implements ITask {
    public TaskPut() {
    }

    public void apply(Command command) {
        if (ImageCache.getInstance().getCurrent() == null) {
            Editor.getInstance().getUI().currentImageError(command);
            return ;
        }
        if (!this.checkArgs(command, Internationalisation.getInstance().getResources().getString("putWhat")))
            return ;
        ImageCache.getInstance().put(command.getCommandArgs().get(0), null);
        Editor.getInstance().getUI().put();
    }
}