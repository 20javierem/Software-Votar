package com.babas.controllers;

import com.babas.models.School;
import com.babas.models.Student;
import com.babas.utilities.Babas;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Vector;

public class Schools extends Babas {
    private static Root<School> root;
    private static CriteriaQuery<School> criteria;
    private static Vector<School> todos;

    public static School get(Integer id) {
        School school = session.find(School.class, id, LockModeType.NONE);
        return school;
    }

    public static Vector<School> getTodos(){
        criteria = builder.createQuery(School.class);
        criteria.select(criteria.from(School.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }

}
