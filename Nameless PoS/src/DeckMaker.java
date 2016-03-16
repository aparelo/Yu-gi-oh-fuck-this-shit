/*
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeckMaker {
    private static File cardBank = new File("C:\\Users\\Siim-Sander\\Documents\\GitHub\\Nameless-Pos\\Nameless_PoS_GUI\\src\\application\\Bank.txt"); //Siia peab tegeliku Card Banki nime panema
    private static ArrayList<String> cardBankList = new ArrayList<>();

    public static void printCardBank() throws Exception {
        Scanner sc = new Scanner(cardBank, "UTF-8");
    	if(cardBank.exists()){
            int indeks = 1;
            while(sc.hasNextLine()) {
                String kaart = sc.nextLine();
                cardBankList.add(kaart);
                String[] kaartSplit = kaart.split(",");
                if (kaartSplit[1].equals("Hero")) {
                    Kaart tempCard = new Kaart(kaartSplit[0],Integer.parseInt(kaartSplit[2]),Integer.parseInt(kaartSplit[3]),Integer.parseInt(kaartSplit[4]),kaartSplit[5],kaartSplit[6]);
                    System.out.println(indeks + tempCard.toString());
                    indeks++;
                } else if (kaartSplit[1].equals("Spell")) {
                    Kaart tempCard = new Kaart(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]),kaartSplit[2],Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
                    System.out.println(indeks + tempCard.toString());
                    indeks++;
                }
            }
        }
    }
    public static void makeDeck() throws Exception {
        System.out.println("Welcome to deck maker, here are all the cards that can be added to a deck.");
        if(cardBank.exists()){
            Scanner sc = new Scanner(cardBank, "UTF-8");
            int indeks = 1;
            while(sc.hasNextLine()) {
                String kaart = sc.nextLine();
                cardBankList.add(kaart);
                String[] kaartSplit = kaart.split(",");
                if (kaartSplit[1].equals("Hero")) {
                    Kaart tempCard = new Kaart(kaartSplit[0],Integer.parseInt(kaartSplit[2]),Integer.parseInt(kaartSplit[3]),Integer.parseInt(kaartSplit[4]),kaartSplit[5],kaartSplit[6]);
                    System.out.println(indeks + tempCard.toString());
                    indeks++;
                } else if (kaartSplit[1].equals("Spell")) {
                    Kaart tempCard = new Kaart(kaartSplit[0],kaartSplit[4],Integer.parseInt(kaartSplit[3]),kaartSplit[2],Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
                    System.out.println(indeks + tempCard.toString());
                    indeks++;
                }
            }
        }
        System.out.println("Please enter the numbers of the cards you wish to add to your deck, separated by commas. \n " +
                "Keep in mind that a deck has to have a minimum of 40 cards, there is no maximum."
        );
        Scanner scan = new Scanner(System.in);
        String selectedCards = scan.next();
        String[] selectedList = selectedCards.split(",");
        if(selectedList.length>6) {
            List<String> tempDeck = new ArrayList<>();
            for(String card: selectedList) {
                tempDeck.add(cardBankList.get(Integer.parseInt(card)));
            }

            System.out.println("Choose a name for your deck.");
            String filename = scan.next();
            filename = new File("Nameless Pos\\src\\" + filename +".csv").getAbsolutePath();
            File fail2 = new File(filename);
            PrintWriter pw = new PrintWriter(fail2, "UTF-8");
            for(String toDeck: cardBankList) {
                pw.println(toDeck);
            }
            pw.close();
        }
        else {
            System.out.println("Not enough cards in the deck, try again.");
        }


    }
}
*/
