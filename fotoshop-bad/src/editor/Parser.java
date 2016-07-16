package editor;

import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import internationalisation.Internationalisation;

/**
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a three word command. It returns the command
 * as an object of class Command.
 */
public class Parser {
    private Scanner         reader;

    /**
     * Create a parser object. This object will retrieve user input or data from a script file.
     */
    public Parser() {
        this.setStdinStream();
    }

    /**
     * Changes the reader file descriptor to the one given in parameter.
     * @param str Any file descriptor created from a file (standard input not applicable).
     */
    public void setInputStream(FileInputStream str) { 
        this.reader = new Scanner(str);
    }

    /**
     * Creates a new scanner with standard input file descriptor and store it.
     */
    public void setStdinStream() {
        this.reader = new Scanner(System.in);
    }

    /**
     * Display a prompt and retrieve user input which is translated into a command object.
     * @return The next command from the user.
     */
    public Command getCommand() {
        String          inputLine;
        List<String>    words = new ArrayList<>();

        System.out.print(Internationalisation.getInstance().getResources().getString("prompt"));

        inputLine = this.reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        while (tokenizer.hasNext()) {
            words.add(tokenizer.next());
        }

        return (new Command(words));
    }
}
