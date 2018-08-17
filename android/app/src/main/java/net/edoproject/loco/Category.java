package net.edoproject.loco;

import android.databinding.BaseObservable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

public class Category extends BaseObservable {
    private String name;
    private boolean expanded;
    private List<Item> items;

    @JsonIgnore private boolean alreadyHave = false;
    @JsonIgnore private boolean inNewApartment = false;
    @JsonIgnore private boolean completed = false;

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

    public void toogleExpanded() {
        setExpanded(!isExpanded());
        notifyChange();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void toogleAlreadyHave() {
        alreadyHave = !alreadyHave;
        for (Item item: items) {
            item.setAlreadyHave(!alreadyHave);
        }
    }

    public void toogleInNewApartment() {
        inNewApartment = !inNewApartment;
        for (Item item: items) {
            item.setInNewApartment(!inNewApartment);
        }
    }

    public void toogleCompleted() {
        completed = !completed;
        for (Item item: items) {
            item.setCompleted(!completed);
        }
    }

    public void setAction(Item.Action action) {
        for (Item item: items) {
            item.setAction(action);
        }
    }
}
