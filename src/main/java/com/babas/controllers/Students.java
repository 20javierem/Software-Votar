package com.babas.controllers;

import com.babas.models.Student;
import com.babas.utilities.Babas;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Vector;

public class Students extends Babas {
    private static Root<Student> root;
    private static CriteriaQuery<Student> criteria;
    private static Vector<Student> todos;

    public static Student get(Integer id) {
        Student student = session.find(Student.class, id, LockModeType.NONE);
        return student;
    }

    public static Vector<Student> getTodos(){
        criteria = builder.createQuery(Student.class);
        criteria.select(criteria.from(Student.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }

    public static Student getByDni(String dni){
        criteria = builder.createQuery(Student.class);
        root=criteria.from(Student.class);
        criteria.select(root)
                .where(builder.equal(root.get("dni"),dni));
        return session.createQuery(criteria).uniqueResult();
    }

}
