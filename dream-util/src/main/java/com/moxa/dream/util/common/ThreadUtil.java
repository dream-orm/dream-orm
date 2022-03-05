package com.moxa.dream.util.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
    private static int available = 2 * Runtime.getRuntime().availableProcessors() + 1;
    private static ExecutorService executorService = Executors.newFixedThreadPool(available);

    public static void execute(Runnable runnable) {

        executorService.execute(runnable);
    }

}
