package com.babas.utilitiesTables.buttonEditors;

import com.babas.models.Student;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.tablesModels.StudentTableModel;
import com.babas.views.dialogs.DeditStudent;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorStudent extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButtonAction button;
    private JTable table;

    public JButtonEditorStudent(JTable table) {
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
            Student student=((StudentTableModel) table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow()));
            DeditStudent deditStudent=new DeditStudent(student);
            deditStudent.setVisible(true);
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
