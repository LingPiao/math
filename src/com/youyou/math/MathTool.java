package com.youyou.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MathTool {
    public static enum Type {
        Add(1), Minus(2), Multiply(3), Divide(4), MultAdd(5), MultMinus(6), MultMult(7), DivideAdd(8), DivideMinus(9), AddMinus(10), Mod(11), Brackets(12);
        private int code;

        public int getCode() {
            return code;
        }

        Type(int code) {
            this.code = code;
        }
    }

    // 1=+, 2=-, 3=x, 4=÷, 5=x+, 6=x-, 7=xx, 8=÷+,9=÷-, 10=+-,11=mod, 12=()
    private Map<Integer, Integer> operationCnt = new HashMap<>();


    public static void main(String[] args) {
        MathTool t = new MathTool();
        String[] qs = t.genMath(60, t.getDefaultRate());
        System.out.println(qs[0]);
        System.out.println("\n==================== Answers ===========================");
        System.out.println(qs[1]);
    }

    public Map<Integer, Integer> getDefaultRate() {
        Map<Integer, Integer> rate = new HashMap<>();
        rate.put(Type.Add.code, 0);
        rate.put(Type.Minus.code, 0);
        rate.put(Type.Multiply.code, 0);
        rate.put(Type.Divide.code, 0);
        rate.put(Type.MultAdd.code, 0);
        rate.put(Type.MultMinus.code, 0);
        rate.put(Type.MultMult.code, 0);
        rate.put(Type.DivideAdd.code, 0);
        rate.put(Type.DivideMinus.code, 0);
        rate.put(Type.AddMinus.code, 0);
        rate.put(Type.Mod.code, 0);
        rate.put(Type.Brackets.code, 0);
        return rate;
    }

    private void cleanAndInitCount() {
        operationCnt.clear();

        operationCnt.put(Type.Add.code, 0);
        operationCnt.put(Type.Minus.code, 0);
        operationCnt.put(Type.Multiply.code, 0);
        operationCnt.put(Type.Divide.code, 0);
        operationCnt.put(Type.MultAdd.code, 0);
        operationCnt.put(Type.MultMinus.code, 0);
        operationCnt.put(Type.MultMult.code, 0);
        operationCnt.put(Type.DivideAdd.code, 0);
        operationCnt.put(Type.DivideMinus.code, 0);
        operationCnt.put(Type.AddMinus.code, 0);
        operationCnt.put(Type.Mod.code, 0);
        operationCnt.put(Type.Brackets.code, 0);
    }

    public String[] genMath(int totalNum) {
        return genMath(totalNum, getDefaultRate());
    }

    public String[] genMath(int totalNum, Map<Integer, Integer> rate) {
        return genMath(totalNum, rate);
    }

    public String[] genMath(int totalNum, Map<Integer, Integer> rate, boolean less20) {
        cleanAndInitCount();

        StringBuilder sb = new StringBuilder("");
        StringBuilder ans = new StringBuilder("");

        Random r1 = new Random();
        Random r2 = new Random();

        int genCnt = 0;
        boolean newLineCheck = false;

        for (int i = 0; i < totalNum; i++) {
            int z1 = 2 + r1.nextInt(8);
            int z2 = 2 + r2.nextInt(8);

            if (genCnt % 3 == 0) {
                sb.append("\n");
                ans.append("\n");
            }

            while (isGenRequired(rate)) {
                int opRand = 1 + r1.nextInt(4);

                // +
                if (opRand == 1 && operationCnt.get(Type.Add.code) < rate.get(Type.Add.code)) {
                    int a = 1 + r1.nextInt(less20 ? 14 : 49);
                    int b = 1 + r2.nextInt(less20 ? 5 : 49);
                    sb.append(a).append(" + ").append(b).append(" = ").append(genTabs(3));
                    ans.append(a).append(" + ").append(b).append(" =").append(a + b).append(genTabs(2));
                    operationCnt.put(Type.Add.code, operationCnt.get(Type.Add.code) + 1);
                    genCnt++;
                    break;
                }


                // -
                if (opRand == 2 && operationCnt.get(Type.Minus.code) < rate.get(Type.Minus.code)) {
                    int a = 1 + r1.nextInt(less20 ? 19 : 49);
                    int b = 1 + r2.nextInt(less20 ? 9 : 49);
                    if (a < b) {
                        int x = a;
                        a = b; //交换
                        b = x;
                    }
                    sb.append(a).append(" - ").append(b).append(" = ").append(genTabs(3));
                    ans.append(a).append(" - ").append(b).append(" =").append(a - b).append(genTabs(2));
                    operationCnt.put(Type.Minus.code, operationCnt.get(Type.Minus.code) + 1);
                    genCnt++;
                    break;
                }


                // x
                if (opRand == 3 && operationCnt.get(Type.Multiply.code) < rate.get(Type.Multiply.code)) {
                    sb.append(z1).append(" x ").append(z2).append(" = ").append(genTabs(3));
                    ans.append(z1).append(" x ").append(z2).append(" =").append(z1 * z2).append(genTabs(2));
                    operationCnt.put(Type.Multiply.code, operationCnt.get(Type.Multiply.code) + 1);
                    genCnt++;
                    break;
                }

                //  /
                if (opRand == 4 && operationCnt.get(Type.Divide.code) < rate.get(Type.Divide.code)) {
                    if (z1 == 0 || z1 == z2) {
                        z1 = 2 + r2.nextInt(8);
                    }
                    sb.append(z1 * z2).append(" ÷ ").append(z1).append(" = ").append(genTabs(3));
                    ans.append(z1 * z2).append(" ÷ ").append(z1).append(" =").append(z2).append(genTabs(2));
                    operationCnt.put(Type.Divide.code, operationCnt.get(Type.Divide.code) + 1);
                    genCnt++;
                    break;
                }
            }

            if (isGenRequired(rate)) {
                continue;
            }

            if (!newLineCheck) {//检查=-x/生成完的最后一行是不是要换行
                if (genCnt % 3 == 0) {
                    sb.append("\n");
                    ans.append("\n");
                }
                newLineCheck = true;
            }

            // mod
            if (operationCnt.get(Type.Mod.code) < rate.get(Type.Mod.code)) {
                int x = 1 + r1.nextInt(9);
                int y = 1 + r2.nextInt(9);
                if (x > y) { //x作为余数应该用最小的
                    int t = x; // 交换
                    x = y;
                    y = t;
                }
                if ((x * y + x) % y == 0) {
                    y = y + 1;
                }
                sb.append(x * y + x).append(" ÷ ").append(y).append(" = ").append(genTabs(3));
                ans.append(x * y + x).append(" ÷ ").append(y).append(" =").append((x * y + x) / y).append("...").append((x * y + x) % y).append(genTabs(2));
                operationCnt.put(Type.Mod.code, operationCnt.get(Type.Mod.code) + 1);
                genCnt++;
                continue;
            }


            // ()
            if (operationCnt.get(Type.Brackets.code) < rate.get(Type.Brackets.code)) {
                int a = 1 + r1.nextInt(49);
                int b = 1 + r2.nextInt(49);
                if (a < b) {
                    int x = a;
                    a = b; //交换
                    b = x;
                }
                if (i % 2 == 0) {
                    sb.append(a).append(" - (  )").append(" = ").append(a - b).append(genTabs(3));
                    ans.append(a).append(" - (").append(b).append(") =").append(a - b).append(genTabs(2));
                } else {
                    sb.append(a).append(" + (  )").append(" = ").append(a + b).append(genTabs(3));
                    ans.append(a).append(" + (").append(b).append(") =").append(a + b).append(genTabs(2));
                }
                operationCnt.put(Type.Brackets.code, operationCnt.get(Type.Brackets.code) + 1);
                genCnt++;
                continue;
            }

            // x+
            if (operationCnt.get(Type.MultAdd.code) < rate.get(Type.MultAdd.code)) {
                int x = 2 + r1.nextInt(8);
                int y = 2 + r2.nextInt(8);
                int z = 2 + r2.nextInt(8);
                sb.append(x).append(" x ").append(y).append(" + ").append(z).append(" = ").append(genTabs(3));
                ans.append(x).append(" x ").append(y).append(" + ").append(z).append(" =").append(x * y + z).append(genTabs(2));
                operationCnt.put(Type.MultAdd.code, operationCnt.get(Type.MultAdd.code) + 1);
                genCnt++;
                continue;
            }

            // x-, -x
            if (operationCnt.get(Type.MultMinus.code) < rate.get(Type.MultMinus.code)) {
                int x = 2 + r1.nextInt(8);
                int y = 2 + r2.nextInt(8);
                int z = 1 + r2.nextInt(49);
                if (x * y > z) {
                    sb.append(x).append(" x ").append(y).append(" - ").append(z).append(" = ").append(genTabs(3));
                    ans.append(x).append(" x ").append(y).append(" - ").append(z).append(" =").append(x * y - z).append(genTabs(2));
                } else {
                    sb.append(z).append(" - ").append(x).append(" x ").append(y).append(" = ").append(genTabs(3));
                    ans.append(z).append(" - ").append(x).append(" x ").append(y).append(" =").append(z - x * y).append(genTabs(2));
                }
                operationCnt.put(Type.MultMinus.code, operationCnt.get(Type.MultMinus.code) + 1);
                genCnt++;
                continue;
            }

            // ÷+
            if (operationCnt.get(Type.DivideAdd.code) < rate.get(Type.DivideAdd.code)) {
                int x = 2 + r1.nextInt(8);
                int y = 2 + r2.nextInt(8);
                int z = 2 + r2.nextInt(8);
                if (y == 0) {
                    y = 1 + r2.nextInt(9);
                }
                sb.append(x * y).append(" ÷ ").append(y).append(" + ").append(z).append(" = ").append(genTabs(3));
                ans.append(x * y).append(" ÷ ").append(y).append(" + ").append(z).append(" =").append(x + z).append(genTabs(2));
                operationCnt.put(Type.DivideAdd.code, operationCnt.get(Type.DivideAdd.code) + 1);
                genCnt++;
                continue;
            }

            // ÷-, -÷
            if (operationCnt.get(Type.DivideMinus.code) < rate.get(Type.DivideMinus.code)) {
                int x = 2 + r1.nextInt(8);
                int y = 2 + r2.nextInt(8);
                int z = 1 + r2.nextInt(49);
                if (x > z) {
                    sb.append(x * y).append(" ÷ ").append(y).append(" - ").append(z).append(" = ").append(genTabs(3));
                    ans.append(x * y).append(" ÷ ").append(y).append(" - ").append(z).append(" =").append(x - z).append(genTabs(2));
                } else {
                    sb.append(z).append(" - ").append(x * y).append(" ÷ ").append(y).append(" = ").append(genTabs(3));
                    ans.append(z).append(" - ").append(x * y).append(" ÷ ").append(y).append(" =").append(z - x / y).append(genTabs(2));
                }
                operationCnt.put(Type.DivideMinus.code, operationCnt.get(Type.DivideMinus.code) + 1);
                genCnt++;
                continue;
            }

            // +-, -+, --
            if (operationCnt.get(Type.AddMinus.code) < rate.get(Type.AddMinus.code)) {
                int a = 2 + r1.nextInt(8);
                int b = 2 + r2.nextInt(8);
                int c = 2 + r2.nextInt(48);

                if ((a + b) > c) {
                    sb.append(a).append(" + ").append(b).append(" - ").append(c).append(" = ").append(genTabs(2));
                    ans.append(a).append(" + ").append(b).append(" - ").append(c).append(" =").append(a + b - c).append(genTabs(2));
                } else {
                    sb.append(c).append(" - ").append(a).append(" - ").append(b).append(" = ").append(genTabs(2));
                    ans.append(c).append(" - ").append(a).append(" - ").append(b).append(" =").append(c - a - b).append(genTabs(2));
                }
                operationCnt.put(Type.AddMinus.code, operationCnt.get(Type.AddMinus.code) + 1);
                genCnt++;
                continue;
            }

            // xx
            if (operationCnt.get(Type.MultMult.code) < rate.get(Type.MultMult.code)) {
                int x = 1 + r1.nextInt(3);
                int y = 1 + r2.nextInt(3);
                int z = 2 + r2.nextInt(8);
                sb.append(x).append(" x ").append(y).append(" x ").append(z).append(" = ").append(genTabs(2));
                ans.append(x).append(" x ").append(y).append(" x ").append(z).append(" =").append(x * y * z).append(genTabs(2));
                operationCnt.put(Type.MultMult.code, operationCnt.get(Type.MultMult.code) + 1);
                genCnt++;
                continue;
            }
        }
        sb.append("\n");
        ans.append("\n");
        return new String[]{sb.toString(), ans.toString()};
    }

    private boolean isGenRequired(Map<Integer, Integer> rate) {
        return operationCnt.get(Type.Add.code) < rate.get(Type.Add.code) || operationCnt.get(Type.Minus.code) < rate.get(Type.Minus.code)
                || operationCnt.get(Type.Multiply.code) < rate.get(Type.Multiply.code) || operationCnt.get(Type.Divide.code) < rate.get(Type.Divide.code);
    }


    private String genTabs(int cnt) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cnt; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }


}
