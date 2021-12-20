package interview.boundedbuffer;

public class BoundedBuffer {
  public static void main(String[] args) throws Exception{
    final BlockingQueue<Integer> q = new BlockingQueue<>(5);

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
          for(int i=0;i<25;i++){
            int num =  q.dequeue();
            System.out.println("Dequeued "+num);
          }
        }catch (InterruptedException ie){

        }
      }
    });


    Thread t3 = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          for(int i=0;i<25;i++){
            int num =  q.dequeue();
            System.out.println("Dequeued "+num);
          }
        }catch (InterruptedException ie){

        }
      }
    });

    t1.start();
    Thread.sleep(4000);
    t2.start();
    t2.join();
    t3.start();
    t3.join();
  }
}

class BlockingQueue<T> {
  T[] array;
  Object lock = new Object();
  int size = 0;
  int capacity;
  int head = 0,tail=0;

  public BlockingQueue(int capacity){
    array= (T[])new Object[capacity];
    this.capacity=capacity;
  }

  public void enqueue(T item) throws InterruptedException{
    synchronized (lock){
      while (size == capacity){
        lock.wait();
      }
      if(tail==capacity){
        tail = 0;
      }
      array[tail] = item;
      size++;
      tail++;lock.notifyAll();
    }
  }

  public T dequeue()throws InterruptedException{
    T item = null;
    synchronized (lock){
      while (size==0){
        lock.wait();
      }

      if (head == capacity){
        head = 0;
      }

      item = array[head];
      array[head] = null;
      head++;
      size--;
      lock.notifyAll();
    }
    return item;
  }
}
