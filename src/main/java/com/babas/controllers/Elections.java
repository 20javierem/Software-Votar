package com.babas.controllers;

import com.babas.models.Election;
import com.babas.models.Student;
import com.babas.utilities.Babas;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Vector;

public class Elections extends Babas {
    private static Root<Election> root;
    private static CriteriaQuery<Election> criteria;
    private static Vector<Election> todos;

    public static Election get(Integer id) {
        Election election = session.find(Election.class, id, LockModeType.NONE);
        return election;
    }

    public static Vector<Election> getTodos(){
        criteria = builder.createQuery(Election.class);
        criteria.select(criteria.from(Election.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
}
