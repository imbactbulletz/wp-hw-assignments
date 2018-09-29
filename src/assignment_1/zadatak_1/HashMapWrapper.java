package assignment_1.zadatak_1;

import java.util.HashMap;

public class HashMapWrapper<K,V> {
    private HashMap<K,V> internalHashMap;
    private int capacity = 0;
    private int maxCapacity;

    public HashMapWrapper(int maxCapacity){
        this.maxCapacity = maxCapacity;
        internalHashMap = new HashMap<K,V>();
    }

    public synchronized void put(K key, V value){
        if(capacity == maxCapacity) {
            System.out.println("The Hashmap is already full. Exiting.");
            return;
        }
        internalHashMap.put(key, value);
        capacity++;

        System.out.println("[K:" + key + ", V:" + value + "] has been written." + internalHashMap.size());

    }

    public synchronized V remove(K key){
        if(capacity == 0){
            System.out.println("The HashMap is empty. Exiting.");
            return null;
        }
        V result = internalHashMap.remove(key);
        capacity--;

        if(result != null)
        System.out.println("[K:" + key + ", V:" + result + "] has been deleted.");
        return result;

    }

    public HashMap getHashMap(){
        return internalHashMap;
    }
}


