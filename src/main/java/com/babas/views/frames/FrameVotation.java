package com.babas.views.frames;

import com.babas.App;
import com.babas.custom.FondoPanel;
import com.babas.models.Election;

import javax.swing.*;

public class FrameVotation extends JDialog {
    private JPanel contentPane;
    private JLabel btnNameSchool;
    private JButton btnVotation;
    private JLabel lblDescriptionElection;
    private JPanel paneVotation;
    private JLabel lblImage;
    private Election election;

    public FrameVotation(Election election){
        this.election=election;
        initComponents();
    }
    private void initComponents(){
        setContentPane(contentPane);
        setResizable(false);
        loadElection();
        setModal(true);
        pack();
        setLocationRelativeTo(null);
    }
    private void loadElection(){
        lblDescriptionElection.setText(election.getDescription());
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        paneVotation=new FondoPanel(new ImageIcon(App.class.getResource("Images/votar.jpg")).getImage(),null);
    }
}
