import java.util.ArrayList;
import java.util.Scanner;
public class Manguvaljak {
	  static Mangija currentPlayer;
	  static Mangija currentOpponent;

	    public static void uusKaik() throws InterruptedException {
	        System.out.println("It's " + currentPlayer.getNimi() + "'s turn.");
	        for(int i=10;i>0;i--) {
	                System.out.println("Displaying cards in " + i);
	                Thread.sleep(250); //Delay for new turn count
	        }
	        System.out.println("Your cards are: ");
	        for(Kaart kaesKaart: currentPlayer.getMangijaKasi()){
	            System.out.println(kaesKaart + "\n");
	        }
	        System.out.println("The field: ");
	        System.out.println("your side: ");
			ArrayList<Integer> tempBuffBuffers = new ArrayList<>();
			ArrayList<Integer> tempVulnerabilityBuffers = new ArrayList<>();
	        for(Kaart sinuValjak: currentPlayer.getMangijaLaud()) {
	            if(sinuValjak.getTyyp().equals("Hero")) {
					if (sinuValjak.getBuffers().size() != 0) {
						for (int i = 0; i < sinuValjak.getBuffers().size(); i++) {
							if (sinuValjak.getBuffers().get(i).getLength() == sinuValjak.getBuffers().get(i).getMoveCount()) {
								if (sinuValjak.getBuffers().get(i).getEffekt().equals("Attack")) {
									sinuValjak.setAttackBuff(sinuValjak.getAttackBuff() - sinuValjak.getBuffers().get(i).getTugevus());
									sinuValjak.setAttack(sinuValjak.getAttack() - sinuValjak.getBuffers().get(i).getTugevus());
									tempBuffBuffers.add(i);
								} else {
									sinuValjak.setDefenceBuff(sinuValjak.getDefenceBuff() - sinuValjak.getBuffers().get(i).getTugevus());
									sinuValjak.setDefence(sinuValjak.getDefence() - sinuValjak.getBuffers().get(i).getTugevus());
									tempBuffBuffers.add(i);
								}
							}
						}
					} else {
						continue;
					}
					if (sinuValjak.getVulnerabilities().size() != 0) {
						for (int j = 0; j < sinuValjak.getVulnerabilities().size(); j++) {
							if (sinuValjak.getVulnerabilities().get(j).getLength() == sinuValjak.getVulnerabilities().get(j).getMoveCount()) {
								if (sinuValjak.getVulnerabilities().get(j).getEffekt().equals("Attack")) {
									sinuValjak.setAttackVulnerability(sinuValjak.getAttackVulnerability() + sinuValjak.getVulnerabilities().get(j).getTugevus());
									sinuValjak.setAttack(sinuValjak.getAttack() + sinuValjak.getVulnerabilities().get(j).getTugevus());
									tempVulnerabilityBuffers.add(j);
								} else {
									sinuValjak.setDefenceVulnerability(sinuValjak.getDefenceVulnerability() + sinuValjak.getVulnerabilities().get(j).getTugevus());
									sinuValjak.setDefence(sinuValjak.getDefence() + sinuValjak.getVulnerabilities().get(j).getTugevus());
									tempVulnerabilityBuffers.add(j);
								}
							}
						}
					}
					for (int indeks : tempBuffBuffers) {
						kaartSurnuAeda(sinuValjak.getBuffers().get(indeks),currentPlayer);
						sinuValjak.getBuffers().remove(indeks);
					}
					for (int indeks2 : tempVulnerabilityBuffers) {
						kaartSurnuAeda(sinuValjak.getVulnerabilities().get(indeks2),currentPlayer);
						sinuValjak.getVulnerabilities().remove(indeks2);
					}
					tempBuffBuffers = new ArrayList<>();
					tempVulnerabilityBuffers = new ArrayList<>();
					System.out.println(sinuValjak);
				}
				else if(sinuValjak.getTyyp().equals("Spell") && sinuValjak.isOlek()) {
					System.out.println(sinuValjak);
				}
				else {
					System.out.println("Face down card: " + sinuValjak);
				}

	        }
	        // Siin saab l�bi
	        System.out.println("Opponents side: ");
	        for(Kaart vastaseValjak: currentOpponent.getMangijaLaud()) {
				if(vastaseValjak.getTyyp().equals("Hero")) {
					System.out.println(vastaseValjak);
				}
				else if(vastaseValjak.getTyyp().equals("Spell") && vastaseValjak.isOlek()) {
					System.out.println(vastaseValjak);
				}
				else {
					System.out.println("Face down card.");
				}
				vastaseValjak.setMoveCount(vastaseValjak.getMoveCount()+1);
	        }
	    }
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
		hero.setBuffers(new ArrayList<Kaart>());
		hero.setVulnerabilities(new ArrayList<Kaart>());
		kaartSurnuAeda(spell, mangija);
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
		if (currentPlayer.getMangijaLaud().size() == 0) {
			System.out.println("You don't have any heroes to attack with!");
			return false;
		}
		else {
			System.out.println("Choose the hero to attack with:\n");
			int i = 1;
			ArrayList<Kaart> tempHerod = new ArrayList<Kaart>();
			for (Kaart kaart : currentPlayer.getMangijaLaud()) {
				if (kaart.getTyyp().equals("Hero")) {
					tempHerod.add(kaart);
					System.out.println(i + " " + kaart.getNimi() + "\n");
					i++;
				}
		}	String valik = scan.next();
			Kaart ryndavHero = tempHerod.get(Integer.parseInt(valik)-1);
			if (heroArvVastasel == false) {
				currentOpponent.setElud(currentOpponent.getElud() - ryndavHero.getAttack());
				System.out.println("Attack succeeded! Opponents lives:" + currentOpponent.getElud());
				return true;
			}
			System.out.println("Choose the opponents hero to attack:\n");
			int j = 1;
			ArrayList<Kaart> tempVastaseHerod = new ArrayList<Kaart>();
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
		boolean playerHasBuffs = false;
		boolean playerHasVulnerabilities = false;
		boolean playerHasPurges = false;
		boolean playerHasHeroes = false;
		boolean opponentHasHeroes = false;
		for (Kaart kaart : currentPlayer.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Hero")) {
				playerHasHeroes = true;
			}
			if (kaart.getTyyp().equals("Spell")) {
				if (kaart.getAlamTyyp().equals("Buff") && kaart.isActivity() == false) {
					playerHasSpells = true;
					playerHasBuffs = true;
				}
				if (kaart.getAlamTyyp().equals("Purge")) {
					playerHasSpells = true;
					playerHasPurges = true;
				}
				if (kaart.getAlamTyyp().equals("Vulnerability") && kaart.isActivity() == false) {
					playerHasSpells = true;
					playerHasVulnerabilities = true;
				}
			}
		}
		if (playerHasSpells == false) {
			System.out.println("You don't have any spells on the battlefield to use!");
			return false;
		}
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
			if (kaart.getTyyp().equals("Spell") && kaart.isOlek() == false) {
				if (kaart.getAlamTyyp().equals("Purge")) {
					if (tempSpellType.contains("Purge")) {
						continue;
					}
					else {
					tempSpellType.add("Purge");
					}
				}
				if (kaart.getAlamTyyp().equals("Buff")) {
					if (tempSpellType.contains("Buff")) {
						continue;
					}
					else {
					tempSpellType.add("Buff");
				}
				}
				if (kaart.getAlamTyyp().equals("Vulnerability")) {
					if (tempSpellType.contains("Vulnerability")) {
						continue;
					}
					else {
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
					if (playerHasHeroes == false && opponentHasHeroes == false) {
						System.out.println("There are no heroes to purge!");
						return false;
					}
					System.out.println("Choose the card, you want to use:\n"); // Kaardi valik, millega purgeitakse
					int l = 1;
					ArrayList<Kaart> tempSpells = new ArrayList<>();
					for(Kaart kaart : currentPlayer.getMangijaLaud()) {
						if (kaart.getAlamTyyp().equals("Purge")) {
							tempSpells.add(kaart);
							System.out.println(l + ")" + kaart);
							l++;
						}
					}
					String spellChoice = scan.next();
					System.out.println("Do you want to purge your own hero or the enemies hero:\n 1) My own hero\n 2) The enemies hero");
					String valik2 = scan.next();
					if (valik2.equals("1")) {
						System.out.println("Choose the hero, you want to purge:\n");
						int j = 1;
						ArrayList<Kaart> tempHerod = new ArrayList<>();
						for(Kaart kaart : currentPlayer.getMangijaLaud()) {
							if (kaart.getTyyp().equals("Hero")) {
								tempHerod.add(kaart);
								System.out.println(j + ")" + kaart);
								j++;
							}
						}
						String heroChoice = scan.next();
						Kaart purgeHero = tempHerod.get(Integer.parseInt(heroChoice) - 1);
						Kaart purgeSpell = tempSpells.get(Integer.parseInt(spellChoice) - 1);
						purge(currentOpponent,purgeHero, purgeSpell);
						System.out.println("Card purged!");
						return true;
					}
					if (valik2.equals("2")) {
						System.out.println("Choose the hero, you want to purge:\n");
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
					Kaart purgeHero = tempHerod.get(Integer.parseInt(heroChoice) - 1);
					Kaart purgeSpell = tempSpells.get(Integer.parseInt(spellChoice) - 1);
					purge(currentOpponent,purgeHero, purgeSpell);
					System.out.println(purgeHero.getNimi() + " purged! Heroes stats now: \n" + "Attack: " + purgeHero.getAttack() + "\n" + "Defence: " + purgeHero.getDefence());
					return true;
		}
				}
				if (type.equals("Buff")) {
					if (playerHasHeroes == false) {
						System.out.println("You don't have any heroes on the battlefield to buff!");
						return false;
					}
					System.out.println("Choose the card, you want to use:\n"); // Kaardi valik, millega buffitakse
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
				else {
					if (opponentHasHeroes == false) {
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
					vulnerabilityPlacement(currentPlayer, currentOpponent, vulnerableHero, vulnerableSpell);
					System.out.println("Vulnerability placed on " + vulnerableHero.getNimi() + "." + " Heroes stats now:\n " + "Attack: " + vulnerableHero.getAttack() + "\n" + "Defence: " + vulnerableHero.getDefence());
					vulnerableSpell.setOlek(true);
					return true;
			}
}
	public static boolean placeSpell(Mangija currentPlayer) {
		boolean heroHasSpells = false;
        Scanner scan = new Scanner(System.in);
        int i = 1;
        ArrayList<Kaart> tempSpells = new ArrayList<Kaart>();
		System.out.println("Which spell do you want to place on the battlefield:\n");
		for (Kaart kaart : currentPlayer.getMangijaKasi()) {
			if (kaart.getTyyp().equals("Spell")) {
				heroHasSpells = true;
				tempSpells.add(kaart);
				System.out.println(i + ")" + kaart);
				i++;
			}
		}
		if (heroHasSpells == false) {
			System.out.println("You don't have any spells to place on the battlefield!");
			return false;
		}
		String valik = scan.next();
		Kaart chosenSpell = tempSpells.get(Integer.parseInt(valik) - 1);
		System.out.println("Card " + chosenSpell.getNimi() + " added to the battlefield.");
		kaartLauale(chosenSpell, currentPlayer);
		return true;
		
	}
}

		

	


