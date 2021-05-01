package app.techsol.Models;

public class UserModel {
String uid,username, useremail, userpassword, usermobileno, usercnic, useraddress, usergender;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public UserModel() {
    }

    public UserModel(String uid, String username, String useremail, String userpassword, String usermobileno, String usercnic, String useraddress, String usergender) {
        this.uid = uid;
        this.username = username;
        this.useremail = useremail;
        this.userpassword = userpassword;
        this.usermobileno = usermobileno;
        this.usercnic = usercnic;
        this.useraddress = useraddress;
        this.usergender = usergender;
    }
}
