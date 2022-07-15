package com.babas.views.Tabs;

import com.babas.custom.TabPane;
import com.babas.models.Candidate;
import com.babas.models.Election;
import com.babas.views.models.ViewCandidate;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TabVotation {
    private JButton votarButton;
    private JScrollPane scroll;
    private JPanel panelLista;
    private TabPane tabPane;
    private Election election;
    private List<ViewCandidate> productViews=new ArrayList<>();

    public TabVotation(Election election){
        this.election=election;
        initComponents();
    }
    private void initComponents(){
        loadTable();
        tabPane.setTitle("Votación");
    }
    private void loadTable(){
        for(Candidate candidate: election.getCandidates()){
            ViewCandidate productView=new ViewCandidate(candidate);
            productViews.add(productView);
            panelLista.add(productView.getContentPane());
        }
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
