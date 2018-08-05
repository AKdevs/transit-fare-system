import java.io.Serializable;

/** AdminUser is used by employee of the Transit System, who can get report to do cost analysis. */
class AdminUser extends UserAccount implements Serializable{
  /* Account Number for AdminUser starts at 20000001 to distinguish with other account/card numbers.*/

  AdminUser(String name, String email, String password) {
    super(name, email, password);
    this.accountNumber = null;
  }
}
