/** AdminUser is used by employee of the Transit System, who can get report to do cost analysis. */
class AdminUser extends UserAccount {

  private static int nextAccountNum = 20000001;
  /* Account Number for AdminUser starts at 20000001 to distinguish with other account/card numbers.*/

  AdminUser(String name, String email) {
    super(name, email);
    this.accountNumber = Integer.toString(nextAccountNum);
    nextAccountNum += 1;
  }
}