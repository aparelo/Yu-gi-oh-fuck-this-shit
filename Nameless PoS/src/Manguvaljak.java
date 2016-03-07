import java.util.ArrayList;
import java.util.Scanner;
public class Manguvaljak {
	 static Mangija hetkeMangija;
	 static Mangija hetkeVastane;

	    public static void uusKaik() throws InterruptedException {
	        System.out.println("It's " + hetkeMangija.getNimi() + "'s turn.");
	        for(int i=10;i>0;i--) {
	                System.out.println("Displaying cards in " + i);
	                Thread.sleep(1000);
	        }
	        System.out.println("Your cards are: ");
	        for(Kaart kaesKaart: hetkeMangija.getMangijaKasi()){
	            System.out.println(kaesKaart + "\n");
	        }
	        System.out.println("The field: ");
	        System.out.println("your side: ");
	        for(Kaart sinuValjak: hetkeMangija.getMangijaLaud()) {
	            System.out.println(sinuValjak + "\n");
	        }
	        System.out.println("Opponents side: ");
	        for(Kaart vastaseValjak: hetkeVastane.getMangijaLaud()) {
	            System.out.println(vastaseValjak + "\n");
				vastaseValjak.setMoveCount(vastaseValjak.getMoveCount()+1);
	        }
	    }
	    public static boolean kaartLauale(Kaart nimi, Mangija mangija) {
	    	if (mangija.getMangijaLaud().size() == 10) {
	    		return false;
	    	}
	    	else {
	        mangija.getMangijaKasi().remove(nimi);
	        mangija.getMangijaLaud().add(nimi);
	        return true;
	    }
	    }
	    public static void kaartSurnuAeda(Kaart nimi, Mangija mangija) {
	        mangija.getMangijaLaud().remove(nimi);
	        mangija.getMangijaSurnuAed().add(nimi);
	    }
	    public static void kaartKatte(Mangija mangija, ArrayList<Kaart> mangijaDeck) {
	        mangija.getMangijaKasi().add(mangijaDeck.get(0));
	        mangijaDeck.remove(0);
	        mangija.setMangijaDeck(mangijaDeck);
	    }

	public void buffPlacement(Mangija mangija, Kaart hero, Kaart spell) {
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
		
	}
	public void purge(Mangija mangija, Kaart hero) {
		ArrayList<Kaart> mangijaLaud = mangija.getMangijaLaud();
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
			}
		}
	}
	public void vulnerabilityPlacement(Mangija mangija, Kaart hero, Kaart spell) {
		ArrayList<Kaart> mangijaLaud = mangija.getMangijaLaud();
		for (Kaart kaart : mangijaLaud) {
			if (kaart.equals(hero)) {
				if(spell.getEffekt().equals("Attack")) {
					int attackVulnerability = hero.getAttackVulnerability() + spell.getTugevus();
					hero.setAttackVulnerability(attackVulnerability);
					int attack = hero.getAttack() - spell.getTugevus();
					hero.setAttack(attack);
					
				}
				else {
					int defenceVulnerability = hero.getDefenceVulnerability() + spell.getTugevus();
					hero.setDefenceVulnerability(defenceVulnerability);
					int defence = hero.getDefence() - spell.getTugevus();
					hero.setDefence(defence);
				}
				
			}
		}
	}
	public static boolean attack(Mangija hetkeMangija, Mangija hetkeVastane) {
		boolean heroArvMangijal = false;
		boolean heroArvVastasel = false;
        Scanner scan = new Scanner(System.in);
		for (Kaart kaart : hetkeMangija.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Hero")) {
				heroArvMangijal = true;
			}
		}
		for (Kaart kaart : hetkeVastane.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Hero")) {
				heroArvVastasel = true;
			}
		}
		if (hetkeMangija.getMangijaLaud().size() == 0) {
			System.out.println("You don't have any heroes to attack with!");
			return false;
		}
		else {
			System.out.println("Choose the hero to attack with:\n");
			int i = 1;
			ArrayList<Kaart> tempHerod = new ArrayList<Kaart>();
			for (Kaart kaart : hetkeMangija.getMangijaLaud()) {
				if (kaart.getTyyp().equals("Hero")) {
					tempHerod.add(kaart);
					System.out.println(i + " " + kaart.getNimi() + "\n");
					i++;
				}
		}	String valik = scan.next();
			Kaart ryndavHero = tempHerod.get(Integer.parseInt(valik)-1);
			if (heroArvVastasel == false) {
				hetkeVastane.setElud(hetkeVastane.getElud() - ryndavHero.getAttack());
				System.out.println("Attack succeeded! Opponents lives:" + hetkeVastane.getElud());
				return true;
			}
			System.out.println("Choose the opponents hero to attack:\n");
			int j = 1;
			ArrayList<Kaart> tempVastaseHerod = new ArrayList<Kaart>();
			for (Kaart kaart : hetkeVastane.getMangijaLaud()) {
				if (kaart.getTyyp().equals("Hero")) {
					tempVastaseHerod.add(kaart);
					System.out.println(j + " " + kaart.getNimi() + "\n");
					j++;
				}
	}		String valik2 = scan.next();
			Kaart vastaseHero = tempVastaseHerod.get(Integer.parseInt(valik2)-1);
			if (vastaseHero.getDefence() < ryndavHero.getAttack()) {
				System.out.println("Attack succeeded! The hero " + vastaseHero.getNimi() + " has been defeated." + " Opponents lives: " + (hetkeVastane.getElud() - (ryndavHero.getAttack() - vastaseHero.getDefence())));
				Manguvaljak.kaartSurnuAeda(vastaseHero, hetkeVastane);
				return true;
			}
			else {
				System.out.println("Attack did not succeed! Your hero" + ryndavHero.getNimi() + " has been defeated");
				Manguvaljak.kaartSurnuAeda(ryndavHero, hetkeMangija);
				return true;
			}
		}

	}
}


