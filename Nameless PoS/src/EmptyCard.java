import javafx.scene.image.Image;

public class EmptyCard extends Kaart {
    private boolean empty;

    public EmptyCard() {
        this.empty = true;
    }

    public Image getFrontPicture() {
        return null;
    }

    public String toCSV() {
        return "Empty";
    }
    public String toString() {
        return "Empty";
    }
    public String toInfo() {
        return "Empty";
    }


}
