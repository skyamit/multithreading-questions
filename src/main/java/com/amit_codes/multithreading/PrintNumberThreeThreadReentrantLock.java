package com.amit_codes.multithreading;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class PrintNumberThreeThreadReentrantLock {
    // NOT ALLOWED BECAUSE LOCK CAN BE APPLIED AND UNLOCKED BY SAME THREAD!!
    public static void main(String[] args) {
        lock2.lock();
        lock3.lock();
//        printNumber();
        printNumberSemaphore();
    }
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    private static ReentrantLock lock3 = new ReentrantLock();

    public static void printNumber() {
        new Thread(() -> {
            for (int i = 1; i < 20; i = i + 3) {
                System.out.println("Thread 1 - " + i);
                lock2.unlock();
                lock1.lock();
            }
        }).start();

        new Thread(() -> {
            for (int i = 2; i < 20; i = i + 3) {
                System.out.println("Thread 2 - " + i);
                lock3.unlock();
                lock2.lock();
            }
        }).start();
        new Thread(() -> {
            for (int i = 3; i < 20; i = i + 3) {
                System.out.println("Thread 3 - " + i);
                lock1.unlock();
                lock3.lock();
            }
        }).start();
    }

    // Instead we can use Semaphone to controll lock
    private static Semaphore slock1 = new Semaphore(1);
    private static Semaphore slock2 = new Semaphore(0);
    private static Semaphore slock3 = new Semaphore(0);

    public static void printNumberSemaphore() {
        new Thread(() -> {
            for (int i = 1; i < 20; i += 3) {
                slock1.acquireUninterruptibly();

                System.out.println("Thread 1 - " + i);

                slock2.release();
            }
        }).start();

        new Thread(() -> {
            for (int i = 2; i < 20; i += 3) {
                slock2.acquireUninterruptibly();

                System.out.println("Thread 2 - " + i);

                slock3.release();
            }
        }).start();

        new Thread(() -> {
            for (int i = 3; i < 20; i += 3) {
                slock3.acquireUninterruptibly();

                System.out.println("Thread 3 - " + i);

                slock1.release();
            }
        }).start();
    }
}
