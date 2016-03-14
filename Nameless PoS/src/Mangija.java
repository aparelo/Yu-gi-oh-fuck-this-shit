import java.util.ArrayList;
public class Mangija {
	
	private String nimi;
	private int elud;
	private static int mana;
	private int spellsOnField;
	private int heroesOnField;
	private ArrayList<Kaart> mangijaLaud;
	private ArrayList<Kaart> mangijaKasi;
	private ArrayList<Kaart> mangijaDeck;
	private ArrayList<Kaart> mangijaSurnuAed;
	
	
	public Mangija(String nimi, ArrayList <Kaart> deck) {
		this.nimi = nimi;
		this.elud = 20;
		this.mana = 20;
		this.spellsOnField = 0;
		this.heroesOnField = 0;
		this.mangijaDeck = deck;
		this.mangijaLaud = new ArrayList<Kaart>();
		this.mangijaKasi = new ArrayList<Kaart>();
		this.mangijaSurnuAed = new ArrayList<Kaart>();
	}
	
	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	public int getElud() {
		return elud;
	}
	public void setElud(int elud) {
		this.elud = elud;
	}

	public static int getMana() {
		return mana;
	}
	public static void setMana(int mana) {
		Mangija.mana = mana;
	}
	public int getSpellsOnField() {
		return spellsOnField;
	}

	public void setSpellsOnField(int spellsOnField) {
		this.spellsOnField = spellsOnField;
	}

	public int getHeroesOnField() {
		return heroesOnField;
	}

	public void setHeroesOnField(int heroesOnField) {
		this.heroesOnField = heroesOnField;
	}

	public ArrayList<Kaart> getMangijaLaud() {
		return mangijaLaud;
	}

	public void setMangijaLaud(ArrayList<Kaart> mangijaLaud) {
		this.mangijaLaud = mangijaLaud;
	}
	public ArrayList<Kaart> getMangijaKasi() {
		return mangijaKasi;
	}
	public void setMangijaKasi(ArrayList<Kaart> mangijaKasi) {
		this.mangijaKasi = mangijaKasi;
	}
	public ArrayList<Kaart> getMangijaDeck() {
		return mangijaDeck;
	}
	public void setMangijaDeck(ArrayList<Kaart> mangijaDeck) {
		this.mangijaDeck = mangijaDeck;
	}
	public ArrayList<Kaart> getMangijaSurnuAed() {
		return mangijaSurnuAed;
	}
	public void setMangijaSurnuAed(ArrayList<Kaart> mangijaSurnuAed) {
		this.mangijaSurnuAed = mangijaSurnuAed;
	}
	public String toString() {
		return "Mängija nimi: " + nimi + " Elud: " + elud + " Mana: " + mana;
	}

	
}
