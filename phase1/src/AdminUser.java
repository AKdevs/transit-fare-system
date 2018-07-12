/** AdminUser is used by employee of the Transit System, who can get report to do cost analysis. */
public class AdminUser extends UserAccount {

  private static int nextAccountNum = 20000001;
  /* Account Number for AdminUser starts at 20000001 to distinguish with other account/card numbers.*/

  public AdminUser(String name, String email) {
    super(name, email);
    this.accountNumber = Integer.toString(nextAccountNum);
    nextAccountNum += 1;
  }

  /**
   * Prints out total amount of fare collected for date and total number of bus stops/subway
   * stations reached by passengers on that date.
   *
   * @param date the date which AdminUser would like to view the report for
   */
  static void getDailyReport(String date) {
    System.out.println("All fares received by transit system on " + date + ": " + TransitSystem.getDailyFares(date));
    System.out.println("Number of stations reached by travellers on " + date + ": " + TransitSystem.getDailyStation(date));
  }
}
