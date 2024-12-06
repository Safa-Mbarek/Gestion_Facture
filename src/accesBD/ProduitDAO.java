package accesBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entities.Produit;

public class ProduitDAO {
	/*
	 * Produit findById(String reference) : Elle cherche dans la table Produit et retourne le Produit ayant 
	 * la référence donnée en paramètre. Si elle ne le trouve pas, elle renvoie null.
	 */
	public Produit findById(String reference) {
		Produit produit = null;
		if (reference == null)
			return produit;
		Connection cnx = SConnection.getInstance();
		PreparedStatement st=null;
		try {
			if(cnx==null ||cnx.isClosed())
				return produit;
			st = cnx.prepareStatement("select * from produit where reference= ?");
			st.setString(1, reference);
			ResultSet res = st.executeQuery();
			if (res.next())
			/* On utilise if et non pas while car on sait qu'i existe 
			 * au plus un enregistrement dans la table Produit, dont la clé 
			 * est 'ref'
			*/
			{
				produit = new Produit(res.getString(1), res.getString(2), res.getFloat(3));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return produit;
	}
	
	/*
	 * void save (Produit p) : Elle ajoute le produit p à la table Produit, s’il n’existe pas. 
	 * Dans le cas contraire, elle met à jour ses caractéristiques.	
	 */

	public void save(Produit p) {
		if (p == null)
			return;
		Connection cnx = SConnection.getInstance();
		int n = 0;
		PreparedStatement st=null;
		try {
			if(cnx==null ||cnx.isClosed())
				return;
			Produit prod= this.findById(p.getReference());
			if(prod!=null) {
				st = cnx.prepareStatement("update produit set libelle=?, prix=? where reference=?");
				st.setString(1, p.getLibelle());
				st.setFloat(2, p.getPrix());
				st.setString(3, p.getReference());
				n = st.executeUpdate();
				if(n!=0)
					System.out.println("Produit "+p.getReference()+" modifié.");
			}
			else {
				st = cnx.prepareStatement("insert into produit values (?, ?, ?,?)");
				st.setString(1, p.getReference());;
				st.setString(2, p.getLibelle());
				st.setFloat(3, p.getPrix());
				st.setInt(4, 1);
				n = st.executeUpdate();
				if(n!=0)
					System.out.println("Produit "+p.getReference()+" ajouté.");
				}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * void delete(String ref) : Elle met le champ etat du produit ayant 
	 * la reference ref à la valeur '0'(archivé).
	 */
	
	public void delete(String ref) {
		if (ref == null)
			return;
		Connection cnx = SConnection.getInstance();
		PreparedStatement st=null;
		int n = 0;
		try {
			if(cnx==null ||cnx.isClosed())
				return;
			//La suppression consiste à mettre le champ état à 'archive'
			st = cnx.prepareStatement("update produit set etat=0 where reference= ?");
			st.setString(1, ref);
			n = st.executeUpdate();
			if(n>0)
				System.out.println("Produit archiv�");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ************************************************************//
	
}
