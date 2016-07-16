package editor;

import task.*;
import internationalisation.Internationalisation;
import ui.*;

/**
 * This class is the main processing class of the Fotoshop application. 
 * Fotoshop is a very simple image editing tool. Users can apply a number of
 * filters to an image. That's all. It should really be extended to make it more
 * useful!
 * To edit an image, create an instance of this class and call the "edit"
 * method.
 */
public class Editor {
    private static Editor           editor = null;
    private Internationalisation    langSupport;
    private Tasks                   tasks;
    private Parser                  parser;
    private UI                      ui;
    private boolean                 run;
    
    /**
     * Create the editor and initialise internalisationation support, commands,
     * parser and set running state to true.
     */
    private Editor(String bundleName, String local, String lang, UI ui) {
        this.langSupport = Internationalisation.getInstance(bundleName, local, lang);
        this.tasks = new Tasks();
        this.parser = new Parser();
        this.ui = ui;
        this.run = true;
    }
    
    /**
     * Editor singleton accessor, creates an editor instance if none is found
     * else returns it.
     * @return Editor object. Instanciated with default values.
     */
    public static Editor getInstance() {
        if (editor == null) {
            editor = new Editor(Main.DEFAULT_BUNDLE, Main.DEFAULT_LOCAL, Main.DEFAULT_LANG, new CLI());
        }
        return (editor);
    }
    
    /**
     * Editor singleton accessor, creates an editor instance if none is found else returns it.
     * @param bundleName String, you can specify a bundlename contained in internationalisation package from the command line. Default "MiscBundle".
     * @param local String, you can specify a locality like "GB", "US", "FR". Default "GB".
     * @param lang String, language selected like "en", "fr". Default "en".
     * @param ui UI object, GUI or CLI
     * @return Editor object.
     */
    public static Editor getInstance(String bundleName, String local, String lang, UI ui) {
        if (editor == null) {
            editor = new Editor(bundleName, local, lang, ui);
        }
        return (editor);
    }
    
    /**
     * Main edit routine. Loops until the end of the editing session.
     */
    public void edit() {
        if (this.getUI() instanceof GUI) {
        }
        else if (this.getUI() instanceof CLI) {
            this.printWelcome();
            while (run) {
                Command command = this.parser.getCommand();
                this.processCommand(command);
            }
            this.printBye();
        }
    }

    /**
     * Print out the opening message for the user.
     */
    private void printWelcome() {
        this.ui.welcome();
    }

    /**
     * Print out the ending message for the user.
     */
    private void printBye() {
        this.ui.bye();
    }

    /**
     * Check if a command is known in our loaded data from internationalisation bundles.
     * Then call execution function with the right index by translating the command in given language to its index.
     * @param command The command to be processed.
     */
    public void processCommand(Command command) {
        if (command.isUnknown()) {
            this.ui.unknown();
            return ;
        }
        String commandWord = command.getCommandWord();
        this.tasks.execute(this.langSupport.getCommandWords().get(commandWord), command);
    }

    /**
     * Return parser object reference used to have access to the parser from the script task.
     * @return Parser object.
     */
    public Parser getParser() {
        return (this.parser);
    }    
    
    /**
     * Return the tasks object.
     * @return Tasks object which stores all command tasks indexed by a string which is the command name in selected language.
     */
    public Tasks getCommands() {
        return (this.tasks);
    }
    
    /**
     * Return an user interface object.
     * @return UI object which is a GUI or a CLI.
     */
    public UI getUI() {
        return (this.ui);
    }

    /**
     * Set the running state of the editor to false (exit the program then).
     */
    public void stop() {
        this.run = false;
    }

    /**
     * Set the running state of the editor to true.
     */
    public void turnOn() {
        this.run = true;
    }
    
    /**
     * Return the running state of the editor.
     * @return Boolean representing running state of the editor.
     */
    public boolean isRunning() {
        return (this.run);
    }
}
