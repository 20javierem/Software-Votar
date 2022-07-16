package com.babas.utilitiesTables.buttonEditors;

import com.babas.models.Election;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.tablesModels.ElectionTableModel;
import com.babas.views.dialogs.DeditElection;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorElection extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButtonAction button;
    private JTable table;

    public JButtonEditorElection(JTable table) {
        this.table=table;
        button=new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(table.getSelectedRow()!=-1){
            Election election=((ElectionTableModel) table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow()));
            DeditElection deditElection=new DeditElection(election,table);
            deditElection.setVisible(true);
            UtilitiesTables.actualizarTabla(table);
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
