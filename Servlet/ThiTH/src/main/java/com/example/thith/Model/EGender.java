package com.example.thith.Model;

public enum EGender {
    Nam(1, "Nam"), Nữ(2, "Nữ");
    private int id;
    private String name;

    EGender(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static EGender findById(int id) {
        for (EGender e : EGender.values()) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
    public static EGender find(String type) {
        for (EGender e : EGender.values()) {
            if (e.toString().equals(type)) {
                return e;
            }
        }
        return null;
    }
}
