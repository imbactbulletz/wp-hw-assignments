package assignment_6.src.servlets;

import beans.Products;
import beans.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "DashServlet", urlPatterns = {"/"})
public class DashServlet extends HttpServlet {
    File file;
    Products products;

    // pronalazi lokaciju naseg fajla sa proizvodima i kreira proizvode na osnovu njega
    @Override
    public void init() throws ServletException {
        super.init();

        try {
            file = new File(getServletContext().getResource("products.txt").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // inicijalno ucitavamo sve proizvode iz tekstualnog fajla
        products = new Products();
        products.setFile(file);
        products.readProducts();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(true);

        // pretraga - ako je korisnik uneo nesto u pretragu to uzimamo i cuvamo u sesiji kako bismo filtrirali jsp stranicu kada se ponovo ucita
        String searchCriteria = request.getParameter("search");
        session.setAttribute("searchCriteria", searchCriteria);

        // ako sesija nema proizvode, onda stavljamo proizvode
        if(session.getAttribute("products") == null){
            session.setAttribute("products", products);
            session.setAttribute("shoppingCart", new ShoppingCart());
        }

        // saljemo klijenta na stranicu za dodavanje
        response.sendRedirect("browse.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request,response);
    }

}
