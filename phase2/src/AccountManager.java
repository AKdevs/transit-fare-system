import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/** Manage all accounts which are stored in TransitSystem. */
class AccountManager implements Serializable{

  /**
   * Stores all the user accounts
   */
  private ArrayList<UserAccount> userAccounts;
  /**
   * The current logged in user
   */
  private UserAccount loggedInUser;
  /**
   * Uses passwordManager to manage password for user accounts
   */
  private PasswordManager passwordManager;
  /**
   * The next card holder user number
   */
  private int nextCardHolderNumber;
  /**
   * The next administration user number
   */
  private int nextAdminUserNumber;

  /**
   * Constructs an account manager
   */
  AccountManager(){
    this.userAccounts = new ArrayList<>();
    this.passwordManager = new PasswordManager();
    this.nextCardHolderNumber = 10000001;
    this.nextAdminUserNumber = 20000001;
  }

  /**
   * Get the pass word manager
   *
   *
   * @return pass word manager
   */
  PasswordManager getPasswordManager() {
    return this.passwordManager;
  }

  UserAccount getLastCreatedAccount() {
    return userAccounts.get(userAccounts.size() - 1);
  }

  void setLoggedInUser(UserAccount ua) {
    loggedInUser = ua;
  }

  UserAccount getLoggedInUser() {
    return loggedInUser;
  }

  /**
   * Creates an account for card holder.
   *
   * @param name name of card holder
   * @param email email of card holder
   */
  void createCardHolderAccount(String name, String email, String password) {
    CardHolder newAccount = new CardHolder(name, email, password);
    userAccounts.add(newAccount);
    newAccount.setAccountNumber(Integer.toString(nextCardHolderNumber));
    nextCardHolderNumber++;
    System.out.println("CardHolder Account " + newAccount.getAccountNum() + " created");
  }

  /**
   * Creates an account for administration user account.
   *
   * @param name name of an administration user
   * @param email email of an administration user
   */
  void createAdminAccount(String name, String email, String password) {
    AdminUser newAccount = new AdminUser(name, email, password);
    userAccounts.add(newAccount);
    newAccount.setAccountNumber(Integer.toString(nextAdminUserNumber));
    nextAdminUserNumber++;
    System.out.println("AdminUser Account " + newAccount.getAccountNum() + " created");
  }


  /**
   * Finds and returns card holder account for given accountNumber, null if not found.
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

  boolean isAdmin(UserAccount ua) {
    return ua instanceof AdminUser;
  }
}
