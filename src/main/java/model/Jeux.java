package model;

public class Jeux {
	private int jeuxId;
	private String titre;
	private String description;
	private double prix;
	private java.sql.Date dateSortie;
	private String paysOrigine;
	private String connexion;
	private String jeuxMode;
	private int genreId;
	private String genreDescription;
	private int plateformeId;
	private String plateformeNom;
	
	public int getJeuxId() {
		return jeuxId;
	}

	public void setJeuxId(int jeuxId) {
		this.jeuxId = jeuxId;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public java.sql.Date getDateSortie() {
		return dateSortie;
	}

	public void setDateSortie(java.sql.Date dateSortie) {
		this.dateSortie = dateSortie;
	}

	public String getPaysOrigine() {
		return paysOrigine;
	}

	public void setPaysOrigine(String paysOrigine) {
		this.paysOrigine = paysOrigine;
	}

	public String getConnexion() {
		return connexion;
	}

	public void setConnexion(String connexion) {
		this.connexion = connexion;
	}

	public String getJeuxMode() {
		return jeuxMode;
	}

	public void setMode(String mode) {
		this.jeuxMode = mode;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	@Override
	public String toString() {
		return "Jeux [_JeuxId=" + jeuxId + ", _JeuxTitre=" + titre + ", _JeuxDescription=" + description
				+ ", _JeuxPrix=" + prix + ", _JeuxDateSortie=" + dateSortie + ", _JeuxPaysOrigine="
				+ paysOrigine + ", _JeuxConnexion=" + connexion + ", _JeuxMode=" + jeuxMode + ", _GenreId="
				+ genreId + "]";
	}

	public String getGenreDescription() {
		return genreDescription;
	}

	public void setGenreDescription(String genreDescription) {
		this.genreDescription = genreDescription;
	}

	public String getPlateformeNom() {
		return plateformeNom;
	}

	public void setPlateformeNom(String plateformeDescription) {
		this.plateformeNom = plateformeDescription;
	}

	public int getPlateformeId() {
		return plateformeId;
	}

	public void setPlateformeId(int plateformeId) {
		this.plateformeId = plateformeId;
	}

}