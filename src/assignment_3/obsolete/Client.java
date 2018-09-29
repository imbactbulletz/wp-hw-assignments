package assignment_3.obsolete;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final int port = 9000;

    public static void printMenu(){
        System.out.println("Enter an option:");
        System.out.println("1. Add a new contact");
        System.out.println("2. List all contacts");
        System.out.println("3. Exit");
    }

    public static void addContact(PrintWriter out, Scanner scn) throws Exception{

        // sending number
        System.out.println("Number:");
        out.println(scn.nextLine());
        // sending name
        System.out.println("Name");
        out.println(scn.nextLine());
        // sending last name
        System.out.println("Last name:");
        out.println(scn.nextLine());
    }

    public static void main(String[] args) {
        try{
            InetAddress address = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(address, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

            printMenu();
            Scanner scn = new Scanner(System.in);
            String clientEntry = scn.nextLine();

            while(UtilClass.valueOf(clientEntry) != 3){
                // add a new contact
                if(UtilClass.valueOf(clientEntry) == 1){
                    out.println(ProtocolMessages.ENTER_DATA);
                    addContact(out, scn);
                }
                // list all contacts
                if(UtilClass.valueOf(clientEntry) == 2){
                    out.println(ProtocolMessages.LIST_DATA);
                    String data =  in.readLine();
                    while(UtilClass.valueOf(data) != ProtocolMessages.END_OF_SENDING_DATA){
                        System.out.println( data + " " +  in.readLine() + " " + in.readLine());
                        data = in.readLine();
                    }

                }
                printMenu();
                clientEntry = scn.nextLine();

            }


            // exit option
            if(UtilClass.valueOf(clientEntry) == 3){
                out.println(ProtocolMessages.DISCONNECT);
                in.close();
                out.close();
                socket.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
