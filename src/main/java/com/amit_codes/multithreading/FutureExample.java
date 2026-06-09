package com.amit_codes.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureExample {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            int number = i;

            Future<Integer> future = executor.submit(() -> {
                Thread.sleep(1000);

                System.out.println(
                        Thread.currentThread().getName()
                                + " processed " + number);

                return number * number;
            });

            futures.add(future);
        }

        List<Integer> result = new ArrayList<>();

        for (Future<Integer> future : futures) {
            result.add(future.get());
        }

        System.out.println(result);

        executor.shutdown();
    }
}