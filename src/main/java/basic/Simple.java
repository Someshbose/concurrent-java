package basic;

public class Simple implements Runnable{

    @Override
    public void run() {
        System.out.println("A simple thread running");
    }

    public static void main(String args[]){
        Thread thread = new Thread(new Simple());
        thread.start();
        try {
            thread.join();
        }catch (InterruptedException e){
            System.err.println("Error occuered!");
        }

        System.out.println("thread execution ended...");
    }
}
