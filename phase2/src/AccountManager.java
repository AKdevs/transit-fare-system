import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/** Manage all accounts which are stored in TransitSystem. */
class AccountManager implements Serializable{
  private ArrayList<CardHolder> cardholders;
  private ArrayList<AdminUser> adminUsers;
  private ArrayList<UserAccount> userAccounts;
  private UserAccount loggedInUser;
  private PasswordManager passwordManager;
  private DataSaving dataSaving;

  AccountManager(){
    this.cardholders = new ArrayList<>();
    this.adminUsers = new ArrayList<>();
    this.userAccounts = new ArrayList<>();
    this.passwordManager = new PasswordManager();
  }

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
    int accountnum;
    accountnum = 10000000 + cardholders.size();

    cardholders.add(newAccount);
    userAccounts.add(newAccount);
    newAccount.setAccountNumber(Integer.toString(accountnum));
    System.out.println("CardHolder Account " + newAccount.getAccountNum() + " created");
    dataSaving.save();

  }

  /**
   * Creates an account for administration user account.
   *
   * @param name name of an administration user
   * @param email email of an administration user
   */
  void createAdminAccount(String name, String email, String password) {
    AdminUser newAccount = new AdminUser(name, email, password);
    int accountnum = 20000000 + adminUsers.size();
    adminUsers.add(newAccount);
    userAccounts.add(newAccount);
    newAccount.setAccountNumber(Integer.toString(accountnum));
    System.out.println("AdminUser Account " + newAccount.getAccountNum() + " created");
    dataSaving.save();
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
