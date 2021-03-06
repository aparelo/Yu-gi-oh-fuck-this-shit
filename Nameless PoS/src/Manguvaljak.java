import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

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
        }
        if (mangija.getSpellsOnField() == 5 || mangija.getHeroesOnField() == 5) {
            if ((currentPlayer.getSpellsOnField() == 5 && card.getTyyp().equals("Spell")) || (currentPlayer.getHeroesOnField() == 5 && card.getTyyp().equals("Hero"))) {
                Gamescenes.setLabelText("Not enough space on the field.");
                return false;
            }
        }
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

            mangija.setMana(mangija.getMana() - card.getManaPoints());
            if (currentPlayer.getLocation() == 1) {
                Gamescenes.getoMana().setText("Manapoints: " + currentPlayer.getMana());
            } else {
                Gamescenes.getMana().setText("Manapoints: " + currentPlayer.getMana());
            }
        return true;

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
        if(mangija.getMangijaDeck().size() > 0) {
            mangija.getMangijaKasi().add(mangija.getMangijaDeck().get(0));
            Animations.cardToHand(mangija);
            mangija.getMangijaDeck().remove(0);
        }
    }

    public static boolean attack(Kaart attackingCard) {
        boolean heroArvVastasel = false;

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
            Manguvaljak.currentPlayer.setAttackCount(1);
            if(currentPlayer.getLocation() == 1) { //1 = oMana
                Gamescenes.getHp().setText("Hitpoints: " + currentOpponent.getElud());
            }
            else {
                Gamescenes.getoHp().setText("Hitpoints: " + currentOpponent.getElud());
            }
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
                                    if (vastaseHero.getDefence() >= attackingCard.getAttack()) {
                                        Gamescenes.setLabelText("Attack did not succeed! Your hero " + attackingCard.getNimi() + " has been defeated");
                                        System.out.println(attackingCard);
                                        Manguvaljak.kaartSurnuAeda(attackingCard, currentPlayer);
                                        Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                                        Manguvaljak.currentPlayer.setAttackCount(1);
                                        break;
                                    }
                                    else {
                                        Gamescenes.setLabelText("Attack succeeded! The hero " + vastaseHero.getNimi() + " has been defeated." + " Opponents lives: " + (currentOpponent.getElud() - (attackingCard.getAttack() - vastaseHero.getDefence())));
                                        currentOpponent.setElud(currentOpponent.getElud() - (attackingCard.getAttack() - vastaseHero.getDefence()));
                                        if(currentPlayer.getLocation() == 1) { //1 = oMana
                                            if(currentOpponent.getElud() < 0) {
                                                Gamescenes.getHp().setText("Hitpoints: 0");
                                            }
                                            else {
                                                Gamescenes.getHp().setText("Hitpoints: " + currentOpponent.getElud());
                                            }
                                        }
                                        else {
                                            if(currentOpponent.getElud() <0) {
                                                Gamescenes.getoHp().setText("Hitpoints: 0");
                                            }
                                            else {
                                                Gamescenes.getoHp().setText("Hitpoints: " + currentOpponent.getElud());
                                            }
                                        }
                                        if(currentOpponent.getElud() == 0) {
                                            Gamescenes.setLabelText("Game over " + currentPlayer.getNimi() +" won");
                                        }
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
                System.out.println("Attack ended!");
                return true;

            }
        }
        return false;
    }


    public static boolean useSpell(Kaart card) {
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
                    double mouseY = e.getSceneY();
                    for (Kaart hero : currentOpponent.getHeroMap().keySet()) {
                        if (!hero.toString().equals("Empty")) {
                            String indeks = Animations.getPositionIndex(hero, currentOpponent);

                            Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

                            Bounds heroBounds = heroNode.localToScene(heroNode.getBoundsInLocal());

                            if (heroBounds.contains(mouseX, mouseY)) {
                                Kaart purgeHero = hero;
                                Kaart purgeSpell = card;
                                Purge.purge(currentPlayer, purgeHero, purgeSpell);
                                Gamescenes.setLabelText(purgeHero.getNimi() + " purged! Heroes stats now: " + "Attack: " + purgeHero.getAttack() + " Defence: " + purgeHero.getDefence());
                                Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                                break;
                            }

                        }
                    }
                    for (Kaart hero : currentPlayer.getHeroMap().keySet()) {
                        if (!hero.toString().equals("Empty")) {
                            String indeks = Animations.getPositionIndex(hero, currentPlayer);
                            Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

                            Bounds heroBounds = heroNode.localToScene(heroNode.getBoundsInLocal());

                            if (heroBounds.contains(mouseX, mouseY)) {
                                Kaart purgeHero = hero;
                                Kaart purgeSpell = card;
                                Purge.purge(currentPlayer, purgeHero, purgeSpell);
                                Gamescenes.setLabelText(purgeHero.getNimi() + " purged! Heroes stats now: " + "Attack: " + purgeHero.getAttack() + " Defence: " + purgeHero.getDefence());
                                Gamescenes.getBattleScenePane().setOnMouseClicked(null);
                                break;
                            }

                        }
                    }

                }
            });
            return true;
        }
        else if (type.equals("Buff")) {
            System.out.println(card.isOlek());
            if (card.isOlek()) {
                Gamescenes.setLabelText("You have already used this card!");
                return false;
            }
            if (!playerHasHeroes) {
                Gamescenes.setLabelText("You have no heroes to buff!");
                return false;
            }
            Gamescenes.setLabelText("Choose the hero you want to buff");
            for (Kaart hero : currentPlayer.getHeroMap().keySet()) {
                if (!hero.toString().equals("Empty")) {
                    String indeks = Animations.getPositionIndex(hero, currentPlayer);
                    Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);
                    heroNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            Buff.buffPlacement(currentPlayer, hero, card);
                            System.out.println(hero.toInfo());
                            Gamescenes.setLabelText("Buff placed on " + hero.getNimi() + "." + "Heroes stats now:" + " Attack: " + hero.getAttack() + " Defence: " + hero.getDefence());
                        }
                    });
                }
            }
        }
        else if (type.equals("Vulnerability")) {
            if (card.isOlek()) {
                Gamescenes.setLabelText("You have already used this card!");
                return false;
            }
            if (!opponentHasHeroes) {
                Gamescenes.setLabelText("The enemy doesn't have any heroes to make more vulnerable!");
                return false;
            }
            Gamescenes.setLabelText("Choose the hero you want to make more vulnerable");
            for (Kaart hero : currentOpponent.getHeroMap().keySet()) {
                if (!hero.toString().equals("Empty")) {
                    String indeks = Animations.getPositionIndex(hero, currentOpponent);
                    Node heroNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);
                    heroNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            Vulnerability.vulnerabilityPlacement(hero, card);
                            Gamescenes.setLabelText("Vulnerability placed on " + hero.getNimi() + "." + "Heroes stats now:" + " Attack: " + hero.getAttack() + " Defence: " + hero.getDefence());
                            card.setOlek(true);
                        }
                    });
                }
            }
        }
            return true;
        }
        }




