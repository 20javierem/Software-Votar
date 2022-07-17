package com.babas.views.dialogs;

import com.babas.models.Candidate;
import com.babas.models.Election;
import com.babas.models.Student;
import com.babas.models.Vote;
import com.babas.views.models.ViewCandidate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DVote extends JDialog{
    private JPanel contentPane;
    private JButton votarButton;
    private JScrollPane scroll;
    private JPanel paneList;
    private Election election;
    private ButtonGroup buttonGroup=new ButtonGroup();
    private Vote vote;
    private Student student;
    private List<ViewCandidate> viewCandidates=new ArrayList<>();

    public DVote(Election election,Student student){
        this.election=election;
        this.student=student;
        initComponents();
        votarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVote();
            }
        });
    }
    private void setVote(){
        vote.setCandidate(getCandidateSelected());
        vote.save();
        dispose();
    }
    private Candidate getCandidateSelected(){
        for(ViewCandidate viewCandidate:viewCandidates){
            if(viewCandidate.getBtnSelected().isSelected()){
                viewCandidate.getCandidate().getVotes().add(vote);
                return viewCandidate.getCandidate();
            }
        }
        return null;
    }
    private void initComponents(){
        vote=new Vote();
        student.getVotes().add(vote);
        election.getVotes().add(vote);
        vote.setElection(election);
        vote.setStudent(student);
        setTitle("Voto");
        setContentPane(contentPane);
        loadTable();
        setModal(true);
        pack();
        setLocationRelativeTo(null);
    }

    private void loadTable(){
        BoxLayout boxLayout = new BoxLayout(paneList, BoxLayout.Y_AXIS);
        paneList.setLayout(boxLayout);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        for(Candidate candidate: election.getCandidates()){
            ViewCandidate viewCandidate=new ViewCandidate(candidate);
            paneList.add(viewCandidate.getContentPane());
            viewCandidates.add(viewCandidate);
            buttonGroup.add(viewCandidate.getBtnSelected());
        }
    }
}
