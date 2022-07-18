package com.babas.models;

import com.babas.utilities.Babas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Election extends Babas {

    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    @NotBlank(message = "Descripción")
    private String description;

    @OneToMany(mappedBy = "election")
    @NotEmpty(message = "Candidatos")
    private List<Candidate> candidates=new ArrayList<>();

    @OneToMany(mappedBy = "election")
    private List<Vote> totalVotes=new ArrayList<>();

    @Transient
    private List<Candidate> candidatesChanges=new ArrayList<>();

    private boolean active=true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Vote> getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(List<Vote> totalVotes) {
        this.totalVotes = totalVotes;
    }

    public List<Vote> getVotosBlank(){
        List<Vote> votesBlanck=new ArrayList<>();
        for (Vote voto : getTotalVotes()) {
            if(voto.getCandidate()==null){
                votesBlanck.add(voto);
            }
        }
        return votesBlanck;
    }
    public List<Candidate> getCandidatesChanges() {
        return candidatesChanges;
    }

    public void setCandidatesChanges(List<Candidate> candidatesChanges) {
        this.candidatesChanges = candidatesChanges;
    }
}
