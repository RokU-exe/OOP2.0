package models;

public class Surveyor {
    private int id;
    private String name;

    public Surveyor(String id, String name) {
        this.id = Integer.parseInt(id);
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

