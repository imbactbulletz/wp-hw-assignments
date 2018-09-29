package assignment_3;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int port = 9000;
    private static Phonebook phonebook = new Phonebook();


    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running.");

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("["+ socket.getInetAddress()+"] has connected.");
                (new Thread(new ServerThread(socket, phonebook))).start();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
