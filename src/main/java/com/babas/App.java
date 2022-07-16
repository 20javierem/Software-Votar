package com.babas;

import com.babas.models.Election;
import com.babas.utilities.Babas;
import com.babas.utilities.Utilities;
import com.babas.views.frames.FramePrincipal;
import com.babas.views.frames.FrameVotation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Utilities.tema("genome");
        Babas.initialize();
        FramePrincipal framePrincipal=new FramePrincipal();
        framePrincipal.setVisible(true);
    }
}
