package editor;

import ui.*;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main class for the Fotoshop application
 */
public class Main extends Application {
    private static String[]     args;
    public final static String  DEFAULT_BUNDLE = "MiscBundle";
    public final static String  DEFAULT_LOCAL = "GB";
    public final static String  DEFAULT_LANG = "en";

    @Override
    public void start(Stage stage) {
        String bundleName = DEFAULT_BUNDLE;
        String local = DEFAULT_LOCAL;
        String lang = DEFAULT_LANG;

        if (this.args.length >= 2) {
                local = this.args[0];
                lang = this.args[1];
                if (this.args.length >= 3)
                    bundleName = this.args[2];
        }
        Editor editor = Editor.getInstance(bundleName, local, lang, new GUI(stage));
        editor.edit();
    }
    
    /**
     * Fotoshop program entry point
     * @param pArgs Array of arguments
     */
    public static void main(String[] pArgs) {
        args = pArgs;
        launch(args);
    }
}
