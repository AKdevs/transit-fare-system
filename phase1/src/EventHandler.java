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
    for (int i = 0; i < 50; i++) {
      mainHandler.play();
    }
    // System.out.println(mainSystem.getCards());
    // System.out.println(mainSystem.getUserAccounts());
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
          try {
            Card ca = system.findCard(Integer.parseInt(eventTokens[2]));
            CardHolder cha = (CardHolder) system.findUserAccount(Integer.parseInt(eventTokens[1]));
            cha.activateCard(ca);
            break;
          } catch (Exception e) {
            System.out.println("Invalid account or card number.");
          }

        case "deactivate":
          try {
            Card cd = system.findCard(Integer.parseInt(eventTokens[2]));
            CardHolder chd = (CardHolder) system.findUserAccount(Integer.parseInt(eventTokens[1]));
            chd.deactivateCard(cd);
            break;

          } catch (Exception e) {
            System.out.println("Invalid account or card number.");
          }

        case "link":
          try {
            Card currentCard = system.findCard(Integer.parseInt(eventTokens[2]));
            UserAccount currentHolder = system.findUserAccount(Integer.parseInt(eventTokens[1]));
            ((CardHolder) currentHolder).linkCard(currentCard);
            break;

          } catch (Exception e) {
            System.out.println("Invalid account or card number.");
          }

        case "unlink":
          try {
            UserAccount ua = system.findUserAccount(Integer.parseInt(eventTokens[1]));
            Card cc = system.findCard(Integer.parseInt(eventTokens[2]));
            ((CardHolder) ua).unlinkCard(cc);
            break;

          } catch (Exception e) {
            System.out.println("Invalid account or card number.");
          }

        case "load":
          try {
            Card travelCard = system.findCard(Integer.parseInt(eventTokens[2]));
            travelCard.addBalance(Double.parseDouble(eventTokens[3]));
            break;

          } catch (Exception e) {
            System.out.println("Invalid account or card number.");
          }

        case "view":
          if (eventTokens[1].equals("report")) {
            String date = eventTokens[2];
            AdminUser.getDailyReport(date);
          } else if (eventTokens[1].equals("info")) {
            try {
              UserAccount user = system.findUserAccount(Integer.parseInt(eventTokens[2]));
              user.viewInfo();
            } catch (Exception e) {
              System.out.println("Invalid account number.");
            }
          } else if (eventTokens[1].equals("trips")) {
            try {
              Card card = system.findCard(Integer.parseInt(eventTokens[3]));
              card.viewMostRecentTrips();
            } catch (Exception e) {
              System.out.println("Invalid account or card number.");
            }
          } else if (eventTokens[1].equals("balance")) {
            try {
              Card card = system.findCard(Integer.parseInt(eventTokens[3]));
              card.viewBalance();
            } catch (Exception e) {
              System.out.println("Invalid account or card number.");
            }
          } else if (eventTokens[1].equals("cost")) {
            try {
              UserAccount user = system.findUserAccount(Integer.parseInt(eventTokens[2]));
              ((CardHolder) user).viewMonthlyCost();

            } catch (Exception e) {
              System.out.println("Invalid card number.");
            }

          } else if (eventTokens[1].equals("allTrips")) {
            try {
              Card card = system.findCard(Integer.parseInt(eventTokens[3]));
              card.viewAllTrips();
            } catch (Exception e) {
              System.out.println("Invalid account or card number.");
            }
          }
          break;

        case "change":
          try {
            UserAccount user = system.findUserAccount(Integer.parseInt(eventTokens[1]));
            user.changeName(eventTokens[2]);

          } catch (Exception e) {
            System.out.println("Invalid account number.");
          }

          // case "remove": remove card , remove userAccount
          // case change points name in TransitLine
          // case add point to transitline
          // case add new transitLine
          // Do we need a static SystemManager???????????
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
}
