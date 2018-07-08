import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EventHandler {
  private TransitSystem system;
  private Scanner eventsBuffer;

  public static void main(String[] args) throws Exception {
      TransitSystem mainSystem = new TransitSystem();
      EventHandler mainHandler = new EventHandler(mainSystem);
      mainHandler.play();
  }

  EventHandler(TransitSystem system) throws Exception {
    this.system = system;
    this.eventsBuffer = new Scanner(new File("events.txt"));
  }

  void play() {
    String currentEvent = eventsBuffer.nextLine();
    String[] eventTokens = currentEvent.split("|");
    String action = eventTokens[0];
    switch (action) {
      case "entry": // we can also do it in TransitSystem ???
        system.getTripManager().recordTapIn(
                eventTokens[1], eventTokens[2], eventTokens[3], eventTokens[4], eventTokens[5]);
      case "exit":
        system.getTripManager().recordTapOut(
                eventTokens[1], eventTokens[2], eventTokens[3], eventTokens[4], eventTokens[5]);
      case "create":
        if (eventTokens[1].equals("account")) {
          system.createUserAccount(eventTokens[2], eventTokens[3]);
        } else if (eventTokens[1].equals("card")) {
          system.createCard();
        }
      case "activate":
        Card ca = system.findCard(Integer.parseInt(eventTokens[2]));
        CardHolder cha = (CardHolder) system.findUserAccount(Integer.parseInt(eventTokens[1]));
        cha.activateCard(ca);
      case "deactivate":
          Card cd = system.findCard(Integer.parseInt(eventTokens[2]));
          CardHolder chd = (CardHolder) system.findUserAccount(Integer.parseInt(eventTokens[1]));
          chd.activateCard(cd);
      case "link":
        Card currentCard = system.findCard(Integer.parseInt(eventTokens[2]));
        UserAccount currentHolder = system.findUserAccount(Integer.parseInt(eventTokens[1]));
        ((CardHolder) currentHolder).linkCard(currentCard);
      case "unlink":
        UserAccount ua = system.findUserAccount(Integer.parseInt(eventTokens[1]));
        Card cc = system.findCard(Integer.parseInt(eventTokens[2]));
        ((CardHolder) ua).deLinkCard(cc);
        /*
      case "load":
          Card c = system.findCard(Integer.parseInt(eventTokens[1]));
          c.addBalance(Double.parseDouble(eventTokens[2]));
          TransitSystem.addAllFares(eventTokens[3], Double.parseDouble(eventTokens[2]));
      case "view":
        if (eventTokens[1].equals("report")) {
          TransitSystem.printDailyReport(); // static??
        }else if (eventTokens[1].equals("info")) {
            UserAccount user = system.findUserAccount(Integer.parseInt(eventTokens[2]));
            user.viewInfo();
        }else if (eventTokens[1].equals("trips")) {
            Card card = system.findCard(Integer.parseInt(eventTokens[2]));
            card.viewRecentTrips();
        }else if (eventTokens[1].equals("balance")) {
            Card card = system.findCard(Integer.parseInt(eventTokens[2]));
            card.viewBalance();
        }else if (eventTokens[1].equals("cost")) {
            Card card = system.findCard(Integer.parseInt(eventTokens[2]));
            card.viewMonthlyCost();
        }
      case "change":
          UserAccount user = system.findUserAccount(Integer.parseInt(eventTokens[1]));
          user.changeName(eventTokens[2]);
      //case "remove":
      */
    }
  }
}
