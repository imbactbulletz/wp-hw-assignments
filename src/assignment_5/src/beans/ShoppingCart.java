package assignment_5.src.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Reperezentuje korpu za kupovinu. Sadrzi vektor stavki (uredjeni par
 * (proizvod, kolicina)).
 */
public class ShoppingCart implements Serializable {
	
	private ArrayList<ShoppingCartItem> items;

	public ShoppingCart() {
		items = new ArrayList<ShoppingCartItem>();
	}

	public void addItem(Product product, int count) {
		int alreadyExistsInCart = 0;


		// provera da li vec postoji item u korpi
		for(ShoppingCartItem cartItem : items){
			if(cartItem.getProduct().getId().equals(product.getId())){
				// ako postoji, onda samo uvecavamo count umesto dodavanja novog objekta
				cartItem.setCount(cartItem.getCount()+count);

				// postavljamo flag da je nadjen na 1 da ne bismo dodavali u listu kasnije
				alreadyExistsInCart =1;
				break;
			}
		}

		if(alreadyExistsInCart == 0){
			items.add(new ShoppingCartItem(product, count));
		}
	}

	public ArrayList<ShoppingCartItem> getItems() {
		return items;
	}
}
