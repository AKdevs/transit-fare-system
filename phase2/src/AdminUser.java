/** AdminUser is used by employee of the Transit System, who can get report to do cost analysis. */
class AdminUser extends UserAccount {
  private Aggregator aggregator;
  private static int nextAccountNum = 20000001;
  /* Account Number for AdminUser starts at 20000001 to distinguish with other account/card numbers.*/

  AdminUser(String name, String email, String password) {
    super(name, email, password);
    this.accountNumber = Integer.toString(nextAccountNum);
    nextAccountNum += 1;
  }

  void setAggregator(Aggregator a) {
    this.aggregator = a;
  }

  Aggregator getAggregator() {
    return this.aggregator;
  }

}
