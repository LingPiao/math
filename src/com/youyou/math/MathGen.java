package com.youyou.math;

import javax.swing.*;
import java.awt.*;

public class MathGen {
    private JTextField txtTotalNum;
    private JPanel MainFrame;
    private JLabel lblTotalNum;
    private JButton button1;
    private JCheckBox checkBox1;

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
        frame.setContentPane(new MathGen().MainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

    }
}
