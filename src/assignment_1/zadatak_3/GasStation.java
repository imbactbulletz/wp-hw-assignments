package assignment_1.zadatak_3;

import res.UtilClass;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class GasStation {
    private Semaphore gasUnits;

    public GasStation(int numberOfGasUnits){
        gasUnits = new Semaphore(numberOfGasUnits,true);
    }

    public void seizeGasUnit(){
        try{
            //System.out.println("Arriving at the Gas Station..");
            System.out.println("Arrived at the Gas Station. Available Gas Units: " + gasUnits.availablePermits());

            gasUnits.acquire();
            System.out.println("Gas Unit seized! Remaining available: " + gasUnits.availablePermits());

            try{
                // fueling up time
                sleep(UtilClass.getRandomNumber(2000));
            }
            finally{
                // paying time
                sleep(UtilClass.getRandomNumber(2000));

                // releasing
                gasUnits.release();
                System.out.println("Releasing Gas unit! Remaining Gas Units available: " + gasUnits.availablePermits());
            }
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }

}
