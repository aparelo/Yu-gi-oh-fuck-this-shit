public class Hero {

	String nimi;
	String tyyp;
	int attack;
	int defence;
	int rank;
	String eriatribuut;
	String alamTyyp;
	
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
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefence() {
		return defence;
	}
	public void setDefence(int defence) {
		this.defence = defence;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getEriatribuut() {
		return eriatribuut;
	}
	public void setEriatribuut(String eriatribuut) {
		this.eriatribuut = eriatribuut;
	}
	public String getAlamTyyp() {
		return alamTyyp;
	}
	public void setAlamTyyp(String alamTyyp) {
		this.alamTyyp = alamTyyp;
	}
	public String toString() {
		return "Hero:" + nimi + "Attack:" + attack + "Defence:" + defence + "Rank:" + rank + "Special attribute:" + eriatribuut + "Sub type:" + alamTyyp;	
	}
	
}


