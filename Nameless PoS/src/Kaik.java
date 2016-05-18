import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import jdk.nashorn.internal.runtime.arrays.AnyElements;
import org.omg.CORBA.Any;

import java.util.ArrayList;
import java.util.HashMap;


public class Kaik extends Manguvaljak {
    public static int turnCount = 0;

    public static boolean needCards;

    public static void uusKaik() throws InterruptedException {

        currentOpponent.setAttackCount(0);
        currentPlayer.setMana(currentPlayer.getMana() + 1);
        if(currentPlayer.getLocation() == 1) { //1 = oMana
            Gamescenes.getoMana().setText("Manapoints: " + currentPlayer.getMana());
        }
        else {
            Gamescenes.getMana().setText("Manapoints: " + currentPlayer.getMana());
        }

        ArrayList<Integer> tempBuffBuffers = new ArrayList<>();
        ArrayList<Integer> tempVulnerabilityBuffers = new ArrayList<>();
        ArrayList<Kaart> tempHeroes = new ArrayList<>();
        ArrayList<Kaart> tempSpells = new ArrayList<>();
        for (Kaart sinuValjak : currentPlayer.getMangijaLaud()) {
            if (sinuValjak.getTyyp().equals("Hero")) {
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
                } else if (sinuValjak.getVulnerabilities().size() != 0) {
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
                for (int k = tempBuffBuffers.size() - 1; k >= 0; k--) {
                    Manguvaljak.kaartSurnuAeda(sinuValjak.getBuffers().get(k), currentPlayer);
                    sinuValjak.getBuffers().remove(k);
                }
                for (int l = tempVulnerabilityBuffers.size() - 1; l >= 0; l--) {
                    Manguvaljak.kaartSurnuAeda(sinuValjak.getVulnerabilities().get(l), currentOpponent);
                    sinuValjak.getVulnerabilities().remove(l);
                }
                tempBuffBuffers = new ArrayList<>();
                tempVulnerabilityBuffers = new ArrayList<>();
            }

        }
        // Siin saab l�bi
        for (Kaart vastaseValjak : currentOpponent.getMangijaLaud()) {
            vastaseValjak.setMoveCount(vastaseValjak.getMoveCount() + 1);
        }

        turnCount++;
        Manguvaljak.currentPlayer.setAttackCount(0);
        Animations.makeCardsUnclickable(Manguvaljak.currentPlayer);
        Animations.makeCardsAttackable(Manguvaljak.currentPlayer);
        Animations.makeCardsClickable(Manguvaljak.currentOpponent);
        handShuffle();

        Animations.flipDown(Manguvaljak.currentPlayer);
        Animations.flipUp(Manguvaljak.currentOpponent);


        Mangija tempChanger = Manguvaljak.currentPlayer;
        Manguvaljak.currentPlayer = Manguvaljak.currentOpponent;
        Manguvaljak.currentOpponent = tempChanger;

    }

    public static void endTurn() throws InterruptedException {
        turnCount++;
        Manguvaljak.currentPlayer.setAttackCount(0);
        Animations.makeCardsUnclickable(Manguvaljak.currentPlayer);
        Animations.makeCardsAttackable(Manguvaljak.currentPlayer);
        Animations.makeCardsClickable(Manguvaljak.currentOpponent);
        handShuffle();

        Animations.flipDown(Manguvaljak.currentPlayer);
        Animations.flipUp(Manguvaljak.currentOpponent);


        Mangija tempChanger = Manguvaljak.currentPlayer;
        Manguvaljak.currentPlayer = Manguvaljak.currentOpponent;
        Manguvaljak.currentOpponent = tempChanger;


    }

    public static void handShuffle() throws InterruptedException {

        int kaartideArv = Manguvaljak.currentPlayer.getMangijaKasi().size();
        if (kaartideArv < 5) {
            int uusiKaarte = 5 - kaartideArv;
            ArrayList<Kaart> tempKasi = Manguvaljak.currentPlayer.getMangijaKasi();
            ArrayList<Kaart> tempDeck = Manguvaljak.currentPlayer.getMangijaDeck();
            for (int i = 0; i < uusiKaarte; i++) {
                Manguvaljak.kaartKatte(Manguvaljak.currentPlayer);
                if (Manguvaljak.currentPlayer.getMangijaDeck().size() == 0) {
                    //gameOverError = "DeckToZero";
                }
            }
            Manguvaljak.currentPlayer.setMangijaDeck(tempDeck);
            Manguvaljak.currentPlayer.setMangijaKasi(tempKasi);
        }

        int kaartideArv2 = Manguvaljak.currentOpponent.getMangijaKasi().size();
        if (kaartideArv2 < 5) {
            int uusiKaarte2 = 5 - kaartideArv2;
            ArrayList<Kaart> tempKasi2 = Manguvaljak.currentOpponent.getMangijaKasi();
            ArrayList<Kaart> tempDeck2 = Manguvaljak.currentOpponent.getMangijaDeck();
            for (int i = 0; i < uusiKaarte2; i++) {
                Manguvaljak.kaartKatte(Manguvaljak.currentOpponent);
                if (Manguvaljak.currentOpponent.getMangijaDeck().size() == 0) {
                    //gameOverError = "DeckToZero";
                }
            }
            Manguvaljak.currentOpponent.setMangijaDeck(tempDeck2);
            Manguvaljak.currentOpponent.setMangijaKasi(tempKasi2);
        }

        if (kaartideArv < 5 || kaartideArv2 < 5) {
            needCards = true;
        } else {
            needCards = false;
        }
    }
}

