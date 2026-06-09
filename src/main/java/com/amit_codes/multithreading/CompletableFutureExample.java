package com.amit_codes.multithreading;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureExample {

    public static void main(String[] args) {

        ExecutorService executor =
                Executors.newFixedThreadPool(5);

        List<CompletableFuture<Integer>> futures =
                IntStream.rangeClosed(1, 10)
                        .mapToObj(number ->
                                CompletableFuture.supplyAsync(() -> {

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }

                                    System.out.println(
                                            Thread.currentThread().getName()
                                                    + " processed "
                                                    + number);

                                    return number * number;
                                }, executor))
                        .toList();

        // All will wait until all completed
        CompletableFuture<Void> all =
                CompletableFuture.allOf(
                        futures.toArray(
                                new CompletableFuture[0]));
        all.join(); // just let us know that task is complete, returns void

        List<Integer> result =
                futures.stream()
                        .map(CompletableFuture::join)// returns result similar to future.get
                        .collect(Collectors.toList());

        System.out.println(result);

        executor.shutdown();
    }
}