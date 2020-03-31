package com.youyou.math;

import java.util.*;

public class MathTool {
    public static enum Type {
        //WholeHd:整10,100加减
        Add(1), Minus(2), Multiply(3), Divide(4), MultAdd(5), MultMinus(6),
        MultMult(7), DivideAdd(8), DivideMinus(9), AddMinus(10),
        Mod(11), Brackets(12), WholeHd(13);
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
        rate.put(Type.WholeHd.code, 0);
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
        operationCnt.put(Type.WholeHd.code, 0);
    }

    public String[] genMath(int totalNum) {
        return genMath(totalNum, getDefaultRate());
    }

    public String[] genMath(int totalNum, Map<Integer, Integer> rate) {
        return genMath(totalNum, rate);
    }

    public QA genMathQuestionsAndAnswers(int totalNum, Map<Integer, Integer> rate, int scope) {
        String[] qa = genMath(totalNum, rate, scope);
        return new QA(qa[0], qa[1]);
    }

    public List<QA> genMathQuestionsAndAnswers(int totalNum, Map<Integer, Integer> rate, int scope, int copies) {
        List<QA> qas = new ArrayList<>();
        for (int i = 0; i < copies; i++) {
            String[] qa = genMath(totalNum, rate, scope);
            qas.add(new QA(qa[0], qa[1]));
        }
        return qas;
    }

