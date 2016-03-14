import java.util.ArrayList;

public class Hero extends Kaart {
	
	private String nimi; //Hero, Spell
	private String tyyp; //Hero, Spell
	private int attack; //Hero
	private int defence; //Hero
	private int rank; // Hero
	private String eriatribuut; //Hero
	private String alamTyyp; //Hero, Spell
	private int attackBuff; //Hero
	private int defenceBuff;//Hero
	private int attackVulnerability; //Hero
	private int defenceVulnerability; // Hero
	private int manaPoints; //Spell
	private int moveCount; //Kaikude counter
	private ArrayList<Kaart> buffers;
	private ArrayList<Kaart> vulnerabilities;
	
	public Hero(String nimi, int attack, int defence, int rank, String eriatribuut, String alamTyyp) {
		this.nimi = nimi;
		this.tyyp = "Hero";
		this.attack = attack;
		this.defence = defence;
		this.rank = rank;
		this.eriatribuut = eriatribuut;
		this.alamTyyp = alamTyyp;
		this.attackBuff = 0;
		this.defenceBuff = 0;
		this.attackVulnerability = 0;
		this.defenceVulnerability = 0;
		this.moveCount = 0;
		this.buffers = new ArrayList<Kaart>();
		this.vulnerabilities = new ArrayList<Kaart>();
	}
	public String toString() {
		return "(Name : " + nimi + ", Attack : " + attack + ", Defence : " +  defence;
	}
	

}
