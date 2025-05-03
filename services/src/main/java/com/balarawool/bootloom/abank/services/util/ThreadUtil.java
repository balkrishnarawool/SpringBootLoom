package com.balarawool.bootloom.abank.services.util;

public class ThreadUtil {
    public static void logAndWait(String task) {
        long delay = 1 + (long)(Math.random() * 5);
        System.out.println("Performing task: " + task + "(). It will take " + delay + " seconds");
        try {
            Thread.sleep(delay * 1_000);
            System.out.println("Done task: " + task + "()");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
