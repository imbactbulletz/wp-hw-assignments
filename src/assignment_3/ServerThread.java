package assignment_3;

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
            String clientInput = in.readLine();
            if(clientInput.contains("ime")) {
                String response = respond(clientInput);
                out.println(response);
            }

            System.out.println("HTTP ZAHTEV KLIJENTA:\n");
            do{
                System.out.println(clientInput);
                clientInput = in.readLine();
            } while(!clientInput.trim().equals(""));



            in.close();
            out.close();
            socket.close();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }

    }

    private String respond(String clientInput){
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        String[] paramValues = new String[4];
        String tempString = null;

        try {


            paramValues[0] = clientInput.substring( clientInput.indexOf("ime=")+4, clientInput.indexOf("&"));
            tempString = clientInput.substring(clientInput.indexOf("&")+1);

            paramValues[1] = tempString.substring( tempString.indexOf("prezime=")+8, tempString.indexOf("&"));
            tempString = tempString.substring(tempString.indexOf("&")+1);

            paramValues[2] = tempString.substring( tempString.indexOf("broj=")+5, tempString.indexOf("&"));
            //tempString = tempString.substring(tempString.indexOf("&")+1);

            paramValues[3] = tempString.substring( tempString.indexOf("type=")+5, tempString.indexOf("HTTP")-1);
            //tempString = tempString.substring(tempString.indexOf("&")+1);



        }
        catch(IndexOutOfBoundsException iex){
            System.out.println("WTF");
            iex.printStackTrace();

        }
        // we have the record values in paramValues

        if(paramValues[3].equals("Submit")){
            if(paramValues[0].equals("") || paramValues[1].equals("") || paramValues[2].equals("")){
               response = invalidInput(response);
            }
            else{
                phonebook.addContact(paramValues[2],paramValues[0],paramValues[1]);
                response = validInput(response);
            }
        }

        if(paramValues[3].equals("List")){
            response = generateHTMLfromPhonebook(response);
        }
        return response;
    }

    private String invalidInput(String response){
        response+= "<!DOCTYPE html>\n" +
                "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "    <head>\n" +
                "      <title>\n" +
                "        Web Programiranje 3. domaci\n" +
                "      </title>\n" +
                "      <meta http-equiv=\"content-type\" content=\"text/html\"; charset=\"utf-8\" />\n" +
                "    </head>\n" +
                "    <body>\n" +
                "      <h1>Imenik</h1>\n" +
                "\n" +
                "      <form action =\"http://localhost:9000\" method=\"GET\" name=\"forma1\">\n" +
                "        <table cellspacing = 0 cellpadding = 3 border = 0>\n" +
                "          <tr>\n" +
                "            <td>Ime</td>\n" +
                "            <td><input type=\"text\" name=\"ime\"></td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td>Prezime</td>\n" +
                "            <td><input type=\"text\" name=\"prezime\"></td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td>Broj</td>\n" +
                "            <td><input type=\"text\" name=\"broj\"></td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td><input name=\"type\" type=\"submit\" value=\"Submit\"></td>\n" +
                "            <td><input name=\"type\" type=\"submit\" value=\"List\"></td>\n" +
                "            <td></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "        <h1>Sva polja moraju biti popunjena!</h1>\n" +
                "      </form>\n" +
                "    </body>\n" +
                "  </html>\n";
        return response;
    }

    private String validInput(String response){
        response += "<!DOCTYPE html>\n" +
                "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "    <head>\n" +
                "      <title>\n" +
                "        Web Programiranje 3. domaci\n" +
                "      </title>\n" +
                "      <meta http-equiv=\"content-type\" content=\"text/html\"; charset=\"utf-8\" />\n" +
                "    </head>\n" +
                "    <body>\n" +
                "      <h1>Imenik</h1>\n" +
                "\n" +
                "      <form action =\"http://localhost:9000\" method=\"GET\" name=\"forma1\">\n" +
                "        <table cellspacing = 0 cellpadding = 3 border = 0>\n" +
                "          <tr>\n" +
                "            <td>Ime</td>\n" +
                "            <td><input type=\"text\" name=\"ime\"></td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td>Prezime</td>\n" +
                "            <td><input type=\"text\" name=\"prezime\"></td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td>Broj</td>\n" +
                "            <td><input type=\"text\" name=\"broj\"></td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td><input name=\"type\" type=\"submit\" value=\"Submit\"></td>\n" +
                "            <td><input name=\"type\" type=\"submit\" value=\"List\"></td>\n" +
                "            <td></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "        <h1>SUCCESS!</h1>\n" +
                "      </form>\n" +
                "    </body>\n" +
                "  </html>\n";
        return response;
    }

    private String generateHTMLfromPhonebook(String response){
        response += "<!DOCTYPE html>\n" +
                "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "    <head>\n" +
                "      <title>\n" +
                "        Web Programiranje 3. domaci\n" +
                "      </title>\n" +
                "      <meta http-equiv=\"content-type\" content=\"text/html\"; charset=\"utf-8\" />\n" +
                "    </head>\n" +
                "    <body>\n" +
                "      <h1>Imenik</h1>\n" +
                "\n" +
                "      <form action =\"http://localhost:9000\" method=\"GET\" name=\"forma1\">\n" +
                "        <table cellspacing = 0 cellpadding = 3 border = 0>\n" +
                "          <tr>\n" +
                "            <td>Ime</td>\n" +
                "            <td><input type=\"text\" name=\"ime\"></td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td>Prezime</td>\n" +
                "            <td><input type=\"text\" name=\"prezime\"></td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td>Broj</td>\n" +
                "            <td><input type=\"text\" name=\"broj\"></td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td><input name=\"type\" type=\"submit\" value=\"Submit\"></td>\n" +
                "            <td><input name=\"type\" type=\"submit\" value=\"List\"></td>\n" +
                "            <td></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "        <h1>Kontakti:</h1>";


        response += "\n<table>";
        response += "<tr><th>Ime</th> <th>Prezime</th> <th>Broj</th></tr>";

        Iterator it = phonebook.getData().entrySet().iterator();

        if(it.hasNext() == false){
            response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
            response = validInput(response);
            return response;
        }


        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            ArrayList<String> as = (ArrayList<String>)pair.getValue();
            String table_row = "<tr>\n<td>" + as.get(0) + "</td>\n<td>" + as.get(1) + "</td>\n<td>" + pair.getKey() + "</td>\n</tr>";
            response += table_row;
            
        }

        response += "</table>";

        response +="      </form>\n" +
                "    </body>\n" +
                "  </html>";


        return response;
    }
}

