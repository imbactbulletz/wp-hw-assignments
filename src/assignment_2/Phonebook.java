package assignment_2;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Phonebook {
    private ConcurrentHashMap data;

    public Phonebook(){
        data = new ConcurrentHashMap<String, ArrayList<String>>();
    }

    public synchronized void addContact(String number, String name, String lastName){
        ArrayList<String> pair = new ArrayList<String>(){
            @Override
            public String toString(){
                return name + " " + lastName;
            }
        };

        pair.add(name);
        pair.add(lastName);

        data.put(number,pair);

    }

    public ConcurrentHashMap getData() {
        return data;
    }
}
