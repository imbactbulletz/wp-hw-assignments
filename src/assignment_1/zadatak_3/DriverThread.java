package assignment_1.zadatak_3;

public class DriverThread implements Runnable {
    private GasStation gasStation;

    public DriverThread(GasStation gasStation){
        this.gasStation = gasStation;
    }


    @Override
    public void run() {
        gasStation.seizeGasUnit();
    }
}
