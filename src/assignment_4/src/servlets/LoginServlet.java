package assignment_4.src.servlets;

import dataStructures.WebsiteUsersMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Ovaj Servlet se zove kada se klikne na Login dugme na login stranici, tj kada se u address bar u browseru unese
 * "http://localhost:8080/login".
 *
 * Funkcionalnost: parsira prosledjene akreditive (prosledjene od strane klijenta) i proverava njihovu validnost.
 * Ako su akreditivi validni, klijentu se vraca glavna stranica imenika na kojoj unosi podatke u imenik, i postavlja se sesija.
 * Ako akreditivi nisu validni, klijentu se ponovo vraca login stranica sa porukom o pogresno unetim podacima.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet{
    WebsiteUsersMap usersMap; // "baza" korisnika koju imamo

    public LoginServlet(){
        super();
        usersMap = new WebsiteUsersMap(); // inicijalizacija "baze" i populisanje test podacima
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); // uzimamo username iz POST metode
        String password = request.getParameter("password"); // uzimamo password iz POST metode

        response.setContentType("text/html"); // koji odgovor saljemo klijentu
        PrintWriter out = response.getWriter();

        if(username == null || password == null){ // ukoliko su akreditivi u losem formatu klasifikujemo ih kao neispravne
            invalidParameters(out); // saljemo klijenta opet na login stranicu i dodajemo poruku o pogresno unetim podacima i zatvaramo socket
        }
        if(username.equals("") || password.equals("")){ // ista stvar kao i gore
            invalidParameters(out);
        }


        // Ukoliko korisnik postoji u nasoj "bazi" korisnika, i ukoliko se njegovi akreditivi poklapaju sa unetim podacima od strane klijenta,
        // onda klijentu saljemo glavnu stranicu imenika i setujemo parametre sesije kako bismo dalje prepoznali da li je ulogovan ili ne.
        if(usersMap.getUsers().containsKey(username)){

            String mappedPassword = (String)usersMap.getUsers().get(username);

            if(password.equals(mappedPassword)){
                validParameters(out, username);


                //nakon sto smo mu poslali HTML stranicu postavljamo atribute sesije
                // Sesija je jedan objekat koji server daje klijentu i taj objekat zivi sve dok je browser ukljucen
                // u nasem slucaju korisnik je se tek ulogovao i inicijalno nema sesiju pa mu zadajemo atribute sesije.
                HttpSession session = request.getSession(true); // vraca trenutnu sesiju klijenta ako je ima, a ako je nema onda je kreira za nas

                session.setAttribute("username",username);
                session.setAttribute("password",password);

                out.close(); // zatvara konekciju
            }
        }
        else{
                invalidParameters(out); // salje klijentu poruku o pogresno unetim podacima za login
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response); // pozivamo doGet da ne bismo 2 puta pisali identican kod
    }

    private void validParameters(PrintWriter writer, String username){
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
                "      <h1>Hello "+ username +"!</h1>\n" +
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
                "        </form>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

                writer.println(HTMLPage);
    }

    private void invalidParameters(PrintWriter writer){
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
                "              <td><input type=\"password\" name=\"password\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td><input name=\"submit\" type=\"submit\" value=\"Login\"></td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </form>\n" +
                "       <h1>Wrong username/password!</h1>"+
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

        writer.println(HTMLPage);
        writer.close();
    }
}
