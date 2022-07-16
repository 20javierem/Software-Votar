package com.babas.views.dialogs;

import com.babas.models.Candidate;
import com.babas.models.Election;
import com.babas.models.Student;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.buttonEditors.JButtonEditorStudent;
import com.babas.utilitiesTables.tablesCellRendered.StudentCellRendered;
import com.babas.utilitiesTables.tablesModels.StudentTableModel;
import com.babas.views.frames.FramePrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DaddCandidates extends JDialog{
    private JPanel contentPane;
    private JButton btnHecho;
    private JTextField txtStudent;
    private JTable table;
    private JButton btnAddCandidate;
    private Election election;
    private StudentTableModel model;
    private JTable tableElection;
    public DaddCandidates(Election election,JTable tableElection){
        this.election=election;
        this.tableElection=tableElection;
        initComponents();
        btnAddCandidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCandidate();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }
    private void onCancel(){
        dispose();
    }
    private void addCandidate(){
        if(table.getSelectedRow()!=-1){
            verifyCandidate(model.get(table.convertRowIndexToModel(table.getSelectedRow())));
        }
    }
    private void verifyCandidate(Student student){
        boolean esta=false;
        for (Candidate candidate:election.getCandidates()){
            if (Objects.equals(candidate.getStudent().getDni(), student.getDni())) {
                esta = true;
                break;
            }
        }
        if(!esta){
            Candidate candidate=new Candidate();
            candidate.setElection(election);
            candidate.setStudent(student);
            election.getCandidates().add(candidate);
            UtilitiesTables.actualizarTabla(tableElection);
        }
    }
    private void initComponents(){
        setContentPane(contentPane);
        loadStudents();
        setModal(true);
        pack();
        setLocationRelativeTo(null);
    }
    private void loadStudents(){
        model=new StudentTableModel(FramePrincipal.students);
        table.setModel(model);
        table.removeColumn(table.getColumn(""));
        UtilitiesTables.headerNegrita(table);
        StudentCellRendered.setCellRenderer(table);
    }
}