    // 0 - 100以内, 1 - 20以内,2 - 3位数, 3 - 4位数
    public String[] genMath(int totalNum, Map<Integer, Integer> rate, int scope) {
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

            if (scope == 0) {
                z1 = 1 + r1.nextInt(8);
                z2 = 1 + r2.nextInt(8);
            } else if (scope == 1) {
                z1 = 1 + r1.nextInt(8);
                z2 = 1 + r2.nextInt(8);
            } else if (scope == 2) {
                z1 = 50 + r1.nextInt(49);
                z2 = 1 + r1.nextInt(9);
            } else if (scope == 3) {
                z1 = 50 + r1.nextInt(49);
                z2 = 50 + r1.nextInt(49);
            }

            if (genCnt % 3 == 0) {
                sb.append("\n");
                ans.append("\n");
            }

            while (isGenRequired(rate)) {
                int opRand = 1 + r1.nextInt(5);

                // +
                if (opRand == 1 && operationCnt.get(Type.Add.code) < rate.get(Type.Add.code)) {
                    int a = 1, b = 1;
                    if (scope == 0) {
                        a = 1 + r1.nextInt(49);
                        b = 1 + r2.nextInt(49);
                    } else if (scope == 1) {
                        a = 1 + r1.nextInt(14);
                        b = 1 + r2.nextInt(5);
                    } else if (scope == 2) {
                        a = 100 + r1.nextInt(499);
                        b = 100 + r1.nextInt(499);
                    } else if (scope == 3) {
                        a = 1000 + r1.nextInt(7999);
                        b = 100 + r1.nextInt(199);
                    }
                    sb.append(a).append(" + ").append(b).append(" = ").append(genTabs(3));
                    ans.append(a).append(" + ").append(b).append(" =").append(a + b).append(genTabs(2));
                    operationCnt.put(Type.Add.code, operationCnt.get(Type.Add.code) + 1);
                    genCnt++;
                    break;
                }


                // -
                if (opRand == 2 && operationCnt.get(Type.Minus.code) < rate.get(Type.Minus.code)) {
                    int a = 1, b = 1;
                    if (scope == 0) {
                        a = 1 + r1.nextInt(49);
                        b = 1 + r2.nextInt(49);
                    } else if (scope == 1) {
                        a = 1 + r1.nextInt(19);
                        b = 1 + r2.nextInt(9);
                    } else if (scope == 2) {
                        a = 100 + r1.nextInt(499);
                        b = 100 + r1.nextInt(499);
                    } else if (scope == 3) {
                        a = 1000 + r1.nextInt(7999);
                        b = 100 + r1.nextInt(199);
                    }

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

                //  WholeHD 整10/100加减
                if (opRand == 5 && operationCnt.get(Type.WholeHd.code) < rate.get(Type.WholeHd.code)) {
                    int it1 = 100 + r1.nextInt(660);
                    it1 = it1 - (it1 % 10); //去掉个位
                    int it2 = 40 + r2.nextInt(200);
                    it2 = it2 - (it2 % 10); //去掉个位
                    int op=1+r2.nextInt(2);
                    if (op == 1) {
                        sb.append(it1 + it2).append(" - ").append(it1).append(" = ").append(genTabs(2));
                        ans.append(it1 + it2).append(" - ").append(it1).append(" =").append(it2).append(genTabs(2));
                    } else {
                        sb.append(it1).append(" + ").append(it2).append(" = ").append(genTabs(2));
                        ans.append(it1).append(" + ").append(it2).append(" =").append(it1 + it2).append(genTabs(2));
                    }
                    operationCnt.put(Type.WholeHd.code, operationCnt.get(Type.WholeHd.code) + 1);
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
                int x = 1, y = 1;
                if (scope == 0) {
                    x = 1 + r1.nextInt(9);
                    y = 2 + r2.nextInt(8);
                } else if (scope == 1) {
                    x = 1 + r1.nextInt(9);
                    y = 1 + r2.nextInt(9);
                } else if (scope == 2) {
                    x = 50 + r1.nextInt(49);
                    y = 50 + r1.nextInt(49);
                } else if (scope == 3) {
                    x = 100 + r1.nextInt(899);
                    y = 50 + r1.nextInt(49);
                }

                int modMax = (x > y ? y : x);
                int mod = 1 + r1.nextInt(modMax > 1 ? modMax - 1 : modMax);

                sb.append(x * y + mod).append(" ÷ ").append(y).append(" = ").append(genTabs(3));
                ans.append(x * y + mod).append(" ÷ ").append(y).append(" =").append((x * y + mod) / y).append("...").append((x * y + mod) % y).append(genTabs(2));
                operationCnt.put(Type.Mod.code, operationCnt.get(Type.Mod.code) + 1);
                genCnt++;
                continue;
            }


            // ()
            if (operationCnt.get(Type.Brackets.code) < rate.get(Type.Brackets.code)) {
                int a = 1, b = 1;
                if (scope == 0) {
                    a = 1 + r1.nextInt(49);
                    b = 1 + r2.nextInt(49);
                } else if (scope == 1) {
                    a = 1 + r1.nextInt(19);
                    b = 1 + r2.nextInt(9);
                } else if (scope == 2) {
                    a = 100 + r1.nextInt(499);
                    b = 100 + r1.nextInt(499);
                } else if (scope == 3) {
                    a = 500 + r1.nextInt(499);
                    b = 500 + r1.nextInt(499);
                }

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
                int x = 2, y = 2, z = 2;

                if (scope == 0) {
                    x = 2 + r1.nextInt(8);
                    y = 2 + r2.nextInt(8);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 1) {
                    x = 2 + r1.nextInt(8);
                    y = 2 + r2.nextInt(8);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 2) {
                    x = 50 + r1.nextInt(49);
                    y = 50 + r1.nextInt(49);
                    z = 100 + r2.nextInt(899);
                } else if (scope == 3) {
                    x = 50 + r1.nextInt(49);
                    y = 50 + r1.nextInt(49);
                    z = 1000 + r2.nextInt(7999);
                }

                sb.append(x).append(" x ").append(y).append(" + ").append(z).append(" = ").append(genTabs(3));
                ans.append(x).append(" x ").append(y).append(" + ").append(z).append(" =").append(x * y + z).append(genTabs(2));
                operationCnt.put(Type.MultAdd.code, operationCnt.get(Type.MultAdd.code) + 1);
                genCnt++;
                continue;
            }

            // x-, -x
            if (operationCnt.get(Type.MultMinus.code) < rate.get(Type.MultMinus.code)) {
                int x = 2, y = 2, z = 1;
                if (scope == 0) {
                    x = 2 + r1.nextInt(8);
                    y = 2 + r2.nextInt(8);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 1) {
                    x = 2 + r1.nextInt(8);
                    y = 2 + r2.nextInt(8);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 2) {
                    x = 50 + r1.nextInt(49);
                    y = 50 + r1.nextInt(49);
                    z = 100 + r2.nextInt(899);
                } else if (scope == 3) {
                    x = 50 + r1.nextInt(49);
                    y = 50 + r1.nextInt(49);
                    z = 1000 + r2.nextInt(7999);
                }

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
                int x = 2, y = 2, z = 1;
                if (scope == 0) {
                    x = 2 + r1.nextInt(8);
                    y = 2 + r2.nextInt(8);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 1) {
                    x = 2 + r1.nextInt(8);
                    y = 2 + r2.nextInt(8);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 2) {
                    x = 50 + r1.nextInt(49);
                    y = 50 + r1.nextInt(49);
                    z = 100 + r2.nextInt(899);
                } else if (scope == 3) {
                    x = 50 + r1.nextInt(49);
                    y = 50 + r1.nextInt(49);
                    z = 1000 + r2.nextInt(7999);
                }

                sb.append(x * y).append(" ÷ ").append(y).append(" + ").append(z).append(" = ").append(genTabs(3));
                ans.append(x * y).append(" ÷ ").append(y).append(" + ").append(z).append(" =").append(x + z).append(genTabs(2));
                operationCnt.put(Type.DivideAdd.code, operationCnt.get(Type.DivideAdd.code) + 1);
                genCnt++;
                continue;
            }

            // ÷-, -÷
            if (operationCnt.get(Type.DivideMinus.code) < rate.get(Type.DivideMinus.code)) {
                int x = 2, y = 2, z = 1;
                if (scope == 0) {
                    x = 2 + r1.nextInt(8);
                    y = 2 + r2.nextInt(8);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 1) {
                    x = 2 + r1.nextInt(8);
                    y = 2 + r2.nextInt(8);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 2) {
                    x = 50 + r1.nextInt(49);
                    y = 50 + r1.nextInt(49);
                    z = 100 + r2.nextInt(899);
                } else if (scope == 3) {
                    x = 50 + r1.nextInt(49);
                    y = 50 + r1.nextInt(49);
                    z = 1000 + r2.nextInt(7999);
                }
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
                int a = 1, b = 1, c = 2;
                if (scope == 0) {
                    a = 2 + r1.nextInt(49);
                    b = 2 + r2.nextInt(49);
                    c = 2 + r2.nextInt(48);
                } else if (scope == 1) {
                    a = 2 + r1.nextInt(19);
                    b = 2 + r2.nextInt(9);
                    c = 2 + r2.nextInt(9);
                } else if (scope == 2) {
                    a = 100 + r1.nextInt(499);
                    b = 100 + r1.nextInt(499);
                    c = 100 + r1.nextInt(499);
                } else if (scope == 3) {
                    a = 500 + r1.nextInt(499);
                    b = 500 + r1.nextInt(499);
                    c = 1000 + r1.nextInt(4999);
                }

                if ((a + b) > c) {
                    sb.append(a).append(" + ").append(b).append(" - ").append(c).append(" = ").append(genTabs(3));
                    ans.append(a).append(" + ").append(b).append(" - ").append(c).append(" =").append(a + b - c).append(genTabs(2));
                } else {
                    sb.append(c).append(" - ").append(a).append(" - ").append(b).append(" = ").append(genTabs(3));
                    ans.append(c).append(" - ").append(a).append(" - ").append(b).append(" =").append(c - a - b).append(genTabs(2));
                }
                operationCnt.put(Type.AddMinus.code, operationCnt.get(Type.AddMinus.code) + 1);
                genCnt++;
                continue;
            }

            // xx
            if (operationCnt.get(Type.MultMult.code) < rate.get(Type.MultMult.code)) {

                int x = 2, y = 2, z = 1;
                if (scope == 0) {
                    x = 2 + r1.nextInt(3);
                    y = 2 + r2.nextInt(3);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 1) {
                    x = 2 + r1.nextInt(3);
                    y = 2 + r2.nextInt(3);
                    z = 2 + r2.nextInt(8);
                } else if (scope == 2) {
                    x = 2 + r1.nextInt(3);
                    y = 3 + r2.nextInt(5);
                    z = 10 + r2.nextInt(20);
                } else if (scope == 3) {
                    x = 10 + r2.nextInt(20);
                    y = 10 + r2.nextInt(20);
                    z = 2 + r2.nextInt(8);
                }

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
        return operationCnt.get(Type.Add.code) < rate.get(Type.Add.code) ||
                operationCnt.get(Type.Minus.code) < rate.get(Type.Minus.code) ||
                operationCnt.get(Type.Multiply.code) < rate.get(Type.Multiply.code) ||
                operationCnt.get(Type.Divide.code) < rate.get(Type.Divide.code) ||
                operationCnt.get(Type.WholeHd.code) < rate.get(Type.WholeHd.code);
    }


    private String genTabs(int cnt) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cnt; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }


}
