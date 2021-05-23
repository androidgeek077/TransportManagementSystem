package app.techsol.Models;

public class PassModel {
    String passid;
    String userid;
    String currentdate;
    String passstartdate;
    String passenddate;
    String bustype;
    String description;
    String qrurl;

    public PassModel() {
    }

    public String getPassid() {
        return passid;
    }

    public void setPassid(String passid) {
        this.passid = passid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getPassstartdate() {
        return passstartdate;
    }

    public void setPassstartdate(String passstartdate) {
        this.passstartdate = passstartdate;
    }

    public String getPassenddate() {
        return passenddate;
    }

    public void setPassenddate(String passenddate) {
        this.passenddate = passenddate;
    }

    public String getBustype() {
        return bustype;
    }

    public void setBustype(String bustype) {
        this.bustype = bustype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQrurl() {
        return qrurl;
    }

    public void setQrurl(String qrurl) {
        this.qrurl = qrurl;
    }

    public String getpassstatus() {
        return passstatus;
    }

    public void setpassstatus(String passstatus) {
        this.passstatus = passstatus;
    }

    public PassModel(String passid, String userid, String currentdate, String passstartdate, String passenddate, String bustype, String description, String qrurl, String passstatus) {
        this.passid = passid;
        this.userid = userid;
        this.currentdate = currentdate;
        this.passstartdate = passstartdate;
        this.passenddate = passenddate;
        this.bustype = bustype;
        this.description = description;
        this.qrurl = qrurl;
        this.passstatus = passstatus;
    }

    String passstatus;

   
}
