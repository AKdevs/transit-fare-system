public class AdminUser extends UserAccount {

    private static long nextAccountNum = 888800000000;

    public AdminUser(String name, String email) {
        super(name, email);
        this.accountNumber = nextAccountNum;
        nextAccountNum += 1;
    }


    public void getDailyReport() {

    }



}
