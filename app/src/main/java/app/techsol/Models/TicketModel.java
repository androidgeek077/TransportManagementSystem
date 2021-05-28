package app.techsol.Models;

public class TicketModel {
    String ticketid, userid, ticketfare, route, busNo, ticketstatus, validityDate;

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTicketfare() {
        return ticketfare;
    }

    public void setTicketfare(String ticketfare) {
        this.ticketfare = ticketfare;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getTicketstatus() {
        return ticketstatus;
    }

    public void setTicketstatus(String ticketstatus) {
        this.ticketstatus = ticketstatus;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public TicketModel() {
    }

    public TicketModel(String ticketid, String userid, String ticketfare, String route, String busNo, String ticketstatus, String validityDate) {
        this.ticketid = ticketid;
        this.userid = userid;
        this.ticketfare = ticketfare;
        this.route = route;
        this.busNo = busNo;
        this.ticketstatus = ticketstatus;
        this.validityDate = validityDate;
    }
}
