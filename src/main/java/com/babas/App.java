package com.babas;

import com.babas.utilities.Babas;
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
        Babas.initialize();
        Utilities.tema("genome");
        FramePrincipal framePrincipal=new FramePrincipal();
        framePrincipal.setVisible(true);
    }
}
