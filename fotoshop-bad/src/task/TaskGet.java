package task;

import editor.Command;
import editor.Editor;
import image.ImageCache;
import internationalisation.Internationalisation;

/**
 * Implements command get which changes a current image in the cache.
 */
public class TaskGet extends ATask implements ITask {
    public TaskGet() {
    }

    public void apply(Command command) {
        if (!this.checkArgs(command, Internationalisation.getInstance().getResources().getString("getWhat")))
            return ;
        ImageCache.getInstance().get(command.getCommandArgs().get(0));
        Editor.getInstance().getUI().get();
    }
}