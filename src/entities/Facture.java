package entities;

import java.time.LocalDate;
import java.util.*;

public class Facture {
	private String numero;
	private LocalDate date;
	private Client client;
	private Collection<LigneFacture> lignes = new ArrayList<LigneFacture>();

	public Facture(String numero, LocalDate date, Client client) {
		super();
		this.numero = numero;
		this.date = date;
		this.client = client;
	}

	public Facture(String numero) {
		super();
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Collection<LigneFacture> getLignes() {
		return lignes;
	}

	public void setLignes(Collection<LigneFacture> lignes) {
		this.lignes = lignes;
	}

	@Override
	public String toString() {
		return "Facture [numero=" + numero + ", date=" + date + ", client=" + client + ", lignes=" + lignes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Facture other = (Facture) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

}
