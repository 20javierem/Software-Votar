package com.babas.utilitiesTables.tablesCellRendered;


import com.babas.utilitiesTables.UtilitiesTables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

import static com.babas.utilitiesTables.UtilitiesTables.buscarTexto;

public class StudentCellRendered extends DefaultTableCellRenderer {
    private Map<Integer, String> listaFiltros;
    public StudentCellRendered(Map<Integer,String> listaFiltros){
        this.listaFiltros=listaFiltros;
    }
    public static void setCellRenderer(JTable table, Map<Integer,String> listaFiltros){
        StudentCellRendered cellRendered=new StudentCellRendered(listaFiltros);
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }
    public static void setCellRenderer(JTable table){
        StudentCellRendered cellRendered=new StudentCellRendered(null);
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            table.getColumnModel().getColumn(column).setMaxWidth(25);
            table.getColumnModel().getColumn(column).setMinWidth(25);
            table.getColumnModel().getColumn(column).setPreferredWidth(25);
            return UtilitiesTables.isButonSelected(isSelected,"x16/editar.png",table);
        }else{
            JTextField componente=buscarTexto(listaFiltros,value,column,this);
            switch(table.getColumnName(column)){
                case "ID":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(40);
                    table.getColumn(table.getColumnName(column)).setMinWidth(40);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(40);
                    break;
                case "DNI":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(120);
                    table.getColumn(table.getColumnName(column)).setMinWidth(120);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(120);
                    break;
                default:
                    break;
            }
            return componente;
        }
    }
}
