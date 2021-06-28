package interview;

import java.util.concurrent.Semaphore;

public class PrintNumberSeries {
    private int n;
    private Semaphore zeroSem , oddSem, evenSem;

    PrintNumberSeries(int n){
        this.n=n;
        zeroSem = new Semaphore(1);
        oddSem = new Semaphore(0);
        evenSem = new Semaphore(0);
    }

    public void PrintZero(){
        for (int i=0; i<n ;i++){
            try {
                zeroSem.acquire();
            }
            catch (Exception e){

            }
            System.out.println(0);
            (i%2==0?evenSem:oddSem).release();
        }
    }

    public void PrintEven(){
        for (int i=2; i<n ;i+=2){
            try {
                evenSem.acquire();
            }
            catch (Exception e){

            }
            System.out.println(i+"even");
            zeroSem.release();
        }
    }

    public void PrintOdd(){
        for (int i=1; i<n ;i+=2){
            try {
                oddSem.acquire();
            }
            catch (Exception e){

            }
            System.out.println(i+"odd");
            zeroSem.release();
        }
    }
}

class PrintNumberSeriesThread extends Thread{
    PrintNumberSeries zeo;
    String method;

    PrintNumberSeriesThread(PrintNumberSeries zeo,String method){
        this.zeo=zeo;
        this.method=method;
    }

    @Override
    public void run() {
        if (method.equals("zero")){
            zeo.PrintZero();
        }
        else if (method.equals("even")){
            zeo.PrintEven();
        }
        else if (method.equals("odd")){
            zeo.PrintOdd();
        }
    }

    public static void main(String[] args) {
        PrintNumberSeries series = new PrintNumberSeries(15);

        PrintNumberSeriesThread thread1= new PrintNumberSeriesThread(series,"zero");
        PrintNumberSeriesThread thread2= new PrintNumberSeriesThread(series,"even");
        PrintNumberSeriesThread thread3= new PrintNumberSeriesThread(series,"odd");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}


