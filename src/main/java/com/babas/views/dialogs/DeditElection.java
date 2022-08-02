package com.babas.views.dialogs;

import com.babas.custom.ModelPieChart;
import com.babas.custom.PieChart;
import com.babas.models.Election;
import com.babas.utilities.Babas;
import com.babas.utilities.Colors;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.buttonEditors.JButtonEditorCandidate;
import com.babas.utilitiesTables.buttonEditors.JButtonEditorStudent;
import com.babas.utilitiesTables.tablesCellRendered.CandidateCellRendered;
import com.babas.utilitiesTables.tablesModels.CandidatesTableModel;
import com.babas.validators.ElectionValidator;
import com.babas.views.frames.FramePrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DeditElection extends JDialog{
    private JPanel contentPane;
    private JTextField txtDescription;
    private JTable table;
    private JButton btnAddCandidates;
    private JButton btnSave;
    private JButton btnHecho;
    private JLabel lblVotesBlank;
    private JLabel lblTotalVotes;
    private JTabbedPane tabbedPane1;
    private PieChart pieChart;
    private Election election;
    private boolean update=false;
    private JTable tableElection;
    private CandidatesTableModel model;

    public DeditElection(Election election,JTable tableElection){
        super((Frame) tableElection.getRootPane().getParent(),"Nueva elección",true);
        this.election=election;
        this.tableElection=tableElection;
        initComponents();
        btnAddCandidates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAddCandidates();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
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
        if(election.getId()!=null){
            election.getCandidatesChanges().forEach(candidate -> {
                candidate.setElection(election);
                candidate.save();
            });
            election.refresh();
        }
        dispose();
    }
    private void save(){
        election.setActive(true);
        election.setDescription(txtDescription.getText().trim().toUpperCase());
        Set<ConstraintViolation<Election>> errors = ElectionValidator.loadViolations(election);
        if (errors.isEmpty()) {
            election.save();
            election.getCandidates().forEach(Babas::save);
            if(update){
                dispose();
            }else{
                FramePrincipal.electionsActives.add(election);
                FramePrincipal.elections.add(election);
                election=new Election();
                loadElection();
            }
        } else {
            ElectionValidator.mostrarErrores(errors);
        }
        if(tableElection!=null){
            UtilitiesTables.actualizarTabla(tableElection);
        }
    }
    public DeditElection(JTable tableElection){
        this(new Election(),tableElection);
    }
    private void loadAddCandidates(){
        DaddCandidates daddCandidates=new DaddCandidates(election,table);
        daddCandidates.setVisible(true);
    }
    private void initComponents(){
        setContentPane(contentPane);
        if(election.getId()!=null){
            setTitle("Editar elección");
            btnSave.setText("Guardar");
            btnHecho.setText("Cancelar");
            update=true;
        }
        loadElection();
        getRootPane().setDefaultButton(btnSave);
        pack();
        setLocationRelativeTo(null);
    }
    private void loadElection(){
        election.getCandidatesChanges().clear();
        txtDescription.setText(election.getDescription());
        lblTotalVotes.setText(String.valueOf(election.getTotalVotes().size()));
        lblVotesBlank.setText(String.valueOf(election.getVotosBlank().size()));
        model=new CandidatesTableModel(election.getCandidates());
        table.setModel(model);
        table.getColumnModel().getColumn(model.getColumnCount() - 1).setCellEditor(new JButtonEditorCandidate(election,table));
        UtilitiesTables.headerNegrita(table);
        CandidateCellRendered.setCellRenderer(table);
        if(!election.isActive()){
            table.removeColumn(table.getColumn(""));
            btnAddCandidates.setVisible(false);
            txtDescription.setEditable(false);
            txtDescription.setBorder(null);
        }
        loadPiechart();
    }
    private void loadPiechart(){
        pieChart.setChartType(PieChart.PeiChartType.DONUT_CHART);
        if(!election.getVotosBlank().isEmpty()){
            pieChart.addData(new ModelPieChart("Votos en blanco", election.getVotosBlank().size(), Colors.colors[0]));
        }
        for(int i=0;i<election.getCandidates().size();i++){
            pieChart.addData(new ModelPieChart(election.getCandidates().get(i).getStudent().getName(), election.getCandidates().get(i).getVotes().size(), Colors.colors[i+1]));
        }

    }
}
