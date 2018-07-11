import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EventHandler {
  private TransitSystem system;
  private TripManager tripManager;
  private TransitManager transitManager;
  private AccountManager accountManager;
  private Scanner eventsBuffer;

  public static void main(String[] args) throws Exception {
    TransitSystem mainSystem = new TransitSystem();

    EventHandler mainHandler = new EventHandler(mainSystem);
    while (mainHandler.eventsBuffer.hasNextLine()) {
      mainHandler.play();
    }
  }

  EventHandler(TransitSystem system) throws Exception {
    this.system = system;
    this.eventsBuffer = new Scanner(new File("phase1/events.txt"));
    this.tripManager = new TripManager();
    this.transitManager = new TransitManager();
    this.accountManager = new AccountManager();
  }

  void play() {
    String currentEvent = eventsBuffer.nextLine();
    String[] eventTokens = currentEvent.split(" \\| ");
    String action = eventTokens[0].trim();
    if (system.getOperatingStatus().equals("on")) {
      switch (action) {
        case "entry":
          tripManager.recordTapIn(
              eventTokens[1], eventTokens[2], eventTokens[3], eventTokens[4], eventTokens[5]);
          break;
        case "exit":
          tripManager.recordTapOut(
              eventTokens[1], eventTokens[2], eventTokens[3], eventTokens[4], eventTokens[5]);
          break;
        case "create":
          if ((eventTokens[1].equals("account")) && (eventTokens[2].equals("CardHolder"))) {
            accountManager.createCardHolderAccount(eventTokens[3], eventTokens[4]);
          } else if ((eventTokens[1].equals("account")) && (eventTokens[2].equals("AdminUser"))) {
            accountManager.createAdminAccount(eventTokens[3], eventTokens[4]);
          } else if (eventTokens[1].equals("card")) {
            system.createCard();
          }
          break;

        case "activate":
          Card ca = system.findCard(eventTokens[2]);
          CardHolder cha = (CardHolder) system.findUserAccount(eventTokens[1]);
          cha.activateCard(ca);
          break;

        case "deactivate":
          Card cd = system.findCard(eventTokens[2]);
          CardHolder chd = (CardHolder) system.findUserAccount(eventTokens[1]);
          chd.deactivateCard(cd);
          break;

        case "link":
          Card currentCard = system.findCard(eventTokens[2]);
          UserAccount currentHolder = system.findUserAccount(eventTokens[1]);
          ((CardHolder) currentHolder).linkCard(currentCard);
          break;

        case "unlink":
          UserAccount ua = system.findUserAccount(eventTokens[1]);
          Card cc = system.findCard(eventTokens[2]);
          ((CardHolder) ua).unlinkCard(cc);
          break;

        case "load":
          String cardNumber = eventTokens[2];
          Card travelCard = system.findCard(cardNumber);
          if (travelCard != null) {
            travelCard.addBalance(Double.parseDouble(eventTokens[3]));
          } else {
            System.out.println("Request failed. This card does not exist");
          }
          break;

        case "view":
          if (eventTokens[1].equals("report")) {
            String date = eventTokens[2];
            AdminUser.getDailyReport(date);
            break;
          }
          String accountNumber = eventTokens[2];
          if (!userExists(accountNumber)) {
            System.out.println(
                "Request unsuccessful. Account " + accountNumber + " does not exist.");
            break;
          }

          if (eventTokens[1].equals("info")) {
            UserAccount user = system.findUserAccount(eventTokens[2]);
            user.viewInfo();
          } else if (eventTokens[1].equals("trips")) {
            Card card = system.findCard(eventTokens[3]);
            card.viewMostRecentTrips();
          } else if (eventTokens[1].equals("balance")) {
            Card card = system.findCard(eventTokens[3]);
            card.viewBalance();
          } else if (eventTokens[1].equals("cost")) {
            UserAccount user = system.findUserAccount(eventTokens[2]);
            ((CardHolder) user).viewMonthlyCost();
          } else if (eventTokens[1].equals("allTrips")) {
            Card card = system.findCard(eventTokens[3]);
            card.viewAllTrips();
          }
          break;

        case "change":
          UserAccount user = system.findUserAccount(eventTokens[1]);
          user.changeName(eventTokens[2]);
          break;

        case "power":
          if (eventTokens[1].equals("off")) {
            system.powerOffSystem();
          }
          break;
      }
    } else {
      if ((action.equals("power")) && (eventTokens[1].equals("on"))) {
        system.powerOnSystem();
        String month = eventTokens[3].substring(5, 7);
        String date = eventTokens[3].substring(8);
        system.setCurrentMonth(month);
        system.setCurrentDate(date);
        System.out.println("The TransitSystem is powered on, " + eventTokens[3] + ".");
      } else {
        System.out.println(
            "Transit is closed for the day. Please come back during our operating hours.");
      }
    }
  }

  private boolean userExists(String accountNumber) {
    if (system.findUserAccount(accountNumber) == null) {
      return false;
    }
    return true;
  }

  private boolean cardExists(String cardNumber) {
    if (system.findCard(cardNumber) == null) {
      return false;
    }
    return true;
  }
}
