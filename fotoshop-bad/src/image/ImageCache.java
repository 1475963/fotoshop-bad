package image;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collection;

/**
 * This class is an implementation an image cache which provides a save
 * of multiple images into an index (it is used to implement undo command).
 */
public class ImageCache {
    private static ImageCache           imageCache = null;
    private Map<String, List<Image>>    cache;
    private String                      index;

    /**
     * Create the image cache and initilise the cache.
     */
    private ImageCache() {
        this.cache = new HashMap<>();
    }

    /**
     * ImageCache singleton accessor, creates a cache instance if none is found
     * else returns the existing one.
     * @return ImageCache object.
     */
    public static ImageCache getInstance() {
        if (imageCache == null) {
            imageCache = new ImageCache();
        }
        return (imageCache);
    }

    /**
     * Puts image into the cache at a provided index.
     * @param name Index where the image is stored.
     * @param image Image object to store.
     */
    public void	put(String name, Image image) {
        if (!this.cache.containsKey(name)) {
            this.cache.put(name, new ArrayList<>());
        }
        if (this.index != null && !name.equals(this.index)) {
            this.cache.get(name).clear();
            this.cache.get(this.index).forEach(img -> this.cache.get(name).add(img));
        }
        if (image != null)
            this.cache.get(name).add(image);
    }

    /**
     * Get image from index.
     * @param name String, index.
     * @return Image object retrieved from index.
     */
    public Image get(String name) {
        if (!this.cache.containsKey(name))
            return (null);
        this.index = name;
        return (this.getCurrent());
    }

    /**
     * Return the current imaqge in the cache.
     * @return Image current image.
     */
    public Image getCurrent() {
        if (this.cache.containsKey(this.index) && this.cache.get(this.index).size() > 0)
            return (this.cache.get(this.index).get(this.cache.get(this.index).size() - 1));
        return (null);
    }

    /**
     * Removes last filter.
     */
    public void rollBack() {
        if (this.cache.containsKey(this.index) && this.cache.get(this.index).size() > 1) {
            this.cache.get(this.index).remove(this.cache.get(this.index).size() - 1);
        }
    }
    
    /**
     * Return the current index.
     * @return String which is an index.
     */
    public String getIndex() {
        return (index);
    }
    
    /**
     * Return the size of the cache
     * @return length of the map
     */
    public int size() {
        return (this.cache.size());
    }
    
    /**
     * Return the keys
     * @return Set of keys strings
     */
    public Set<String> keys() {
        return (this.cache.keySet());
    }
    
    /**
     * Return the values
     * @return list of images
     */
    public Collection<List<Image>> values() {
        return (this.cache.values());
    }
    
    /**
     * Return last version of each image index
     * @return List of last image versions
     */
    public List<Image> lastVersions() {
        List<Image> res = new ArrayList<>();
        
        this.cache.values().stream().filter(versions -> versions.size() > 0)
                                    .forEach(versions -> res.add(versions.get(versions.size() - 1)));
        return (res);
    }
}
