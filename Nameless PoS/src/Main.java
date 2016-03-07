import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Tere tulemast kaardimangu Not quite Yu-Gi-Oh! Alustamiseks sisestage oma nimi ja Deckid");
        Scanner scan = new Scanner(System.in);
        //Kusi kasutajalt deckide asukohti ja mängijate nimi.
        System.out.println("Mängija 1 nimi:");
        String mangija1nimi = scan.next();
        System.out.println("Player 1 deck faili asukoht: ");
        String player1Deck = scan.next();
        System.out.println("Mängija 2 nimi:");
        String mangija2nimi = scan.next();
        System.out.println("Player 2 deck faili asukoht: ");
        String player2Deck = scan.next();
        File deckFile1 = new File(player1Deck);
        File deckFile2 = new File(player2Deck);
        Scanner sc1 = new Scanner(deckFile1, "UTF-8");
        Scanner sc2 = new Scanner(deckFile2, "UTF-8");
        ArrayList<Kaart> deck1 = new ArrayList<>();
        ArrayList<Kaart> deck2 = new ArrayList<>();
        //Loo esimese mangija deck
        while(sc1.hasNextLine()) {
            String kaart = sc1.nextLine();
            String[] kaartSplit = kaart.split(",");
            if (kaartSplit[1].equals("Hero")) {
                Kaart tempCard = new Kaart(kaartSplit[0],Integer.parseInt(kaartSplit[2]),Integer.parseInt(kaartSplit[3]),Integer.parseInt(kaartSplit[4]),kaartSplit[5],kaartSplit[6]);
                deck1.add(tempCard);
            } else if (kaartSplit[1].equals("Spell")) {
                Kaart tempCard = new Kaart(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]),kaartSplit[2],Integer.parseInt(kaartSplit[5]));
                deck1.add(tempCard);
            }
        }
        Collections.shuffle(deck1);
        Mangija mangija1 = new Mangija(mangija1nimi, deck1); // Luuakse esimese mängija isend
        //Loo teise mangija deck
        while(sc2.hasNextLine()){
            String kaart = sc2.nextLine();
            String[] kaartSplit = kaart.split(",");
            if (kaartSplit[1].equals("Hero")) {
                Kaart tempCard = new Kaart(kaartSplit[0],Integer.parseInt(kaartSplit[2]),Integer.parseInt(kaartSplit[3]),Integer.parseInt(kaartSplit[4]),kaartSplit[5],kaartSplit[6]);
                deck2.add(tempCard);
            } else {
                Kaart tempCard = new Kaart(kaartSplit[0],kaartSplit[2],Integer.parseInt(kaartSplit[3]),kaartSplit[4],Integer.parseInt(kaartSplit[5]));
                deck2.add(tempCard);
            }
        }
        //Loo teise mangija isend
        Collections.shuffle(deck2);
        Mangija mangija2 = new Mangija(mangija2nimi, deck2);
        Random alustaja = new Random();
        int number = alustaja.nextInt(2);
        if(number == 1) {
            Manguvaljak.hetkeMangija = mangija1;
            Manguvaljak.hetkeVastane = mangija2;
        }
        else {
            Manguvaljak.hetkeMangija = mangija2;
            Manguvaljak.hetkeVastane = mangija1;
        }
        String gameOverError = "";
        //Alusta manguga
       mainloop: while (Manguvaljak.hetkeVastane.getElud() > 0 && Manguvaljak.hetkeVastane.getMangijaDeck().size() > 0 && Manguvaljak.hetkeMangija.getElud() > 0 && Manguvaljak.hetkeMangija.getMangijaDeck().size() > 0) {
        	//Tomba kaardid deckist
            int kaartideArv = Manguvaljak.hetkeMangija.getMangijaKasi().size();
            if(kaartideArv < 5) {
                int uusiKaarte = 5 - kaartideArv;
                ArrayList<Kaart> tempKasi = Manguvaljak.hetkeMangija.getMangijaKasi();
                ArrayList<Kaart> tempDeck = Manguvaljak.hetkeMangija.getMangijaDeck();
                for(int i=0;i<uusiKaarte;i++) {
                    Manguvaljak.kaartKatte(Manguvaljak.hetkeMangija,Manguvaljak.hetkeMangija.getMangijaDeck());
                    if(Manguvaljak.hetkeMangija.getMangijaDeck().size() == 0){
                    	gameOverError = "DeckToZero";
                        break mainloop;
                    }
                }
                Manguvaljak.hetkeMangija.setMangijaDeck(tempDeck);
                Manguvaljak.hetkeMangija.setMangijaKasi(tempKasi);
            }
            Manguvaljak.uusKaik();
            int attackCount = 0; //Nulli attackCount
            while(true) {
            	innerloop:
                System.out.println("Sisesta tegevuse number, mida soovid teha:\n 1) Aseta hero valjakule\n 2) Kasuta spelli \n 3) Pane spell lauale \n 4) Attack! \n 5) Lõpeta käik\n");
                int tegevus = Integer.parseInt(scan.next());
                if (tegevus == 1) {
                    System.out.println("Vali kaart, mida soovid lauale panna: ");
                    int indeks = 1;
                    for(Kaart kaart: Manguvaljak.hetkeMangija.getMangijaKasi()){
                        System.out.println(indeks +") "+  kaart +"\n");
                        indeks++;
                    }
                    int kaartToLaud = Integer.parseInt(scan.next())-1;
                    boolean onnestus = Manguvaljak.kaartLauale(Manguvaljak.hetkeMangija.getMangijaKasi().get(kaartToLaud),Manguvaljak.hetkeMangija);
                    if(onnestus){
                        System.out.println("Kaart asetatud.");
                        System.out.println("Manguvali:");
                        for(Kaart kaart: Manguvaljak.hetkeMangija.getMangijaLaud()) {
                            System.out.println(kaart);
                        }
                    }
                    else {
                        System.out.println("Kaardi asetamine ebaonnestus!");
                    }
                } else if (tegevus == 2) {
                    System.out.println("Not yet implemented");
                } else if (tegevus == 3) {
                    System.out.println("Not yet implemented");
                } else if (tegevus == 4) {
                    if(attackCount == 0) {
                        boolean attackState = Manguvaljak.attack(Manguvaljak.hetkeMangija, Manguvaljak.hetkeVastane);
                        if (attackState) {
                            attackCount++;
                        }
                    }
                    else {
                        System.out.println("You can only attack once during a turn.");
                    }
                } else {
                    break;
                }
            }
            Mangija tempChanger = Manguvaljak.hetkeMangija;
            Manguvaljak.hetkeMangija = Manguvaljak.hetkeVastane;
            Manguvaljak.hetkeVastane = tempChanger;
        }
        if (gameOverError.equals("DeckToZero")) {
        	System.out.println("Congratulations, " + Manguvaljak.hetkeVastane.getNimi() + "!" + "Boohoo, " + Manguvaljak.hetkeMangija.getNimi() + " you suck.");
        }
        
        
        }
    }
