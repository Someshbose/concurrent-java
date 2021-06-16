package basic;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FixedThreadPoolExecutor {
    public static void main(String[] args) {
        int expectedConsurrentOrders = 10;
        Executor executor = Executors.newFixedThreadPool(expectedConsurrentOrders);

        for (int i=0;i<101;i++){
            FixedThreadPoolExecutor sample= new FixedThreadPoolExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    sample.show();
                }
            });
        }
    }

    public void show(){
        System.out.println("showtime!");
    }
}
