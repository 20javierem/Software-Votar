package com.babas.views.frames;

import com.babas.controllers.Elections;
import com.babas.controllers.Schools;
import com.babas.controllers.Students;
import com.babas.custom.TabbedPane;
import com.babas.models.Election;
import com.babas.models.School;
import com.babas.models.Student;
import com.babas.utilities.Propiedades;
import com.babas.utilities.Utilities;
import com.babas.views.Tabs.TabElections;
import com.babas.views.Tabs.TabStudents;
import com.babas.views.dialogs.DSettings;
import com.babas.views.dialogs.DstarVotation;
import com.moreno.Notify;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class FramePrincipal extends JFrame{
    private JPanel contentPane;
    private JButton btnVotation;
    private JToggleButton btnElecctions;
    private JLabel lblNameSchool;
    private JSplitPane splitPane;
    private TabbedPane tabbedPane;
    private JToggleButton btnStudents;
    private JButton btnSettings;
    public static List<Student> students;
    public static List<Election> elections;
    public static List<Election> electionsActives;
    public static School school;
    private TabStudents tabStudents;
    private TabElections tabElections;
    private Propiedades propiedades;
    private JFrame frame=this;
    private boolean change=true;

    public FramePrincipal(Propiedades propiedades){
        this.propiedades=propiedades;
        initComponents();
        btnVotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStartElection();
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
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSettings();
            }
        });
    }
    private void loadSettings(){
        DSettings dSettings =new DSettings(propiedades,this);
        dSettings.setVisible(true);
        loadSchool();
    }
    public void loadStudents(){
        if(tabStudents ==null){
            tabStudents =new TabStudents();
        }
        if (tabbedPane.indexOfComponent(tabStudents.getTabPane()) == -1) {
            tabStudents = new TabStudents();
            tabStudents.getTabPane().setOption(btnStudents);
            tabbedPane.addTab(tabStudents.getTabPane().getTitle(), tabStudents.getTabPane().getIcon(), tabStudents.getTabPane());
        }
        tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(tabStudents.getTabPane()));
    }
    public void loadElections(){
        if(tabElections ==null){
            tabElections =new TabElections();
        }
        if (tabbedPane.indexOfComponent(tabElections.getTabPane()) == -1) {
            tabElections = new TabElections();
            tabElections.getTabPane().setOption(btnElecctions);
            tabbedPane.addTab(tabElections.getTabPane().getTitle(), tabElections.getTabPane().getIcon(), tabElections.getTabPane());

        }
        tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(tabElections.getTabPane()));
    }
    private void loadStartElection(){
        if(!electionsActives.isEmpty()){
            DstarVotation dstarVotation=new DstarVotation(propiedades,this);
            dstarVotation.setVisible(true);
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","No se encontraron elecciones activas");
        }

    }
    private void initComponents(){
        Utilities.setJFrame(this);
        setContentPane(contentPane);
        setTitle("Software-votación");
        loadData();
        loadSchool();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        pack();
        setLocationRelativeTo(null);
        Utilities.setPropiedades(propiedades);
    }
    public void loadSchool(){
        lblNameSchool.setText(school.getName());
    }
    private void loadData(){
        students=Students.getTodos();
        elections= Elections.getTodos();
        electionsActives=Elections.getActives();
        school= Schools.get(1);
        if(school==null){
            school=new School();
            school.setName("");
            school.save();
        }
    }
}
