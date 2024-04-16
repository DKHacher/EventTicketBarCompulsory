package XML.Be;

public class PromoTicket {

    private int id;
    private String ticketType;
    private String ticketDescription;

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public PromoTicket(int id, String ticketType, String ticketDescription) {

        this.id = id;
        this.ticketType = ticketType;
        this.ticketDescription = ticketDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
