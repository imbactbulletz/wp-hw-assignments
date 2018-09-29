package assignment_4.src.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DashServlet", urlPatterns = {"/"})
public class DashServlet extends HttpServlet {

    public DashServlet(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(true);

        String username = (String)session.getAttribute("username");
        String password = (String)session.getAttribute("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        System.out.println(username);
        System.out.println(password);

        if(username == null || password == null){
            redirectBack(out);
        }
        else{
            redirectToMainPage(out, username);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
                "              <td><input type=\"password\" name=\"password\"></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td><input name=\"submit\" type=\"submit\" value=\"Login\"></td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </form>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

        writer.println(HTMLPage);
        writer.close();
    }

    public void redirectToMainPage(PrintWriter writer, String username){
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
                "              <td><input type=\"password\" name=\"lastName\"></td>\n" +
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
}
