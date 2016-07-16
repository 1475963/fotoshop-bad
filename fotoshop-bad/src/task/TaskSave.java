package task;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import editor.Command;
import image.ImageCache;
import editor.Editor;
import internationalisation.Internationalisation;

/**
 * Implements command save which saves the image in a file.
 */
public class TaskSave extends ATask implements ITask {
    public TaskSave() {
    }

    public void apply(Command command) {
        if (ImageCache.getInstance().getCurrent() == null) {
            Editor.getInstance().getUI().currentImageError(null);
            return ;
        }
        if (!this.checkArgs(command, Internationalisation.getInstance().getResources().getString("saveWhere")))
            return ;

        String outputName = command.getCommandArgs().get(0);
        try {
            File outputFile = new File(outputName);
            ImageIO.write(ImageCache.getInstance().getCurrent(), "jpg", outputFile);
            Editor.getInstance().getUI().save(outputName);
        }
        catch (IOException e) {
            Editor.getInstance().getUI().saveException(e.getMessage());
        }
    }
}