
public class EmptyCard extends Kaart {
    private boolean empty;

    public EmptyCard() {
        this.empty = true;
    }

    public String toCSV() {
        return "Empty";
    }
    public String toString() {
        return "Empty";
    }


}
