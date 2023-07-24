package com.example.thith.Model;

import java.time.LocalDate;

public class Teacher {

    private int id;
    private String name;
    private LocalDate dob;
    private String hobbie;
    private EGender gender;
    private Degree degree;

    public Teacher() {
    }

    public Teacher(int id, String name, LocalDate dob, String hobbie, EGender gender, Degree degree) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.hobbie = hobbie;
        this.gender = gender;
        this.degree = degree;
    }

    public Teacher(int id, String name, LocalDate dob, String hobbie, EGender gender) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.hobbie = hobbie;
        this.gender = gender;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getHobbie() {
        return hobbie;
    }

    public void setHobbie(String hobbie) {
        this.hobbie = hobbie;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }
}
