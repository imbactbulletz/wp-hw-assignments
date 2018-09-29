/*
* Zadatak 1.
* Implementirati HashMap tako da bude threadSafe, moze maksimalno da primi n elemenata, upotrebom synchronized bloka. K threadova upisuje, dok M threadova cita.
*
 */

package assignment_1.zadatak_1;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 7; // HashMap capacity
        int k = 9; // number of threads that are putting data into the hashmap
        int r = 15; // number of threads removing data from the hashmap

        // the hashmap "wrapper" that we're going to perform on
        HashMapWrapper<Integer,String> hashMap = new HashMapWrapper<Integer,String>(n);

        // Creating Threads that put values into the wrapped hashmap
        for(int i=0; i<k; i++){
            (new Thread(new PutThread(hashMap))).start();
        }
        // Creating Threads that remove values
        for(int i=0; i<r; i++){
            (new Thread(new RemoveThread(hashMap))).start();
        }



    }
}
