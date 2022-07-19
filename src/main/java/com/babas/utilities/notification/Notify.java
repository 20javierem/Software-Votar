package com.babas.utilities.notification;

import com.babas.App;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Notify extends JDialog {

    private Animator animator;
    private final JFrame fram;
    private boolean showing;
    private Thread thread;
    private int animate = 10;
    private BufferedImage imageShadow;
    private int shadowSize = 6;
    private Notify.Type type;
    private Notify.Location location;
    private JButton btnClose;
    private javax.swing.JLabel lblTittle;
    private javax.swing.JLabel lblMessage;
    private JPanel contentPane;
    private JLabel lblIcon;
    private JPanel pane;
    private JPanel pane1;
    private String tittle;
    private String message;

    public Notify(JFrame fram, Notify.Type type, Notify.Location location, String tittle,String message) {
        super(fram);
        this.fram = fram;
        this.type = type;
        this.location = location;
        this.message=message;
        this.tittle=tittle;
        initComponents();
        initAnimator();
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeNotification();
            }
        });
    }

    private void initComponents() {
        setContentPane(contentPane);
        paint();
        setUndecorated(true);
        setFocusableWindowState(false);
        pack();
        if (type == Notify.Type.SUCCESS) {
            lblIcon.setIcon(new javax.swing.ImageIcon(App.class.getResource("Icons/x30/sucess.png")));
        } else if (type == Notify.Type.INFO) {
            lblIcon.setIcon(new javax.swing.ImageIcon(App.class.getResource("Icons/x30/info.png")));
        } else {
            lblIcon.setIcon(new javax.swing.ImageIcon(App.class.getResource("Icons/x30/warning.png")));
        }
        lblTittle.setText(tittle);
        lblMessage.setText(message);
    }

    private void initAnimator() {
        TimingTarget target = new TimingTargetAdapter() {
            private int x=0;
            private int y=0;
            private int top;
            private boolean top_to_bot;

            @Override
            public void begin() {
                if (!showing) {
                    setOpacity(0f);
                    int margin = 10;
                    if (location == Notify.Location.TOP_CENTER) {
                        x = fram.getX() + ((fram.getWidth() - getWidth()) / 2);
                        y = fram.getY()+margin;
                        top_to_bot = true;
                    } else if (location == Notify.Location.TOP_RIGHT) {
                        x = fram.getX() + fram.getWidth() - getWidth() - 2*margin;
                        y = fram.getY()+margin;
                        top_to_bot = true;
                    } else if (location == Notify.Location.TOP_LEFT) {
                        x = fram.getX() + 2*margin;
                        y = fram.getY()+margin;
                        top_to_bot = true;
                    } else if (location == Notify.Location.BOTTOM_CENTER) {
                        x = fram.getX() + ((fram.getWidth() - getWidth()) / 2);
                        y = fram.getY() + fram.getHeight() - getHeight();
                        top_to_bot = false;
                    } else if (location == Notify.Location.BOTTOM_RIGHT) {
                        x = fram.getX() + fram.getWidth() - getWidth() - 2*margin;
                        y = fram.getY() + fram.getHeight() - getHeight() - margin;
                        top_to_bot = false;
                    } else if (location == Notify.Location.BOTTOM_LEFT) {
                        x = fram.getX() + 2*margin;
                        y = fram.getY() + fram.getHeight() - getHeight() - margin;
                        top_to_bot = false;
                    } else {
                        x = fram.getX() + ((fram.getWidth() - getWidth()) / 2);
                        y = fram.getY() + ((fram.getHeight() - getHeight()) / 2);
                        top_to_bot = true;
                    }
                    top = y;
                    setLocation(x,y);
                    setVisible(true);
                }
            }
            @Override
            public void end() {
                showing = !showing;
                if (showing) {
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sleep();
                            closeNotification();
                        }
                    });
                    thread.start();
                } else {
                    dispose();
                }
            }
            @Override
            public void timingEvent(float fraction) {
                float alpha;
                if (showing) {
                    alpha= 1f - fraction;
                    int y = (int) ((1f - fraction) * animate);
                    if (top_to_bot) {
                        setLocation(x, top + y);
                    } else {
                        setLocation(x, top - y);
                    }
                } else {
                    alpha = fraction;
                    int y = (int) (fraction * animate);
                    if (top_to_bot) {
                        setLocation(x, top + y);
                    } else {
                        setLocation(x, top - y);
                    }
                }
                setOpacity(alpha);
            }
        };
        animator = new Animator(500, target);
        animator.setResolution(5);
    }

    public void showNotification() {
        animator.start();
    }

    public void closeNotification() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        if (animator.isRunning()) {
            if (!showing) {
                animator.stop();
                showing = true;
                animator.start();
            }
        } else {
            showing = true;
            animator.start();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
    }

    private void paint(){
        pane.setBackground(pane.getBackground().brighter());
        if (type == Type.SUCCESS) {
            contentPane.setBackground(new Color(18, 163, 24));
        } else if (type == Type.INFO) {
            contentPane.setBackground(new Color(28, 139, 206));
        } else {
            contentPane.setBackground(new Color(241, 196, 15));
        }
    }


    public enum Type {
        SUCCESS, INFO, WARNING
    }

    public enum Location {
        TOP_CENTER, TOP_RIGHT, TOP_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT, BOTTOM_LEFT, CENTER
    }

}

