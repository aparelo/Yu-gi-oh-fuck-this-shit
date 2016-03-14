import java.util.ArrayList;

public class Buff extends Kaart{
	public String nimi;
	public String tyyp; //Hero, Spell
	public String alamTyyp; //Hero, Spell
	public int manaPoints; //Spell
	public String effekt;
	public int tugevus;
	public int length;
	public int moveCount;
	public boolean activity;
	public boolean olek;


	public Buff(String nimi, String alamTyyp, int manaPoints, String effekt, int tugevus, int length) {
		this.nimi = nimi;
		this.alamTyyp = alamTyyp;
		this.manaPoints = manaPoints;
		this.effekt = effekt;
		this.tugevus = tugevus;
		this.length = length;
		this.moveCount = 0;
		this.activity = false;
		this.olek = false;
}
	public String toString() {
		return "(Name: " + nimi + " Sub type: " + alamTyyp + " Mana points to play: " + manaPoints + " Effect: " + effekt + " Strength: " + tugevus + " Duration " + length;
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
	}
}