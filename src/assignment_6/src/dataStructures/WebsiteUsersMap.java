package assignment_6.src.dataStructures;

import java.util.concurrent.ConcurrentHashMap;


public class WebsiteUsersMap {
    private ConcurrentHashMap users;

    public WebsiteUsersMap(){
        users = new ConcurrentHashMap<String, String>();
        populateMap();

    }

    public synchronized void addUser(String name, String password){
        users.put(name, password);

    }
    private void populateMap(){
        this.addUser("Stefan","st3fan");
        this.addUser("admin","admin");
    }
    public ConcurrentHashMap getUsers() {
        return users;
    }
}

