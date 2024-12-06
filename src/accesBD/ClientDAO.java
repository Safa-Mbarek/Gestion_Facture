package accesBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Client;

public class ClientDAO {
	/*
	 * Client findById(String code) : Elle cherche dans la table Client et retourne le Client ayant 
	 * le code donné en paramètre. Si elle ne le trouve pas, elle renvoie null.
	 */
		public Client findById(String code) {
			Client client = null;
			if (code == null)
				return client;
			Connection cnx = SConnection.getInstance();
			PreparedStatement st=null;
			try {
				if (cnx==null || cnx.isClosed())
					return client;
				st = cnx.prepareStatement("select * from client where code= ?");
				st.setString(1, code);
				ResultSet res = st.executeQuery();
				if (res.next())
				/*On utilise if et non pas while car on sait qu'i existe
				 * au plus un enregistrement dans la table Client, dont la clé 
				 * est 'code'
				 */		
					
				{
					client = new Client(res.getString("code"), res.getString(2));
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
			return client;
		}
	
	/*
	 * void save (Client c) : Elle ajoute le client c à la table Client s’il n’existe pas. 
	 * Dans le cas contraire, elle met à jour ses caractéristiques.	
	 */
	public void save(Client c) {
		if (c == null)
			return;
		Connection cnx = SConnection.getInstance();
		int n = 0;
		PreparedStatement st=null;
		try {
			if (cnx==null || cnx.isClosed())
				return;
			st = cnx.prepareStatement("update client set nom=? where code=? ");
			st.setString(2, c.getCode());
			st.setString(1, c.getNom());
			n = st.executeUpdate();
			if(n!=0)
				System.out.println("Client "+c.getCode()+" modifi�.");
			else {
				st = cnx.prepareStatement("insert into client (code,nom) values (?, ?)");
				st.setString(1, c.getCode());
				st.setString(2, c.getNom());
				n = st.executeUpdate();
				if(n!=0)
					System.out.println("Client "+c.getCode()+" modifié.");
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
	}

	/*
	 * void delete(String c) : Elle met le champ etat du client ayant le code c 
	 * à la valeur FALSE (ce qui veut dire qu’il est archivé).
	 */
	public void delete(String c) {
		if (c == null)
			return;
		Connection cnx = SConnection.getInstance();
		PreparedStatement st=null;
		int n = 0;
		try {
			if (cnx==null || cnx.isClosed())
				return;
			//La suppression consiste à mettre le champ état à false
			st = cnx.prepareStatement("update client set etat=false where code= ?");
			st.setString(1, c);
			n = st.executeUpdate();
			if(n>0)
				System.out.println("Client archiv�");
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

	

}
