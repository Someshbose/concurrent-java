package basic;

public class ThreadLocalExample {
    public static void main(String[] args) throws Exception{
        Counter counter = new Counter();
        Thread[] tasks= new Thread[100];

        for (int i=0;i<100;i++){
            Thread thread = new Thread(()->{
                for (int j=0;j<100;j++){
                    counter.increment();
                    System.out.println(counter.counter.get());
                }

            });
            tasks[i]=thread;
            thread.start();
        }

        for (int i=0;i<100;i++){
            tasks[i].join();
        }

        System.out.println(counter.counter.get());
    }

}

class Counter{
    ThreadLocal<Integer> counter = ThreadLocal.withInitial(()->0);
    public void increment(){
        counter.set(counter.get()+1);
    }
}
