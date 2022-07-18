package com.babas;

import com.babas.utilities.Babas;
import com.babas.utilities.Propiedades;
import com.babas.utilities.Utilities;
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
        propiedades.guardar();
        Utilities.setTema(propiedades.getTema());
        Babas.initialize();
        FramePrincipal framePrincipal=new FramePrincipal(propiedades);
        framePrincipal.setVisible(true);
    }
}
