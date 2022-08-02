package com.babas.utilities;

import com.babas.controllers.Students;
import com.babas.models.Student;
import com.babas.utilitiesTables.UtilitiesTables;
import com.babas.validators.StudentValidator;
import com.babas.views.frames.FramePrincipal;
import com.moreno.Notify;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class CSVReader {

    public static void importStudents(JTable table){
        Path path=pedirNombre((Frame) table.getRootPane().getParent());
        if(path!=null){
            if(Files.exists(path)){
                try(BufferedReader bufferedReader= Files.newBufferedReader(path, StandardCharsets.US_ASCII)) {
                    String line = bufferedReader.readLine();
                    while (line!=null){
                        String[] attributes = line.split(",");
                        Student student = Student.create(attributes);
                        Set<ConstraintViolation<Student>> errors = StudentValidator.loadViolations(student);
                        if (errors.isEmpty()) {
                            if(Students.getByDni(student.getDni())==null){
                                student.save();
                                FramePrincipal.students.add(student);
                                UtilitiesTables.actualizarTabla(table);
                            }
                        }
                        line = bufferedReader.readLine();
                    }
                    Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Alumnos importados");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static Path  pedirNombre(Frame frame){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Importar estudiantes");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showDialog(frame,"Importar") == JFileChooser.APPROVE_OPTION) {
            return Paths.get(chooser.getSelectedFile().getPath());
        }
        return null;
    }
}
