package editor;

import java.util.List;
import java.util.stream.Collectors;
import internationalisation.Internationalisation;

/**
 * This class holds information about a command that was issued by the user.
 * A command currently consists of a string and a list of strings:
 * A command word and a list of arguments.
 */
public class Command {
    private String          commandWord;
    private List<String>    args;

    /**
     * Create a command object. This will create a command object with a command
     * word and a list of arguments.
     * @param words List of strings each string is a word.
     */
    public Command(List<String> words) {
        if (words.size() >= 1) {
            this.commandWord = words.remove(0);
            this.args = words.stream()
                             .collect(Collectors.toList());
        }
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord() {
        return (this.commandWord);
    }

    /**
     * @return Arguments of this command. Returns null if there was no
     * arguments.
     */
    public List<String> getCommandArgs() {
        return (this.args);
    }
    
    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown() {
        if (this.commandWord != null
                && Internationalisation.getInstance().getCommandWords().keySet().contains(this.commandWord)
                && Editor.getInstance().getCommands().containsIndex(Internationalisation.getInstance().getCommandWords().get(this.commandWord))) {
            return false;
        }
        return true;
    }

    /**
     * @return true if the command has arguments.
     */
    public boolean hasArgs() {
        return !(this.args != null ^ !this.args.isEmpty());
    }
}
