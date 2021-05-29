package app.techsol.Models;

public class BusModel {
    String busid;
    String busname;
    String busno;
    String bustype;

    public String getBusfare() {
        return busfare;
    }

    public void setBusfare(String busfare) {
        this.busfare = busfare;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getBusname() {
        return busname;
    }

    public void setBusname(String busname) {
        this.busname = busname;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getBustype() {
        return bustype;
    }

    public void setBustype(String bustype) {
        this.bustype = bustype;
    }

    public BusModel() {
    }



    String busfare;
    Double buslat, buslong;

    public BusModel(String busid, String busname, String busno, String bustype, String busfare) {
        this.busid = busid;
        this.busname = busname;
        this.busno = busno;
        this.bustype = bustype;
        this.busfare = busfare;
    }

    public BusModel(String busid, String busname, String busno, String bustype, String busfare, Double buslat, Double buslong) {
        this.busid = busid;
        this.busname = busname;
        this.busno = busno;
        this.bustype = bustype;
        this.busfare = busfare;
        this.buslat = buslat;
        this.buslong = buslong;
    }
}
