package com.babas.views.dialogs;

import com.babas.App;
import com.babas.models.Candidate;
import com.babas.models.Election;
import com.babas.models.Student;
import com.babas.utilities.Utilities;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.buttonEditors.JButtonEditorStudent;
import com.babas.utilitiesTables.tablesCellRendered.StudentCellRendered;
import com.babas.utilitiesTables.tablesModels.StudentTableModel;
import com.babas.views.frames.FramePrincipal;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class DaddCandidates extends JDialog{
    private JPanel contentPane;
    private JButton btnHecho;
    private JTextField txtStudent;
    private JTable table;
    private JButton btnAddCandidate;
    private Election election;
    private StudentTableModel model;
    private JTable tableElection;
    private JButton btnClearSearch;
    private Map<Integer, String> listaFiltros = new HashMap<Integer, String>();
    private TableRowSorter<StudentTableModel> modeloOrdenado;
    private List<RowFilter<StudentTableModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;

    public DaddCandidates(Election election,JTable tableElection){
        this.election=election;
        this.tableElection=tableElection;
        initComponents();
        btnAddCandidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCandidate();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        txtStudent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filter();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void onCancel(){
        dispose();
    }
    private void addCandidate(){
        if(table.getSelectedRow()!=-1){
            verifyCandidate(model.get(table.convertRowIndexToModel(table.getSelectedRow())));
        }else{
            Utilities.sendNotification("ERROR","Seleccione un estudiante", TrayIcon.MessageType.ERROR);
        }
    }
    private void verifyCandidate(Student student){
        boolean esta=false;
        for (Candidate candidate:election.getCandidates()){
            if (Objects.equals(candidate.getStudent().getDni(), student.getDni())) {
                esta = true;
                break;
            }
        }
        if(!esta){
            Candidate candidate=new Candidate();
            candidate.setElection(election);
            candidate.setStudent(student);
            election.getCandidates().add(candidate);
            UtilitiesTables.actualizarTabla(tableElection);
        }
    }
    private void initComponents(){
        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnAddCandidate);
        loadStudents();
        setModal(true);
        pack();
        setLocationRelativeTo(null);
        loadPlaceHolders();
    }
    private void loadPlaceHolders(){
        txtStudent.putClientProperty("JTextField.placeholderText","Estudiante...");
        loadBtnClearSearch();
    }
    private void loadBtnClearSearch(){
        btnClearSearch=new JButton();
        btnClearSearch.setIcon(new ImageIcon(App.class.getResource("Icons/x24/cerrar.png")));
        btnClearSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtStudent.putClientProperty("JTextField.trailingComponent",btnClearSearch);
        btnClearSearch.setVisible(false);
        btnClearSearch.addActionListener(e -> {
            txtStudent.setText(null);
            btnClearSearch.setVisible(false);
            filter();
        });
        txtStudent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                btnClearSearch.setVisible(txtStudent.getText().length() > 0);
            }
        });
    }
    private void filter(){
        filtros.clear();
        String busqueda = txtStudent.getText().trim();
        filtros.add(RowFilter.regexFilter(("(?i)"+ busqueda),0,1,2));
        listaFiltros.put(0, busqueda);
        listaFiltros.put(1, busqueda);
        listaFiltros.put(2, busqueda);
        filtroand = RowFilter.andFilter(filtros);
        modeloOrdenado.setRowFilter(filtroand);
    }
    private void loadStudents(){
        model=new StudentTableModel(FramePrincipal.students);
        table.setModel(model);
        table.removeColumn(table.getColumn(""));
        modeloOrdenado = new TableRowSorter<>(model);
        table.setRowSorter(modeloOrdenado);
        UtilitiesTables.headerNegrita(table);
        StudentCellRendered.setCellRenderer(table,listaFiltros);
    }
}
