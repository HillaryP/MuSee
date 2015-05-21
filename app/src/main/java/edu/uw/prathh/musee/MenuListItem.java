package edu.uw.prathh.musee;

/**
 * Created by Hill on 4/1/2015.
 */
public class MenuListItem {
    private String label;

    public MenuListItem(String label) {
        this.label = label.toUpperCase();
    }

    public String getLabel() {
        return this.label;
    }
}
