package task;

import editor.Editor;
import editor.Command;
import image.ImageCache;

/**
 * Implements command undo which removes the last filter applied using the cache rollback functionality.
 */
public class TaskUndo extends ATask implements ITask {
    public TaskUndo() {
    }

    public void apply(Command command) {
        ImageCache.getInstance().rollBack();
        Editor.getInstance().getUI().undo();
    }
}