package accesBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import entities.Facture;
import entities.LigneFacture;
import entities.Produit;

public class LigneFactureDAO {
	/*
	 * void save(Facture f, LigneFacture lf) : Elle permet d’ajouter la ligne 
	 * facture lf de la facture f à la table LigneFacture si elle n’existe pas. 
	 * Dans le cas contraire, elle met à jour ses caractéristiques. 
	 */
	public void save(Facture f, LigneFacture lf) {
		if (f == null || lf == null)
			return;
		Connection cnx = SConnection.getInstance();
		int n = 0;
		PreparedStatement stNew=null,st=null;
		ResultSet r=null;
		try {
			if (cnx == null || cnx.isClosed())
				return;
			st = cnx.prepareStatement("Select * from LigneFacture where numFacture=? and refProduit=?");	
			st.setString(1, f.getNumero());
			st.setString(2, lf.getProd().getReference());
			r= st.executeQuery();
			if(r.next()) 
			/*On utilise if et non pas while car on sait qu'i existe 
			 * au plus un enregistrement dans la table LigneFacture, dont la clé 
			 * est la concatenantion du numéro de f et de la reference du produit de lf
			 */
			{
				stNew= cnx.prepareStatement("update LigneFacture set quantite = ? where numFacture=? and refProduit=?");
				stNew.setInt(1, lf.getQuantite());
				stNew.setString(2, f.getNumero());
				stNew.setString(3, lf.getProd().getReference());
				n=stNew.executeUpdate();
				if(n>0)
					System.out.println("ligne facture mise � jour");
			}
			else {
				stNew= cnx.prepareStatement("insert into LigneFacture (refProduit , numFacture,quantite, prix) values (?,?,?,?)");
				stNew.setString(1, lf.getProd().getReference());
				stNew.setString(2, f.getNumero());
				stNew.setInt(3, lf.getQuantite());
				stNew.setFloat(4, lf.getPrix());
				n=stNew.executeUpdate();
				if(n>0)
					System.out.println("ligne facture ajout�e");
			}				
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				st.close();
				stNew.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * void delete(Facture f, Produit p) : Elle permet de supprimer une ligne de 
	 * la facture f référençant le produit p de la table LigneFacture. 
	 */
	public void delete(Facture f, Produit p) {
		if (f == null || p == null)
			return;
		Connection cnx = SConnection.getInstance();
		int n = 0;
		PreparedStatement st;
		try {
			if (cnx == null || cnx.isClosed())
				return;
			st = cnx.prepareStatement("delete from lignefacture where numfacture=? and refproduit=?");
			st.setString(1, f.getNumero());
			st.setString(2, p.getReference());
			n = st.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Collection<LigneFacture> findAllByFacture (Facture f) : elle consiste à chercher
	 * dans la table LigneFacture toutes les lignes de la facture F. 
	 * Si elle ne trouve aucune ligne, elle renvoie une collection vide.
	 */
	 public Collection<LigneFacture> findAllByFacture(Facture f) {
		Collection<LigneFacture> lignes = new ArrayList<>();
		if (f == null)
			return lignes;
		Connection cnx = SConnection.getInstance();
		PreparedStatement st=null;
		try {
			st = cnx.prepareStatement("select reference, libelle, p.prix as prixp,"
					+ "l.prix as prixl, quantite from LigneFacture l join produit p on "
					+ "p.reference= l.refproduit where numfacture=?");
			st.setString(1, f.getNumero());
			ResultSet res = st.executeQuery();
			while (res.next()) {
				lignes.add(new LigneFacture(new Produit(res.getString("reference"), res.getString("libelle"),
						res.getFloat("prixp")), res.getFloat("prixl"),
						res.getInt("quantite")));
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
		
		return lignes;// la collection retournée peux être vide
	}

}
