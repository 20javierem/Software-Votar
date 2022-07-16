package com.babas.views.Tabs;

import com.babas.custom.TabPane;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.buttonEditors.JButtonEditorElection;
import com.babas.utilitiesTables.buttonEditors.JButtonEditorStudent;
import com.babas.utilitiesTables.tablesCellRendered.ElectionCellRendered;
import com.babas.utilitiesTables.tablesCellRendered.StudentCellRendered;
import com.babas.utilitiesTables.tablesModels.ElectionTableModel;
import com.babas.views.dialogs.DeditElection;
import com.babas.views.frames.FramePrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabElections {
    private TabPane tabPane;
    private JButton btnNewElection;
    private JTable table;
    private ElectionTableModel model;
    public TabElections() {
        initComponents();
        btnNewElection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewElection();
            }
        });
    }
    private void initComponents(){
        tabPane.setTitle("Elecciones");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
            }
        });
        loadElections();
    }
    private void loadNewElection(){
        DeditElection deditElection=new DeditElection(table);
        deditElection.setVisible(true);
    }
    private void loadElections(){
        model=new ElectionTableModel(FramePrincipal.elections);
        table.setModel(model);
        table.getColumnModel().getColumn(model.getColumnCount() - 1).setCellEditor(new JButtonEditorElection(table));
        UtilitiesTables.headerNegrita(table);
        ElectionCellRendered.setCellRenderer(table);
    }
    public TabPane getTabPane() {
        return tabPane;
    }
}
