package task;

import editor.Editor;
import editor.Command;

/**
 * Implements command look which prints the current image name and filters.
 */
public class TaskLook extends ATask implements ITask {
    public TaskLook() {
    }

    public void apply(Command command) {
        Editor.getInstance().getUI().look(command);
    }
}