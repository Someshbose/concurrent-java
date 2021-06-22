package interview;

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

  void customerWalksIn(){

  }

  void barber(){

  }

  public static void runTest(){

  }
}
