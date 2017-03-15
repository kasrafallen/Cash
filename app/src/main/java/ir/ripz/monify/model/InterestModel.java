package ir.ripz.monify.model;

public class InterestModel {
    public final static int DEFAULT_ID = 45;
    private String name;
    private int color;
    private boolean is_default;
    private int id;

    public boolean is_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public InterestModel(int color, String name, int id) {
        this.color = color;
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InterestModel() {
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
