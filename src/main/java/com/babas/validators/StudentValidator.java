package com.babas.validators;

import com.babas.models.Student;
import com.babas.utilities.Utilities;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

import static com.babas.validators.ProgramValidator.PROGRAMA_VALIDATOR;

public class StudentValidator {
    public static Set<ConstraintViolation<Student>> loadViolations(Student student) {
        Set<ConstraintViolation<Student>> violations = PROGRAMA_VALIDATOR.validate(student);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Student>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Student> error1= (ConstraintViolation<Student>) errores[0];
        String error = "Verfique el campo: "+error1.getMessage();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}