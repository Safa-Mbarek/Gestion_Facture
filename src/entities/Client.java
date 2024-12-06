package entities;

import java.util.Objects;

public class Client {
	private String code;
	private String nom;

	public Client(String code, String nom) {
		super();
		this.code = code;
		this.nom = nom;
	}

	public Client(String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "Client [code=" + code + ", nom=" + nom + "]";
	}

}
