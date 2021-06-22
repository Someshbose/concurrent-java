package interview;

import java.util.concurrent.Semaphore;

public class BoundedBufferWithSemaphore {

  public static void main(String[] args) throws Exception{
    final BlockingQueueWithSemaphore<Integer> q = new BlockingQueueWithSemaphore<>(5);

    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          for(int i=0;i<50;i++){
            q.enqueue(new Integer(i));
            System.out.println("Enqueued "+i);
          }
        }catch (InterruptedException ie){

        }
      }
    });

    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          for(int i=50;i<100;i++){
            q.enqueue(new Integer(i));
            System.out.println("Enqueued "+i);
          }
        }catch (InterruptedException ie){

        }
      }
    });

    Thread t3 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          for(int i=0;i<50;i++){
            int item = q.dequeue();
            System.out.println("Dequed "+item);
          }
        }catch (InterruptedException ie){

        }
      }
    });

    Thread t4 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          for(int i=0;i<50;i++){
            int item = q.dequeue();
            System.out.println("Dequed "+item);
          }
        }catch (InterruptedException ie){

        }
      }
    });

    t1.start();
    t2.start();
    t3.start();
    t4.start();

    t1.join();
    t2.join();
    t3.join();
    t4.join();

    //Thread.sleep(1000);
  }

}

class BlockingQueueWithSemaphore<T> {
  T[] array;
  Semaphore semLock = new CountingSemaphore(1,1);
  Semaphore semProducer = new CountingSemaphore(1,1);
  Semaphore semConsumer = new CountingSemaphore(1,0);
  int size = 0;
  int capacity;
  int head = 0,tail=0;

  public BlockingQueueWithSemaphore(int capacity){
    array= (T[])new Object[capacity];
    this.capacity=capacity;
  }

  public void enqueue(T item) throws InterruptedException{
    semProducer.acquire();
    semLock.acquire();
    if(tail==capacity){
      tail = 0;
    }
    array[tail] = item;
    size++;
    tail++;
    semLock.release();
    semConsumer.release();
  }


  public T dequeue()throws InterruptedException{
    T item = null;
    semConsumer.acquire();
    semLock.acquire();

    if (head == capacity){
      head = 0;
    }

    item = array[head];
    array[head] = null;
    head++;
    size--;

    semLock.release();
    semProducer.release();

    return item;
  }
}

class CountingSemaphore extends Semaphore {
  public CountingSemaphore(int i, int j){
    super(i);
    this.release(i-j);
  }
}

