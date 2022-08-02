package com.babas.views.dialogs;

import com.babas.App;
import com.babas.controllers.Students;
import com.babas.controllers.Votes;
import com.babas.custom.FondoPanel;
import com.babas.models.Election;
import com.babas.models.Student;
import com.babas.utilities.Propiedades;
import com.babas.utilities.Utilities;
import com.babas.views.frames.FramePrincipal;
import com.moreno.Notify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Dvotation extends JFrame {
    private JPanel contentPane;
    private JLabel lblNameSchool;
    private JButton btnVotation;
    private JLabel lblDescriptionElection;
    private JPanel paneVotation;
    private JButton btnEndVotation;
    private JLabel lblImage;
    private Election election;
    private FramePrincipal framePrincipal;

    public Dvotation(Election election,FramePrincipal framePrincipal){
        Utilities.getPropiedades().setElection(String.valueOf(election.getId()));
        Utilities.getPropiedades().guardar();
        this.election=election;
        this.framePrincipal=framePrincipal;
        initComponents();
        btnVotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startVote();
            }
        });
        btnEndVotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endElection();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onDispose();
            }
        });
    }
    private void onDispose(){
        framePrincipal.dispose();
    }
    private void endElection(){
        String password=JOptionPane.showInputDialog(null,"TERMINAR ELECCIÓN","Ingrese la contraseña",JOptionPane.PLAIN_MESSAGE);
        if(password!=null){
            if(!password.isBlank()){
                if(Utilities.getPropiedades().getPassword().equals(password)){
                    Utilities.getPropiedades().setElection("");
                    Utilities.getPropiedades().guardar();
                    election.setActive(false);
                    election.save();
                    FramePrincipal.electionsActives.remove(election);
                    dispose();
                    framePrincipal.setVisible(true);
                }else{
                    Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR","La constraseña es incorrecta");
                }
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR","Ingrese la contraseña");
                endElection();
            }
        }
    }
    private void startVote(){
        String dni=JOptionPane.showInputDialog(this,"DNI","Ingrese su dni",JOptionPane.PLAIN_MESSAGE);
        if(dni!=null){
            if(!dni.isBlank()){
                Student student= Students.getByDni(dni);
                if(student!=null){
                    if(Votes.getVote(student,election)==null){
                        DVote dVote=new DVote(this,election,student);
                        dVote.setVisible(true);
                    }else{
                        Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR","El estudiante ya realizó el voto");
                        startVote();
                    }
                }else{
                    Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR","No se encontró al estudiante");
                    startVote();
                }
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR","Ingrese el dni del estudiante");
                startVote();
            }
        }
    }
    private void initComponents(){
        setContentPane(contentPane);
        setTitle(election.getDescription());
        setResizable(false);
        getRootPane().setDefaultButton(btnVotation);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        loadElection();
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
