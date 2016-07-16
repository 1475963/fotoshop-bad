package ui;

import editor.Command;
import editor.Editor;
import image.ImageCache;
import internationalisation.Internationalisation;
import java.util.stream.IntStream;
import javafx.application.Platform;

public class CLI implements UI {
    public CLI() {
    }
    
    public void welcome() {
        System.out.println(Internationalisation.getInstance().getResources().getString("welcomeMessage"));
        this.look(null);
    }
    
    public void bye() {
        System.out.println(Internationalisation.getInstance().getResources().getString("byeMessage"));
    }
    
    public void unknown() {
        System.out.println(Internationalisation.getInstance().getResources().getString("unknownMessage"));
    }
    
    public void imageException(String name) {
        System.out.println(Internationalisation.getInstance().getResources().getString("loadImageException1") + name);
        System.out.println(Internationalisation.getInstance().getResources().getString("loadImageException2") + System.getProperty("user.dir"));
    }
    
    public void argsError(String msg) {
        System.out.println(msg);
    }
    
    public void currentImageError(Command command) {
        Editor.getInstance().getUI().help();
        Editor.getInstance().getUI().look(command);
    }
    
    public void saveException(String msg) {
        System.out.println(msg);
        this.help();
        this.look(null);
    }
    
    public void scriptException(String name) {
        System.out.println(Internationalisation.getInstance().getResources().getString("scriptErrMsg") + name);
    }
    
    public void open(String name) {
        System.out.println(Internationalisation.getInstance().getResources().getString("openLoaded") + name);
    }
    
    public void save(String name) {
        System.out.println(Internationalisation.getInstance().getResources().getString("saveSaved") + name);
    }
    
    public void look(Command command) {
        System.out.println(Internationalisation.getInstance().getResources().getString("lookCurrent") + (ImageCache.getInstance().getIndex() == null ? "null" : ImageCache.getInstance().getIndex()));
        System.out.print(Internationalisation.getInstance().getResources().getString("lookFilters"));
        if (ImageCache.getInstance().getCurrent() != null) {
            IntStream.range(0, ImageCache.getInstance().getCurrent().sizeFilter())
                     .forEach(i -> System.out.println(ImageCache.getInstance().getCurrent().getFilter(i) + " "));
        }
        System.out.println();
    }
    
    public void mono() {
        
    }
    
    public void rot() {
        
    }
    
    public void help() {
        System.out.println(Internationalisation.getInstance().getResources().getString("helpMessage"));
    }
    
    public void quit() {
        Platform.exit();
    }
    
    public void script() {
        
    }
    
    public void put() {
        
    }
    
    public void get() {
        
    }
    
    public void undo() {
        
    }
}
