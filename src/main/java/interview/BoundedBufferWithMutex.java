package interview;

import java.util.concurrent.locks.ReentrantLock;

public class BoundedBufferWithMutex {

  public static void main(String[] args) throws Exception{
    final BlockingQueue<Integer> q = new BlockingQueue<>(5);

    Thread pt1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
            int i=1;
            while (true){
              q.enqueue(i);
              System.out.println("Producer thread 1 enqueued"+i);
              i++;
          }
        }catch (InterruptedException ie){

        }
      }
    });



    Thread pt2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          int i=5000;
          while (true){
            q.enqueue(i);
            System.out.println("Producer thread 2 enqueued"+i);
            i++;
          }
        }catch (InterruptedException ie){

        }
      }
    });


    Thread pt3 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          int i=10000;
          while (true){
            q.enqueue(i);
            System.out.println("Producer thread 3 enqueued"+i);
            i++;
          }
        }catch (InterruptedException ie){

        }
      }
    });

    Thread ct1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          while (true){
            int item = q.dequeue();
            System.out.println("Consumer thread 1 enqueued"+item);
          }
        }catch (InterruptedException ie){

        }
      }
    });

    Thread ct2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          while (true){
            int item = q.dequeue();
            System.out.println("Consumer thread 2 enqueued"+item);
          }
        }catch (InterruptedException ie){

        }
      }
    });

    Thread ct3 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          while (true){
            int item = q.dequeue();
            System.out.println("Consumer thread 3 enqueued"+item);
          }
        }catch (InterruptedException ie){

        }
      }
    });

    pt1.setDaemon(true);
    pt2.setDaemon(true);
    pt3.setDaemon(true);
    ct1.setDaemon(true);
    ct2.setDaemon(true);
    ct3.setDaemon(true);

    pt1.start();
    pt2.start();
    pt3.start();
    ct1.start();
    ct2.start();
    ct3.start();

    Thread.sleep(1000);
  }
}

class BlockingQueueWithMutex<T> {
  T[] array;
  ReentrantLock lock = new ReentrantLock();
  int size = 0;
  int capacity;
  int head = 0,tail=0;

  public BlockingQueueWithMutex(int capacity){
    array= (T[])new Object[capacity];
    this.capacity=capacity;
  }

  public void enqueue(T item) throws InterruptedException{
      lock.lock();
      while (size == capacity){
        lock.unlock();
        lock.lock();
      }
      if(tail==capacity){
        tail = 0;
      }
      array[tail] = item;
      size++;
      tail++;
      lock.unlock();
   }


  public T dequeue()throws InterruptedException{
    T item = null;
    while (size==0){
      lock.unlock();
      lock.lock();
    }

    if (head == capacity){
      head = 0;
    }

    item = array[head];
    array[head] = null;
    head++;
    size--;
    lock.unlock();

    return item;
  }
}
