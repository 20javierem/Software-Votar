package com.babas.views.dialogs;

import com.babas.models.Election;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.tablesCellRendered.ElectionCellRendered;
import com.babas.utilitiesTables.tablesModels.ElectionTableModel;
import com.babas.views.frames.FramePrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DstarVotation extends JDialog {
    private JPanel contentPane;
    private JButton btnHecho;
    private JButton btnStartVotation;
    private JTable table;
    private ElectionTableModel model;

    public DstarVotation(){
        initComponents();
        btnStartVotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                starElection();
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
    private void starElection(){
        if(table.getSelectedRow()!=-1){
            Election election=model.get(table.getSelectedRow());
            dispose();
            Dvotation dvotation =new Dvotation(election);
            dvotation.setVisible(true);
        }else{
           JOptionPane.showMessageDialog(null,"Debe seleccionar una elección","ERROR",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void initComponents(){
        setTitle("Iniciar elecciones");
        setContentPane(contentPane);
        loadElections();
        pack();
        setModal(true);
        setLocationRelativeTo(null);
    }
    private void loadElections(){
        model=new ElectionTableModel(FramePrincipal.electionsActives);
        table.setModel(model);
        table.removeColumn(table.getColumn(""));
        UtilitiesTables.headerNegrita(table);
        ElectionCellRendered.setCellRenderer(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
