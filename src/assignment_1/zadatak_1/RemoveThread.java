package assignment_1.zadatak_1;

import res.UtilClass;

public class RemoveThread implements Runnable{
    private HashMapWrapper hashmap;

    public RemoveThread(HashMapWrapper hashmap){
        this.hashmap = hashmap;
    }

    @Override
    public void run() {
        int key = UtilClass.getRandomNumber(10);

        hashmap.remove(key);
    }
}
