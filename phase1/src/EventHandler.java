import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class EventHandler {
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
    }
  }
}
