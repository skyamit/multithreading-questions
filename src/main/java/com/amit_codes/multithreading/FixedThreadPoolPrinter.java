package com.amit_codes.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolPrinter {

    public static void main(String[] args) {
        FixedThreadPoolPrinter main = new FixedThreadPoolPrinter();
        System.out.println("Fixed Thread Pool - ");
        main.printFixedThreadPool();
        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {}
        System.out.println("Cached Thread Pool - ");
        main.printCachedThreadPool();
    }

    public void printFixedThreadPool() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(3);
            for (int i = 0; i < 20; i++) {
                int data = i;
                executor.submit(() -> System.out.println(Thread.currentThread().getName() + " _ "  + data));
            }
            executor.shutdown();
        }
        catch (Exception ignored) {}
    }

    public void printCachedThreadPool() {
        try {
            ExecutorService executor = Executors.newCachedThreadPool();
            for (int i = 0; i < 20; i++) {
                int data = i;
                executor.submit(() -> System.out.println(Thread.currentThread().getName() + " _ "  + data));
            }
            executor.shutdown();
        } catch (Exception ignored) {}
    }
}
