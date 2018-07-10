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
    for (int i = 0; i < 8; i++) {
      mainHandler.play();
    }
    //System.out.println(mainSystem.getCards());
    //System.out.println(mainSystem.getUserAccounts());
  }

  EventHandler(TransitSystem system) throws Exception {
    this.system = system;
    this.eventsBuffer = new Scanner(new File("phase1/src/events.txt"));
  }

  void play() {
    String currentEvent = eventsBuffer.nextLine();
    String[] eventTokens = currentEvent.split(" \\| ");
    String action = eventTokens[0].trim();
    switch (action) {
      case "entry": // we can also do it in TransitSystem ???
        system
            .getTripManager()
            .recordTapIn(
                eventTokens[1], eventTokens[2], eventTokens[3], eventTokens[4], eventTokens[5]);
      case "exit":
        system
            .getTripManager()
            .recordTapOut(
                eventTokens[1], eventTokens[2], eventTokens[3], eventTokens[4], eventTokens[5]);
        break;
      case "create":
        if ((eventTokens[1].equals("account"))&&(eventTokens[2].equals("CardHolder"))) {
          system.createCardHolderAccount(eventTokens[3], eventTokens[4]);
        }
        else if((eventTokens[1].equals("account"))&&(eventTokens[2].equals("AdminUser"))) {
          system.createAdminAccount(eventTokens[3], eventTokens[4]);
        }
        else if (eventTokens[1].equals("card")) {
          system.createCard();
        }
        break;

      case "activate":
        Card ca = system.findCard(Integer.parseInt(eventTokens[2]));
        CardHolder cha = (CardHolder) system.findUserAccount(Integer.parseInt(eventTokens[1]));
        cha.activateCard(ca);
        break;

      case "deactivate":
        Card cd = system.findCard(Integer.parseInt(eventTokens[2]));
        CardHolder chd = (CardHolder) system.findUserAccount(Integer.parseInt(eventTokens[1]));
        chd.deactivateCard(cd);
        break;

      case "link":
        Card currentCard = system.findCard(Integer.parseInt(eventTokens[2]));
        UserAccount currentHolder = system.findUserAccount(Integer.parseInt(eventTokens[1]));
        ((CardHolder) currentHolder).linkCard(currentCard);
        break;

      case "unlink":
        UserAccount ua = system.findUserAccount(Integer.parseInt(eventTokens[1]));
        Card cc = system.findCard(Integer.parseInt(eventTokens[2]));
        ((CardHolder) ua).unlinkCard(cc);
        break;

      case "load":
        UserAccount holder = system.findUserAccount(Integer.parseInt(eventTokens[1]));
        Card travelCard = system.findCard(Integer.parseInt(eventTokens[2]));
        travelCard.addBalance(Double.parseDouble(eventTokens[3]));
        break;

      case "view":
        if (eventTokens[1].equals("report")) {
          String date = eventTokens[2];
          AdminUser.getDailyReport(date); // static??
        } else if (eventTokens[1].equals("info")) {
          UserAccount user = system.findUserAccount(Integer.parseInt(eventTokens[2]));
          user.viewInfo();
        } else if (eventTokens[1].equals("trips")) {
          UserAccount user = system.findUserAccount(Integer.parseInt(eventTokens[2]));
          Card card = system.findCard(Integer.parseInt(eventTokens[3]));
          card.viewMostRecentTrips();
        } else if (eventTokens[1].equals("balance")) {
          UserAccount user = system.findUserAccount(Integer.parseInt(eventTokens[2]));
          Card card = system.findCard(Integer.parseInt(eventTokens[3]));
          card.viewBalance();
        } else if (eventTokens[1].equals("cost")) {
          Card card = system.findCard(Integer.parseInt(eventTokens[2]));
          card.viewMonthlyCost();
        }
        break;

      case "change":
        UserAccount user = system.findUserAccount(Integer.parseInt(eventTokens[1]));
        user.changeName(eventTokens[2]);
        // case "remove": remove card , remove userAccount
        // case change points name in TransitLine
        //case add point to transitline
        //case add new transitLine
        //Do we need a static SystemManager???????????


    }
  }
}
