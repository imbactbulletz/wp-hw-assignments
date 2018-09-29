package assignment_1.zadatak_2;

import res.UtilClass;

import java.util.concurrent.CyclicBarrier;

public class CBThread implements Runnable{
    private CyclicBarrier barrier;
    private Buffer buffer;
    private int startRange;
    private int endRange;

    public CBThread(int startRange, int endRange, Buffer buffer, CyclicBarrier barrier){
        this.buffer = buffer;
        this.startRange = startRange;
        this.endRange = endRange;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        //System.out.println("Counting [" + startRange + "," + endRange + "]");
        for(int i=startRange; i<= endRange; i++){
            if(UtilClass.isPrime(i)){
                buffer.writeToBuffer(i);
            }
        }
        try {
            barrier.await();
        }
        catch(Exception e){
            e.printStackTrace();
        }
       // System.out.println("Done counting.");
    }
}
