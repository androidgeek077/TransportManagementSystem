package app.techsol.Models;

public class MoneyModel {
    String moneyid,userid, amount;

    public String getMoneyid() {
        return moneyid;
    }

    public void setMoneyid(String moneyid) {
        this.moneyid = moneyid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public MoneyModel() {
    }

    public MoneyModel(String moneyid, String userid, String amount) {
        this.moneyid = moneyid;
        this.userid = userid;
        this.amount = amount;
    }
}
