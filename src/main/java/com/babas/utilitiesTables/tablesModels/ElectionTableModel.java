package com.babas.utilitiesTables.tablesModels;

import com.babas.models.Election;
import com.babas.utilitiesTables.buttonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ElectionTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","DESCRIPCIÓN","ESTADO","CANDIDATOS",""};
    private Class[] m_colTypes = {Integer.class,String.class, String.class,Integer.class, JButton.class};
    private List<Election> vector;

    public ElectionTableModel(List<Election> vector){
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
        Election election=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return election.getId();
            case 1:
                return election.getDescription();
            case 2:
                return election.isActive()?"ACTIVA":"TERMINADA";
            case 3:
                return election.getCandidates().size();
            default:
                return new JButtonAction("x16/editar.png");
        }
    }
    public Election get(int index){
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

