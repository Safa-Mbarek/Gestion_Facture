package entities;

public class LigneFacture {
	private Produit prod;
	private float prix;
	private int quantite;

	public LigneFacture(Produit prod, float prix, int quantite) {
		super();
		this.prod = prod;
		this.prix = prix;
		this.quantite = quantite;
	}

	public Produit getProd() {
		return prod;
	}

	public void setProd(Produit prod) {
		this.prod = prod;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public String toString() {
		return "LigneFacture [prod=" + prod + ", prix=" + prix + ", quantite=" + quantite + "]";
	}

}
