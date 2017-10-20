package camt.cbsd.lab05.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Student {
    long id;
    String studentId;
    String name;
    String surname;
    double gpa;
    String image;
    boolean feature;
    int penAmount;
    String description;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public long getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public double getGpa() {
        return gpa;
    }

    public String getImage() {
        return image;
    }

    public boolean isFeature() {
        return feature;
    }

    public int getPenAmount() {
        return penAmount;
    }

    public String getDescription() {
        return description;
    }
    public Student(){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Student(long id, String studentId, String name, String surname, double gpa, String image, boolean feature, int penAmount, String description) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;

        this.gpa = gpa;
        this.image = image;
        this.feature = feature;
        this.penAmount = penAmount;
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
