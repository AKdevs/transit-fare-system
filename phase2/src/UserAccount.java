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
  /**
   * Stores password of the account
   */
  protected String password;
  /**
   * Stores the question index list for the account
   */
  protected List<Integer> questionIndexList;
  /**
   * Stores the answer list of the security question
   */
  protected List<String> answerList;

  /**
   * Constructs the user account
   * @param name name of the user
   * @param email email of the user
   * @param password pass word of the account
   */
  UserAccount(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.questionIndexList = new ArrayList<>();
    this.answerList = new ArrayList<>();
  }

  /**
   * Set the email for the account
   * @param email email for user
   */
  void setEmail(String email) {
    this.email = email;
  }

  /**
   * Set the name for the account
   * @param name user name
   */
  void setName(String name) {
    this.name = name;
  }

  /**
   * Set the password for the account
   *
   * @param password account password
   */
  void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get the password of the account
   * @return password
   */
  String getPassword() {
    return password;
  }

  /**
   * Set the question index list for the user account
   *
   * @param indexList question index list
   */
  void setQuestionIndexList(List<Integer> indexList) {
    questionIndexList = indexList;
  }

  /**
   * Set the answer list for the account
   * @param answerList answer list
   */
  void setAnswerList(List<String> answerList) {
    this.answerList = answerList;
  }


  /**
   * Set account number for user account
   *
   * @param accountNumber set account number for user account
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * get question index list for the account
   * @return question index list
   */
  List<Integer> getQuestionIndexList() {
    return questionIndexList;
  }

  /**
   * Get the answer list of the account
   *
   * @return the answer list
   */
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
