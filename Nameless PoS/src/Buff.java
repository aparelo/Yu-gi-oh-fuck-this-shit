import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class Buff extends Kaart {
	private String nimi; //Hero, Spell
	private String tyyp; //Hero, Spell
	private String alamTyyp; //Hero, Spell
	private int manaPoints; //Spell
	private String effekt; //Spell
	private int tugevus; //Spell
	private int length; // Spell
	private int moveCount; //Kaikude counter
	private boolean olek; // True - faceup, False, facedown
	private boolean activity;
    private Image frontPicture;
    private Image backPicture;

	public Buff(String nimi, String alamTyyp, int manaPoints, String effekt, int tugevus, int length) {
		this.nimi = nimi;
		this.tyyp = "Spell";
		this.alamTyyp = alamTyyp;
		this.manaPoints = manaPoints;
		this.effekt = effekt;
		this.tugevus = tugevus;
		this.length = length;
		this.moveCount = 0;
		this.olek = false;
		this.activity = false;
        try {
            this.frontPicture = new Image("\\img\\" + nimi.replace(" ","") + ".jpg");
        }
        catch (IllegalArgumentException e) {
            this.frontPicture =  new Image("\\img\\BuffImg.jpg");
            this.backPicture = new Image ("\\img\\CardBackground.jpg");
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


	public int getTugevus() {
		return tugevus;
	}


	public void setTugevus(int tugevus) {
		this.tugevus = tugevus;
	}


	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public int getMoveCount() {
		return moveCount;
	}


	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
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
		return "Name: " + nimi + ", Sub type: " + alamTyyp + " Mana points to play: " + manaPoints + " Spell effect: " + effekt + " Spell strength: " + tugevus + " Spell duration: " + length;
	}
	public String toCSV() {
		return nimi+","+"Spell"+","+ effekt+","+manaPoints+","+alamTyyp+","+tugevus+","+length;
	}

    public String toInfo() {
        return "Name: " + nimi + " Sub Type: " + alamTyyp + " Mana to play: " + manaPoints + " Effect: " + effekt + " Strength: " + tugevus + " Duration: " + length;
    }

	public static void buffPlacement(Mangija mangija, Kaart hero, Kaart spell) {
		ArrayList<Kaart> mangijaLaud = mangija.getMangijaLaud();
		for (Kaart kaart : mangijaLaud) {
			if (kaart.equals(hero)) {
				if (spell.getEffekt().equals("Attack")) {
					int attackBuff = hero.getAttackBuff() + spell.getTugevus();
					hero.setAttackBuff(attackBuff);
					int attack = hero.getAttack() + spell.getTugevus();
					hero.setAttack(attack);
				}
				else {
					int defenceBuff = hero.getDefenceBuff() + spell.getTugevus();
					hero.setDefenceBuff(defenceBuff);
					int defence = hero.getDefence() + spell.getTugevus();
					hero.setDefence(defence);
				}
			}
		}
		hero.getBuffers().add(spell);
		spell.setOlek(true);
		spell.setActivity(true);

        for (Kaart heroes : mangija.getHeroMap().keySet()) {
            if (!hero.toString().equals("Empty")) {
                String indeks = Animations.getPositionIndex(heroes, mangija);
                Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);
                heroNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        Manguvaljak.attack(heroes);
                    }
                });
            }
        }
	}
}
