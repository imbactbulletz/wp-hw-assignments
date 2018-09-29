package assignment_1.zadatak_2;

import java.util.concurrent.CyclicBarrier;

/*
* Zadatak 2.
* Startovati N niti i svaka nit dobija M desetica. Svaka nit racuna sve proste brojeve u datom opsegu. Implementirati CyclicBarrier.
* Sve niti kada nadju prost broj upisuju u deljeni niz. Kada svi zavrse svoj posao, niz se ispisuje korisniku.

 */
public class Main {
    public static void main(String[] args) {
        int n = 50; // range to test
        int m = 1; // number of tens that each thread should calculate
        Buffer buffer = new Buffer(n);

        CyclicBarrier barrier = new CyclicBarrier(((int)n/(m*10)),
                new Runnable(){
                    @Override
                    public void run() {
                        buffer.printBuffer();
                    }
                });

        for(int i=0; i< (int)(n/(m*10)); i++){
            // primer: range je 500. racuna se po jedna desetica:
            // prvi thread testira od 10*(0) + 1 = 1 do 10*(0)+(0) = 0
            // drugi testira od 10*(1)+1 = 11 do 10*(1)+10 = 20, itd.
            (new Thread(new CBThread((m*i*10)+1, (m*10*i) +(m*10), buffer, barrier))).start();
        }


    }
}
