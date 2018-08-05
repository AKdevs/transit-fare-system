import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/** Manage all accounts which are stored in TransitSystem. */
class AccountManager {
  private ArrayList<CardHolder> cardholders;
  private ArrayList<AdminUser> adminusers;
  private ArrayList<UserAccount> userAccounts;
  private UserAccount loggedInUser;
  private PasswordManager passwordManager;

  AccountManager(){
    this.cardholders = new ArrayList<>();
    this.adminusers = new ArrayList<>();
    this.userAccounts = new ArrayList<>();
    this.passwordManager = new PasswordManager();
    try
    {
      // Reading the object from a file
      FileInputStream file = new FileInputStream("data-AccountManager.bin");
      ObjectInputStream in = new ObjectInputStream(file);

      // Method for deserialization of object
      cardholders = (ArrayList<CardHolder>)in.readObject();
      adminusers = (ArrayList<AdminUser>)in.readObject();
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
    if (cardholders.size() == 0){
      accountnum = 10000001;
    } else {
      int lastnumber;
      lastnumber = Integer.parseInt(cardholders.get(cardholders.size() -1).getAccountNum());
      accountnum = lastnumber + 1;
    }

    cardholders.add(newAccount);
    userAccounts.add(newAccount);
    newAccount.setAccountNumber(Integer.toString(accountnum));
    System.out.println("CardHolder Account " + newAccount.getAccountNum() + " created");

    try
    {
      //Saving of object in a file
      FileOutputStream file = new FileOutputStream("data-AccountManager.bin");
      ObjectOutputStream out = new ObjectOutputStream(file);

      // Method for serialization of object
      out.writeObject(cardholders);
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

  /**
   * Creates an account for administration user account.
   *
   * @param name name of an administration user
   * @param email email of an administration user
   */
  void createAdminAccount(String name, String email, String password) {
    AdminUser newAccount = new AdminUser(name, email, password);
    int accountnum;
    if (adminusers.size() == 0){
      accountnum = 20000001;
    } else {
      int lastnumber;
      lastnumber = Integer.parseInt(adminusers.get(adminusers.size() -1).getAccountNum());
      accountnum = lastnumber + 1;
    }

    adminusers.add(newAccount);
    userAccounts.add(newAccount);
    newAccount.setAccountNumber(Integer.toString(accountnum));
    System.out.println("AdminUser Account " + newAccount.getAccountNum() + " created");

    try
    {
      //Saving of object in a file
      FileOutputStream file = new FileOutputStream("data-AccountManager.bin");
      ObjectOutputStream out = new ObjectOutputStream(file);

      // Method for serialization of object
      out.writeObject(adminusers);
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
