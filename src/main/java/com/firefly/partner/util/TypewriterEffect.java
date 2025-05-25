package com.firefly.partner.util;

public class TypewriterEffect {
    public static void printWord(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("打印被中断");
                return;
            }
        }
        System.out.println();
    }
}