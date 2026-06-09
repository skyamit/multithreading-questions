package com.amit_codes.multithreading;

import java.util.concurrent.Semaphore;

public class MasterWorkerPrinter {

    static class Worker implements Runnable {

        private final String name;
        private final Semaphore workSignal = new Semaphore(0);
        private final Semaphore doneSignal = new Semaphore(0);
        private volatile int number;

        public Worker(String name) {
            this.name = name;
        }

        public void assignWork(int number) {
            this.number = number;
            workSignal.release();
        }

        public void waitUntilDone() throws InterruptedException {
            doneSignal.acquire();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    workSignal.acquire();
                    System.out.println(name + " -> " + number);
                    doneSignal.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Worker w1 = new Worker("Thread-1");
        Worker w2 = new Worker("Thread-2");
        Worker w3 = new Worker("Thread-3");

        new Thread(w1).start();
        new Thread(w2).start();
        new Thread(w3).start();

        for (int i = 1; i <= 20; i++) {
            Worker worker;
            if (i % 3 == 1) {
                worker = w1;
            } else if (i % 3 == 2) {
                worker = w2;
            } else {
                worker = w3;
            }
            worker.assignWork(i);
            worker.waitUntilDone();
        }

        System.exit(0);
    }
}