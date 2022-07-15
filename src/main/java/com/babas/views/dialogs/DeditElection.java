package com.babas.views.dialogs;

import com.babas.models.Election;
import com.babas.utilities.Utilities;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.tablesCellRendered.CandidateCellRendered;
import com.babas.utilitiesTables.tablesModels.CandidatesTableModel;

import javax.swing.*;

public class DeditElection extends JDialog{
    private JPanel contentPane;
    private JTextField txtDescription;
    private JTable table;
    private JButton btnAddCandidates;
    private JButton btnSave;
    private JButton btnHecho;
    private Election election;
    private boolean update=false;
    private CandidatesTableModel model;

    public DeditElection(){
        this(new Election());
    }
    public DeditElection(Election election){
        this.election=election;
        initComponents();
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
