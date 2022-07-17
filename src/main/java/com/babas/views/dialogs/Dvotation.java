package com.babas.views.dialogs;

import com.babas.App;
import com.babas.controllers.Students;
import com.babas.controllers.Votes;
import com.babas.custom.FondoPanel;
import com.babas.models.Election;
import com.babas.models.Student;
import com.babas.models.Vote;
import com.babas.utilities.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dvotation extends JDialog {
    private JPanel contentPane;
    private JLabel btnNameSchool;
    private JButton btnVotation;
    private JLabel lblDescriptionElection;
    private JPanel paneVotation;
    private JLabel lblImage;
    private Election election;

    public Dvotation(Election election){
        this.election=election;
        initComponents();
        btnVotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startVote();
            }
        });
    }
    private void startVote(){
        String dni=JOptionPane.showInputDialog(null,"DNI","Ingrese su dni",JOptionPane.PLAIN_MESSAGE);
        if(dni!=null){
            if(!dni.isBlank()){
                Student student= Students.getByDni(dni);
                if(student!=null){
                    if(Votes.getVote(student,election)==null){
                        DVote dVote=new DVote(election,student);
                        dVote.setVisible(true);
                    }else{
                        Utilities.sendNotification("Error","El estudiante ya realizó el voto", TrayIcon.MessageType.INFO);
                    }
                }else{
                    startVote();
                    Utilities.sendNotification("Error","No se encontró al estudiante", TrayIcon.MessageType.ERROR);
                }
            }else{
                startVote();
            }
        }
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
