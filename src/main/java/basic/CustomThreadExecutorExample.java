package basic;

import java.util.concurrent.Executor;

public class CustomThreadExecutorExample {
    public static void main(String[] args) {
        DumbExecutor executor = new DumbExecutor();
        executor.execute(new MyTask());
    }

    static class DumbExecutor implements Executor{

        @Override
        public void execute(Runnable command) {
            Thread newThread = new Thread(command);
            newThread.start();
        }
    }

    static class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.println("My task is running!");
        }
    }
}
