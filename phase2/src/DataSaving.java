import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DataSaving {
  private TransitSystem transitSystem;
  private Aggregator aggregator;
  void save(){
    try
    {
      //Saving of object in a file
      FileOutputStream file = new FileOutputStream("data.bin");
      ObjectOutputStream out = new ObjectOutputStream(file);

      // Method for serialization of object
      out.writeObject(transitSystem.getTransitManager());
      out.writeObject(transitSystem.getAccountManager());
      out.writeObject(transitSystem.getCardManager());
      out.writeObject(transitSystem.getTripManager());
      out.writeObject(transitSystem.getCurrentDate());
      out.writeObject(transitSystem.getCurrentMonth());
      out.writeObject(transitSystem.getOperatingStatus());


      out.close();
      file.close();

      System.out.println("Object has been serialized");

    }
    catch(IOException ex)
    {
      System.out.println("IOException is caught");
    }
  }
  void saveReport(){
    try
    {
      //Saving of object in a file
      FileOutputStream file = new FileOutputStream("data.bin");
      ObjectOutputStream out = new ObjectOutputStream(file);

      // Method for serialization of object
      out.writeObject(aggregator.getAllFares());
      out.writeObject(aggregator.getNumberOfStations());
      out.writeObject(aggregator.getAvailableDate());
      out.writeObject(aggregator.getTransitLineDailySummary());


      out.close();
      file.close();

      System.out.println("Object has been serialized");

    }
    catch(IOException ex)
    {
      System.out.println("IOException is caught");
    }
  }
}
