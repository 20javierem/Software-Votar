package com.babas;

import com.babas.utilities.notification.Notification;

import java.awt.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Notification notification=new Notification(new Frame(), Notification.Type.INFO, Notification.Location.TOP_CENTER,"exito");
        notification.showNotification();
//        Propiedades propiedades=new Propiedades();
//        propiedades.guardar();
//        Utilities.setTema(propiedades.getTema());
//        Babas.initialize();
//        FramePrincipal framePrincipal=new FramePrincipal(propiedades);
//        framePrincipal.setVisible(true);
    }
}
