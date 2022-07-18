package com.babas.views.Tabs;

import com.babas.custom.TabPane;
import com.babas.utilities.CSVReader;
import com.babas.utilities.Utilities;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.buttonEditors.JButtonEditorStudent;
import com.babas.utilitiesTables.tablesCellRendered.StudentCellRendered;
import com.babas.utilitiesTables.tablesModels.StudentTableModel;
import com.babas.views.dialogs.DeditStudent;
import com.babas.views.frames.FramePrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabStudents {
    private TabPane tabPane;
    private JButton btnNewProduct;
    private JTable table;
    private JButton btnImport;
    private StudentTableModel model;

    public TabStudents() {
        initComponents();
        btnNewProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewStudent();
            }
        });
        btnImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importStudents();
            }
        });
    }
    private void importStudents(){
        CSVReader.importStudents(table);
    }
    private void initComponents(){
        tabPane.setTitle("Estudiantes");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
            }
        });
        loadStudents();
    }
    private void loadNewStudent(){
        DeditStudent deditStudent=new DeditStudent(table);
        deditStudent.setVisible(true);
    }
    private void loadStudents(){
        model=new StudentTableModel(FramePrincipal.students);
        table.setModel(model);
        table.getColumnModel().getColumn(model.getColumnCount() - 1).setCellEditor(new JButtonEditorStudent(table));
        UtilitiesTables.headerNegrita(table);
        StudentCellRendered.setCellRenderer(table);
    }
    public TabPane getTabPane() {
        return tabPane;
    }
}
