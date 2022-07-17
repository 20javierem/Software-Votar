package com.babas.controllers;

import com.babas.models.Election;
import com.babas.models.Student;
import com.babas.models.Vote;
import com.babas.utilities.Babas;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Vector;

public class Votes extends Babas {
    private static Root<Vote> root;
    private static CriteriaQuery<Vote> criteria;
    private static Vector<Vote> todos;

    public static Vote get(Integer id) {
        Vote vote = session.find(Vote.class, id, LockModeType.NONE);
        return vote;
    }

    public static Vector<Vote> getTodos(){
        criteria = builder.createQuery(Vote.class);
        criteria.select(criteria.from(Vote.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }

    public static Vote getVote(Student student, Election election){
        criteria = builder.createQuery(Vote.class);
        root=criteria.from(Vote.class);
        criteria.select(root).where(builder.and(
                builder.equal(root.get("student"),student),
                builder.equal(root.get("election"),election)));
        return session.createQuery(criteria).uniqueResult();
    }

}
