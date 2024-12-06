package accesBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import entities.Client;
import entities.Facture;
import entities.LigneFacture;

public class FactureDAO {
	/*
	 * Facture findById(String num) : Elle cherche dans la table Facture, la facture
	 * ayantla clé num. Si elle ne la trouve pas, elle renvoie null
	 */
	public Facture findById(String num) {
		Facture facture = null;
		if (num == null)
			return facture;
		Connection cnx = SConnection.getInstance();
		ClientDAO cl = new ClientDAO();
		LigneFactureDAO lfdao = new LigneFactureDAO();
		PreparedStatement st = null;
		try {
			if (cnx == null || cnx.isClosed())
				return facture;
			st = cnx.prepareStatement("select * from facture where numero= ?");
			st.setString(1, num);
			ResultSet res = st.executeQuery();
			if (res.next())

			{

				facture = new Facture(res.getString(1), res.getObject(2, LocalDate.class),
						cl.findById(res.getString(3)));
				facture.setLignes(lfdao.findAllByFacture(facture));
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

		return facture;
	}

	/*
	 * void save(Facture f) : Elle ajoute la facture f et ses lignes à la bd, si
	 * elle n’existe pas. Elle la met à jour dans le cas contraire.
	 */

	public void save(Facture f) {
		if (f == null)
			return;

		// Vérification de l'existance du client dans la base
		ClientDAO cDao = new ClientDAO();
		Client c = cDao.findById(f.getClient().getCode());
		if (c == null) {
			System.out.println("Le client n'existe pas dans la base");
			return;
		}

		// Ajout de la facture
		String rq = "insert into facture (numero, codeClient, dateFacture) VALUES (?,?,?)";
		Connection cnx = SConnection.getInstance();
		PreparedStatement st = null;
		int n = 0;
		try {
			if (cnx == null || cnx.isClosed())
				return;
			st = cnx.prepareStatement(rq);
			st.setString(1, f.getNumero());
			st.setString(2, f.getClient().getCode());
			st.setObject(3, f.getDate());
			n = st.executeUpdate();
			// ajout des lignes de la facture
			LigneFactureDAO lfdao = new LigneFactureDAO();
			for (LigneFacture lf : f.getLignes()) {
				lfdao.save(f, lf);
			}
			
		} 
		catch (SQLException e) {
			System.out.println("La facture existe déjà");
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

	/*
	 * void delete (String n) : elle met le champ etat de la facture ayant le
	 * numéro n à la valeur FALSE (ce qui veut dire que la facture est archivée),
	 * si cette facture date de moins de dix ans. Sinon, elle la supprime de la bd.
	 * Les mêmes traitements s’appliquent aux lignes de la facture.
	 */
	public void delete(String num) {
		if (num == null)
			return;
		Connection cnx = SConnection.getInstance();
		PreparedStatement st=null,st1=null;
		LocalDate d = null;
		try {
			if (cnx == null || cnx.isClosed())
				return;

			Facture f = this.findById(num);
			if (f != null) {
				d = f.getDate();
				// Si la facture date de 10ans ou plus, on la supprime
				if (Period.between(d, LocalDate.now()).getYears() >= 10) {
					st = cnx.prepareStatement("delete from facture where numero=?");
					st.setString(1, num);
					st.executeUpdate();
				}
				// Si la facture date de moins de 10 ans, on l'archive
				else {
					st = cnx.prepareStatement("update facture set etat=false where numero=?");
					st.setString(1, num);
					int n = st.executeUpdate();
					// On archive également les lignes de la facture
					if (n > 0) {
						st1 = cnx.prepareStatement("update lignefacture set etat=false where numfacture=?");
						st1.setString(1, num);
						st1.executeUpdate();
						
					}
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				st.close();
				if(st1!=null)
					st1.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
