package assignment_4.src.servlets;

import dataStructures.Phonebook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Ovaj servlet se zove kada je, vec ulogovani korisnik, kliknuo na "Add" ili "List" dugme u formi za unos podataka u imenik.
 * Na osnovu "action" parametra prosledjenog putem GET metode mozemo utvrditi da li je korisnik zatrazio unos ili izlistavanje podataka.
 *
 * Funkcionalnost: Proverava da li klijent ima sesiju. Ukoliko klijent nema sesiju vraca ga na login stranicu.
 * Ukoliko klijent ima sesiju, ispituje se prosledjena "akcija" a zatim se adekvatno reaguje na nju.
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/a"})
public class ActionServlet extends HttpServlet {
    private Phonebook phonebook; // nas telefonski imenik ( Uvek postoji samo jedna instanca servleta, dok se za svaku konekciju klijenta u browseru
                                 // pravi novi thread koji pristupa tom servletu. Zato svaki atribut servleta je zapravo deljeni resurs izmedju threadova
                                 // ( u ovom slucaju deljeni resurs je nas imenik) )

    public ActionServlet(){
        super();

        phonebook = new Phonebook(); // inicijalizujemo nas imenik pri kreiranju servleta
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true); // vraca sesiju klijeta

        String username = (String)session.getAttribute("username");
        String password = (String)session.getAttribute("password");

        response.setContentType("text/html"); // kaze klijentu kakav odgovor mu se salje
        PrintWriter out = response.getWriter();

        if(username == null || password == null){ // ukoliko su nevalidni akreditivi klijenta redirektujemo nazad na login stranicu
             redirectBack(out);
        }
        else{
            String name = request.getParameter("name"); // prosledjeno ime kontakta u glavnoj formi za dodavanje u imenik
            String lastName = request.getParameter("lastName");
            String number = request.getParameter("number");
            String action = request.getParameter("action"); // prosledjena "akcija" koja se desila (ako klijent klikne na "Add" dugme
                                                               // salje se action sa vrednoscu "Add" a ako je "List" dugme salje se
                                                               // action sa vrednoscu "List" kako bismo mogli da razlikujemo sta je klijent trazio

            // ukoliko je akcija Add dodajemo kontakt i saljemo klijentu slicnu stranicu kao sto je ona u kojoj je uneo podatke o kontaktu, uz
            // poruku da je kontakt uspesno dodat ukoliko se broj vec nije nalazio u imeniku ili poruku da broj vec postoji u imeniku
            if(action.equals("Add")){
                addContact(out, name, lastName, number);
            }

            // ukoliko je akcija List onda dinamicki generisemo HTML stranicu na osnovu naseg imenika i saljemo je klijentu
            if(action.equals("List")){
                listContacts(out);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    private void redirectBack(PrintWriter writer){
        String HTMLPage = "<!DOCTYPE html>\n" +
                "\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Login - Phonebook </title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"page-wrap\" align=\"center\">\n" +
                "      <h1>Login page</h1>\n" +
                "\n" +
                "      <div id=\"login-area\">\n" +
                "        <form method=\"post\" action=\"http://localhost:8080/login\">\n" +
                "          <table cellspacing = 0 cellpadding = 3 border = 0>\n" +
                "            <tr>\n" +
                "              <td>Username:</td>\n" +
                "              <td><input type=\"text\" name=\"username\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td>Password:</td>\n" +
                "              <td><input type=\"text\" name=\"password\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td><input name=\"submit\" type=\"submit\" value=\"Login\"></td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </form>\n" +
                "       <h1>You must be logged in before taking any actions!</h1>"+
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

        writer.println(HTMLPage);
        writer.close();
    }

    private void addContact(PrintWriter writer, String name, String lastName, String number){
        String result; // ovde smestamo poruku kojom cemo da informisemo korisnika da li broj postoji ili ne u imeniku.

        if(phonebook.getData().containsKey(number)){
            result = "That number is already in the phonebook!";
        }
        else{
            phonebook.addContact(number,name,lastName);
            result = "Successfully added a contact!";
        }

        // generisemo HTML stranicu i vracamo korisniku
        String HTMLPage = "<!DOCTYPE html>\n" +
                "\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Phonebook - Main Page</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"page-wrap\" align=\"center\">\n" +
                "      <h1>Main page</h1>\n" +
                "      <br>\n" +
                "      <div id=\"userPanel-area\">\n" +
                "        <form method=\"get\" action=\"http://localhost:8080/a\">\n" +
                "          <table cellspacing = 0 cellpadding = 3 border = 0>\n" +
                "            <tr>\n" +
                "              <td>Name:</td>\n" +
                "              <td><input type=\"text\" name=\"name\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td>Last Name:</td>\n" +
                "              <td><input type=\"text\" name=\"lastName\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td>Number:</td>\n" +
                "              <td><input type=\"text\" name=\"number\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td><input name=\"action\" type=\"submit\" value=\"Add\"></td>\n" +
                "              <td><input name=\"action\" type=\"submit\" value=\"List\"></td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "          <h1>"+ result +"</h1>" +
                "        </form>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

        writer.println(HTMLPage);
        writer.close();
    }

    private void listContacts(PrintWriter writer){
        // prvi deo HTML stranice
        String HTMLPageFirstPart = "<!DOCTYPE html>\n" +
                "\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Phonebook - Main Page</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"page-wrap\" align=\"center\">\n" +
                "      <h1>Main page</h1>\n" +
                "      <br>\n" +
                "      <div id=\"userPanel-area\">\n" +
                "        <form method=\"get\" action=\"http://localhost:8080/a\">\n" +
                "          <table cellspacing = 0 cellpadding = 3 border = 0>\n" +
                "            <tr>\n" +
                "              <td>Name:</td>\n" +
                "              <td><input type=\"text\" name=\"name\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td>Last Name:</td>\n" +
                "              <td><input type=\"text\" name=\"lastName\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td>Number:</td>\n" +
                "              <td><input type=\"text\" name=\"number\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td><input name=\"action\" type=\"submit\" value=\"Add\"></td>\n" +
                "              <td><input name=\"action\" type=\"submit\" value=\"List\"></td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "          <h1>Contacts:</h1>";

                // HTML koji dinamicki generisemo
                String generatedHTML = "";

                Iterator iterator = phonebook.getData().entrySet().iterator();

                // ukoliko je imenik prazan
                if(!iterator.hasNext()){
                    generatedHTML += "<h1> There are currently no contacts in the phonebook!</h1>";
                }
                else{
                    // pravimo tabelu i u nju smestamo podatke iz imenika
                    generatedHTML = "<table>";
                    while(iterator.hasNext()){
                        Map.Entry pair = (Map.Entry) iterator.next(); // par (Key,Value) gde je Key broj korisnika a Value ArrayLista sa 2 podatka (ime i prezime kontakta

                        ArrayList<String> as = (ArrayList<String>)pair.getValue();
                        String table_row = "<tr>\n<td>" + as.get(0) + "</td>\n<td>" + as.get(1) + "</td>\n<td>" + pair.getKey() + "</td>\n</tr>";
                        generatedHTML += table_row; // dodajemo red u tabelu
                    }
                    generatedHTML += "</table>"; // kraj tabele
                }

                String HTMLPageSecondPart =
                "        </form>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";


                // spajamo prvi, dinamicki generisani i drugi deo HTML stranice i sve to vracamo klijentu
                HTMLPageFirstPart += generatedHTML;
                HTMLPageFirstPart += HTMLPageSecondPart;

                writer.println(HTMLPageFirstPart);
                writer.close();
    }
}
