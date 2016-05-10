import java.util.ArrayList;


public class Kaik extends Manguvaljak {
    public static void uusKaik() throws InterruptedException {
        currentPlayer.setMana(currentPlayer.getMana() + 1);

        ArrayList<Integer> tempBuffBuffers = new ArrayList<>();
        ArrayList<Integer> tempVulnerabilityBuffers = new ArrayList<>();
        ArrayList<Kaart> tempHeroes = new ArrayList<>();
        ArrayList<Kaart> tempSpells = new ArrayList<>();
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
                }
                else if (sinuValjak.getVulnerabilities().size() != 0) {
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
                for (int k=tempBuffBuffers.size()-1;k>=0;k--) {
                    kaartSurnuAeda(sinuValjak.getBuffers().get(k),currentPlayer);
                    sinuValjak.getBuffers().remove(k);
                }
                for (int l=tempVulnerabilityBuffers.size()-1; l>=0;l--) {
                    kaartSurnuAeda(sinuValjak.getVulnerabilities().get(l),currentOpponent);
                    sinuValjak.getVulnerabilities().remove(l);
                }
                System.out.println(sinuValjak);
                tempBuffBuffers = new ArrayList<>();
                tempVulnerabilityBuffers = new ArrayList<>();
            }

        }
        // Siin saab l�bi
        for(Kaart vastaseValjak: currentOpponent.getMangijaLaud()) {
            vastaseValjak.setMoveCount(vastaseValjak.getMoveCount()+1);
        }
    }
}
