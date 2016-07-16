package internationalisation;

import editor.Main;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * This class implements the internationalisation feature.
 */
public class Internationalisation {
    private static Internationalisation languageSupport = null;
    private Locale                      currentLang;
    private ResourceBundle              resources;
    private Map<String, String>         commandWords;
    private List<String>                advancedCommands;
    private String                      currentBundle;

    /**
     * Internationalisation singleton accessor, creates an internationalisation object instance.
     * @param bundleName String, BundleName.
     * @param local String, Localisation.
     * @param lang String, Langue.
     */
    private Internationalisation(String bundleName, String local, String lang) {
        this.setResources(bundleName, local, lang);
        List<String> indexes = Arrays.asList(this.resources.getString("defaultCommands").split(","));
        List<String> words = Arrays.asList(this.resources.getString("commands").split(","));
        this.advancedCommands = Arrays.asList(this.resources.getString("advancedCommands").split(","));
        this.commandWords = new HashMap<>();
        IntStream.range(0, indexes.size())
                 .forEach(i -> this.commandWords.put(words.get(i), indexes.get(i)));
    }

    /**
     * Internationalisation singleton accessor, creates an internationalisation object instance
     * if none is found else returns it.
     * @return Internationalisation object.
     */
    public static Internationalisation getInstance() {
        if (languageSupport == null) {
            languageSupport = new Internationalisation(Main.DEFAULT_BUNDLE, Main.DEFAULT_LOCAL, Main.DEFAULT_LANG);
        }
        return (languageSupport);
    }
    
    /**
     * Internationalisation singleton accessor, creates an internationalisation object instance
     * if none is found else returns it.
     * @param bundleName String, BundleName.
     * @param local String, Localisation.
     * @param lang String, Langue.
     * @return Internationalisation object.
     */
    public static Internationalisation getInstance(String bundleName, String local, String lang) {
        if (languageSupport == null) {
            languageSupport = new Internationalisation(bundleName, local, lang);
        }
        return (languageSupport);
    }

    /**
     * Set current bundle path and change language, reload resources.
     * @param bundleName String, BundleName.
     * @param local String, Localisation.
     * @param lang String, Langue.
     */
    public void setResources(String bundleName, String local, String lang) {
        this.currentBundle = bundleName;
        this.changeLang(local, lang);
    }
    
    /**
     * Change current language and reload resources.
     * @param local Localisation string like "GB".
     * @param lang Language like "en".
     */
    public void changeLang(String local, String lang) {
        this.currentLang = new Locale(lang, local);
        this.resources = ResourceBundle.getBundle("internationalisation." + this.currentBundle, this.currentLang);
    }

    /**
     * Return a map of indexes and command words.
     * @return Map of strings indexed by strings.
     */
    public Map<String, String> getCommandWords() {
        return (this.commandWords);
    }
    
    /**
     * Return a resource bundle object.
     * @return ResourceBundle object.
     */
    public ResourceBundle getResources() {
        return (this.resources);
    }
    
    /**
     * Return a list of strings which are filters
     * @return List of string which are image editing commands
     */
    public List<String> getAdvancedCommands() {
        return (this.advancedCommands);
    }
}
