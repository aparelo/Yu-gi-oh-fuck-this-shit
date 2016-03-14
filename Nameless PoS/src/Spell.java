public class Spell extends Kaart {

	public Spell(String nimi, String alamTyyp, int manaPoints, String effekt, int tugevus, int length){
		if(alamTyyp.equals("Buff")) {
			Kaart tempSpell = new Buff(nimi,alamTyyp,manaPoints,effekt,tugevus,length);
		}
		if(alamTyyp.equals("Purge")) {
			Kaart tempSpell = new Purge(nimi,alamTyyp,manaPoints,effekt);
		}
		else {
			Kaart tempSpell = new Vulnerability(nimi,alamTyyp,manaPoints,effekt,tugevus,length);
		}
	}
}
