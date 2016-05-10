import javafx.geometry.Bounds;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class Mangija {
	
	private String nimi;
	private int elud;
	private int mana;
	private int spellsOnField;
	private int heroesOnField;
	private ArrayList<Kaart> mangijaLaud;
	private ArrayList<Kaart> mangijaKasi;
	private ArrayList<Kaart> mangijaDeck;
	private ArrayList<Kaart> mangijaSurnuAed;
	private HashMap<String,Kaart> handMap;
	private HashMap<String,Kaart> spellMap;
	private HashMap<String,Kaart> heroMap;
    private double deckX;
    private double deckY;


	public Mangija(String nimi, ArrayList <Kaart> deck, int location) {
		this.nimi = nimi;
		this.elud = 20;
		this.mana = 20;
		this.spellsOnField = 0;
		this.heroesOnField = 0;
		this.mangijaDeck = deck;
		this.mangijaSurnuAed = new ArrayList<Kaart>();
		if (location == 1) {
			HashMap<String,Kaart> handMap = new HashMap<String,Kaart>();
			HashMap<String,Kaart> spellMap = new HashMap<String,Kaart>();
			HashMap<String,Kaart> heroMap = new HashMap<String,Kaart>();
			handMap.put("#0",null);
			handMap.put("#1",null);
			handMap.put("#2",null);
			handMap.put("#3",null);
			handMap.put("#4",null);
			spellMap.put("#5",null);
			spellMap.put("#6",null);
			spellMap.put("#7",null);
			spellMap.put("#8",null);
			spellMap.put("#9",null);
			heroMap.put("#10",null);
			heroMap.put("#11",null);
			heroMap.put("#12",null);
			heroMap.put("#13",null);
			heroMap.put("#14",null);
            this.handMap = handMap;
            this.spellMap = spellMap;
            this.heroMap = heroMap;

            Node deck = Gamescenes.getBattleScenePane().lookup("#33");
            Bounds cardBounds = deck.localToScene(deck.getBoundsInLocal());
            this.deckX = cardBounds.getMinX() - 110;
            this.deckY = cardBounds.getMinY() - 24;

		}
		else {
			HashMap<String,Kaart> handMap = new HashMap<String,Kaart>();
			HashMap<String,Kaart> spellMap = new HashMap<String,Kaart>();
			HashMap<String,Kaart> heroMap = new HashMap<String,Kaart>();
			handMap.put("#15",null);
			handMap.put("#16",null);
			handMap.put("#17",null);
			handMap.put("#18",null);
			handMap.put("#19",null);
			spellMap.put("#20",null);
			spellMap.put("#21",null);
			spellMap.put("#22",null);
			spellMap.put("#23",null);
			spellMap.put("#24",null);
			heroMap.put("#25",null);
			heroMap.put("#26",null);
			heroMap.put("#27",null);
			heroMap.put("#28",null);
			heroMap.put("#29",null);
            this.handMap = handMap;
            this.spellMap = spellMap;
            this.heroMap = heroMap;

            Node deck = Gamescenes.getBattleScenePane().lookup("#30");
            Bounds cardBounds = deck.localToScene(deck.getBoundsInLocal());
            this.deckX = cardBounds.getMinX() - 110;
            this.deckY = cardBounds.getMinY() - 24;
		}
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

	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
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
		return "M�ngija nimi: " + nimi + " Elud: " + elud + " Mana: " + mana;
	}
    public double getDeckX() {
        return deckX;
    }
    public double getDeckY() {
        return deckY;
    }

	
}
