package com.babas.views.dialogs;

import com.babas.models.Student;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.validators.StudentValidator;
import com.babas.views.frames.FramePrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DeditStudent extends JDialog {
    private JPanel contentPane;
    private JTextField txtName;
    private JTextField txtDni;
    private JButton btnSave;
    private JButton btnHecho;
    private Student student;
    private JTable table;
    private boolean update=false;

    public DeditStudent(JTable table){
        this(new Student(),table);
    }
    public DeditStudent(Student student,JTable table){
        this.table=table;
        this.student=student;
        initComponents();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
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
        if(update){
            student.refresh();
        }
        dispose();
    }
    private void initComponents(){
        setContentPane(contentPane);
        if(student.getId()!=null){
            setTitle("Editar estudiante");
            btnSave.setText("Guardar");
            btnHecho.setText("Cancelar");
            update=true;
        }
        loadStudent();
        getRootPane().setDefaultButton(btnSave);
        setModal(true);
        pack();
        setLocationRelativeTo(null);
    }
    private void save(){
        student.setDni(txtDni.getText());
        student.setName(txtName.getText());
        Set<ConstraintViolation<Student>> errors = StudentValidator.loadViolations(student);
        if (errors.isEmpty()) {
            student.save();
            if(update){
                onCancel();
            }else{
                FramePrincipal.students.add(student);
                student=new Student();
                loadStudent();
            }
        } else {
            StudentValidator.mostrarErrores(errors);
        }
        if(table!=null){
            UtilitiesTables.actualizarTabla(table);
        }
    }
    private void loadStudent(){
        txtDni.setText(student.getDni());
        txtName.setText(student.getName());
    }
}
