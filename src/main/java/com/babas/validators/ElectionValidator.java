package com.babas.validators;

import com.babas.models.Election;
import com.babas.utilities.Utilities;
import com.moreno.Notify;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static com.babas.validators.ProgramValidator.PROGRAMA_VALIDATOR;

public class ElectionValidator {

    public static Set<ConstraintViolation<Election>> loadViolations(Election election) {
        Set<ConstraintViolation<Election>> violations = PROGRAMA_VALIDATOR.validate(election);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Election>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Election> error1= (ConstraintViolation<Election>) errores[0];
        String error = "Verfique el campo: "+error1.getMessage();
        Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR",error);

    }
}
