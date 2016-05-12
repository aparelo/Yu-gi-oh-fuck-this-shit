import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Kaart {

	private  String nimi; //Hero, Spell
	private  String tyyp; //Hero, Spell
	private  int attack; //Hero
	private  int defence; //Hero
	private  int rank; // Hero
	private  String eriatribuut; //Hero
	private  String alamTyyp; //Hero, Spell
	private  int attackBuff; //Hero
	private  int defenceBuff;//Hero
	private  int attackVulnerability; //Hero
	private  int defenceVulnerability; // Hero
	private  int manaPoints; //Spell
	private  String effekt; //Spell
	private  int tugevus; //Spell
	private  int length; // Spell
	private  int moveCount; //Kaikude counter
	private  ArrayList<Kaart> buffers;
	private  ArrayList<Kaart> vulnerabilities;
	private  boolean olek; // True - faceup, False, facedown
	private  boolean activity;
	private Image frontPicture;
	private Image backPicture;
    private boolean empty;


	public String getNimi() {
		return nimi;
	}
	public  void setNimi(String nimi) {
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
	public  void setAttack(int attack) {
		if (attack < 0) {
			this.attack = 0;
		} else {
			this.attack = attack;
		}
	}
	public int getDefence() {
		return defence;
	}
	public  void setDefence(int defence) {
		if (defence < 0) {
			this.defence = 0;
		} else {
			this.defence = defence;
		}
	}
	public int getRank() {
		return rank;
	}
	public  void setRank(int rank) {
		this.rank = rank;
	}
	public String getEriatribuut() {
		return eriatribuut;
	}
	public  void setEriatribuut(String eriatribuut) {
		this.eriatribuut = eriatribuut;
	}
	public String getAlamTyyp() {
		return alamTyyp;
	}
	public  void setAlamTyyp(String alamTyyp) {
		this.alamTyyp = alamTyyp;
	}
	public int getAttackBuff() {
		return attackBuff;
	}

	public  void setAttackBuff(int attackBuff) {
		this.attackBuff = attackBuff;
	}

	public int getDefenceBuff() {
		return defenceBuff;
	}

	public void setDefenceBuff(int defenceBuff) {
		this.defenceBuff = defenceBuff;
	}

	public int getAttackVulnerability() {
		return attackVulnerability;
	}

	public  void setAttackVulnerability(int attackVulnerability) {
		if(this.getAttack() - attackVulnerability < 0) {
			int tempNeg = this.getAttack()-attackVulnerability;
			this.attackVulnerability = attackVulnerability+tempNeg;
		}
		else {
			this.attackVulnerability = attackVulnerability;
		}

	}

	public int getDefenceVulnerability() {
		return defenceVulnerability;
	}

	public  void setDefenceVulnerability(int defenceVulnerability) {
		if(this.getDefence() - defenceVulnerability < 0) {
			int tempNeg = this.getDefence()-defenceVulnerability;
			this.defenceVulnerability = defenceVulnerability+tempNeg;
		}
		else {
			this.defenceVulnerability = defenceVulnerability;
		}
	}
	public  int getManaPoints() {
		return manaPoints;
	}
	
	public void setManaPoints(int manaPoints) {
		this.manaPoints = manaPoints;
	}
	public String getEffekt() {
		return effekt;
	}
	public  void setEffekt(String effekt) {
		this.effekt = effekt;
	}
	public int getTugevus() {
		return tugevus;
	}
	public int getMoveCount() {
		return moveCount;
	}
	public  void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
	public  void setTugevus(int tugevus) {
		this.tugevus = tugevus;
	}
	public int getLength() {
		return length;
	}
	public  void setLength(int length) {
		this.length = length;
	}
	public ArrayList<Kaart> getBuffers() {
		return buffers;
	}
	public  void setBuffers(ArrayList<Kaart> buffers) {
		this.buffers = buffers;
	}
	public ArrayList<Kaart> getVulnerabilities() {
		return vulnerabilities;
	}
	public  void setVulnerabilities(ArrayList<Kaart> vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}
	public boolean isOlek() {
		return olek;
	}
	public  void setOlek(boolean olek) {
		this.olek = olek;
	}
	public boolean isActivity() {
		return activity;
	}
	public  void setActivity(boolean activity) {
		this.activity = activity;
	}
	public abstract Image getFrontPicture();
    public Image getBackPicture() {
        return new Image("\\img\\CardBackground.jpg");
    }
    public boolean isEmpty() {
        return true;
    }

	public abstract String toString();
	public abstract String toCSV();
    public abstract String toInfo();
	}

