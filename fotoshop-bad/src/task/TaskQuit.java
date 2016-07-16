package task;

import editor.Command;
import editor.Editor;
import internationalisation.Internationalisation;

/**
 * Implements command quit which stores the image into the cache.
 */
public class TaskQuit extends ATask implements ITask {
    public TaskQuit() {
    }

    public void apply(Command command) {
        if (command != null && command.hasArgs()) {
            Editor.getInstance().getUI().argsError(Internationalisation.getInstance().getResources().getString("quitWhat"));
        }
        else {
            // we want to quit
            Editor.getInstance().stop();
            Editor.getInstance().getUI().quit();
        }
    }
}