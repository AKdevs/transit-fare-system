import java.util.ArrayList;

/** Manage all accounts which are stored in TransitSystem. */
public class AccountManager extends TransitSystem {

  /**
   * Creates an account for card holder.
   *
   * @param name name of card holder
   * @param email email of card holder
   */
  void createCardHolderAccount(String name, String email) {
    CardHolder newAccount = new CardHolder(name, email);
    addUserAccount(newAccount);
    System.out.println("CardHolder Account " + newAccount.getAccountNum() + " created");
  }

  /**
   * Creates an account for administration user account.
   *
   * @param name name of an administration user
   * @param email email of an administration user
   */
  void createAdminAccount(String name, String email) {
    AdminUser newAccount = new AdminUser(name, email);
    addUserAccount(newAccount);
    System.out.println("AdminUser Account " + newAccount.getAccountNum() + " created");
  }

  /**
   * Add a user account.
   *
   * @param newUser a new user with account
   */
  void addUserAccount(UserAccount newUser) {
    getUserAccounts().add(newUser);
  }

  void removeUserAccount(String accountNumber) {
    getUserAccounts().remove(findUserAccount(accountNumber));
  }
}
