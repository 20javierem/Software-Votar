package com.babas.views.models;

import com.babas.custom.JPanelGradiente;
import com.babas.models.Candidate;

import javax.swing.*;
import java.awt.*;

public class ViewCandidate {
    private JPanel contentPane;
    private JPanel panel1;
    private JLabel lblImage;
    private JRadioButton btnSelected;
    private JLabel lblCandidate;
    private JLabel lblDni;
    private Candidate candidate;

    public ViewCandidate(Candidate candidate){
        this.candidate=candidate;
        initComponents();
    }
    private void initComponents(){
        lblCandidate.setText(candidate.getStudent().getName());
        lblDni.setText(candidate.getStudent().getDni());
    }
    public JPanel getContentPane(){
        return contentPane;
    }

    public JRadioButton getBtnSelected(){
        return btnSelected;
    }
    public Candidate getCandidate(){
        return candidate;
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        panel1=new JPanelGradiente(new Color(0xE76BCA),new Color(0xD4AE5E));
    }
}
