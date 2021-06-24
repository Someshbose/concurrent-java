package interview;

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopProblem {
  final int CHAIRS = 3;
  Semaphore waitForCustomerToEntry = new Semaphore(0);
  Semaphore waitForBarberToGetReady = new Semaphore(0);
  Semaphore waitForCustomerToLeave = new Semaphore(0);
  Semaphore waitForBarberToCutHair = new Semaphore(0);

  int waitingCustomer = 0 ;
  ReentrantLock lock = new ReentrantLock();
  int hairCutGiven = 0;

  void customerWalksIn() throws InterruptedException{
    lock.lock();
    if(waitingCustomer==CHAIRS){
      System.out.println("Customer walks out. All chairs are occupied!");
      lock.unlock();
      return;
    }
    waitingCustomer++;
    lock.unlock();

    waitForCustomerToEntry.release();
    waitForBarberToGetReady.acquire();

    lock.lock();
    waitingCustomer--;
    lock.unlock();

    waitForBarberToCutHair.acquire();
    waitForCustomerToLeave.release();
  }

  void barber() throws InterruptedException{
    while (true){
      waitForCustomerToEntry.acquire();
      waitForBarberToGetReady.release();
      hairCutGiven++;
      System.out.println("Barber cutting hair ... "+hairCutGiven);
      Thread.sleep(50);
      waitForBarberToCutHair.release();
      waitForCustomerToLeave.acquire();
    }
  }

  public static void runTest() throws InterruptedException {
    HashSet<Thread> set = new HashSet<>();
    final  BarberShopProblem barberShopProblem = new BarberShopProblem();

    Thread barberThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          barberShopProblem.barber();
        }catch (Exception e){

        }
      }
    });

    for(int i=0;i<10;i++){
      Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
          try{
            barberShopProblem.customerWalksIn();
          }catch (Exception e){

          }
        }
      });
      set.add(t);
    }

    for(Thread t:set){
      t.start();
    }

    for(Thread t:set){
      t.join();
    }

    set.clear();
    Thread.sleep(800);

    for(int i=0;i<5;i++){
      Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
          try{
            barberShopProblem.customerWalksIn();
          }catch (Exception e){

          }
        }
      });
      set.add(t);
    }

    for(Thread t:set){
      t.start();
    }

    barberThread.join();

  }

  public static void main(String[] args)throws Exception {
    runTest();
  }
}
