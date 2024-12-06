package tests;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import accesBD.ClientDAO;
import accesBD.FactureDAO;
import accesBD.LigneFactureDAO;
import accesBD.ProduitDAO;
import accesBD.SConnection;
import entities.Client;
import entities.Facture;
import entities.LigneFacture;
import entities.Produit;

public class Test {
	public static void main(String[] args) {
		
		  ClientDAO cdao = new ClientDAO(); 
		  Client c1=new Client("00001", "Mohamed");
		  Client c2=new Client("00002", "Ali");
		  cdao.save(c1);
		  cdao.save(c2);
		//cdao.delete(c1); 
		  Client c3=cdao.findById("12345");
		  if(c3!=null)
			  System.out.println("Client trouvé : "+c3);
		  
		  System.out.println("************************************************");
		  
		  ProduitDAO pdao = new ProduitDAO();
		  Produit p= new Produit("c100","Taille-haie à essence 722 mm", 490.000f);
		  pdao.save(p);
		  pdao.delete("c100");	
		  
		  System.out.println("************************************************");
		  
		  LigneFactureDAO lfdao = new LigneFactureDAO();
		  FactureDAO fdao = new FactureDAO(); 
		  Facture f = null; 
		  DateTimeFormatter ff = DateTimeFormatter.ofPattern("dd-MM-yyyy"); 
		  LigneFacture lf1 = new LigneFacture(pdao.findById("54321"), 1800, 3); 
		  LigneFacture lf2 = new LigneFacture(pdao.findById("c100"), 450, 2);
		  
		  LocalDate ld= LocalDate.parse("12-04-2002",ff);
		  Client c4= new Client("00001", "Salem");
		  f = new Facture("00002002", ld,c4); 
		  f.getLignes().add(lf1);
		  f.getLignes().add(lf2);
		  fdao.save(f); 
		  System.out.println(fdao.findById("00002022")); 
		  
		  // lfdao.delete(f, pdao.findById("54321")); 
		  fdao.delete("00002002"); 
		  SConnection.close();
		 
		
	}
}
