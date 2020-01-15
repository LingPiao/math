package com.youyou.math;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MathGenApp {

    private static JFrame frame;

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
    private JCheckBox ckBr;
    private JButton btnView;
    private JLabel lblStatus;
    private JCheckBox ckAns;


    private int totalQuestionNum = 0;
    private boolean validated = true;

    private MathTool mathTool = new MathTool();
    private static Map<String, MathTool.Type> checkBoxNameMap2Type = new HashMap<>();

    static {
        checkBoxNameMap2Type.put("加", MathTool.Type.Add);
        checkBoxNameMap2Type.put("减", MathTool.Type.Minus);
        checkBoxNameMap2Type.put("乘", MathTool.Type.Multiply);
        checkBoxNameMap2Type.put("除", MathTool.Type.Divide);
        checkBoxNameMap2Type.put("乘加", MathTool.Type.MultAdd);
        checkBoxNameMap2Type.put("乘减", MathTool.Type.MultMinus);
        checkBoxNameMap2Type.put("乘乘", MathTool.Type.MultMult);
        checkBoxNameMap2Type.put("除加", MathTool.Type.DivideAdd);
        checkBoxNameMap2Type.put("除减", MathTool.Type.DivideMinus);
        checkBoxNameMap2Type.put("加减", MathTool.Type.AddMinus);
        checkBoxNameMap2Type.put("除余", MathTool.Type.Mod);
        checkBoxNameMap2Type.put("括号", MathTool.Type.Brackets);
    }

    public MathGenApp() {


        btnGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblStatus.setText("OK");
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                view.setText("");

                setTotalQuestionNum();
                Map<Integer, Integer> qstNums = getSelectedTypeAndRate();
                if (validated) {
                    String[] qs = mathTool.genMath(totalQuestionNum, qstNums);
                    StringBuilder sb = new StringBuilder();
                    sb.append(qs[0]);
                    if (ckAns.isSelected()) {
                        sb.append("\n=========================答案===============================\n");
                        sb.append(qs[1]);
                    }
                    view.setText(sb.toString());
                    lblStatus.setText("");
                }
            }
        });
    }

    private void setTotalQuestionNum() {
        if (txtTotalNum.getText().trim().length() < 2) {
            showMsg("题量应为:10~200 以内的数值!");
        } else {
            try {
                totalQuestionNum = Integer.parseInt(txtTotalNum.getText().trim());
            } catch (Exception e) {
                showMsg("题量应为:10~200 以内的数值!");
            }
        }
    }

    private void showMsg(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "提示", JOptionPane.WARNING_MESSAGE);
    }

    private Map<Integer, Integer> getSelectedTypeAndRate() {
        Map<Integer, Integer> expectedNums = mathTool.getDefaultRate();
        java.util.List<JCheckBox> allTypes = new ArrayList<>();
        allTypes.add(ckAdd);
        allTypes.add(ckMinus);
        allTypes.add(ckMult);
        allTypes.add(ckDevide);
        allTypes.add(ckMa);
        allTypes.add(ckMm);
        allTypes.add(ckMmu);
        allTypes.add(ckDa);
        allTypes.add(ckDm);
        allTypes.add(ckAm);
        allTypes.add(ckDmod);
        allTypes.add(ckBr);

        java.util.List<JCheckBox> selected = new ArrayList<>();
        for (JCheckBox ck : allTypes) {
            if (ck.isSelected()) {
                selected.add(ck);
            }
        }
        if (selected.size() < 1) {
            validated = false;
            showMsg("请选择题型!");
            return expectedNums;
        }

        if (selected.size() > totalQuestionNum) {
            validated = false;
            showMsg("题量少于题型!");
            return expectedNums;
        }

        if (selected.size() == allTypes.size()) {//All selected
            // 60 Questions
            // 3=x, 4=÷, 60%
            int r1 = (int) (totalQuestionNum * 0.3);
            expectedNums.put(MathTool.Type.Multiply.getCode(), r1);
            expectedNums.put(MathTool.Type.Divide.getCode(), r1);

            // 1=+, 2=-, 14%
            int r2 = (int) (totalQuestionNum * 0.07);
            expectedNums.put(MathTool.Type.Add.getCode(), r2);
            expectedNums.put(MathTool.Type.Minus.getCode(), r2);

            // 5=x+, 6=x-, 7=xx, 8=÷+,9=÷-, 10=+-, 11=mod  Rate: 21%, each: 3%
            int r3 = (int) (totalQuestionNum * 0.03);
            expectedNums.put(MathTool.Type.MultAdd.getCode(), r3);
            expectedNums.put(MathTool.Type.MultMinus.getCode(), r3);
            expectedNums.put(MathTool.Type.MultMult.getCode(), r3);
            expectedNums.put(MathTool.Type.DivideAdd.getCode(), r3);
            expectedNums.put(MathTool.Type.DivideMinus.getCode(), r3);
            expectedNums.put(MathTool.Type.AddMinus.getCode(), r3);
            expectedNums.put(MathTool.Type.Mod.getCode(), r3);

            // 12=() %5
            int r4 = (int) (totalQuestionNum * 0.05);
            expectedNums.put(MathTool.Type.Brackets.getCode(), r4);

            int rest = totalQuestionNum - r1 * 2 - r2 * 2 - r3 * 7 - r4; //余下分别添加
            if (rest >= 2) {
                expectedNums.put(MathTool.Type.Brackets.getCode(), r4 + 2);
                rest -= 2;
            }
            if (rest > 0) {
                expectedNums.put(MathTool.Type.Mod.getCode(), r3 + 1);
                rest -= 1;
            }
            if (rest > 0) {
                expectedNums.put(MathTool.Type.MultMinus.getCode(), r3 + 1);
                rest -= 1;
            }
            if (rest > 0) {
                expectedNums.put(MathTool.Type.MultAdd.getCode(), r3 + 1);
                rest -= 1;
            }
            if (rest > 0) {
                expectedNums.put(MathTool.Type.DivideMinus.getCode(), r3 + 1);
                rest -= 1;
            }
            if (rest > 0) {
                expectedNums.put(MathTool.Type.MultMult.getCode(), r3 + 1);
                rest -= 1;
            }
            if (rest > 0) {
                expectedNums.put(MathTool.Type.DivideAdd.getCode(), r3 + 1);
                rest -= 1;
            }
            expectedNums.put(MathTool.Type.Multiply.getCode(), r1 + rest); //余下的加到X里

        } else {
            int rate = totalQuestionNum / selected.size();
            int rest = totalQuestionNum % selected.size();
            int i = 0;
            for (JCheckBox ck : selected) {
                if (i++ == 0) {
                    expectedNums.put(checkBoxNameMap2Type.get(ck.getText()).getCode(), rate + rest);
                } else {
                    expectedNums.put(checkBoxNameMap2Type.get(ck.getText()).getCode(), rate);
                }
            }
        }
        return expectedNums;
    }


    public static void main(String[] args) {
        frame = new JFrame("MathGen");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setTitle("数学练习题");
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
