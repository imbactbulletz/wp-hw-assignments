package assignment_1.zadatak_1;

public class PutThread implements Runnable {
    private HashMapWrapper hashmap;

    public PutThread(HashMapWrapper hashMap){
        this.hashmap = hashMap;
    }
    @Override
    public void run() {
        int key = res.UtilClass.getRandomNumber(10);
        String value = Integer.toString(res.UtilClass.getRandomNumber(10));

        hashmap.put(key, value);
    }
}
