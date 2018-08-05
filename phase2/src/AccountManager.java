import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/** Manage all accounts which are stored in TransitSystem. */
class AccountManager {
  private ArrayList<UserAccount> userAccounts;
  private UserAccount loggedInUser;
  private PasswordManager passwordManager;

  AccountManager(){
    this.userAccounts = new ArrayList<>();
    this.passwordManager = new PasswordManager();
    try
    {
      // Reading the object from a file
      FileInputStream file = new FileInputStream("data-AccountManager.bin");
      ObjectInputStream in = new ObjectInputStream(file);

      // Method for deserialization of object
      userAccounts = (ArrayList<UserAccount>)in.readObject();

      in.close();
      file.close();

      System.out.println("Object has been deserialized ");
    }

    catch(IOException ex)
    {
      System.out.println("IOException is caught");
    }

    catch(ClassNotFoundException ex)
    {
      System.out.println("ClassNotFoundException is caught");
    }
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
    if (userAccounts.size() == 0){
      accountnum = 10000001;
    } else {
      int lastnumber;
      lastnumber = Integer.parseInt(userAccounts.get(userAccounts.size() -1).getAccountNum());
      accountnum = lastnumber + 1;
    }

    userAccounts.add(newAccount);
    newAccount.setAccountNumber(Integer.toString(accountnum));
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
    int accountnum;
    if (userAccounts.size() == 0){
      accountnum = 20000001;
    } else {
      int lastnumber;
      lastnumber = Integer.parseInt(userAccounts.get(userAccounts.size() -1).getAccountNum());
      accountnum = lastnumber + 1;
    }

    userAccounts.add(newAccount);
    newAccount.setAccountNumber(Integer.toString(accountnum));
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

  boolean isAdmin(UserAccount ua) {
    return ua instanceof AdminUser;
  }


  /** @return list of user accounts. */
  ArrayList<UserAccount> getUserAccounts() {
    return userAccounts;
  }

  /**
   * Add a user account.
   *
   * @param newUser a new user with account
   */
  private void addUserAccount(UserAccount newUser) {

    userAccounts.add(newUser);

    try
    {
      //Saving of object in a file
      FileOutputStream file = new FileOutputStream("data-AccountManager.bin");
      ObjectOutputStream out = new ObjectOutputStream(file);

      // Method for serialization of object
      out.writeObject(userAccounts);

      out.close();
      file.close();

      System.out.println("Object has been serialized");

    }
    catch(IOException ex)
    {
      System.out.println("IOException is caught");
    }
  }
}
