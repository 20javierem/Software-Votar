package com.babas.models;

import com.babas.utilities.Babas;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class School extends Babas {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
