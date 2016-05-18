import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Scanner;
public class Manguvaljak {
    static Mangija currentPlayer;
    static Mangija currentOpponent;
    static String currentPlayerDeck;
    static String currentOpponentDeck;

    public static Node attackedCard;

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
            if(currentPlayer.getLocation() == 1) {
                Gamescenes.getoMana().setText("Manapoints: " + currentPlayer.getMana());
            }
            else {
                Gamescenes.getMana().setText("Manapoints: " + currentPlayer.getMana());
            }
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
        Animations.cardToGraveyard(nimi, mangija);
    }

    public static void kaartKatte(Mangija mangija) throws InterruptedException {
        mangija.getMangijaKasi().add(mangija.getMangijaDeck().get(0));
        Animations.cardToHand(mangija);
        mangija.getMangijaDeck().remove(0);
    }

    public static boolean attack(Kaart attackingCard) {
        boolean heroArvVastasel = false;
        System.out.println(Kaik.turnCount);
        System.out.println(Manguvaljak.currentPlayer.getAttackCount());

        if (Manguvaljak.currentPlayer.getAttackCount() == 1) {
            Gamescenes.setLabelText("You can only attack once during a turn!");
            return false;
        }
        if (Kaik.turnCount < 1) {
            Gamescenes.setLabelText("You cannot attack on the first turn!");
            return false;
        }

        for (Kaart kaart : currentOpponent.getMangijaLaud()) {
            if (kaart.getTyyp().equals("Hero")) {
                heroArvVastasel = true;
            }
        }
        if (!(heroArvVastasel) && Kaik.turnCount > 0) {
            currentOpponent.setElud(currentOpponent.getElud() - attackingCard.getAttack());
            Gamescenes.setLabelText("Attack succeeded! Opponents lives:" + currentOpponent.getElud());
            return true;
        }
        else {
            if (Kaik.turnCount < 0) {
                Gamescenes.setLabelText("You cannot attack on the first turn!");
            }
            else {
                Gamescenes.setLabelText("Choose the hero to attack");
                Gamescenes.getBattleScenePane().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)   {
                        double mouseX = e.getSceneX();
                        double mouseY = e.getSceneY();
                        for (Kaart hero : currentOpponent.getHeroMap().keySet()) {
                            if (!hero.toString().equals("Empty")) {
                                String indeks = Animations.getPositionIndex(hero,currentOpponent);

                                Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

                                Bounds heroBounds = heroNode.localToScene(heroNode.getBoundsInLocal());

                                if (heroBounds.contains(mouseX,mouseY)) {
                                    Kaart  vastaseHero = hero;
                                    if (vastaseHero.getDefence() > attackingCard.getAttack()) {
                                        Gamescenes.setLabelText("Attack did not succeed! Your hero " + attackingCard.getNimi() + " has been defeated");
                                        Manguvaljak.kaartSurnuAeda(attackingCard, currentPlayer);
                                        Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                                        Manguvaljak.currentPlayer.setAttackCount(1);
                                        break;
                                    }
                                    else {
                                        Gamescenes.setLabelText("Attack succeeded! The hero " + vastaseHero.getNimi() + " has been defeated." + " Opponents lives: " + (currentOpponent.getElud() - (attackingCard.getAttack() - vastaseHero.getDefence())));
                                        Manguvaljak.kaartSurnuAeda(vastaseHero, currentOpponent);
                                        heroNode.setOnMouseExited(null);
                                        Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                                        Manguvaljak.currentPlayer.setAttackCount(1);
                                        break;
                                    }


                                }

                            }
                        }
                    }
                });

                return true;

            }
        }
        return false;
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

        String type = card.getAlamTyyp();
        if (type.equals("Purge")) {
            if (!playerHasHeroes && !opponentHasHeroes) {
                Gamescenes.setLabelText("There are no heroes to purge!");
                return false;
            }

        Gamescenes.setLabelText("Click on the hero you wish to use the spell on");
        Gamescenes.getBattleScenePane().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                double mouseX = e.getSceneX();
                System.out.println(mouseX);
                double mouseY = e.getSceneY();
                System.out.println(mouseY);
                for (Kaart hero : currentOpponent.getHeroMap().keySet()) {
                    if (!hero.isEmpty()) {
                        String indeks = Animations.getPositionIndex(hero, currentOpponent);

                        Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

                        Bounds heroBounds = heroNode.localToScene(heroNode.getBoundsInLocal());

                        System.out.println("Node created");
                        if (heroBounds.contains(mouseX, mouseY)) {
                            System.out.println("Purge finished!");
                            Kaart purgeHero = hero;
                            Kaart purgeSpell = card;
                            Purge.purge(currentPlayer, purgeHero, purgeSpell);
                            Animations.cardToGraveyard(purgeSpell, currentPlayer);
                            kaartSurnuAeda(purgeSpell, currentPlayer);
                            Gamescenes.setLabelText(purgeHero.getNimi() + " purged! Heroes stats now: " + "Attack: " + purgeHero.getAttack() + "Defence: " + purgeHero.getDefence());
                            System.out.println("Consumed");
                            Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                        }

                    }
                }
                for (Kaart hero : currentPlayer.getHeroMap().keySet()) {
                    if (!hero.toString().equals("Empty")) {
                        String indeks = Animations.getPositionIndex(hero, currentPlayer);
                        Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

                        Bounds heroBounds = heroNode.localToScene(heroNode.getBoundsInLocal());

                        if (heroBounds.contains(mouseX, mouseY)) {
                            System.out.println("Jõudis siia!");
                            Kaart purgeHero = hero;
                            Kaart purgeSpell = card;
                            Purge.purge(currentPlayer, purgeHero, purgeSpell);
                            Animations.cardToGraveyard(purgeSpell, currentPlayer);
                            kaartSurnuAeda(purgeSpell, currentPlayer);
                            Gamescenes.setLabelText(purgeHero.getNimi() + " purged! Heroes stats now: " + "Attack: " + purgeHero.getAttack() + "Defence: " + purgeHero.getDefence());
                            Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                        }

                    }
                }

            }
        });
    }

			else if (type.equals("Buff")) {

            if (!playerHasHeroes) {
                Gamescenes.setLabelText("You have no heroes to buff!");
                return false;
            }
				Gamescenes.setLabelText("Choose the hero you want to buff");

            Gamescenes.getBattleScenePane().setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    double mouseX = e.getSceneX();
                    double mouseY = e.getSceneY();
                    for (Kaart hero : currentPlayer.getHeroMap().keySet()) {
                        if (!hero.toString().equals("Empty")) {
                            String indeks = Animations.getPositionIndex(hero, currentPlayer);
                            Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

                            Bounds heroBounds = heroNode.localToScene(heroNode.getBoundsInLocal());

                            if (heroBounds.contains(mouseX, mouseY)) {
                                Buff.buffPlacement(currentPlayer,hero,card);
                                Gamescenes.setLabelText("Buff placed on " + hero.getNimi() + "." + "Heroes stats now:" + "Attack: " + hero.getAttack() +  " Defence: " + hero.getDefence());
                                card.setOlek(true);
                                Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                                break;
                            }

                        }
                    }

                }
            });
            return true;

			}
				else {
					if (!opponentHasHeroes) {
						Gamescenes.setLabelText("The enemy doesn't have any heroes to make more vulnerable!");
						return false;
					}
            Gamescenes.setLabelText("Choose the hero you want to make more vulnerable");
            Gamescenes.getBattleScenePane().setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    double mouseX = e.getSceneX();
                    double mouseY = e.getSceneY();
                    for (Kaart hero : currentOpponent.getHeroMap().keySet()) {
                        if (!hero.toString().equals("Empty")) {
                            String indeks = Animations.getPositionIndex(hero, currentOpponent);
                            Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

                            Bounds heroBounds = heroNode.localToScene(heroNode.getBoundsInLocal());

                            if (heroBounds.contains(mouseX, mouseY)) {
                                Vulnerability.vulnerabilityPlacement(hero,card);
                                Gamescenes.setLabelText("Vulnerability placed on " + hero.getNimi() + "." + "Heroes stats now:" + "Attack: " + hero.getAttack() +  " Defence: " + hero.getDefence());
                                card.setOlek(true);
                                Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                                break;
                            }

                        }
                    }

                }
            });
        return true;
			}
        return false;
        }
}



