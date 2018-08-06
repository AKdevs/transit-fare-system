import java.io.Serializable;
import java.util.*;

/** Class UserAccount is used to define users of the system. */
public class UserAccount implements Serializable{
  /** Stores name of this user account */
  private String name;
  /** Stores e-mail of this user account */
  private String email;
  /** Stores account number of this user account */
  protected String accountNumber;
  protected String password;
  protected List<Integer> questionIndexList;
  protected List<String> answerList;

  UserAccount(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.questionIndexList = new ArrayList<>();
    this.answerList = new ArrayList<>();
  }

  void setEmail(String email) {
    this.email = email;
  }

  void setPassword(String password) {
    this.password = password;
  }

  String getPassword() {
    return password;
  }

  void setQuestionIndexList(List<Integer> indexList) {
    questionIndexList = indexList;
  }

  void setAnswerList(List<String> answerList) {
    this.answerList = answerList;
  }


  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  List<Integer> getQuestionIndexList() {
    return questionIndexList;
  }
  List<String> getAnswerList() {
    return answerList;
  }

  /** @return name of UserAccount */
  String getName() {
    return this.name;
  }

  /** @return email of UserAccount */
  String getEmail() {
    return this.email;
  }

  /** @return account number of UserAccount */
  String getAccountNum() {
    return this.accountNumber;
  }

  /**
   * Changes name of this account to newName. Prints out a message to confirm this change.
   *
   * @param newName the new name to be used
   */
  void changeName(String newName) {
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
   * Returns true iff other is equal to this UserAccount
   *
   * @param other the UserAccount to be compared with
   * @return true if the account number of other is equal to that of this UserAccount
   */
  boolean equals(UserAccount other) {
    return this.accountNumber == other.getAccountNum();
  }
}
