public class AdminUser extends UserAccount {

  private static int nextAccountNum = 20000001;

  public AdminUser(String name, String email) {
    super(name, email);
    this.accountNumber = nextAccountNum;
    nextAccountNum += 1;
  }

  public static void getDailyReport(String date) {
    System.out.println(TransitSystem.getDailyFares(date));
    System.out.println(TransitSystem.getDailyStation(date));
  }
}
