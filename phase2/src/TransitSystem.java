import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

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

  public TransitSystem(String filePath) throws ClassNotFoundException, IOException {
    tripManager = new TripManager();
    transitManager = new TransitManager();
    tripManager.addTransitLines(transitManager.getTransitLines());
    accountManager = new AccountManager();
    cardManager = new CardManager();

    File file = new File(filePath);
    if (file.exists()) {
      System.out.println("YAHOO");
      readFromFile(filePath);
    } else {
      file.createNewFile();
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

  /** Power on the system. */
  void powerOnSystem() {
    this.operatingStatus = "on";
  }

  /** Power off the system. */
  void powerOffSystem() {
    this.operatingStatus = "off";
    System.out.println("The TransitSystem has been powered off.");
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


  public void readFromFile(String path) throws ClassNotFoundException {
    try {
      InputStream file = new FileInputStream(path);
      InputStream buffer = new BufferedInputStream(file);
      ObjectInput input = new ObjectInputStream(buffer);

      ArrayList<Object> data = (ArrayList<Object>)input.readObject();
      tripManager = (TripManager)data.get(0);
      transitManager = (TransitManager)data.get(1);
      accountManager = (AccountManager)data.get(2);
      cardManager = (CardManager)data.get(3);
      //deserialize all TransitSystem instance fields
      input.close();
      System.out.println("Object has been deserialized ");
    }

    catch (IOException ex) {
      System.out.print("IO Exception caught");
    }

    catch(ClassNotFoundException ex) {
      System.out.println("ClassNotFoundException is caught");
    }
  }

  public void saveToFile(String filePath) throws IOException {

    OutputStream file = new FileOutputStream(filePath);
    OutputStream buffer = new BufferedOutputStream(file);
    ObjectOutput output = new ObjectOutputStream(buffer);

    // serialize the Map
    //output.writeObject(currentDate);
   // output.writeObject(currentMonth);

    ArrayList<Object> data = new ArrayList<>();
    data.add(tripManager);
    data.add(transitManager);
    data.add(accountManager);
    data.add(cardManager);
    output.writeObject(data);

    output.close();
  }

}

