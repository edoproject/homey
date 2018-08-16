package net.edoproject.loco;

import android.databinding.BaseObservable;

import static net.edoproject.loco.Item.Action.KEEP;

public class Item extends BaseObservable {
    private String name;
    private boolean alreadyHave;
    private boolean inNewApartment;
    private boolean show;
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
        this.setShow(show);
        this.setAction(KEEP);
    }

    public Item(String name,
                boolean alreadyHave,
                boolean inNewApartment,
                boolean show,
                Action action) {
        this.setName(name);
        this.setAlreadyHave(alreadyHave);
        this.setInNewApartment(inNewApartment);
        this.setShow(show);
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
    }

    public void toogleAlreadyHave() {
        setAlreadyHave(!isAlreadyHave());
        notifyChange();
    }

    public boolean isInNewApartment() {
        return inNewApartment;
    }

    public void setInNewApartment(boolean inNewApartment) {
        this.inNewApartment = inNewApartment;
    }

    public void toogleInNewAppartment() {
        setInNewApartment(!isInNewApartment());
        notifyChange();
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public Action getAction() { return action; }

    public void setAction(Action action) {
        this.action = action;
        notifyChange();
    }
}
