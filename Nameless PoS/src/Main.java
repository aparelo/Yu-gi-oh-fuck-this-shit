import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
public class Main {
    public static void main() throws Exception {
        String player1Deck = "Nameless Pos\\Decks\\" + Manguvaljak.currentPlayerDeck + ".txt";
        File deckFile1 = new File(player1Deck);
        String player2Deck = "Nameless Pos\\Decks\\" + Manguvaljak.currentOpponentDeck + ".txt";
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
                Kaart tempCard = new Hero(kaartSplit[0],Integer.parseInt(kaartSplit[2]),Integer.parseInt(kaartSplit[3]),Integer.parseInt(kaartSplit[4]), Integer.parseInt(kaartSplit[5]),kaartSplit[6],kaartSplit[7]);
                deck1.add(tempCard);
            }
            if (kaartSplit[1].equals("Spell")) {
            	if (kaartSplit[4].equals("Buff")) {
            		Kaart tempCard = new Buff(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]),kaartSplit[2],Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
                    deck1.add(tempCard);
            	}
            	if (kaartSplit[4].equals("Vulnerability")) {
            		Kaart tempCard = new Vulnerability(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]),kaartSplit[2],Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
                    deck1.add(tempCard);
            	}
            	if (kaartSplit[4].equals("Purge")) {
            		Kaart tempCard = new Purge(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]), kaartSplit[2]);
                    deck1.add(tempCard);
            	}
            }
        }
        Collections.shuffle(deck1);
        Mangija mangija1 = new Mangija(GUI.player1Name, deck1); // Luuakse esimese mängija isend
        //Loo teise mangija deck
        while(sc2.hasNextLine()){
            String kaart = sc2.nextLine();
            String[] kaartSplit = kaart.split(",");
            if (kaartSplit[1].equals("Hero")) {
                Kaart tempCard = new Hero(kaartSplit[0],Integer.parseInt(kaartSplit[2]),Integer.parseInt(kaartSplit[3]),Integer.parseInt(kaartSplit[4]), Integer.parseInt(kaartSplit[5]),kaartSplit[6],kaartSplit[7]);
                deck2.add(tempCard);
            }
            if (kaartSplit[1].equals("Spell")) {
            	if (kaartSplit[4].equals("Buff")) {
            		Kaart tempCard = new Buff(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]),kaartSplit[2],Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
                    deck2.add(tempCard);
            	}
            	if (kaartSplit[4].equals("Vulnerability")) {
            		Kaart tempCard = new Vulnerability(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]),kaartSplit[2],Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
                    deck2.add(tempCard);
            	}
            	if (kaartSplit[4].equals("Purge")) {
            		Kaart tempCard = new Purge(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]), kaartSplit[2]);
                    deck2.add(tempCard);
            	}
            }
        }
        //Loo teise mangija isend
        Collections.shuffle(deck2);
        Mangija mangija2 = new Mangija(GUI.player2Name, deck2);
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
        System.out.println("Starting game");
        System.out.println(Manguvaljak.currentPlayer.getMangijaDeck().size());
        System.out.println(Manguvaljak.currentOpponent.getMangijaDeck().size());
        //Alusta manguga
       /*mainloop: while (Manguvaljak.currentOpponent.getElud() > 0 && Manguvaljak.currentOpponent.getMangijaDeck().size() > 0 && Manguvaljak.currentPlayer.getElud() > 0 && Manguvaljak.currentPlayer.getMangijaDeck().size() > 0) {
        	//Tomba kaardid deckist
            int kaartideArv = Manguvaljak.currentPlayer.getMangijaKasi().size();
            if(kaartideArv < 5) {
                int uusiKaarte = 5 - kaartideArv;
                ArrayList<Kaart> tempKasi = Manguvaljak.currentPlayer.getMangijaKasi();
                ArrayList<Kaart> tempDeck = Manguvaljak.currentPlayer.getMangijaDeck();
                for (int i=0;i<uusiKaarte;i++) {
                    Manguvaljak.kaartKatte(Manguvaljak.currentPlayer);
                    if (Manguvaljak.currentPlayer.getMangijaDeck().size() == 0){
                    	gameOverError = "DeckToZero";
                        break mainloop;
                    }
                }
                Manguvaljak.currentPlayer.setMangijaDeck(tempDeck);
                Manguvaljak.currentPlayer.setMangijaKasi(tempKasi);
            }
            Kaik.uusKaik();
            int attackCount = 0; // Nulli attackCount
            int useSpellCount = 0; // Nulli useSpellCount
           	//while(true) { //Main loop
            //    System.out.println("Enter the number of action you wish to do:\n 1) Place a hero on the battlefield\n 2) Use a spell \n 3) Place a spell on the battlefield\n 4) Attack! \n 5) End your turn\n");
                /*int tegevus = Integer.parseInt(scan.next());
                if (tegevus == 1) {
                    ArrayList<Kaart> tempHerod = new ArrayList<>();
                    for(Kaart kaart: Manguvaljak.currentPlayer.getMangijaKasi()){
                    	if (kaart.getTyyp().equals("Hero")) {
                    		tempHerod.add(kaart);
                    }
                    }
                    if (tempHerod.size() == 0) {
                    	System.out.println("You don't have any heroes to add to the battlefield!\n");
                    	continue;
                    }
                    if (Manguvaljak.currentPlayer.getHeroesOnField() == 5) {
                    	System.out.println("You cannot add any more heroes to the battlefield!\n");
                    	continue;
                    }
                    System.out.println("Choose the hero you want to place on the battlefield:\n");
                    int indeks = 1;
                    for (Kaart kaart : tempHerod) {
                		System.out.println(indeks +") "+  kaart +"\n");
                		indeks++;
                    }
                    int kaartToLaud = Integer.parseInt(scan.next())-1;
                    Kaart heroChoice = tempHerod.get(kaartToLaud);
                    boolean onnestus = Manguvaljak.kaartLauale(heroChoice,Manguvaljak.currentPlayer);
                    if(onnestus){
                        System.out.println(heroChoice.getNimi() + " added to the battlefield!\n");
                        System.out.println("Battlefield: ");
                        for(Kaart kaart: Manguvaljak.currentPlayer.getMangijaLaud()) {
                            System.out.println(kaart);
                        }
                    }
                } else if (tegevus == 2) {
                    boolean useSpellState = Manguvaljak.useSpell(Manguvaljak.currentPlayer, Manguvaljak.currentOpponent);
                    if (useSpellState) {
                    	useSpellCount++;
                    }
                    else {
                    	continue;
                    }
                    if (useSpellCount == 5 || Manguvaljak.currentPlayer.getSpellsOnField() == 5) {
                    	System.out.println("You cannot add any more spells to the battlefield!\n");
                    }
                } else if (tegevus == 3) {
                    if (Manguvaljak.currentPlayer.getSpellsOnField() == 5) {
                    	System.out.println("You cannot add any more spells to the battlefield!\n");
                    	continue;
                    }
                   boolean placeSpellState = Manguvaljak.placeSpell(Manguvaljak.currentPlayer);

                   if(!placeSpellState){
                       System.out.println("You don't have any spells to place on the battlefield!\n");
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
                        System.out.println("You can only attack once during a turn.\n");
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
        
        
        }*/
    }}
