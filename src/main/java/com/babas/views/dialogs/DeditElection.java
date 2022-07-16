package com.babas.views.dialogs;

import com.babas.models.Election;
import com.babas.models.Student;
import com.babas.utilities.Babas;
import com.babas.utilities.Utilities;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.tablesCellRendered.CandidateCellRendered;
import com.babas.utilitiesTables.tablesModels.CandidatesTableModel;
import com.babas.validators.ElectionValidator;
import com.babas.validators.StudentValidator;
import com.babas.views.frames.FramePrincipal;
import com.babas.views.frames.FrameVotation;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class DeditElection extends JDialog{
    private JPanel contentPane;
    private JTextField txtDescription;
    private JTable table;
    private JButton btnAddCandidates;
    private JButton btnSave;
    private JButton btnHecho;
    private Election election;
    private boolean update=false;
    private JTable tableElection;
    private CandidatesTableModel model;

    public DeditElection(Election election,JTable tableElection){
        this.election=election;
        this.tableElection=tableElection;
        initComponents();
        btnAddCandidates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAddCandidates();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }
    private void onCancel(){
        if(election.getId()!=null){
            election.refresh();
        }
        dispose();
    }
    private void save(){
        election.setActive(true);
        election.setDescription(txtDescription.getText().trim().toUpperCase());
        Set<ConstraintViolation<Election>> errors = ElectionValidator.loadViolations(election);
        if (errors.isEmpty()) {
            election.save();
            if(update){
                onCancel();
            }else{
                FramePrincipal.electionsActives.add(election);
                FramePrincipal.elections.add(election);
                election=new Election();
                loadElection();
            }
        } else {
            ElectionValidator.mostrarErrores(errors);
        }
        if(tableElection!=null){
            UtilitiesTables.actualizarTabla(tableElection);
        }
    }
    public DeditElection(JTable tableElection){
        this(new Election(),tableElection);
    }
    private void loadAddCandidates(){
        DaddCandidates daddCandidates=new DaddCandidates(election,table);
        daddCandidates.setVisible(true);
    }
    private void initComponents(){
        setContentPane(contentPane);
        if(election.getId()!=null){
            setTitle("Editar estudiante");
            btnSave.setText("Guardar");
            btnHecho.setText("Cancelar");
            update=true;
        }
        loadElection();
        getRootPane().setDefaultButton(btnSave);
        setModal(true);
        pack();
        setLocationRelativeTo(null);
    }
    private void loadElection(){
        txtDescription.setText(election.getDescription());
        model=new CandidatesTableModel(election.getCandidates());
        table.setModel(model);
        UtilitiesTables.headerNegrita(table);
        CandidateCellRendered.setCellRenderer(table);
    }
}
