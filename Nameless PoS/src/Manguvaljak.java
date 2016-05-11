import java.util.ArrayList;
import java.util.Scanner;
public class Manguvaljak{
	  static Mangija currentPlayer;
	  static Mangija currentOpponent;
	  static String currentPlayerDeck;
	  static String currentOpponentDeck;

	public static void setCurrentOpponentDeck(String currentOpponentDeck) {
		Manguvaljak.currentOpponentDeck = currentOpponentDeck;
	}

	public static void setCurrentPlayerDeck(String currentPlayerDeck) {
		Manguvaljak.currentPlayerDeck = currentPlayerDeck;
	}

	public static boolean kaartLauale(Kaart nimi, Mangija mangija) {
			if (mangija.getMana() < nimi.getManaPoints()) {
	    		System.out.println("You don't have enough mana to play this card!\n");
	    		return false;
	    	}
	    	else {
	    		mangija.setMana(mangija.getMana() - nimi.getManaPoints());
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
	    public static void kaartKatte(Mangija mangija) throws InterruptedException {
	        mangija.getMangijaKasi().add(mangija.getMangijaDeck().get(0));
			Animations.cardToHand(mangija);
	        mangija.getMangijaDeck().remove(0);
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
			System.out.println("You don't have any heroes to attack with!\n");
			return false;
		}
		else {
			System.out.println("Choose the hero to attack with:\n");
			int i = 1;
			ArrayList<Kaart> tempHerod = new ArrayList<>();
			for (Kaart kaart : currentPlayer.getMangijaLaud()) {
				if (kaart.getTyyp().equals("Hero")) {
					if (kaart.getMoveCount() == 0) {
						continue;
					}
					else {
						tempHerod.add(kaart);
						System.out.println(i + ") " + kaart.getNimi() +  " Attack : " + kaart.getAttack() + " Defence: " + kaart.getDefence() +"\n");
						i++;
					}
				}
		}
			if(tempHerod.size()==0) {
				System.out.println("You don't have any heroes to attack with!\n");
				return false;
			}
			String valik = scan.next();
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
					System.out.println(j + ") " +  kaart.getNimi() +  " Attack : " + kaart.getAttack() + " Defence: " + kaart.getDefence() +"\n");
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
				System.out.println("Attack did not succeed! Your hero " + ryndavHero.getNimi() + " has been defeated");
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
				if(kaart.isOlek()) {
					continue;
				}
				playerHasSpells = true;
			}
		}
		if (!playerHasSpells) {
			System.out.println("You don't have any spells on the battlefield to use!\n");
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
			System.out.println("You cannot add any more cards to the battlefield!\n");
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
				System.out.println("There are no heroes to purge!\n");
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
				Purge.purge(currentPlayer, purgeHero, purgeSpell);
				kaartSurnuAeda(purgeSpell,currentPlayer);
				System.out.println(purgeHero.getNimi() + " purged! Heroes stats now: \n" + "Attack: " + purgeHero.getAttack() + "\n" + "Defence: " + purgeHero.getDefence());
				return true;
			}
			else if (valik2.equals("1") && !playerHasHeroes) {
				System.out.println("You don't have any heroes to purge.\n");
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
				Purge.purge(currentOpponent, purgeHero, purgeSpell);
				kaartSurnuAeda(purgeSpell,currentPlayer);
				System.out.println(purgeHero.getNimi() + " purged! Heroes stats now: \n" + "Attack: " + purgeHero.getAttack() + "\n" + "Defence: " + purgeHero.getDefence());
				return true;
			}
			else if (valik2.equals("2") && !opponentHasHeroes) {
				System.out.println("The opponent has no heroes to Purge.\n");
				return false;
			}
		}
			else if (type.equals("Buff")) {
				if (!playerHasHeroes) {
					System.out.println("You don't have any heroes on the battlefield to buff!\n");
					return false;
				}
				System.out.println("Choose the card you want to use:\n"); // Kaardi valik, millega buffitakse
				int l = 1;
				ArrayList<Kaart> tempSpells = new ArrayList<>();
				for(Kaart kaart : currentPlayer.getMangijaLaud()) {
					if (kaart.getAlamTyyp().equals("Buff")) {
						if (kaart.isOlek()) {
							continue;
						}
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
						System.out.println("The enemy doesn't have any heroes to make more vulnerable!\n");
						return false;
					}
					System.out.println("Choose the card, you want to use:\n"); // Kaardi valik, millega vulnerability peale pannakse
					int l = 1;
					ArrayList<Kaart> tempSpells = new ArrayList<>();
					for(Kaart kaart : currentPlayer.getMangijaLaud()) {
						if (kaart.getAlamTyyp().equals("Vulnerability")) {
							if (kaart.isOlek()) {
								continue;
							}
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
				if (kaart.isOlek()) {
					continue;
				}
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
		System.out.println("Card " + chosenSpell.getNimi() + " added to the battlefield.\n");
		kaartLauale(chosenSpell, currentPlayer);
		return true;
		
	}
}

		

	


