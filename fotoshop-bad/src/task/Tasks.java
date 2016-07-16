package task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import editor.Command;
import internationalisation.Internationalisation;
import java.util.ArrayList;

/**
 * Encapsulates the command map.
 * Used as a proxy to execute a task.
 */
public class Tasks {
    private Map<String, ITask>      commands;
    private ITask[] tasks = {new TaskOpen(), new TaskSave(), new TaskLook(), new TaskMono(),
                             new TaskRot(), new TaskHelp(), new TaskQuit(), new TaskScript(),
                             new TaskPut(), new TaskGet(), new TaskUndo()};

    /**
     * Create a Tasks object which contains a Map of commands indexed by string.
     * Can execute a task and check if an index is known.
     */
    public Tasks() {
        this.commands = new HashMap<>();
        List<String> indexes = Arrays.asList(Internationalisation.getInstance().getResources().getString("defaultCommands").split(","));
        IntStream.range(0, indexes.size())
                 .forEach(i -> this.commands.put(indexes.get(i), this.tasks[i]));
    }

    /**
     * Execute apply a task from command user input.
     * @param index String, index.
     * @param command Command object
     */
    public void execute(String index, Command command) {
        this.commands.get(index).apply(command);
    }
    
    /**
     * Checks if an index is present in the command map.
     * @param index String, index.
     * @return Boolean state.
     */
    public boolean containsIndex(String index) {
        return (this.commands.containsKey(index));
    }
    
    /**
     * Return commands
     * @return List of command strings
     */
    public List<String> getCommands() {
        return (new ArrayList(this.commands.keySet()));
    }
}
