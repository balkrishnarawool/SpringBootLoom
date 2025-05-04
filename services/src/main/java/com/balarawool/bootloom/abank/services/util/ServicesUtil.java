package com.balarawool.bootloom.abank.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServicesUtil {
    private static final Logger logger = LoggerFactory.getLogger(ServicesUtil.class);

    public static void logAndWait(String task) {
        long delay = 1 + (long)(Math.random() * 5);
        logger.info("Performing task: {}(). Time to complete: {} seconds", task, delay);
        try {
            Thread.sleep(delay * 1_000);
            logger.info("Done task: {}()", task);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
