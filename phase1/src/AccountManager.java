import java.util.ArrayList;

public class AccountManager extends TransitSystem {

  void createCardHolderAccount(String name, String email) {
    CardHolder newAccount = new CardHolder(name, email);
    addUserAccount(newAccount);
    System.out.println("CardHolder Account " + newAccount.getAccountNum() + " created");
  }

  void createAdminAccount(String name, String email) {
    AdminUser newAccount = new AdminUser(name, email);
    addUserAccount(newAccount);
    System.out.println("AdminUser Account " + newAccount.getAccountNum() + " created");
  }

  void addUserAccount(UserAccount newUser) {
    getUserAccounts().add(newUser);
  }

  void removeUserAccount(int accountNumber) {
    getUserAccounts().remove(findUserAccount(accountNumber));
  }
}
