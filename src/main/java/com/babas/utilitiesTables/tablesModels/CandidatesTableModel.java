package com.babas.utilitiesTables.tablesModels;

import com.babas.models.Candidate;
import com.babas.models.Election;
import com.babas.utilitiesTables.buttonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CandidatesTableModel extends AbstractTableModel {
    private String[] columnNames = {"DNI","ESTUDIANTE","VOTOS",""};
    private Class[] m_colTypes = {Integer.class,String.class, String.class, JButton.class};
    private List<Candidate> vector;

    public CandidatesTableModel(List<Candidate> vector){
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
        Candidate candidate=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return candidate.getStudent().getDni();
            case 1:
                return candidate.getStudent().getName();
            case 2:
                return candidate.getVotes().size();
            default:
                return new JButtonAction("x24/cerrar.png");
        }
    }
    public Candidate get(int index){
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