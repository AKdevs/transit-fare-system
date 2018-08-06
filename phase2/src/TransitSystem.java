import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class TransitSystem implements Serializable {
  /** Stores the trip manager */
  private TripManager tripManager;
  /** Stores the transit manager */
  protected TransitManager transitManager;
  /** Stores the account manager */
  private AccountManager accountManager;

  private CardManager cardManager;

  /** Stores system current month */
  private String currentMonth;
  /** Stores system current date */
  private String currentDate;

  /** Operating status of system, either "on" or "off" */
  private String operatingStatus = "off";

  /** The instance of the only TransitSystem */
  private static TransitSystem instance;

  private DataSaving dataSaving;


  public TransitSystem() {
    tripManager = new TripManager();
    transitManager = new TransitManager();
    tripManager.addTransitLines(transitManager.getTransitLines());
    accountManager = new AccountManager();
    cardManager = new CardManager();
    accountManager = new AccountManager();

    try
    {
      // Reading the object from a file
      FileInputStream file = new FileInputStream("data.bin");
      ObjectInputStream in = new ObjectInputStream(file);

      // Method for deserialization of object
      currentDate = (String)in.readObject();
      currentMonth = (String)in.readObject();
      operatingStatus = (String)in.readObject();
      tripManager = (TripManager)in.readObject();
      transitManager = (TransitManager)in.readObject();



      in.close();
      file.close();

      System.out.println("Object has been deserialized ");
    }

    catch(IOException ex)
    {
      System.out.println("IOException is caught");
    }

    catch(ClassNotFoundException ex) {
      System.out.println("ClassNotFoundException is caught");
    }
  }

  TripManager getTripManager() {
    return tripManager;
  }


  TransitManager getTransitManager() {
    return transitManager;
  }


  AccountManager getAccountManager() {
    return accountManager;
  }

  CardManager getCardManager() {
    return cardManager;
  }

  /** @return operating status of the system, either "on" or "off". */
  String getOperatingStatus() {
    return this.operatingStatus;
  }

  /**
   *
   * @return instance of the TransitSystem
   */
  public static TransitSystem getInstance() {
    if (instance == null) {
      instance = new TransitSystem();
    }
    return instance;
  }

  /** Power on the system. */
  void powerOnSystem() {
    this.operatingStatus = "on";
    dataSaving.save();
  }

  /** Power off the system. */
  void powerOffSystem() {
    this.operatingStatus = "off";
    System.out.println("The TransitSystem has been powered off.");
    dataSaving.save();
  }

  /** @return current month in MM format */
  String getCurrentMonth() {
    return this.currentMonth;
  }

  /** @return current date in YY-MM-DD format */
  String getCurrentDate() {
    return this.currentDate;
  }

  /** @param month month to be stored */
  void setCurrentMonth(String month) {
    this.currentMonth = month;
  }

  /** @param date date to be stored. */
  void setCurrentDate(String date) {
    this.currentDate = date;
  }
}
