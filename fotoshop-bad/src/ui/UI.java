package ui;

import editor.Command;

public interface UI {
    public void welcome();
    public void bye();
    public void unknown();
    public void imageException(String name);
    public void argsError(String msg);
    public void currentImageError(Command command);
    public void saveException(String msg);
    public void scriptException(String name);
    public void open(String name);
    public void save(String name);
    public void look(Command command);
    public void mono();
    public void rot();
    public void help();
    public void quit();
    public void script();
    public void put();
    public void get();
    public void undo();
}
