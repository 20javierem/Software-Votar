package com.babas;

import com.babas.controllers.Elections;
import com.babas.utilities.Babas;
import com.babas.utilities.Propiedades;
import com.babas.utilities.Utilities;
import com.babas.views.dialogs.Dvotation;
import com.babas.views.frames.FramePrincipal;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Propiedades propiedades=new Propiedades();
        Utilities.setTema(propiedades.getTema());
        Babas.initialize();
        FramePrincipal framePrincipal=new FramePrincipal(propiedades);
        framePrincipal.setVisible(true);
        if(propiedades.getElection()!=null){
            framePrincipal.setVisible(false);
            Dvotation dvotation=new Dvotation(Elections.get(propiedades.getElection()),framePrincipal);
            dvotation.setVisible(true);
        }

    }
}
