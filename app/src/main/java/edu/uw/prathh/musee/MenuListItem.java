package edu.uw.prathh.musee;

/**
 * Created by Hill on 4/1/2015.
 */
public class MenuListItem {
    private String imageSrc;
    private String label;
    private boolean expands;

    public MenuListItem(String imageSrc, String label) {
        this(imageSrc, label, false);
    }

    public MenuListItem(String imageSrc, String label, boolean expands) {
        this.imageSrc = imageSrc;
        this.label = label.toUpperCase();
        this.expands = expands;
    }

    public String getImageSrc() {
        return this.imageSrc;
    }

    public String getLabel() {
        return this.label;
    }

    public boolean isExpandable() {
        return expands;
    }
}
