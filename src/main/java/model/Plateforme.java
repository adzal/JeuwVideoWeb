package model;

public class Plateforme {
	private int plateformeId;
	private String nom;
	private String description;
	private boolean checked;

	public int getPlateformeId() {
		return plateformeId;
	}

	public void setPlateformeId(int plateformeId) {
		this.plateformeId = plateformeId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Plateforme [plateformeId=" + plateformeId + ", nom=" + nom + ", description=" + description + "]";
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}