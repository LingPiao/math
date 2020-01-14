package com.youyou.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Math {

    // 1=+, 2=-, 3=x, 4=÷, 5=x+, 6=x-, 7=xx, 8=÷+,9=÷-, 10=+-,11=mod, 12=()
    private static Map<Integer, Integer> operationCnt = new HashMap<>();

    public static void main(String[] args) {

        for (int i = 1; i < 31; i++) {
            StringBuilder sb = new StringBuilder("");
            sb.append("口算练习" + i).append("\n");
            sb.append(genMath().toString());
            System.out.println(sb.toString());
            System.out.println("======================================================");
        }

    }

    public static StringBuilder genMath() {
        operationCnt.clear();
        operationCnt.put(11, 0);
        operationCnt.put(12, 0);
        StringBuilder sb = new StringBuilder("");
        Random r1 = new Random();
        Random r2 = new Random();

        for (int i = 0; i < 20; i++) {
            if (i < 6) {//x or /
                for (int j = 0; j < 3; j++) {
                    int z1 = 2 + r1.nextInt(8);
                    int z2 = 2 + r2.nextInt(8);
                    if (i % 2 == 1) {
                        if (z1 == 0 || z1 == z2) {
                            z1 = 2 + r2.nextInt(8);
                        }
                        sb.append(z1 * z2).append(" ÷ ").append(z1).append(" = ").append(genTabs(3));
                    } else {
                        sb.append(z1).append(" x ").append(z2).append(" = ").append(genTabs(3));
                    }
                }
                sb.append("\n");
            } else if (i >= 6 && i < 16) { // + , - , x, /
                for (int j = 0; j < 3; j++) {
                    int x = 1 + r1.nextInt(9);
                    int y = 1 + r2.nextInt(9);

                    int a = 1 + r1.nextInt(49);
                    int b = 1 + r2.nextInt(49);

                    int op = 1 + new Random().nextInt(4);
                    switch (op) {
                        case 1:
                            if (operationCnt.get(12).intValue() < 4) {
                                sb.append(a).append(" + (  )").append(" = ").append(a + b).append(genTabs(2));
                                operationCnt.put(12, operationCnt.get(12).intValue() + 1);
                            } else {
                                sb.append(a).append(" + ").append(b).append(" = ").append(genTabs(3));
                            }
                            break;
                        case 2:
                            if (a - b < 0) {
                                sb.append(b).append(" - ").append(a).append(" = ").append(genTabs(3));
                            } else {
                                if (operationCnt.get(12).intValue() < 4) {
                                    sb.append(a).append(" - (  )").append(" = ").append(a - b).append(genTabs(2));
                                    operationCnt.put(12, operationCnt.get(12).intValue() + 1);
                                } else {
                                    sb.append(a).append(" - ").append(b).append(" = ").append(genTabs(3));
                                }
                            }
                            break;
                        case 3:
                            sb.append(x).append(" x ").append(y).append(" = ").append(genTabs(3));
                            break;
                        case 4:
                            if (y == 0) {
                                y = 2 + r2.nextInt(8);
                            }
                            if (operationCnt.get(11).intValue() < 3) {
                                sb.append(x * y + x).append(" ÷ ").append(y).append(" = ").append(genTabs(3));
                                operationCnt.put(11, operationCnt.get(11).intValue() + 1);
                            } else {
                                sb.append(x * y).append(" ÷ ").append(y).append(" = ").append(genTabs(3));
                            }
                            break;
                    }
                }
                sb.append("\n");
            } else { // x+ , x-, /+,/-
                for (int j = 0; j < 3; j++) {
                    int x = 2 + r1.nextInt(8);
                    int y = 2 + r2.nextInt(8);
                    int z = 2 + r2.nextInt(8);

                    int op = 5 + new Random().nextInt(6);
                    switch (op) {
                        case 5:
                            sb.append(x).append(" x ").append(y).append(" + ").append(z).append(" = ").append(genTabs(2));
                            break;
                        case 6:
                            while (x * y - z < 0) {
                                x = 1 + r1.nextInt(9);
                                y = 1 + r2.nextInt(9);
                            }
                            sb.append(x).append(" x ").append(y).append(" - ").append(z).append(" = ").append(genTabs(2));
                            break;
                        case 7:
                            x = 1 + r1.nextInt(3);
                            y = 1 + r2.nextInt(3);
                            sb.append(x).append(" x ").append(y).append(" x ").append(z).append(" = ").append(genTabs(2));
                            break;
                        case 8:
                            if (y == 0) {
                                y = 1 + r2.nextInt(9);
                            }
                            sb.append(x * y).append(" ÷ ").append(y).append(" + ").append(z).append(" = ").append(genTabs(2));
                            break;
                        case 9:
                            if (y == 0) {
                                y = 1 + r2.nextInt(9);
                            }
                            while (x * y / y - z < 0) {
                                x = 1 + r1.nextInt(9);
                                z = 1 + r2.nextInt(9);
                            }
                            sb.append(x * y).append(" ÷ ").append(y).append(" - ").append(z).append(" = ").append(genTabs(2));
                            break;
                        case 10:
                            sb.append(x).append(" + ").append(y).append(" - ").append(z).append(" = ").append(genTabs(2));
                            break;
                    }
                }
                sb.append("\n");
            }
        }
        sb.append("\n");
        return sb;
    }


    private static String genTabs(int cnt) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<cnt;i++){
            sb.append("\t");
        }
        return sb.toString();
    }
    

}
