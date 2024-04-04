package XML.Dal;

import XML.Be.Ticket;

import java.util.List;

public interface ITicket {

    List<Ticket> getAllTickets() throws Exception;

    public Ticket createTicket(Ticket user) throws Exception;

    public void updateTicket(Ticket user) throws Exception;

    public void deleteTicket(Ticket user) throws Exception;

}
