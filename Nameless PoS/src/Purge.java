import javafx.scene.image.Image;

import java.util.ArrayList;

public class Purge extends Kaart {
	private String nimi; //Hero, Spell
	private String tyyp; //Hero, Spell
	private String alamTyyp; //Hero, Spell
	private int manaPoints; //Spell
	private String effekt; //Spell
	private boolean olek; // True - faceup, False, facedown
	private boolean activity;
	private Image frontPicture;

	public Purge(String nimi, String alamTyyp, int manaPoints, String effekt) {
		this.nimi = nimi;
		this.tyyp = "Spell";
		this.alamTyyp = alamTyyp;
		this.manaPoints = manaPoints;
		this.effekt = effekt;
		this.olek = false;
		this.activity = false;
		try {
			this.frontPicture = new Image("\\img\\" + nimi + ".jpg");
		}
		catch (IllegalArgumentException e) {
			this.frontPicture =  new Image("\\img\\PurgeImg.jpg");
		}
	}

	
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


	public String getAlamTyyp() {
		return alamTyyp;
	}


	public void setAlamTyyp(String alamTyyp) {
		this.alamTyyp = alamTyyp;
	}


	public int getManaPoints() {
		return manaPoints;
	}


	public void setManaPoints(int manaPoints) {
		this.manaPoints = manaPoints;
	}


	public String getEffekt() {
		return effekt;
	}


	public void setEffekt(String effekt) {
		this.effekt = effekt;
	}


	public boolean isOlek() {
		return olek;
	}


	public void setOlek(boolean olek) {
		this.olek = olek;
	}


	public boolean isActivity() {
		return activity;
	}


	public void setActivity(boolean activity) {
		this.activity = activity;
	}

	public Image getFrontPicture() {
		return frontPicture;
	}


	public String toString() {
		return "Name: " + nimi + " Sub Type: " + alamTyyp + " Mana points to play: " + manaPoints;
	}
	public String toCSV() {
		return nimi+","+"Spell"+","+ effekt+","+manaPoints+","+alamTyyp;
	}
	public String toInfo() {
		return "Name: " + nimi + " Mana points to play: " + manaPoints + " Effect: " + effekt;
	}
	public static void purge(Mangija mangija, Kaart hero, Kaart spell) {
		ArrayList<Kaart> mangijaLaud = mangija.getMangijaLaud();
		ArrayList<Kaart> tempList = new ArrayList<>();
		for (Kaart kaart : mangijaLaud) {
			if (kaart.equals(hero)) {
				int esialgneAttack = hero.getAttack() -(hero.getAttackBuff() - hero.getAttackVulnerability());
				int esialgneDefence = hero.getDefence() - (hero.getDefenceBuff() - hero.getDefenceVulnerability());
				hero.setAttack(esialgneAttack);
				hero.setDefence(esialgneDefence);
				hero.setAttackBuff(0);
				hero.setDefenceBuff(0);
				hero.setAttackVulnerability(0);
				hero.setDefenceVulnerability(0);
				System.out.println(kaart.getBuffers());
				if(kaart.getBuffers().size() != 0) {
					for (Kaart buffer : kaart.getBuffers()) {
						for (Kaart kaart1 : mangijaLaud) {
							if (buffer.equals(kaart1)) {
								tempList.add(kaart1);
							}
						}
					}
				}
				if(kaart.getVulnerabilities().size() != 0) {
					for (Kaart vulner : kaart.getVulnerabilities()) {
						for (Kaart kaart2 : mangijaLaud) {
							if (vulner.equals(kaart2)) {
								tempList.add(kaart2);
							}
						}
					}
				}
			}
		}
		for(Kaart kaart: tempList) {
			mangijaLaud.remove(kaart);
		}
		hero.setBuffers(new ArrayList<>());
		hero.setVulnerabilities(new ArrayList<>());
		Manguvaljak.kaartSurnuAeda(spell, mangija);
	}

}
