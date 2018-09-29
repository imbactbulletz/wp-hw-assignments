package assignment_6.src.servlets;

import dataStructures.WebsiteUsersMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    WebsiteUsersMap websiteUsersMap;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        websiteUsersMap = UserAuthenticatorServlet.websiteUsersMap; // postojeci korisnici sajta
        HttpSession session = request.getSession(true);


        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // postoji korisnik
        if(websiteUsersMap.getUsers().containsKey(username)){
            session.setAttribute("userAlreadyExists", 1); // saljemo parametar da ispisemo u jsp stranici informaciju o postojecem korisniku

            response.sendRedirect("/register.jsp"); // redirektujemo korisnika opet na stranicu za registraciju
        }
        else{ // ne postoji korisnik, registruje ga
            websiteUsersMap.addUser(username,password);

            // postavlja korisnikove akreditive u sesiju da bismo mogli da verifikujemo da li je korisnik ulogovan ili ne
            session.setAttribute("username", username);
            session.setAttribute("password", password);

            //ukoliko je korisnik gadjao direktno login stranicu
            if(session.getAttribute("shoppingCart") == null){
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("DashServlet");
                requestDispatcher.forward(request,response); // saljemo ga na pregled proizvoda posto nema korpu
            }

            // imao je korpu, znaci stigao je do logina putem biranja itema
            else
                response.sendRedirect("checkout.jsp");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request,response);
    }
}
