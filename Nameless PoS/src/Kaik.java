import java.util.ArrayList;

/**
 * Created by aleksander on 14-Mar-16.
 */
public class Kaik extends Manguvaljak {
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
}
