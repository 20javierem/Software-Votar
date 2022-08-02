package com.babas.views.dialogs;

import com.babas.models.Election;
import com.babas.utilities.Propiedades;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.tablesCellRendered.ElectionCellRendered;
import com.babas.utilitiesTables.tablesModels.ElectionTableModel;
import com.babas.views.frames.FramePrincipal;

import javax.swing.*;
import java.awt.event.*;

public class DstarVotation extends JDialog {
    private JPanel contentPane;
    private JButton btnHecho;
    private JButton btnStartVotation;
    private JTable table;
    private ElectionTableModel model;
    private Propiedades propiedades;
    private FramePrincipal framePrincipal;

    public DstarVotation(Propiedades propiedades, FramePrincipal framePrincipal){
        super(framePrincipal,"Iniciar Elección",true);
        this.propiedades=propiedades;
        this.framePrincipal=framePrincipal;
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
                onDispose();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onDispose();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void onDispose(){
        dispose();
    }
    private void starElection(){
        if(table.getSelectedRow()!=-1){
            Election election=model.get(table.getSelectedRow());
            dispose();
            framePrincipal.setVisible(false);
            Dvotation dvotation =new Dvotation(election,framePrincipal);
            dvotation.setVisible(true);
        }else{
           JOptionPane.showMessageDialog(null,"Debe seleccionar una elección","ERROR",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void initComponents(){
        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnStartVotation);
        loadElections();
        pack();
        setLocationRelativeTo(framePrincipal);
    }
    private void loadElections(){
        model=new ElectionTableModel(FramePrincipal.electionsActives);
        table.setModel(model);
        table.removeColumn(table.getColumn(""));
        table.removeColumn(table.getColumn("TOTAL VOTOS"));
        table.removeColumn(table.getColumn("VOTOS BLANCOS"));
        UtilitiesTables.headerNegrita(table);
        ElectionCellRendered.setCellRenderer(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
