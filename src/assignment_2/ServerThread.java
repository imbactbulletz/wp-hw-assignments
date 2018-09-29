package assignment_2;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ServerThread implements Runnable {
    private Phonebook phonebook;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket socket, Phonebook phonebook) {
        this.phonebook = phonebook;
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new  BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message = in.readLine();

            while(UtilClass.valueOf(message) != ProtocolMessages.DISCONNECT){
                if(UtilClass.valueOf(message) == ProtocolMessages.ENTER_DATA) {
                    phonebook.addContact(in.readLine(), in.readLine(), in.readLine());
                }

                if(UtilClass.valueOf(message) == ProtocolMessages.LIST_DATA){
                    System.out.println("listing data");
                    Iterator it = phonebook.getData().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        out.println(pair.getKey());

                        ArrayList<String> as = (ArrayList<String>)pair.getValue();

                        out.println(as.get(0));
                        out.println(as.get(1));
                    }

                    out.println(ProtocolMessages.END_OF_SENDING_DATA);
                    System.out.println("done listing");
                }
                message = in.readLine();
            }

            if(UtilClass.valueOf(message) == ProtocolMessages.DISCONNECT){
                System.out.println("Client ("+ socket.getInetAddress()+ ")" + " has disconnected");
                in.close();
                out.close();
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
