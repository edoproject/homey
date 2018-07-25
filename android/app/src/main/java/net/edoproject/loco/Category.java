package net.edoproject.loco;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private boolean expanded;
    private List<Item> items;

    public Category(String name,
                    boolean expanded,
                    List<Item> items) {

        this.setName(name);
        this.setExpanded(expanded);
        this.setItems(items);
    }

    public Category() {
        this.setName("");
        this.setExpanded(true);
        this.setItems(new ArrayList<>());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
