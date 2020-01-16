package com.youyou.math;

import java.util.Random;

public class RdmTest {
    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(1 + r.nextInt(4));
        }

    }
}
