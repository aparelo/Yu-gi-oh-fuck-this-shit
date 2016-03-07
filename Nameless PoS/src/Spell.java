
public class Spell {

	private String nimi;
	private String tyyp;
	private String effekt;
	private int manaPoints;
	private String alamTyyp;
	
	public String getNimi() {
		return nimi;
	}
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	public String getTyyp() {
		return tyyp;
	}
	public void setTyyp(String tyyp) {
		this.tyyp = tyyp;
	}
	public String getEffekt() {
		return effekt;
	}
	public void setEffekt(String effekt) {
		this.effekt = effekt;
	}
	public int getManaPoints() {
		return manaPoints;
	}
	public void setManaPoints(int manaPoints) {
		this.manaPoints = manaPoints;
	}
	public String getAlamTyyp() {
		return alamTyyp;
	}
	public void setAlamTyyp(String alamTyyp) {
		this.alamTyyp = alamTyyp;
	}
	public String toString() {
		return "Spell:" + nimi + "tyyp:" + tyyp + "Effect:" + effekt + "Mana to play:" + manaPoints + "Sub type:" + alamTyyp;
	}
}
