package com.babas.models;

import com.babas.utilities.Babas;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends Babas {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    @NotBlank(message = "Nombres y apellidos")
    private String name;

    @NotBlank(message = "DNI")
    private String dni;

    @OneToMany(mappedBy = "student")
    private List<Vote> votes=new ArrayList<>();

    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public static Student create(String[] attributes){
        Student student=new Student();
        student.setDni(attributes[0]);
        student.setName(attributes[1]);
        return student;
    }
}
