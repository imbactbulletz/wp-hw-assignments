package assignment_1.zadatak_3;

import res.UtilClass;

/*
*
* Zadatak 3.
* Potrebno je simulirati situaciju kada posetioci dolaze na pumpu.
* Pošto je broj jedinica za tocenje gotiva ogranicen vozaci moraju da cekaju dok se neka jedinica ne oslobodi.
* Svaki posetilac prvo provodi proizvoljno vreme u voznji, zatim dolazi na punpu I bira jednu od jedinica.
* Svaka jedinica moze da toci sve vrste goriva.
* Zatim proizvoljno dugo toci goriva I potom proizvoljno dugo provodi na kasi I na kraju napusta jedinicu.
* Simulirati upotrebom semaphore.
 */
public class Main {
    public static void main(String[] args) {
        int drivers = 10; // number of drivers
        int gasUnits = 5; // total number of gas units at the gas station

       GasStation gasStation = new GasStation(gasUnits);

        for(int i=0; i<drivers; i++){
            Thread driverThread = new Thread(new DriverThread(gasStation));
            try{
                // circle around in town
                driverThread.sleep(UtilClass.getRandomNumber(500));
                // go to the gas station
                driverThread.start();
            }
            catch(InterruptedException ie){
                ie.printStackTrace();
            }


        }
    }
}
