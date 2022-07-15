package com.babas.views.frames;

import com.babas.controllers.Elections;
import com.babas.controllers.Students;
import com.babas.custom.JPanelGradiente;
import com.babas.custom.TabbedPane;
import com.babas.models.Election;
import com.babas.models.Student;
import com.babas.utilities.Utilities;
import com.babas.views.Tabs.TabElections;
import com.babas.views.Tabs.TabStudents;
import com.babas.views.Tabs.TabVotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class FramePrincipal extends JFrame{
    private JPanel contentPane;
    private JButton btnVotation;
    private JButton btnElecctions;
    private JLabel btnNameSchool;
    private JPanel paneContent;
    private JSplitPane splitPane;
    private TabbedPane tabbedPane;
    private JLabel lblDate;
    private JButton btnStudents;
    private Election election;
    private TabVotation tabVotation;
    public static List<Student> students;
    public static List<Election> elections;
    private TabStudents tabStudents;
    private TabElections tabElections;

    public FramePrincipal(){
        initComponents();
        btnVotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadVotation();
            }
        });
        btnStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStudents();
            }
        });
        btnElecctions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadElections();
            }
        });
    }
    public void loadStudents(){
        if(tabStudents ==null){
            tabStudents =new TabStudents();
        }
        if (tabbedPane.indexOfTab(tabStudents.getTabPane().getTitle()) == -1) {
            tabStudents = new TabStudents();
            tabbedPane.addTab(tabStudents.getTabPane().getTitle(), tabStudents.getTabPane().getIcon(), tabStudents.getTabPane());

        }
        tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(tabStudents.getTabPane().getTitle()));
    }
    public void loadElections(){
        if(tabElections ==null){
            tabElections =new TabElections();
        }
        if (tabbedPane.indexOfTab(tabElections.getTabPane().getTitle()) == -1) {
            tabElections = new TabElections();
            tabbedPane.addTab(tabElections.getTabPane().getTitle(), tabElections.getTabPane().getIcon(), tabElections.getTabPane());

        }
        tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(tabElections.getTabPane().getTitle()));
    }
    public void loadVotation() {
        String dni = JOptionPane.showInputDialog ( "Introduzca su DNI:" );
        if(dni!=null){
            if(!dni.isBlank()){
                Student student= Students.getByDni(dni);
                if(student!=null){
                    if(tabVotation ==null){
                        tabVotation =new TabVotation(election);
                    }
                    if (tabbedPane.indexOfTab(tabVotation.getTabPane().getTitle()) == -1) {
                        tabVotation = new TabVotation(election);
                        tabbedPane.addTab(tabVotation.getTabPane().getTitle(), tabVotation.getTabPane().getIcon(), tabVotation.getTabPane());

                    }
                    tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(tabVotation.getTabPane().getTitle()));
                }else{
                    JOptionPane.showMessageDialog(this,"DNI no encontrado");
                }
            }
        }
    }
    private void initComponents(){
        setContentPane(contentPane);
        setTitle("Software-votación");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        pack();
        setLocationRelativeTo(null);
        election=new Election();
        lblDate.setText(Utilities.formatoFechaHora.format(new Date()));
        loadData();
    }
    private void loadData(){
        students=Students.getTodos();
        elections= Elections.getTodos();
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        paneContent=new JPanelGradiente(new Color(0xE12525),new Color(0xEAEA56));
    }
}
