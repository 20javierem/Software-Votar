package com.babas.utilitiesTables.buttonEditors;

import com.babas.models.Candidate;
import com.babas.models.Election;
import com.babas.utilities.Utilities;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.utilitiesTables.tablesModels.CandidatesTableModel;
import com.babas.utilitiesTables.tablesModels.ElectionTableModel;
import com.babas.views.dialogs.DeditElection;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorCandidate extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButtonAction button;
    private JTable table;
    private Election election;

    public JButtonEditorCandidate(Election election,JTable table) {
        this.table=table;
        this.election=election;
        button=new JButtonAction("x24/cerrar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(table.getSelectedRow()!=-1){
            Candidate candidate=((CandidatesTableModel) table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow()));
            if(candidate.getVotes().isEmpty()){
                int siono=JOptionPane.showOptionDialog(null,"Está seguro de eliminar al candidato?","Eliminar candidato",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,  null,new Object[] { "Si", "No"},"Si");
                if(siono==0){
                    election.getCandidates().remove(candidate);
                    if(candidate.getId()!=null){
                        candidate.setElection(null);
                        candidate.save();
                        election.getCandidatesChanges().add(candidate);
                    }
                }
            }else{
                Utilities.sendNotification("ERROR","El candidato tiene votos a favor", TrayIcon.MessageType.ERROR);
            }
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
