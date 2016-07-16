package task;

import editor.Command;
import editor.Editor;

abstract class ATask {
    protected boolean checkArgs(Command command, String msg) {
        if (command != null && !command.hasArgs()) {
            Editor.getInstance().getUI().argsError(msg);
            return false;
        }
        return true;
    }
}