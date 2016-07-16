package task;

import editor.Command;
import editor.Editor;

/**
 * Implements command help which displays a help message and executes look command on current image.
 */
public class TaskHelp extends ATask implements ITask {
    public TaskHelp() {
    }

    public void apply(Command command) {
        Editor.getInstance().getUI().help();
        Editor.getInstance().getUI().look(command);
    }
}