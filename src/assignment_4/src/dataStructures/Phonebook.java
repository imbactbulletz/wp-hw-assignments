package assignment_4.src.dataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Klasa u kojoj se nalaze podaci koje ulogovani korisnici unose.
 */
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
