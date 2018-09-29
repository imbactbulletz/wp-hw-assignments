package assignment_6.src.servlets;

import beans.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "PreviewServlet", urlPatterns = {"/preview"})
public class PreviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(true);


        // ukoliko nema korpe u sesiji pravimo je i postavljamo u sesiju
        if(session.getAttribute("shoppingCart") == null){
            session.setAttribute("shoppingCart", new ShoppingCart());
        }

        // saljemo korisniku preview stranicu
        response.sendRedirect("preview.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            doGet(request,response);
    }
}
