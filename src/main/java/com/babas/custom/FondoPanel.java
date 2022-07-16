package com.babas.custom;

import javax.swing.*;
import java.awt.*;

public class FondoPanel extends JPanel {
    private Dimension dimension;
    private Image imagen;
    public FondoPanel(Image imagen,Dimension dimension){
        this.imagen=imagen;
        this.dimension=dimension;
    }
    @Override
    public void paint(Graphics g){
        if(dimension!=null){
            imagen=new ImageIcon(imagen.getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH)).getImage();
        }
        g.drawImage(imagen,0,0,getWidth(),getHeight(),this);
        setOpaque(false);
        super.paint(g);
    }
}
