package basic;

import java.util.Timer;
import java.util.TimerTask;

public class TimerExample {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){

            @Override
            public void run() {
                System.out.println("A sample task!");
            }
        };

        timer.schedule(task,10);
    }
}
