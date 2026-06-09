package com.amit_codes.multithreading;

public class PrintNumberThreeThreadSol {

    public static void main(String[] args) {
        PrintNumberThreeThreadSol sol = new PrintNumberThreeThreadSol();
        sol.print(1, "Thread 1");
        sol.print(2, "Thread 2");
        sol.print(3, "Thread 3");
    }

    final Object obj = new Object();
    public int lastNum = 1;

    public void print(int start, String threadName) {
        new Thread(() -> {
            for (int i = start; i < 20; i = i + 3) {
                synchronized (obj) {
                    try {
                        while (lastNum != i) {
                            obj.wait();
                        }
                    } catch (Exception ignored) {
                    }
                    System.out.println(threadName + " - " + i);
                    lastNum++;
                    obj.notifyAll();
                }
            }
        }).start();
    }
}
