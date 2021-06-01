package app.techsol.Models;

public class UserModel {
String uid;
    String usertype;

    public UserModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsermobileno() {
        return usermobileno;
    }

    public void setUsermobileno(String usermobileno) {
        this.usermobileno = usermobileno;
    }

    public String getUsercnic() {
        return usercnic;
    }

    public void setUsercnic(String usercnic) {
        this.usercnic = usercnic;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getUsergender() {
        return usergender;
    }

    public void setUsergender(String usergender) {
        this.usergender = usergender;
    }



    String username;
    String useremail;
    String userpassword;
    String usermobileno;
    String usercnic;
    String useraddress;
    String usergender;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public UserModel(String uid, String usertype, String username, String useremail, String userpassword, String usermobileno, String usercnic, String useraddress, String usergender, String balance) {
        this.uid = uid;
        this.usertype = usertype;
        this.username = username;
        this.useremail = useremail;
        this.userpassword = userpassword;
        this.usermobileno = usermobileno;
        this.usercnic = usercnic;
        this.useraddress = useraddress;
        this.usergender = usergender;
        this.balance = balance;
    }

    String balance;

    public String getProfilepicurl() {
        return profilepicurl;
    }

    public void setProfilepicurl(String profilepicurl) {
        this.profilepicurl = profilepicurl;
    }

    public UserModel(String uid, String usertype, String username, String useremail, String userpassword, String usermobileno, String usercnic, String useraddress, String usergender, String balance, String profilepicurl) {
        this.uid = uid;
        this.usertype = usertype;
        this.username = username;
        this.useremail = useremail;
        this.userpassword = userpassword;
        this.usermobileno = usermobileno;
        this.usercnic = usercnic;
        this.useraddress = useraddress;
        this.usergender = usergender;
        this.balance = balance;
        this.profilepicurl = profilepicurl;
    }

    String profilepicurl;



}
