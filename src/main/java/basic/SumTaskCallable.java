package basic;

import java.util.concurrent.*;

public class SumTaskCallable {

    static ExecutorService threadpool= Executors.newFixedThreadPool(2);
    public static void main(String[] args) {
        Callable<Integer> sumTask = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 5;
            }
        };


        try {
            Future<Integer> f =threadpool.submit(sumTask);
            System.out.println(f.get());
        }
        catch (ExecutionException e ){

        }
        catch (InterruptedException e){

        }
    }
}
