
public class Purge extends Kaart  {
	private String nimi;
	private String tyyp; //Hero, Spell
	private String alamTyyp; //Hero, Spell
	private int manaPoints; //Spell
	private String effekt;
	private int moveCount;
	private boolean activity;
	private boolean olek;

	public Purge(String nimi, String alamTyyp, int manaPoints, String effekt) {
		this.nimi = nimi;
		this.alamTyyp = alamTyyp;
		this.manaPoints = manaPoints;
		this.effekt = effekt;
		this.moveCount = 0;
		this.activity = false;
		this.olek = false;

}
	public String toString() {
		return "(Name: " + nimi + " Sub type: " + alamTyyp + " Mana points to play: " + manaPoints + " Effect: " + effekt;
	}
}