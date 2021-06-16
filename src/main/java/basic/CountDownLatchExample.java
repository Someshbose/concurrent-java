package basic;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    static class Worker extends Thread{
        private CountDownLatch countDownLatch;

        public Worker(CountDownLatch countDownLatch,String name){
            super(name);
            this.countDownLatch=countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("Worker"+Thread.currentThread().getName()+" started");
            try {
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println("Worker"+Thread.currentThread().getName()+" finished");
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Worker A= new Worker(countDownLatch,"A");
        Worker B= new Worker(countDownLatch,"B");

        A.start();
        B.start();
        countDownLatch.await();

        System.out.println("Main thread ended!");
    }
}
