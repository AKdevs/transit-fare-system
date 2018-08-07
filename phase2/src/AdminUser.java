import java.io.Serializable;

/** AdminUser is used by employee of the Transit System, who can get report to do cost analysis. */
class AdminUser extends UserAccount implements Serializable{
  /* Account Number for AdminUser starts at 20000001 to distinguish with other account/card numbers.*/

  /**
   * Constructs an admin user
   *
   * @param name name of the admin user
   * @param email email of the admin user
   * @param password pass word for the admin user
   */
  AdminUser(String name, String email, String password) {
    super(name, email, password);
    this.accountNumber = null;
  }
}
