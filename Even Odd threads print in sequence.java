class Main {
    public static void main(String[] args) {
        EvenOdd od = new EvenOdd();
        od.execute();
    }
}

class EvenOdd {
    private Object object = new Object();
    volatile private boolean isOddTurn = false;
    
    public void execute() {
        new Thread(() -> {
            for (int i = 0; i < 20; i = i + 2) {
                synchronized(object) {
                    try {
                        while (isOddTurn) {
                            object.wait();
                        }
                        System.out.println("Even - "+ i);
                        isOddTurn = true;
                        object.notifyAll();
                    } catch(Exception e) {}
                }
            }
        }).start();
        
        new Thread(() -> {
            for (int i = 1; i < 20; i = i + 2) {
                synchronized(object) {
                    try {
                        while (!isOddTurn) {
                            object.wait();
                        }
                        System.out.println("Odd - "+ i);
                        isOddTurn = false;
                        object.notifyAll();
                    } catch(Exception e) {}
                }
            }
        }).start();
    }
}
