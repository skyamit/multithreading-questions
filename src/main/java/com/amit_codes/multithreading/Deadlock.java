package com.amit_codes.multithreading;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Deadlock {
    public static void main (String[] args) throws java.lang.Exception{
        Multithread multithread = new Multithread();
//        multithread.deadlock();
//        multithread.deadlockResolutionUsingTrylock();
        multithread.deadlockSequenceFix();
    }
}

class Multithread {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    public void deadlock() {

        new Thread(() -> {
            try {
                lock1.lock();
                try {
                    System.out.println("Thread 1: locked lock1");
                    sleep(100);

                    lock2.lock();
                    try {
                        System.out.println("Thread 1: locked lock2");
                    } finally {
                        lock2.unlock();
                    }
                } finally {
                    lock1.unlock();
                }
            } catch (Exception e) {}
        }).start();

        new Thread(() -> {
            try {
                lock2.lock();
                try {
                    System.out.println("Thread 2: locked lock2");
                    sleep(100);

                    lock1.lock();
                    try {
                        System.out.println("Thread 2: locked lock1");
                    } finally {
                        lock1.unlock();
                    }
                } finally {
                    lock2.unlock();
                }
            } catch (Exception e){}
        }).start();
    }

    public void deadlockSequenceFix() {
        new Thread(() -> {
            lock1.lock();
            try {
                System.out.println("Thread 1: locked lock1");

                lock2.lock();
                try {
                    System.out.println("Thread 1: locked lock2");
                } finally {
                    lock2.unlock();
                }

            } finally {
                lock1.unlock();
            }
        }).start();

        new Thread(() -> {
            lock1.lock();
            try {
                System.out.println("Thread 2: locked lock1");

                lock2.lock();
                try {
                    System.out.println("Thread 2: locked lock2");
                } finally {
                    lock2.unlock();
                }

            } finally {
                lock1.unlock();
            }
        }).start();
    }

    public void deadlockResolutionUsingTrylock() {

        Runnable task1 = () -> {
            while (true) {
                try {
                    if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                        try {
                            System.out.println("Thread 1: locked lock1");

                            if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                                try {
                                    System.out.println("Thread 1: locked lock2");
                                    break;
                                } finally {
                                    lock2.unlock();
                                }
                            }
                        } finally {
                            lock1.unlock();
                        }
                    }

                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable task2 = () -> {
            while (true) {
                try {
                    if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                        try {
                            System.out.println("Thread 2: locked lock2");

                            if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                                try {
                                    System.out.println("Thread 2: locked lock1");
                                    break;
                                } finally {
                                    lock1.unlock();
                                }
                            }
                        } finally {
                            lock2.unlock();
                        }
                    }

                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        new Thread(task1).start();
        new Thread(task2).start();
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}