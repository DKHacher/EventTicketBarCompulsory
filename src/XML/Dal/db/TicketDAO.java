package XML.Dal.db;

import XML.Be.Ticket;
import XML.Dal.DatabaseConnector;
import XML.Dal.ITicket;

import java.util.List;

public class TicketDAO implements ITicket {
    private DatabaseConnector databaseConnector;

    public TicketDAO() throws Exception {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<Ticket> getAllTickets() throws Exception {
        return null;
    }

    @Override
    public Ticket createTicket(Ticket ticket) throws Exception {
        return null;
    }

    @Override
    public void updateTicket(Ticket ticket) throws Exception {
        return;
    }

    @Override
    public void deleteTicket(Ticket ticket) throws Exception {
        return;
    }
}
