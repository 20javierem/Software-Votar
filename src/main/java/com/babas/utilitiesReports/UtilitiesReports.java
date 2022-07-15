package com.babas.utilitiesReports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerPanel;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class UtilitiesReports {

    public static JasperViewer getjasperViewer(JasperReport report, Map<String, Object> parameters, JRBeanArrayDataSource sp, boolean isExitOnClose){
        try {
            JasperViewer jasperViewer=new JasperViewer(JasperFillManager.fillReport(report,parameters,sp),isExitOnClose);
            JPanel panel= (JPanel) jasperViewer.getRootPane().getContentPane().getComponent(0);
            JRViewer visor= (JRViewer) panel.getComponent(0);
            JRViewerToolbar toolbar= (JRViewerToolbar) visor.getComponent(0);
            toolbar.getInsets().set(2,2,2,2);
            toolbar.setMaximumSize(new Dimension(toolbar.getWidth(),34));
            toolbar.setMinimumSize(new Dimension(toolbar.getWidth(),34));
            toolbar.setPreferredSize(new Dimension(toolbar.getWidth(),34));
            toolbar.setLayout(new FlowLayout(FlowLayout.CENTER,2,3));
            JRViewerPanel jrViewer= (JRViewerPanel) visor.getComponent(1);
            JScrollPane jScrollPane= (JScrollPane) jrViewer.getComponent(0);
            jScrollPane.setBorder(BorderFactory.createEmptyBorder());
            visor.getComponent(2).setMaximumSize(new Dimension(visor.getComponent(2).getWidth(),34));
            visor.getComponent(2).setPreferredSize(new Dimension(visor.getComponent(2).getWidth(),34));
            ((JPanel)visor.getComponent(2)).setLayout(new FlowLayout(FlowLayout.CENTER));
            Font font=new Font(new JTextField().getFont().getFontName(),Font.PLAIN,14);
            ((JPanel)visor.getComponent(2)).getComponent(0).setFont(font);
            for (Component component: toolbar.getComponents()){
                if(component instanceof JTextField||component instanceof JComboBox){
                    component.setMaximumSize(new Dimension(component.getMaximumSize().width,30));
                    component.setPreferredSize(new Dimension(component.getMaximumSize().width,30));
                    component.setMinimumSize(new Dimension(component.getMaximumSize().width,30));
                }else{
                    component.setMaximumSize(new Dimension(30,30));
                    component.setPreferredSize(new Dimension(30,30));
                    component.setMinimumSize(new Dimension(30,30));
                }
            }
            return jasperViewer;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }

}
