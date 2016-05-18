import javafx.scene.image.Image;

import java.io.FileNotFoundException;
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
	private Image frontPicture;
	

	

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




	public int getAttackBuff() {
		return attackBuff;
	}




	public void setAttackBuff(int attackBuff) {
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




	public void setAttackVulnerability(int attackVulnerability) {
		this.attackVulnerability = attackVulnerability;
	}




	public int getDefenceVulnerability() {
		return defenceVulnerability;
	}




	public void setDefenceVulnerability(int defenceVulnerability) {
		this.defenceVulnerability = defenceVulnerability;
	}




	public int getManaPoints() {
		return manaPoints;
	}




	public void setManaPoints(int manaPoints) {
		this.manaPoints = manaPoints;
	}




	public int getMoveCount() {
		return moveCount;
	}




	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}




	public ArrayList<Kaart> getBuffers() {
		return buffers;
	}




	public void setBuffers(ArrayList<Kaart> buffers) {
		this.buffers = buffers;
	}




	public ArrayList<Kaart> getVulnerabilities() {
		return vulnerabilities;
	}




	public void setVulnerabilities(ArrayList<Kaart> vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}

	public Image getFrontPicture() {
		return frontPicture;
	}




	public Hero(String nimi, int attack, int defence, int rank, int manaPoints, String eriatribuut, String alamTyyp)  {
		this.nimi = nimi;
		this.tyyp = "Hero";
		this.attack = attack;
		this.defence = defence;
		this.rank = rank;
		this.manaPoints = manaPoints;
		this.eriatribuut = eriatribuut;
		this.alamTyyp = alamTyyp;
		this.attackBuff = 0;
		this.defenceBuff = 0;
		this.attackVulnerability = 0;
		this.defenceVulnerability = 0;
		this.moveCount = 0;
		this.buffers = new ArrayList<>();
		this.vulnerabilities = new ArrayList<>();
		try {
			this.frontPicture = new Image("\\img\\" + nimi.replace(" ","") + ".jpg");
		}
		catch (IllegalArgumentException e) {
			this.frontPicture =  new Image("\\img\\HeroImg.jpg");
		}

	}




	public String toString() {
		return "Name: " + nimi + ", Attack: " + attack + ", Defence: " + defence + ", Mana points to play: " + manaPoints;
	}
	public String toCSV() {
		return nimi+","+"Hero"+","+ attack+","+defence+","+rank+","+manaPoints+","+eriatribuut+","+alamTyyp;
	}
	public String toInfo() {
		return "Name: " + nimi + " Attack: " + attack + " Defence: " + defence + " Mana points to play: " + manaPoints;
	}

}
