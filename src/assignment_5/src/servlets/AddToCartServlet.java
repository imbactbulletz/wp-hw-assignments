package assignment_5.src.servlets;

import beans.Products;
import beans.ShoppingCart;
import com.sun.xml.internal.bind.v2.TODO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Ovaj Servlet regulise logiku dodavanja itema u korpu.
 *
 * Ukoliko korpa ne postoji onda se pravi, a ukoliko postoji onda se u postojecu korpu dodaju itemi.
 * Podrazumeva se da se item koji se dodaje nalazi u GET/POST zahtevu korisnika.
 * Kada se item doda onda se klijent salje nazad na stranicu za dodavanje itema.
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/add"})
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(true);

            ShoppingCart shoppingCart;
            // ako korisnik nema korpu dodajemo mu je
            if(session.getAttribute("shoppingCart") == null){
                shoppingCart = new ShoppingCart();

                session.setAttribute("shoppingCart", shoppingCart);
            }
            else{ // ako vec ima shoppingCart onda izvlacimo iz sesije
                shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
            }


            Products products = (Products) session.getAttribute("products"); // skup proizvoda koji korisnik ima na raspolaganju

            // parsiramo prosledjene parametre iz zahteva
            String itemID = request.getParameter("itemId");
            String amount = request.getParameter("itemCount");


            // ukoliko je neko pokusao da direktno gadja "/add" onda ga vratimo na browse.jsp preko DashServleta
            if(itemID == null && amount == null){
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("DashServlet");
                requestDispatcher.forward(request,response);
                return;
            }
            // dodajemo proizvod u korpu
            shoppingCart.addItem(products.getProduct(itemID),Integer.valueOf(amount));
            System.out.println("Velicina korpe: " + shoppingCart.getItems().size());
            response.sendRedirect("/browse.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            doGet(request,response);
    }
}
