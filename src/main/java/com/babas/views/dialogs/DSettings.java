package com.babas.views.dialogs;

import com.babas.utilities.Babas;
import com.babas.utilities.Propiedades;
import com.babas.utilities.Utilities;
import com.babas.views.frames.FramePrincipal;
import com.moreno.Notify;

import javax.swing.*;
import java.awt.event.*;

public class DSettings extends JDialog{
    private JPanel contentPane;
    private JComboBox cbbTema;
    private JButton btnAplyThme;
    private JTextField txtNameInstitution;
    private JButton hechoButton;
    private JTextField txtDatabaseUrl;
    private JButton btnTryConection;
    private JButton btnSave;
    private JTextField txtPassword;
    private Propiedades propiedades;
    private FramePrincipal framePrincipal;

    public DSettings(Propiedades propiedades,FramePrincipal framePrincipal){
        super(framePrincipal,"Condiguraciones",true);
        this.propiedades=propiedades;
        this.framePrincipal=framePrincipal;
        initComponents();
        hechoButton.addActionListener(new ActionListener() {
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
        btnAplyThme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplyTheme();
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
    private void aplyTheme(){
        Utilities.setTema(cbbTema.getSelectedItem().toString());
        Utilities.updateComponents(getRootPane());
        Utilities.updateComponents(framePrincipal.getRootPane());
    }

    private void initComponents(){
        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnSave);
        loadSettings();
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    private void save(){
        if(txtPassword.getText().length()>=6){
            propiedades.setTema(cbbTema.getSelectedItem().toString());
            propiedades.setPassword(txtPassword.getText());
            propiedades.guardar();
            FramePrincipal.school.setName(txtNameInstitution.getText());
            FramePrincipal.school.save();
            Babas.session.getEntityManagerFactory().getProperties().put("connection.url",txtDatabaseUrl.getText());
            framePrincipal.loadSchool();
            onDispose();
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Cambios guardados");
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ADVERTENCIA","La contraseña debe ser de logitud mayor a 5");
        }
    }
    private void onDispose(){
        dispose();
    }

    private void onCancel(){
        FramePrincipal.school.refresh();
        framePrincipal.loadSchool();
        Utilities.setTema(propiedades.getTema());
        Utilities.updateComponents(getRootPane());
        Utilities.updateComponents(framePrincipal.getRootPane());
        onDispose();
    }

    private void loadSettings(){
        txtNameInstitution.setText(FramePrincipal.school.getName());
        txtPassword.setText(propiedades.getPassword());
        txtDatabaseUrl.setText(String.valueOf(Babas.session.getEntityManagerFactory().getProperties().get("connection.url")));
        cbbTema.setSelectedIndex(propiedades.getTema().equals("Claro")?0:1);
    }
}
