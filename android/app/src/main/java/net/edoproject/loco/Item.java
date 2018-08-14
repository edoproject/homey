package net.edoproject.loco;

import android.databinding.BaseObservable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static net.edoproject.loco.Item.Action.KEEP;

public class Item extends BaseObservable {
    private String name;
    private boolean alreadyHave;
    private boolean inNewApartment;
    private boolean completed;
    private Action action;

    public enum Action {
        KEEP,
        DONATE,
        DUMP
    }

    public Item() {
        this.setName(name);
        this.setAlreadyHave(alreadyHave);
        this.setInNewApartment(inNewApartment);
        this.setCompleted(completed);
        this.setAction(KEEP);
    }

    public Item(String name,
                boolean alreadyHave,
                boolean inNewApartment,
                boolean completed,
                Action action) {
        this.setName(name);
        this.setAlreadyHave(alreadyHave);
        this.setInNewApartment(inNewApartment);
        this.setCompleted(completed);
        this.setAction(action);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlreadyHave() {
        return alreadyHave;
    }

    public void setAlreadyHave(boolean alreadyHave) {
        this.alreadyHave = alreadyHave;
        notifyChange();
    }

    public void toogleAlreadyHave() {
        setAlreadyHave(!isAlreadyHave());
        setCompleted(false);
    }

    public boolean isInNewApartment() {
        return inNewApartment;
    }

    public void setInNewApartment(boolean inNewApartment) {
        this.inNewApartment = inNewApartment;
        notifyChange();
    }

    public void toogleInNewAppartment() {
        setInNewApartment(!isInNewApartment());
        setCompleted(false);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        notifyChange();
    }

    public void toogleCompleted() {
        setCompleted(!isCompleted());
    }

    public Action getAction() { return action; }

    public void setAction(Action action) {
        this.action = action;
        setCompleted(false);
        notifyChange();
    }

    @JsonIgnore
    public boolean isDupplicate(){
        return inNewApartment && alreadyHave;
    }
}
