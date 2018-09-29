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

@WebServlet(name = "UserAuthenticatorServlet", urlPatterns = {"/auth"})
public class UserAuthenticatorServlet extends HttpServlet {
    public static WebsiteUsersMap websiteUsersMap = new WebsiteUsersMap();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        // proveravamo da li je neka forma poslala zahtev za auth
        String formUsername = request.getParameter("username");
        String formPassword = request.getParameter("password");



        // forma je poslala zahtev za auth
        if(formUsername != null && formPassword != null){
            // pokusavamo da logujemo korisnika

            if(websiteUsersMap.getUsers().containsKey(formUsername)){ // sadrzi username
                if(websiteUsersMap.getUsers().get(formUsername).equals(formPassword)){ // iste sifre, BINGO!
                    // postavljamo username i password pa saljemo korisnika na checkout
                    session.setAttribute("username", formUsername);
                    session.setAttribute("password", formPassword);


                    // ukoliko smo ulogovali korisnika a on nema korpu znaci da je direktno gadjao login stranicu pa ga saljemo da bira iteme
                    if(session.getAttribute("shoppingCart") == null){
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("DashServlet");
                        requestDispatcher.forward(request,response);
                    }

                    // korisnik je vec birao iteme, ima korpu, sto znaci da ga saljemo na checkout
                    else
                        response.sendRedirect("checkout.jsp");
                    return;
                }
            }

            // nije pronadjen korisnik, saljemo na login
            int badacreditives = 1;


            // saljemo parametar da bi login stranica informisala korisnika o pogresnim parametrima
            session.setAttribute("badAcreditives", 1);
            response.sendRedirect("login.jsp");

        }
        // forma nije poslala zahtev za auth pa mozemo da proverimo da li je korisnik vec logovan putem sesije
        else{

            String username = (String)session.getAttribute("username");
            String password = (String)session.getAttribute("password");


            // vec ulogovan, saljemo ga na checkout stranicu
            if(username != null && password != null){
                response.sendRedirect("/checkout.jsp");
            }
            else{ // klijent pokusava da direktno pristupi /auth onda ga vracamo na login
               response.sendRedirect("login.jsp");
            }
        }






    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
