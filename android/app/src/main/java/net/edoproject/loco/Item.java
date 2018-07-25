package net.edoproject.loco;

public class Item {
    private String name;
    private boolean alreadyHave;
    private boolean inNewApartment;
    private boolean show;

    public Item() {
        this.setName(name);
        this.setAlreadyHave(alreadyHave);
        this.setInNewApartment(inNewApartment);
        this.setShow(show);
    }

    public Item(String name,
                boolean alreadyHave,
                boolean inNewApartment,
                boolean show) {
        this.setName(name);
        this.setAlreadyHave(alreadyHave);
        this.setInNewApartment(inNewApartment);
        this.setShow(show);
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

    public boolean isInNewApartment() {
        return inNewApartment;
    }

    public void setInNewApartment(boolean inNewApartment) {
        this.inNewApartment = inNewApartment;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
