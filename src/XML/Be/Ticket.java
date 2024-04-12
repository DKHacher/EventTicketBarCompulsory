package XML.Be;

public class Ticket {

    private int tickId;
    private int ticketType;
    private int eventId;
    private int coordinatorId;
    private int customerId;


    public int getTicketType() {
        return ticketType;
    }

    public void setTicketType(int ticketType) {
        this.ticketType = ticketType;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(int coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Ticket(int tickId, int ticketType, int eventId, int coordinatorId, int customerId) {
        this.tickId = tickId;
        this.ticketType = ticketType;
        this.eventId = eventId;
        this.coordinatorId = coordinatorId;
        this.customerId = customerId;

    }
}
