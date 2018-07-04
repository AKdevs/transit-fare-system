import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EventHandler {
<<<<<<< HEAD
    private TransitSystem system;
    private Scanner eventsBuffer;
    EventHandler(TransitSystem system) throws Exception {
        this.system = system;
        this.eventsBuffer = new Scanner(new File("events.txt"));
    }

    void play() {
        String currentEvent = eventsBuffer.nextLine();
        String[] eventTokens = currentEvent.split("|");
        String action = eventTokens[0];
        switch (action) {
            case "entry":
                TripSegment ts = new TripSegment(eventTokens[1], eventTokens[2], eventTokens[3], eventTokens[4]);
            case "exit":

            case "create":
                if (eventTokens[1].equals("account")) {
                    //system.createAccount(eventTokens[2], eventTokens[3]);
                    UserAccount newAccount = new UserAccount(eventTokens[2], eventTokens[3]);
                    system.addUserAccount(newAccount);
                } else if (eventTokens[1].equals("card")) {
                    Card newCard = new Card();
                    system.addCard(newCard);
=======
  private TransitSystem system;
  private Scanner eventsBuffer;
>>>>>>> 259cd65b5a20f4331f196f9136e1777e35408348

  EventHandler(TransitSystem system) throws Exception {
    this.system = system;
    this.eventsBuffer = new Scanner(new File("events.txt"));
  }

  void play() {
    String currentEvent = eventsBuffer.nextLine();
    String[] eventTokens = currentEvent.split("|");
    String action = eventTokens[0];
    switch (action) {
      case "entry":
      case "exit":
      case "create":
        if (eventTokens[1].equals("account")) {
          system.createUserAccount(eventTokens[2], eventTokens[3]);
          // UserAccount newAccount = new UserAccount(eventTokens[2], eventTokens[3]);
          // system.addUserAccount(newAccount);
        } else if (eventTokens[1].equals("card")) {
          system.createCard();
          // Card newCard = new Card();
          // system.addCard(newCard);
        }
      case "activate":
        system.findCard(Integer.parseInt(eventTokens[1])).activate();
      case "deactivate":
        system.findCard(Integer.parseInt(eventTokens[1])).deactivate();
      case "link":
        Card currentCard = system.findCard(Integer.parseInt(eventTokens[2]));
        UserAccount currentHolder = system.findUserAccount(Integer.parseInt(eventTokens[1]));
        ((CardHolder) currentHolder).linkCard(currentCard);
      case "unlink":
      case "load":

    }
  }
}
