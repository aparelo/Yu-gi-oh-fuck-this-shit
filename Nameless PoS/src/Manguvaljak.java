import java.util.ArrayList;
import java.util.Scanner;
public class Manguvaljak{
	  static Mangija currentPlayer;
	  static Mangija currentOpponent;



	    public static boolean kaartLauale(Kaart nimi, Mangija mangija) {
	    	if (Mangija.getMana() < Kaart.getManaPoints()) {
	    		System.out.println("You don't have enough mana to play this card!");
	    		return false;
	    	}
	    	else {
	    		Mangija.setMana(Mangija.getMana() - Kaart.getManaPoints());
	    	}
	    	if (mangija.getMangijaLaud().size() == 10) {
	    		return false;
	    	}
	    	else {
	        mangija.getMangijaKasi().remove(nimi);
	        mangija.getMangijaLaud().add(nimi); // removed getLaud.setLaud
	        if (nimi.getTyyp().equals("Hero")) {
	        	int tempHeroesOnField = mangija.getHeroesOnField() + 1;
	        	mangija.setHeroesOnField(tempHeroesOnField);
	        }
	        else {
	        	int tempSpellsOnField = mangija.getSpellsOnField() + 1;
	        	mangija.setSpellsOnField(tempSpellsOnField);
	        }
	        return true;
	    }
	    }
	    public static void kaartSurnuAeda(Kaart nimi, Mangija mangija) {
	        mangija.getMangijaLaud().remove(nimi);
	        mangija.getMangijaSurnuAed().add(nimi);
	        if (nimi.getTyyp().equals("Hero")) {
	        	mangija.setHeroesOnField(mangija.getHeroesOnField() - 1);
	        }
	        else {
	        	mangija.setSpellsOnField(mangija.getSpellsOnField() - 1);
	        }
	    }
	    public static void kaartKatte(Mangija mangija) {
	        mangija.getMangijaKasi().add(mangija.getMangijaDeck().get(0));
	        mangija.getMangijaDeck().remove(0);
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
	public static void purge(Mangija mangija, Kaart hero, Kaart spell) {
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
				for(Kaart buffer: kaart.getBuffers()) {
					for(Kaart kaart1: mangijaLaud) {
						if(buffer.equals(kaart1)) {
							mangijaLaud.remove(kaart1);
						}
					}
				}
				for(Kaart vulner: kaart.getVulnerabilities()) {
					for(Kaart kaart2: mangijaLaud) {
						if(vulner.equals(kaart2)) {
							mangijaLaud.remove(kaart2);
						}
					}
				}
			}
		}
		hero.setBuffers(new ArrayList<>());
		hero.setVulnerabilities(new ArrayList<>());
		kaartSurnuAeda(spell, mangija);
	}
	public static void vulnerabilityPlacement(Mangija currentPlayer, Mangija currentOpponent, Kaart hero, Kaart spell) {
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
				
			
		hero.getVulnerabilities().add(spell);
		spell.setOlek(true);
		spell.setActivity(true);
	}
	public static boolean attack(Mangija currentPlayer, Mangija currentOpponent) {
		boolean heroArvMangijal = false;
		boolean heroArvVastasel = false;
        Scanner scan = new Scanner(System.in);
		for (Kaart kaart : currentPlayer.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Hero")) {
				heroArvMangijal = true;
			}
		}
		for (Kaart kaart : currentOpponent.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Hero")) {
				heroArvVastasel = true;
			}
		}
		if (!heroArvMangijal) {
			System.out.println("You don't have any heroes to attack with!");
			return false;
		}
		else {
			System.out.println("Choose the hero to attack with:\n");
			int i = 1;
			ArrayList<Kaart> tempHerod = new ArrayList<>();
			for (Kaart kaart : currentPlayer.getMangijaLaud()) {
				if (kaart.getTyyp().equals("Hero")) {
					tempHerod.add(kaart);
					System.out.println(i + " " + kaart.getNimi() + "\n");
					i++;
				}
		}	String valik = scan.next();
			Kaart ryndavHero = tempHerod.get(Integer.parseInt(valik)-1);
			if (!heroArvVastasel) {
				currentOpponent.setElud(currentOpponent.getElud() - ryndavHero.getAttack());
				System.out.println("Attack succeeded! Opponents lives:" + currentOpponent.getElud());
				return true;
			}
			System.out.println("Choose the opponents hero to attack:\n");
			int j = 1;
			ArrayList<Kaart> tempVastaseHerod = new ArrayList<>();
			for (Kaart kaart : currentOpponent.getMangijaLaud()) {
				if (kaart.getTyyp().equals("Hero")) {
					tempVastaseHerod.add(kaart);
					System.out.println(j + " " + kaart.getNimi() + "\n");
					j++;
				}
	}		String valik2 = scan.next();
			Kaart vastaseHero = tempVastaseHerod.get(Integer.parseInt(valik2)-1);
			if (vastaseHero.getDefence() < ryndavHero.getAttack()) {
				System.out.println("Attack succeeded! The hero " + vastaseHero.getNimi() + " has been defeated." + " Opponents lives: " + (currentOpponent.getElud() - (ryndavHero.getAttack() - vastaseHero.getDefence())));
				Manguvaljak.kaartSurnuAeda(vastaseHero, currentOpponent);
				return true;
			}
			else {
				System.out.println("Attack did not succeed! Your hero" + ryndavHero.getNimi() + " has been defeated");
				Manguvaljak.kaartSurnuAeda(ryndavHero, currentPlayer);
				return true;
			}
		}
	}

	public static boolean useSpell(Mangija currentPlayer, Mangija currentOpponent) { 
		boolean playerHasSpells = false;
		boolean playerHasHeroes = false;
		boolean opponentHasHeroes = false;
		for (Kaart kaart : currentPlayer.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Hero")) { //Kontrollin kas mangijal on Herosid kellele Spelle rakendada
				playerHasHeroes = true;
			}
			//Kontrollin kas mangijal on spelle, mida kasutada.
			else { //Meil on ainult kahte tuupi kaarte
				playerHasSpells = true;
			}
		}
		if (!playerHasSpells) {
			System.out.println("You don't have any spells on the battlefield to use!");
			return false;
		}

		//Kontrollin, kas vastasel on herosid kelle peal spelle kasutada.
		for (Kaart kaart : currentOpponent.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Hero")) {
				opponentHasHeroes = true;
				break;
			}
		}
		if (currentPlayer.getMangijaLaud().size() == 10) {
			System.out.println("You cannot add any more cards to the battlefield!");
			return false;
		}
        ArrayList <String> tempSpellType = new ArrayList<>();
		for (Kaart kaart : currentPlayer.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Spell") && !kaart.isOlek()) {
				if (kaart.getAlamTyyp().equals("Purge")) {
					if (!tempSpellType.contains("Purge")) {
						tempSpellType.add("Purge");
					}
				}
				else if (kaart.getAlamTyyp().equals("Buff")) {
					if (!tempSpellType.contains("Buff")) {
						tempSpellType.add("Buff");
					}
				}
				else if (kaart.getAlamTyyp().equals("Vulnerability")) {
					if (!tempSpellType.contains("Vulnerability")) {
						tempSpellType.add("Vulnerability");
					}
				}
			}
		}
			Scanner scan = new Scanner(System.in);
			System.out.println("What type of spell do you want to use:\n");
			int i = 1;
			for (String type :  tempSpellType) {
				System.out.println(i + ")" + type + "\n");
				i++;
			}
			String valik = scan.next();

		String type = tempSpellType.get(Integer.parseInt(valik) - 1);
		if (type.equals("Purge")) {
			if (!playerHasHeroes && !opponentHasHeroes) {
				System.out.println("There are no heroes to purge!");
				return false;
			}
			System.out.println("Choose the card, you want to use:\n"); // Kaardi valik, millega purgeitakse
			int l = 1;
			ArrayList<Kaart> tempSpells = new ArrayList<>();
			for (Kaart kaart : currentPlayer.getMangijaLaud()) {
				if (kaart.getAlamTyyp().equals("Purge")) {
					tempSpells.add(kaart);
					System.out.println(l + ")" + kaart);
					l++;
				}
			}
			String spellChoice = scan.next(); // Vali kaart millega Purge.
			System.out.println("Do you want to purge your own hero or the enemies hero:\n 1) My own hero\n 2) The enemies hero");
			String valik2 = scan.next();
			if (valik2.equals("1") && playerHasHeroes) {
				System.out.println("Choose the hero, you want to purge:\n");
				int j = 1;
				ArrayList<Kaart> tempHerod = new ArrayList<>();
				for (Kaart kaart : currentPlayer.getMangijaLaud()) {
					if (kaart.getTyyp().equals("Hero")) {
						tempHerod.add(kaart);
						System.out.println(j + ")" + kaart);
						j++;
					}
				}
				String heroChoice = scan.next();
				Kaart purgeHero = tempHerod.get(Integer.parseInt(heroChoice) - 1);
				Kaart purgeSpell = tempSpells.get(Integer.parseInt(spellChoice) - 1);
				purge(currentOpponent, purgeHero, purgeSpell);
				kaartSurnuAeda(purgeSpell,currentPlayer);
				System.out.println("Card purged!");
				return true;
			}
			else if (valik2.equals("1") && !playerHasHeroes) {
				System.out.println("You don't have any heroes to purge.");
				return false;
			}
			else if (valik2.equals("2") && opponentHasHeroes) {
				System.out.println("Choose the hero, you want to purge:\n");
				int j = 1;
				ArrayList<Kaart> tempHerod = new ArrayList<>();
				for (Kaart kaart : currentOpponent.getMangijaLaud()) {
					if (kaart.getTyyp().equals("Hero")) {
						tempHerod.add(kaart);
						System.out.println(j + ")" + kaart);
						j++;
					}

		String type = tempSpellType.get(Integer.parseInt(valik) - 1);
		if (type.equals("Purge")) {
			if (!playerHasHeroes && !opponentHasHeroes) {
				System.out.println("There are no heroes to purge!");
				return false;
			}
			System.out.println("Choose the card, you want to use:\n"); // Kaardi valik, millega purgeitakse
			int l = 1;
			ArrayList<Kaart> tempSpells = new ArrayList<>();
			for (Kaart kaart : currentPlayer.getMangijaLaud()) {
				if (kaart.getAlamTyyp().equals("Purge")) {
					tempSpells.add(kaart);
					System.out.println(l + ")" + kaart);
					l++;
				}
			}
			String spellChoice = scan.next(); // Vali kaart millega Purge.
			System.out.println("Do you want to purge your own hero or the enemies hero:\n 1) My own hero\n 2) The enemies hero");
			String valik2 = scan.next();
			if (valik2.equals("1") && playerHasHeroes) {
				System.out.println("Choose the hero, you want to purge:\n");
				int j = 1;
				ArrayList<Kaart> tempHerod = new ArrayList<>();
				for (Kaart kaart : currentPlayer.getMangijaLaud()) {
					if (kaart.getTyyp().equals("Hero")) {
						tempHerod.add(kaart);
						System.out.println(j + ")" + kaart);
						j++;
					}
				}
				String heroChoice = scan.next();
				Kaart purgeHero = tempHerod.get(Integer.parseInt(heroChoice) - 1);
				Kaart purgeSpell = tempSpells.get(Integer.parseInt(spellChoice) - 1);
				Purge.purge(currentOpponent, purgeHero, purgeSpell);
				kaartSurnuAeda(purgeSpell,currentPlayer);
				System.out.println("Card purged!");
				return true;
			}
			else if (valik2.equals("1") && !playerHasHeroes) {
				System.out.println("You don't have any heroes to purge.");
				return false;
			}
			else if (valik2.equals("2") && opponentHasHeroes) {
				System.out.println("Choose the hero, you want to purge:\n");
				int j = 1;
				ArrayList<Kaart> tempHerod = new ArrayList<>();
				for (Kaart kaart : currentOpponent.getMangijaLaud()) {
					if (kaart.getTyyp().equals("Hero")) {
						tempHerod.add(kaart);
						System.out.println(j + ")" + kaart);
						j++;
					}
				}

				String heroChoice = scan.next();
				Kaart purgeHero = tempHerod.get(Integer.parseInt(heroChoice) - 1);
				Kaart purgeSpell = tempSpells.get(Integer.parseInt(spellChoice) - 1);
				purge(currentOpponent, purgeHero, purgeSpell);
				kaartSurnuAeda(purgeSpell,currentPlayer);
				System.out.println(purgeHero.getNimi() + " purged! Heroes stats now: \n" + "Attack: " + purgeHero.getAttack() + "\n" + "Defence: " + purgeHero.getDefence());
				return true;
			}
			else if (valik2.equals("2") && !opponentHasHeroes) {
				System.out.println("The opponent has no heroes to Purge");
				return false;
			}
		}
			else if (type.equals("Buff")) {
				if (!playerHasHeroes) {
					System.out.println("You don't have any heroes on the battlefield to buff!");
					return false;
				}
				System.out.println("Choose the card you want to use:\n"); // Kaardi valik, millega buffitakse
				int l = 1;
				ArrayList<Kaart> tempSpells = new ArrayList<>();
				for(Kaart kaart : currentPlayer.getMangijaLaud()) {
					if (kaart.getAlamTyyp().equals("Buff")) {
						tempSpells.add(kaart);
						System.out.println(l + ")" + kaart);
						l++;
					}
				}
				String spellChoice = scan.next();
				System.out.println("Choose the hero, you want to buff:\n");
				int j = 1;
				ArrayList<Kaart> tempHerod = new ArrayList<>();
				for(Kaart kaart : currentPlayer.getMangijaLaud()) {
					if (kaart.getTyyp().equals("Hero")) {
						tempHerod.add(kaart);
						System.out.println(j + ")" + kaart.getNimi());
						j++;
					}
				}
				String heroChoice = scan.next();
				Kaart buffHero = tempHerod.get(Integer.parseInt(heroChoice) - 1);
				Kaart buffSpell = tempSpells.get(Integer.parseInt(spellChoice) - 1);
				buffPlacement(currentPlayer,buffHero,buffSpell);
				System.out.println("Buff placed on " + buffHero.getNimi() + "." + "Heroes stats now:\n" + "Attack: " + buffHero.getAttack() + "\n" + "Defence: " + buffHero.getDefence());
				buffSpell.setOlek(true);
				return true;
			}
				String heroChoice = scan.next();
				Kaart purgeHero = tempHerod.get(Integer.parseInt(heroChoice) - 1);
				Kaart purgeSpell = tempSpells.get(Integer.parseInt(spellChoice) - 1);
				Purge.purge(currentOpponent, purgeHero, purgeSpell);
				kaartSurnuAeda(purgeSpell,currentPlayer);
				System.out.println(purgeHero.getNimi() + " purged! Heroes stats now: \n" + "Attack: " + purgeHero.getAttack() + "\n" + "Defence: " + purgeHero.getDefence());
				return true;
			}
			else if (valik2.equals("2") && !opponentHasHeroes) {
				System.out.println("The opponent has no heroes to Purge");
				return false;
			}
		}
			else if (type.equals("Buff")) {
				if (!playerHasHeroes) {
					System.out.println("You don't have any heroes on the battlefield to buff!");
					return false;
				}
				System.out.println("Choose the card you want to use:\n"); // Kaardi valik, millega buffitakse
				int l = 1;
				ArrayList<Kaart> tempSpells = new ArrayList<>();
				for(Kaart kaart : currentPlayer.getMangijaLaud()) {
					if (kaart.getAlamTyyp().equals("Buff")) {
						tempSpells.add(kaart);
						System.out.println(l + ")" + kaart);
						l++;
					}
				}
				String spellChoice = scan.next();
				System.out.println("Choose the hero, you want to buff:\n");
				int j = 1;
				ArrayList<Kaart> tempHerod = new ArrayList<>();
				for(Kaart kaart : currentPlayer.getMangijaLaud()) {
					if (kaart.getTyyp().equals("Hero")) {
						tempHerod.add(kaart);
						System.out.println(j + ")" + kaart.getNimi());
						j++;
					}
				}
				String heroChoice = scan.next();
				Kaart buffHero = tempHerod.get(Integer.parseInt(heroChoice) - 1);
				Kaart buffSpell = tempSpells.get(Integer.parseInt(spellChoice) - 1);
				Buff.buffPlacement(currentPlayer,buffHero,buffSpell);
				System.out.println("Buff placed on " + buffHero.getNimi() + "." + "Heroes stats now:\n" + "Attack: " + buffHero.getAttack() + "\n" + "Defence: " + buffHero.getDefence());
				buffSpell.setOlek(true);
				return true;
			}
				else {
					if (!opponentHasHeroes) {
						System.out.println("The enemy doesn't have any heroes to make more vulnerable!");
						return false;
					}
					System.out.println("Choose the card, you want to use:\n"); // Kaardi valik, millega vulnerability peale pannakse
					int l = 1;
					ArrayList<Kaart> tempSpells = new ArrayList<>();
					for(Kaart kaart : currentPlayer.getMangijaLaud()) {
						if (kaart.getAlamTyyp().equals("Vulnerability")) {
							tempSpells.add(kaart);
							System.out.println(l + ")" + kaart);
							l++;
						}
					}
					String spellChoice = scan.next();
					System.out.println("Choose the hero, you want to make more vulnerable:\n");
					int j = 1;
					ArrayList<Kaart> tempHerod = new ArrayList<>();
					for(Kaart kaart : currentOpponent.getMangijaLaud()) {
						if (kaart.getTyyp().equals("Hero")) {
							tempHerod.add(kaart);
							System.out.println(j + ")" + kaart);
							j++;
						}
					}
					String heroChoice = scan.next();
					Kaart vulnerableHero = tempHerod.get(Integer.parseInt(heroChoice) - 1);
					Kaart vulnerableSpell = tempSpells.get(Integer.parseInt(spellChoice) - 1);
					Vulnerability.vulnerabilityPlacement(currentPlayer, currentOpponent, vulnerableHero, vulnerableSpell);
					System.out.println("Vulnerability placed on " + vulnerableHero.getNimi() + "." + " Heroes stats now:\n " + "Attack: " + vulnerableHero.getAttack() + "\n" + "Defence: " + vulnerableHero.getDefence());
					vulnerableSpell.setOlek(true);
					return true;
			}
			return false;
} 
	public static boolean placeSpell(Mangija currentPlayer) {
		boolean heroHasSpells = false;
        Scanner scan = new Scanner(System.in);
        int i = 1;
        ArrayList<Kaart> tempSpells = new ArrayList<>();
		System.out.println("Which spell do you want to place on the battlefield:\n");
		for (Kaart kaart : currentPlayer.getMangijaKasi()) {
			if (kaart.getTyyp().equals("Spell")) {
				heroHasSpells = true;
				tempSpells.add(kaart);
				System.out.println(i + ")" + kaart);
				i++;
			}
		}
		if (!heroHasSpells) {
			return false;
		}
		String valik = scan.next();
		Kaart chosenSpell = tempSpells.get(Integer.parseInt(valik) - 1);
		System.out.println("Card " + chosenSpell.getNimi() + " added to the battlefield.");
		kaartLauale(chosenSpell, currentPlayer);
		return true;
		
	}
}

		

	


