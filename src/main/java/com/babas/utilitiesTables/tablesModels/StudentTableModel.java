package com.babas.utilitiesTables.tablesModels;

import com.babas.models.Student;
import com.babas.utilitiesTables.buttonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","DNI","NOMBRES Y APELLIDOS",""};
    private Class[] m_colTypes = {Integer.class,String.class, String.class, JButton.class};
    private List<Student> vector;

    public StudentTableModel(List<Student> vector){
        this.vector=vector;
    }
    @Override
    public int getRowCount() {
        return vector.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    @Override
    public Class getColumnClass(int col) {
        return m_colTypes[col];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return student.getId();
            case 1:
                return student.getDni();
            case 2:
                return student.getName();
            default:
                return new JButtonAction("x16/editar.png");
        }
    }
    public Student get(int index){
        return vector.get(index);
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (m_colTypes[columnIndex].equals(JButton.class)) {
            return true;
        }
        return false;
    }
}
