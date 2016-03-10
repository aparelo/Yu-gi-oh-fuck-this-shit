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
        String relativePath = new File("Nameless Pos\\src\\" + player1Deck).getAbsolutePath();
        String relativePath2 = new File("Nameless Pos\\src\\" + player2Deck).getAbsolutePath();
        File deckFile1 = new File(relativePath);
        File deckFile2 = new File(relativePath2);
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
                Kaart tempCard = new Kaart(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]),kaartSplit[2],Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
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
                Kaart tempCard = new Kaart(kaartSplit[0],kaartSplit[2],Integer.parseInt(kaartSplit[3]),kaartSplit[4],Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
                deck2.add(tempCard);
            }
        }
        //Loo teise mangija isend
        Collections.shuffle(deck2);
        Mangija mangija2 = new Mangija(mangija2nimi, deck2);
        Random alustaja = new Random();
        int number = alustaja.nextInt(2);
        if(number == 1) {
            Manguvaljak.currentPlayer = mangija1;
            Manguvaljak.currentOpponent = mangija2;
        }
        else {
            Manguvaljak.currentPlayer = mangija2;
            Manguvaljak.currentOpponent = mangija1;
        }
        String gameOverError = "";
        //Alusta manguga
       mainloop: while (Manguvaljak.currentOpponent.getElud() > 0 && Manguvaljak.currentOpponent.getMangijaDeck().size() > 0 && Manguvaljak.currentPlayer.getElud() > 0 && Manguvaljak.currentPlayer.getMangijaDeck().size() > 0) {
        	//Tomba kaardid deckist
            int kaartideArv = Manguvaljak.currentPlayer.getMangijaKasi().size();
            if(kaartideArv < 5) {
                int uusiKaarte = 5 - kaartideArv;
                ArrayList<Kaart> tempKasi = Manguvaljak.currentPlayer.getMangijaKasi();
                ArrayList<Kaart> tempDeck = Manguvaljak.currentPlayer.getMangijaDeck();
                for(int i=0;i<uusiKaarte;i++) {
                    Manguvaljak.kaartKatte(Manguvaljak.currentPlayer);
                    if(Manguvaljak.currentPlayer.getMangijaDeck().size() == 0){
                    	gameOverError = "DeckToZero";
                        break mainloop;
                    }
                }
                Manguvaljak.currentPlayer.setMangijaDeck(tempDeck);
                Manguvaljak.currentPlayer.setMangijaKasi(tempKasi);
            }
            Manguvaljak.uusKaik();
            int attackCount = 0; // Nulli attackCount
            int useSpellCount = 0; // Nulli useSpellCount
            int placeSpellCount = 0;//Nulli placeSpellCount
           	innerloop: while(true) {
                System.out.println("Sisesta tegevuse number, mida soovid teha:\n 1) Aseta hero valjakule\n 2) Kasuta spelli \n 3) Pane spell lauale \n 4) Attack! \n 5) Lõpeta käik\n");
                int tegevus = Integer.parseInt(scan.next());
                if (tegevus == 1) {
                    System.out.println("Vali kaart, mida soovid lauale panna: ");
                    int indeks = 1;
                    for(Kaart kaart: Manguvaljak.currentPlayer.getMangijaKasi()){
                        System.out.println(indeks +") "+  kaart +"\n");
                        indeks++;
                    }
                    int kaartToLaud = Integer.parseInt(scan.next())-1;
                    boolean onnestus = Manguvaljak.kaartLauale(Manguvaljak.currentPlayer.getMangijaKasi().get(kaartToLaud),Manguvaljak.currentPlayer);
                    if(onnestus){
                        System.out.println("Kaart asetatud.");
                        System.out.println("Manguvali:");
                        for(Kaart kaart: Manguvaljak.currentPlayer.getMangijaLaud()) {
                            System.out.println(kaart);
                        }
                    }
                    else {
                        System.out.println("Kaardi asetamine ebaonnestus!");
                    }
                } else if (tegevus == 2) {
                    boolean useSpellState = Manguvaljak.useSpell(Manguvaljak.currentPlayer, Manguvaljak.currentOpponent);
                    if (useSpellState) {
                    	useSpellCount++;
                    }
                    if (useSpellState == false) {
                    	continue innerloop;
                    }
                    if (useSpellCount == 5 || Manguvaljak.currentPlayer.getSpellsOnField() == 5) {
                    	System.out.println("You cannot add any more spells to the battlefield!");
                    	continue innerloop;                    	
                    }
                } else if (tegevus == 3) {
                   boolean placeSpellState = Manguvaljak.placeSpell(Manguvaljak.currentPlayer);
                   if (placeSpellState) {
                	   placeSpellCount++;
                   }
                   if (placeSpellState == false) {
                	   continue innerloop;
                   }
                } else if (tegevus == 4) {
                    if(attackCount == 0) {
                        boolean attackState = Manguvaljak.attack(Manguvaljak.currentPlayer, Manguvaljak.currentOpponent);
                        if (attackState) {
                            attackCount++;
                            if (Manguvaljak.currentOpponent.getElud() <= 0) {
                            	gameOverError = "LivesZero";
                            	break mainloop;
                            }
                        }
                    }
                    else {
                        System.out.println("You can only attack once during a turn.");
                    }
                } else {
                    break;
                }
            }
            Mangija tempChanger = Manguvaljak.currentPlayer;
            Manguvaljak.currentPlayer = Manguvaljak.currentOpponent;
            Manguvaljak.currentOpponent = tempChanger;
        }
        if (gameOverError.equals("DeckToZero")) {
        	System.out.println("Congratulations, " + Manguvaljak.currentOpponent.getNimi() + "!" + " Boohoo, " + Manguvaljak.currentPlayer.getNimi() + " you suck.");
        }
        else if (gameOverError.equals("LivesZero")) {
        	System.out.println("Congratulations, " + Manguvaljak.currentPlayer.getNimi() + "!" + " Boohoo, " + Manguvaljak.currentOpponent.getNimi() + " you suck.");
        }
        
        
        }
    }
