package task;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import editor.Command;
import editor.Editor;
import internationalisation.Internationalisation;

/**
 * Implements command script which open a file, read it and executes
 * every command in the script file.
 */
public class TaskScript extends ATask implements ITask {
    public TaskScript() {
    }

    public void apply(Command command) {
        if (!this.checkArgs(command, Internationalisation.getInstance().getResources().getString("scriptWhich")))
            return ;
        String scriptName = command.getCommandArgs().get(0);
        try (FileInputStream inputStream = new FileInputStream(scriptName)) {
            Editor.getInstance().getParser().setInputStream(inputStream);
            while (Editor.getInstance().isRunning()) {
                try {
                    Command cmd = Editor.getInstance().getParser().getCommand();
                    Editor.getInstance().processCommand(cmd);
                }
                catch (Exception ex) {
                    return ;
                }
            }
        } 
        catch (FileNotFoundException ex) {
            Editor.getInstance().getUI().scriptException(scriptName);
        }
        catch (IOException ex) {
            throw new RuntimeException(Internationalisation.getInstance().getResources().getString("scriptPanic"));
        }
        finally {
            Editor.getInstance().getParser().setStdinStream();
            Editor.getInstance().getUI().script();
        }
    }
}