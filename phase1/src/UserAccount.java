import java.io.*;

public class UserAccount {
  private String name;
  private String email;
  protected long accountNumber;

  public UserAccount(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public long getAccountNum() {
    return this.accountNumber;
  }

  public String changeName(String newName) {
    this.name = newName;
    return "Name has been changed to " + this.name + " successfully.";
  }

  /* Functionality handled by Card for phase 1
  public String viewBalance(Card card){

  } */

  public void viewInfo() {
    System.out.println("Name: " + this.name);
    System.out.println("Email: " + this.email);
    System.out.println("Account Number: " + this.accountNumber);
  }

  boolean equals(UserAccount other) {
    return this.accountNumber == other.getAccountNum();
  }
}
