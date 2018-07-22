import java.util.ArrayList;

/** Manage all accounts which are stored in TransitSystem. */
class AccountManager {
  private static ArrayList<UserAccount> userAccounts = new ArrayList<>();
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
   * Finds and returns user account for given accountNumber, null if not found.
   *
   * @param accountNumber number of account to be found.
   * @return user account for given accountNumber, null if not found.
   */
  UserAccount findUserAccount(String accountNumber) {
    for (UserAccount ua : userAccounts) {
      if (ua.getAccountNum().equals(accountNumber)) {
        return ua;
      }
    }
    return null;
  }

  /** @return list of user accounts. */
  ArrayList<UserAccount> getUserAccounts() {
    return userAccounts;
  }

  /**
   * Add a user account.
   *
   * @param newUser a new user with account
   *
   */
  private void addUserAccount(UserAccount newUser) {
    getUserAccounts().add(newUser);
  }
}
