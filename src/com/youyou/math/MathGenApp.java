package com.youyou.math;

import javax.swing.*;
import java.awt.*;

public class MathGenApp {
    private JPanel frmMain;
    private JTextArea view;
    private JPanel firstPanel;
    private JPanel ChkboxPanel;
    private JScrollPane ViewPanel;
    private JPanel StatusPanel;
    private JTextField txtTotalNum;
    private JButton btnGen;
    private JPanel typePanel;
    private JPanel chkBoxs;
    private JCheckBox ckAdd;
    private JCheckBox ckMinus;
    private JCheckBox ckMult;
    private JCheckBox ckDevide;
    private JCheckBox ckMa;
    private JCheckBox ckMm;
    private JCheckBox ckMmu;
    private JCheckBox ckDa;
    private JCheckBox ckDm;
    private JCheckBox ckAm;
    private JCheckBox ckDmod;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MathGen");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setTitle("数学练习题生成");
        Dimension sizeDim = new Dimension(800, 600);
        frame.setPreferredSize(sizeDim);
        frame.setContentPane(new MathGenApp().frmMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        center(frame);

    }

    private static void center(JFrame frame) {

        // get the size of the screen, on systems with multiple displays,
        // the primary display is used
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // calculate the new location of the window
        int w = frame.getSize().width;
        int h = frame.getSize().height;

        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // moves this component to a new location, the top-left corner of
        // the new location is specified by the x and y
        // parameters in the coordinate space of this component's parent
        frame.setLocation(x, y);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
