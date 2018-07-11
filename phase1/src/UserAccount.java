import java.io.*;

/** Class UserAccount is used to define users of the system. */
public class UserAccount {
  private String name;
  private String email;
  protected String accountNumber;

  public UserAccount(String name, String email) {
    this.name = name;
    this.email = email;
  }

  /** @return name of UserAccount */
  public String getName() {
    return this.name;
  }

  /** @return email of UserAccount */
  public String getEmail() {
    return this.email;
  }

  /** @return account number of UserAccount */
  public String getAccountNum() {
    return this.accountNumber;
  }

  /**
   * Changes name of UserAccount to newName Prints out a message to confirm this change.
   *
   * @param newName the new name to be used
   */
  public void changeName(String newName) {
    this.name = newName;
    System.out.println("Account " + this.getAccountNum() + " name changed to " + this.name);
  }

  /* Functionality handled by Card for phase 1
  public String viewBalance(Card card){

  } */

  /** Prints out information of UserAccount */
  public void viewInfo() {
    System.out.println("Name: " + this.name);
    System.out.println("Email: " + this.email);
    System.out.println("Account Number: " + this.accountNumber);
  }

  /**
   * Examines if other is equal to this UserAccount
   *
   * @param other the UserAccount to be compared with
   * @return true if the account number of other is equal to that of this UserAccount
   */
  boolean equals(UserAccount other) {
    return this.accountNumber == other.getAccountNum();
  }
}
