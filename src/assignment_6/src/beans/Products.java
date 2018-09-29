package assignment_6.src.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Klasa koja reprezentuje spisak raspolozivih proizvoda za kupovinu. Spisak
 * proizvoda se inicijalizuje iz tekstualne datoteke, koja je oblika: <br>
 * id;naziv;jedinicna cena
 */
public class Products {
	
	private HashMap<String, Product> products = new HashMap<String, Product>();
	private File file; // fajl iz kojeg citamo

	public Products() {

	}

	/**
	 * Cita proizvode iz datoteke i smesta ih u asocijativnu listu proizvoda.
	 * Kljuc je id proizvoda.
	 */
	public void readProducts() {
		String line, id = "", name = "", price = "";
		StringTokenizer st;
		BufferedReader in;


		try {
			in = new BufferedReader(new FileReader(file));
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					name = st.nextToken().trim();
					price = st.nextToken().trim();
				}
				products.put(id, new Product(id, name, Double
						.parseDouble(price)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** Vraca kolekciju proizvoda. */
	public Collection<Product> values() {
		return products.values();
	}

	/** Vraca proizvod na osnovu njegovog id-a. */
	public Product getProduct(String id) {
		return products.get(id);
	}

	public void setFile(File file) {
		this.file = file;
	}


}
