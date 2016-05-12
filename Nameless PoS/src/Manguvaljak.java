import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Scanner;
public class Manguvaljak {
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

    public static boolean kaartLauale(Kaart card, Mangija mangija) {
        if (mangija.getMana() < card.getManaPoints()) {
            Gamescenes.setLabelText("You don't have enough mana to play this card!");
            return false;
        } else {
            mangija.setMana(mangija.getMana() - card.getManaPoints());
        }
        if (mangija.getMangijaLaud().size() == 10) {
            return false;
        } else {
            mangija.getMangijaKasi().remove(card);
            mangija.getMangijaLaud().add(card);
            Animations.cardToField(card, mangija);

            if (card.getTyyp().equals("Hero")) {
                int tempHeroesOnField = mangija.getHeroesOnField() + 1;
                mangija.setHeroesOnField(tempHeroesOnField);
            } else {
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
        } else {
            mangija.setSpellsOnField(mangija.getSpellsOnField() - 1);
        }
    }

    public static void kaartKatte(Mangija mangija) throws InterruptedException {
        mangija.getMangijaKasi().add(mangija.getMangijaDeck().get(0));
        Animations.cardToHand(mangija);
        mangija.getMangijaDeck().remove(0);
    }

    public static boolean attack(Kaart attackingCard) {
        boolean heroArvMangijal = false;
        boolean heroArvVastasel = false;
        if (Manguvaljak.currentPlayer.getAttackCount() > 1) {
            Gamescenes.setLabelText("You can only attack once during a turn!");
        }

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
            Gamescenes.setLabelText("The enemy has no heroes to attack!");
            return false;
        } else {
            Gamescenes.setLabelText("Choose the hero to attack");
            if (!heroArvVastasel) {
                currentOpponent.setElud(currentOpponent.getElud() - attackingCard.getAttack());
                Gamescenes.setLabelText("Attack succeeded! Opponents lives:" + currentOpponent.getElud());
                return true;
            }

			/*Kaart vastaseHero =   // Need to add the chosen opponents hero
				Gamescenes.setLabelText("Attack succeeded! The hero " + vastaseHero.getNimi() + " has been defeated." + " Opponents lives: " + (currentOpponent.getElud() - (attackingCard.getAttack() - vastaseHero.getDefence())));
				Manguvaljak.kaartSurnuAeda(vastaseHero, currentOpponent);
				return true;*/
            else {
                System.out.println("Attack did not succeed! Your hero " + attackingCard.getNimi() + " has been defeated");
                Manguvaljak.kaartSurnuAeda(attackingCard, currentPlayer);
                return true;
            }
        }
    }


    public static boolean useSpell(Kaart card) {
        boolean playerHasSpells = false;
        boolean playerHasHeroes = false;
        boolean opponentHasHeroes = false;
        for (Kaart kaart : currentPlayer.getMangijaLaud()) {
            if (kaart.getTyyp().equals("Hero")) { //Kontrollin kas mangijal on Herosid kellele Spelle rakendada
                playerHasHeroes = true;
            }
        }

        //Kontrollin, kas vastasel on herosid kelle peal spelle kasutada.
        for (Kaart kaart : currentOpponent.getMangijaLaud()) {
            if (kaart.getTyyp().equals("Hero")) {
                opponentHasHeroes = true;
                break;
            }
        }
        if (currentPlayer.getSpellsOnField() == 5) {
            Gamescenes.setLabelText("You can't add any more spells to the field!");
            return false;
        }
        ArrayList<String> tempSpellType = new ArrayList<>();
        for (Kaart kaart : currentPlayer.getMangijaLaud()) {
            if (kaart.getTyyp().equals("Spell") && !kaart.isOlek()) {
                if (kaart.getAlamTyyp().equals("Purge")) {
                    if (!tempSpellType.contains("Purge")) {
                        tempSpellType.add("Purge");
                    }
                } else if (kaart.getAlamTyyp().equals("Buff")) {
                    if (!tempSpellType.contains("Buff")) {
                        tempSpellType.add("Buff");
                    }
                } else if (kaart.getAlamTyyp().equals("Vulnerability")) {
                    if (!tempSpellType.contains("Vulnerability")) {
                        tempSpellType.add("Vulnerability");
                    }
                }
            }
        }

        String type = card.getAlamTyyp();
        if (type.equals("Purge")) {
            if (!playerHasHeroes && !opponentHasHeroes) {
                Gamescenes.setLabelText("There are no heroes to purge!");
                return false;
            }
        }
                Gamescenes.setLabelText("Click on the hero you wish to use the spell on");
                Gamescenes.getBattleScenePane().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)   {
                        double mouseX = e.getSceneX();
                        System.out.println(mouseX);
                        double mouseY = e.getSceneY();
                        System.out.println(mouseY);
                        for (Kaart hero : currentOpponent.getHeroMap().keySet()) {
                            if (!hero.isEmpty()) {
                                String indeks = Animations.getPositionIndex(hero,currentOpponent);

                                Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

                                Bounds heroBounds = heroNode.localToScene(heroNode.getBoundsInLocal());

                                System.out.println("Node created");
                                if (heroBounds.contains(mouseX,mouseY)) {
                                    System.out.println("Purge finished!");
                                    Kaart  purgeHero = hero;
                                    Kaart purgeSpell = card;
                                    Purge.purge(currentPlayer, purgeHero, purgeSpell);
                                    Animations.cardToGraveyard(purgeSpell,currentPlayer);
                                    kaartSurnuAeda(purgeSpell,currentPlayer);
                                    Gamescenes.setLabelText(purgeHero.getNimi() + " purged! Heroes stats now: " + "Attack: " + purgeHero.getAttack() + "Defence: " + purgeHero.getDefence());
                                    System.out.println("Consumed");
                                    Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                                }

                            }
                        }
                        for (Kaart hero : currentPlayer.getHeroMap().keySet()) {
                            System.out.println("Jõudis tsüklisse!");
                            if (!hero.toString().equals("Empty")) {
                                String indeks = Animations.getPositionIndex(hero,currentPlayer);
                                Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

                                Bounds heroBounds = heroNode.localToScene(heroNode.getBoundsInLocal());

                                System.out.println(indeks + "Node created");
                                if (heroBounds.contains(mouseX,mouseY)) {
                                    System.out.println("Jõudis siia!");
                                    Kaart  purgeHero = hero;
                                    Kaart purgeSpell = card;
                                    Purge.purge(currentPlayer, purgeHero, purgeSpell);
                                    Animations.cardToGraveyard(purgeSpell,currentPlayer);
                                    kaartSurnuAeda(purgeSpell,currentPlayer);
                                    Gamescenes.setLabelText(purgeHero.getNimi() + " purged! Heroes stats now: " + "Attack: " + purgeHero.getAttack() + "Defence: " + purgeHero.getDefence());
                                    System.out.println("Consumed");
                                    Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                                }

                            }
                        }

            }
        });

                /*Gamescenes.getBattleScenePane().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)   {
                        double X = e.getSceneX();
                        double Y = e.getSceneY();

            }});*/


			/*}
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
					return true;*/
        return true;
			}

        }



