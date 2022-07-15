package com.babas.models;

import com.babas.utilities.Babas;
import jakarta.persistence.*;

@Entity
public class Candidate extends Babas {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;
    @OneToOne
    private Student student;

    @ManyToOne
    private Election election;

    private Integer botos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getBotos() {
        return botos;
    }

    public void setBotos(Integer botos) {
        this.botos = botos;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }
}
