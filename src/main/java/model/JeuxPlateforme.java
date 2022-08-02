package model;


public class JeuxPlateforme {
	private int jeuxId;
	private int plateformeId;

	public int getJeuxId() {
		return jeuxId;
	}

	public void setJeuxId(int jeuxId) {
		this.jeuxId = jeuxId;
	}

	public int getPlateformeId() {
		return plateformeId;
	}

	public void setPlateformeId(int plateformeId) {
		this.plateformeId = plateformeId;
	}

	@Override
	public String toString() {
		return "JeuxPlateforme [jeuxId=" + jeuxId + ", plateformeId=" + plateformeId + "]";
	}

}