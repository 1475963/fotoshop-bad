package task;

import editor.Command;

public interface ITask {
    public void apply(Command command);
}