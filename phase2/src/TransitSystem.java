
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.*;

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
  /** Stores system current date, yyyy-mm-dd */
  private String currentDate;

  /** Operating status of system, either "on" or "off" */
  private String operatingStatus = "off";

  /** Stores how many years data will be stored in the system, default is 4 years*/
  private Integer dataStorePeriod;

    private static final Logger logger =
            Logger.getLogger(TransitSystem.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    private static Handler fh;


  public TransitSystem(String filePath) throws ClassNotFoundException, IOException {
    tripManager = new TripManager();
    transitManager = new TransitManager();
    tripManager.addTransitLines(transitManager.getTransitLines());
    accountManager = new AccountManager();
    accountManager.createAdminAccount("root", "", "root");
    cardManager = new CardManager();
    dataStorePeriod = 4;

    try{
    logger.setLevel(Level.ALL);
    consoleHandler.setLevel(Level.ALL);
    logger.addHandler(consoleHandler);
      fh = new FileHandler("/Users/jingjingzhan/Desktop/test_log.txt");
      logger.addHandler(fh);
      SimpleFormatter formatter = new SimpleFormatter();
      fh.setFormatter(formatter);
      } catch (IOException e) {
        e.printStackTrace();
    }


    File file = new File(filePath);
    if (file.exists()) {
      readFromFile(filePath);
    } else {
      file.createNewFile();
    }
  }


    private static Logger getLogger(){
        return logger;
    }

    public static void log(Level level, String msg){
        getLogger().log(level, msg);
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

    public Integer getDataStorePeriod() {
        return dataStorePeriod;
    }

    public void setDataStorePeriod(Integer dataStorePeriod) {
        this.dataStorePeriod = dataStorePeriod;
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
      deleteOldData(currentDate);
    this.operatingStatus = "off";
    //System.out.println("The TransitSystem has been powered off.");
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


    /**
     * Deletes data of (date - dataStorePeriod) from the system.
     * @param date
     */
  void deleteOldData(String date){
      String todayDate = date;
      String currentYear = todayDate.substring(0,4);
      String currentMMDD = todayDate.substring(4);

      Integer currentYearInt = Integer.parseInt(currentYear);
      Integer oldYearInt = currentYearInt - dataStorePeriod;
      String oldDate = oldYearInt.toString() + currentMMDD;

      this.tripManager.getAggregator().deleteOldData(oldDate);

      if (currentMMDD.equals("-03-01")) {
          String anotherOldDate = oldYearInt.toString() + "-02-29";
          //System.out.println("Another Old date: "+ anotherOldDate);
          this.tripManager.getAggregator().deleteOldData(anotherOldDate);
      }
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

