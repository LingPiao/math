package com.youyou.math;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gk on 2020-01-16.
 */
public class DocUtil {

    private static final String CR = "\n";
    private static final String Tab = "\t";


    public static void main(String[] args) {
        createDoc(new String[]{"8 ÷ 4 = \t\t\t5 x 9 = \t\t\t12 ÷ 3 =\n8 ÷ 4 = \t\t\t5 x 9 = \t\t\t12 ÷ 3 = ", "answers"}, false);
    }

    public static String createDoc(String[] qNa, boolean isAnswerRequired) {
        if (qNa == null) {
            return null;
        }
        XWPFDocument doc = new XWPFDocument();


        XWPFParagraph title = doc.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(14);
        titleRun.setFontFamily("宋体");
        titleRun.setText("口算训练");

        XWPFParagraph questions = doc.createParagraph();
        questions.setAlignment(ParagraphAlignment.LEFT);
        // XWPFRun qRun = questions.createRun();
        //qRun.setText(qNa[0]);
        bindEnrichedText(qNa[0], questions, false);

        if (isAnswerRequired) {
            XWPFParagraph p3 = doc.createParagraph();
            p3.setPageBreak(true);

            XWPFParagraph p4 = doc.createParagraph();
            p4.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun r4 = p4.createRun();
            r4.setBold(true);
            r4.setFontSize(14);
            r4.setFontFamily("宋体");
            r4.setText("答案");

            XWPFParagraph p5 = doc.createParagraph();
            p5.setAlignment(ParagraphAlignment.LEFT);
            bindEnrichedText(qNa[1], p5, true);

        }

        String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + ".docx";
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            doc.write(out);
            File directory = new File(".");
            return directory.getCanonicalPath() + "\\" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void bindEnrichedText(String text, XWPFParagraph graph, boolean isAnser) {
        String[] lines = text.split(CR);
        XWPFRun cr = graph.createRun();
        cr.addCarriageReturn();
        cr.addCarriageReturn();
        int tabAdjust = 0;
        if (isAnser) {
            tabAdjust = -2;
        }

        for (String l : lines) {
            String[] cls = l.split(Tab);
            for (String c : cls) {
                if (c == null || c.length() < 1) {
                    continue;
                }
                XWPFRun r = graph.createRun();
                r.setFontFamily("宋体");
                r.setFontSize(14);
                r.setText(c);
                if (c.length() < (12 + tabAdjust)) {
                    genTab(r, 3);
                } else if (c.length() < (16 + tabAdjust)) {
                    genTab(r, 2);
                } else {
                    genTab(r, 1);
                }
            }
            XWPFRun r = graph.createRun();
            r.addCarriageReturn();
        }

    }

    private static void genTab(XWPFRun r, int tabCnt) {
        for (int i = 0; i < tabCnt; i++) {
            r.getCTR().addNewTab();
        }
    }
}
