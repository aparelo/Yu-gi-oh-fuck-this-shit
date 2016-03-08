import java.util.ArrayList;
import java.util.Scanner;
public class Manguvaljak {
	 static Mangija hetkeMangija;
	 static Mangija hetkeVastane;

	    public static void uusKaik() throws InterruptedException {
	        System.out.println("It's " + hetkeMangija.getNimi() + "'s turn.");
	        for(int i=10;i>0;i--) {
	                System.out.println("Displaying cards in " + i);
	                Thread.sleep(250); //Delay for new turn count
	        }
	        System.out.println("Your cards are: ");
	        for(Kaart kaesKaart: hetkeMangija.getMangijaKasi()){
	            System.out.println(kaesKaart + "\n");
	        }
	        System.out.println("The field: ");
	        System.out.println("your side: ");
	        for(Kaart sinuValjak: hetkeMangija.getMangijaLaud()) {
	            if(sinuValjak.getTyyp().equals("Hero")) {
					System.out.println(sinuValjak);
					if(sinuValjak.getBuffers().size() != 0) {
						for(Kaart buffer: sinuValjak.getBuffers()) {
							if(buffer.getLength() == buffer.getMoveCount()) {
								if(buffer.getEffekt().equals("Attack")) {
									sinuValjak.setAttackBuff(sinuValjak.getAttackBuff()-buffer.getTugevus());
									sinuValjak.setAttack(sinuValjak.getAttack()-buffer.getTugevus());
								}
								else {
									sinuValjak.setDefenceBuff(sinuValjak.getDefenceBuff()-buffer.getTugevus());
									sinuValjak.setDefence(sinuValjak.getDefence()-buffer.getTugevus());
								}
								hetkeMangija.getMangijaLaud().remove(hetkeMangija.getMangijaLaud().indexOf(buffer));
								sinuValjak.getBuffers().remove(sinuValjak.getBuffers().indexOf(buffer));
							}
						}
					}
					else {
						continue;
					}
					if(sinuValjak.getVulnerabilities().size() != 0) {
						for(Kaart vulners: sinuValjak.getVulnerabilities()) {
							if(vulners.getLength() == vulners.getMoveCount()) {
								if(vulners.getEffekt().equals("Attack")) {
									sinuValjak.setAttackVulnerability(sinuValjak.getAttackVulnerability() + vulners.getTugevus());
									sinuValjak.setAttack(sinuValjak.getAttack() + vulners.getTugevus());
								}
								else {
									sinuValjak.setDefenceVulnerability(sinuValjak.getDefenceVulnerability() + vulners.getTugevus());
									sinuValjak.setDefence(sinuValjak.getDefence() + vulners.getTugevus());
								}
								hetkeMangija.getMangijaLaud().remove(hetkeMangija.getMangijaLaud().indexOf(vulners));
								sinuValjak.getVulnerabilities().remove(sinuValjak.getVulnerabilities().indexOf(vulners));
							}
						}
					}
				}
				else if(sinuValjak.getTyyp().equals("Spell") && sinuValjak.isOlek()) {
					System.out.println(sinuValjak);
				}
				else {
					System.out.println("Face down card: " + sinuValjak);
				}

	        }
	        System.out.println("Opponents side: ");
	        for(Kaart vastaseValjak: hetkeVastane.getMangijaLaud()) {
				if(vastaseValjak.getTyyp().equals("Hero")) {
					System.out.println(vastaseValjak);
				}
				else if(vastaseValjak.getTyyp().equals("Spell") && vastaseValjak.isOlek()) {
					System.out.println(vastaseValjak);
				}
				else {
					System.out.println("Face down card");
				}
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
		kaartSurnuAeda(spell, mangija);
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
				hero.setBuffers(new ArrayList<Kaart>());
				hero.setVulnerabilities(new ArrayList<Kaart>());
			}
		}
		kaartSurnuAeda(spell, mangija);
	}
	public static void vulnerabilityPlacement(Mangija mangija, Kaart hero, Kaart spell) {
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
		kaartSurnuAeda(spell, mangija);
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

	public static boolean useSpell(Mangija currentPlayer, Mangija currentOpponent) {
		boolean playerHasSpells = false;
		boolean playerHasPurges = false;
		boolean playerHasBuffs = false;
		boolean playerHasVulnerabilities = false;
		boolean playerHasHeroes = false;
		boolean opponentHasHeroes = false;
		for (Kaart kaart : currentPlayer.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Hero")) {
				playerHasHeroes = true;
				break;
			}
		}
		for (Kaart kaart : currentOpponent.getMangijaLaud()) {
			if (kaart.getTyyp().equals("Hero")) {
				opponentHasHeroes = true;
				break;
			}
		}
        Scanner scan = new Scanner(System.in);
        ArrayList <String> tempSpellType = new ArrayList<>();
		for (Kaart kaart : currentPlayer.getMangijaKasi()) {
			if (kaart.getTyyp().equals("Spell")) {
				playerHasSpells = true;
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
				else {
					continue;
				}
			}
		}
		if (currentPlayer.getMangijaLaud().size() == 10) {
			System.out.println("You cannot add any more cards to the battlefield!");
			return false;
		}
		else if (playerHasSpells == false) {
			System.out.println("You don't have any spells to add to the battlfield!");
			return false;
		}
		else {
			System.out.println("What type of spell do you want to use:\n");
			int i = 1;
			for (String type :  tempSpellType) {
				System.out.println(i + ")" + type + "\n");
				i++;
			}
		}

			String valik = scan.next();
			for (int k = 0; k <1;k++) {
				String type = tempSpellType.get(Integer.parseInt(valik) - 1);
				if (type.equals("Purge")) {
					if (playerHasHeroes == false && opponentHasHeroes == false) {
						System.out.println("There are no heroes to purge!");
						return false;
					}
					System.out.println("Choose the card, you want to use:\n"); // Kaardi valik, millega purgeitakse
					int l = 0;
					ArrayList<Kaart> tempSpells = new ArrayList<>();
					for(Kaart kaart : currentPlayer.getMangijaKasi()) {
						if (kaart.getAlamTyyp().equals("Purge")) {
							tempSpells.add(kaart);
							System.out.println(l + ")" + kaart.getNimi());
						}
					}
					String spellChoice = scan.next();
					System.out.println("Do you want to purge your own hero or the enemies hero:\n 1) My own hero\n 2) The enemies hero");
					String valik2 = scan.next();
					if (valik2.equals("1")) {
						System.out.println("Choose the hero, you want to purge:\n");
						int j = 0;
						ArrayList<Kaart> tempHerod = new ArrayList<>();
						for(Kaart kaart : currentPlayer.getMangijaKasi()) {
							if (kaart.getTyyp().equals("Hero")) {
								tempHerod.add(kaart);
								System.out.println(j + ")" + kaart.getNimi());
							}
						}
						String choice3 = scan.next();
						purge(currentPlayer,tempHerod.get(Integer.parseInt(choice3)), tempSpells.get(Integer.parseInt(spellChoice)));
						System.out.println("Card purged!");
						return true;
					}
					if (valik2.equals("2")) {
						System.out.println("Choose the hero, you want to purge:\n");
						int j = 0;
						ArrayList<Kaart> tempHerod = new ArrayList<>();
						for(Kaart kaart : currentOpponent.getMangijaKasi()) {
							if (kaart.getTyyp().equals("Hero")) {
								tempHerod.add(kaart);
								System.out.println(j + ")" + kaart.getNimi());
							}
				}

					String choice4 = scan.next();
					purge(currentOpponent,tempHerod.get(Integer.parseInt(choice4)), tempSpells.get(Integer.parseInt(spellChoice)));
					System.out.println("Card purged!");
					return true;
		}
				}
				if (type.equals("Buff")) {
					if (playerHasHeroes == false) {
						System.out.println("You don't have any heroes on the battlefield to buff!");
						return false;
					}
					System.out.println("Choose the card, you want to use:\n"); // Kaardi valik, millega buffitakse
					int l = 0;
					ArrayList<Kaart> tempSpells = new ArrayList<>();
					for(Kaart kaart : currentPlayer.getMangijaKasi()) {
						if (kaart.getAlamTyyp().equals("Buff")) {
							tempSpells.add(kaart);
							System.out.println(l + ")" + kaart.getNimi());
						}
					}
					String spellChoice = scan.next();
					System.out.println("Choose the hero, you want to buff:\n");
					int j = 0;
					ArrayList<Kaart> tempHerod = new ArrayList<>();
					for(Kaart kaart : currentPlayer.getMangijaKasi()) {
						if (kaart.getTyyp().equals("Hero")) {
							tempHerod.add(kaart);
							System.out.println(j + ")" + kaart.getNimi());
						}
					}
					String heroChoice = scan.next();
					buffPlacement(currentPlayer,tempHerod.get(Integer.parseInt(heroChoice)),tempSpells.get(Integer.parseInt(spellChoice)));
					return true;
				}
				else {
					if (opponentHasHeroes == false) {
						System.out.println("The enemy doesn't have any heroes to make more vulnerable!");
						return false;
					}
					System.out.println("Choose the card, you want to use:\n"); // Kaardi valik, millega buffitakse
					int l = 0;
					ArrayList<Kaart> tempSpells = new ArrayList<>();
					for(Kaart kaart : currentPlayer.getMangijaKasi()) {
						if (kaart.getAlamTyyp().equals("Vulnerability")) {
							tempSpells.add(kaart);
							System.out.println(l + ")" + kaart.getNimi());
						}
					}
					String spellChoice = scan.next();
					System.out.println("Choose the hero, you want to make more vulnerable:\n");
					int j = 0;
					ArrayList<Kaart> tempHerod = new ArrayList<>();
					for(Kaart kaart : currentOpponent.getMangijaKasi()) {
						if (kaart.getTyyp().equals("Hero")) {
							tempHerod.add(kaart);
							System.out.println(j + ")" + kaart.getNimi());
						}
					}
					String heroChoice = scan.next();
					vulnerabilityPlacement(currentOpponent, tempHerod.get(Integer.parseInt(heroChoice)), tempSpells.get(Integer.parseInt(spellChoice)));
					return true;
			}
} //MethodBody
			return false;
} //ClassBody
}

		

	


