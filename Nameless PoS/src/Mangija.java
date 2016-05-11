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
    private HashMap<Kaart, String> handMap;
	private HashMap<Kaart, String> spellMap;
	private HashMap<Kaart, String> heroMap;
    private double deckX;
    private double deckY;


	public Mangija(String nimi, ArrayList <Kaart> deck, int location) {
		this.nimi = nimi;
		this.elud = 20;
		this.mana = 20;
		this.spellsOnField = 0;
		this.heroesOnField = 0;
		this.mangijaDeck = deck;
        this.mangijaKasi = new ArrayList<Kaart>();
        this.mangijaLaud = new ArrayList<Kaart>();
		this.mangijaSurnuAed = new ArrayList<Kaart>();
		if (location == 1) {
			HashMap<Kaart,String> handMap = new HashMap<Kaart,String>();
            HashMap<Kaart,String> spellMap = new HashMap<Kaart,String>();
            HashMap<Kaart,String> heroMap = new HashMap<Kaart,String>();
			handMap.put(new EmptyCard(),"#0");
            handMap.put(new EmptyCard(),"#1");
            handMap.put(new EmptyCard(),"#2");
            handMap.put(new EmptyCard(),"#3");
            handMap.put(new EmptyCard(),"#4");
			spellMap.put(new EmptyCard(),"#5");
            spellMap.put(new EmptyCard(),"#6");
            spellMap.put(new EmptyCard(),"#7");
            spellMap.put(new EmptyCard(),"#8");
            spellMap.put(new EmptyCard(),"#9");
			heroMap.put(new EmptyCard(),"#10");
            heroMap.put(new EmptyCard(),"#11");
            heroMap.put(new EmptyCard(),"#12");
            heroMap.put(new EmptyCard(),"#13");
            heroMap.put(new EmptyCard(),"#14");
            this.handMap = handMap;
            this.spellMap = spellMap;
            this.heroMap = heroMap;


            this.deckX = 721.0;
			this.deckY = 348.0;

		}
		else {
            HashMap<Kaart,String> handMap = new HashMap<Kaart,String>();
            HashMap<Kaart,String> spellMap = new HashMap<Kaart,String>();
            HashMap<Kaart,String> heroMap = new HashMap<Kaart,String>();
            handMap.put(new EmptyCard(),"#15");
            handMap.put(new EmptyCard(),"#16");
            handMap.put(new EmptyCard(),"#17");
            handMap.put(new EmptyCard(),"#18");
            handMap.put(new EmptyCard(),"#19");
            spellMap.put(new EmptyCard(),"#20");
            spellMap.put(new EmptyCard(),"#21");
            spellMap.put(new EmptyCard(),"#22");
            spellMap.put(new EmptyCard(),"#23");
            spellMap.put(new EmptyCard(),"#24");
            heroMap.put(new EmptyCard(),"#25");
            heroMap.put(new EmptyCard(),"#26");
            heroMap.put(new EmptyCard(),"#27");
            heroMap.put(new EmptyCard(),"#28");
            heroMap.put(new EmptyCard(),"#29");
            System.out.println(handMap);
            this.handMap = handMap;
            this.spellMap = spellMap;
            this.heroMap = heroMap;

            this.deckX = 0;
            this.deckY = 214.0;
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

    public HashMap<Kaart, String> getHeroMap() {
        return heroMap;
    }

    public void setHeroMap(HashMap<Kaart, String> heroMap) {
        this.heroMap = heroMap;
    }

    public HashMap<Kaart, String> getSpellMap() {
        return spellMap;
    }

    public void setSpellMap(HashMap<Kaart, String> spellMap) {
        this.spellMap = spellMap;
    }

    public HashMap<Kaart, String> getHandMap() {
        return handMap;
    }

    public void setHandMap(HashMap<Kaart, String> handMap) {
        this.handMap = handMap;
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
