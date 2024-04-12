package XML.Bll;

import XML.Be.PromoTicket;
import XML.Be.Ticket;
import XML.Dal.db.TicketDAO;

import java.util.List;

public class TicketManager {
    private TicketDAO TicketDAO;

    public TicketManager() throws Exception {
        TicketDAO = new TicketDAO();
    }

    public List<PromoTicket> getAllPromoTickets() throws Exception {
        return TicketDAO.getAllPromoTickets();
    }

    public List<Ticket> getAllTickets() throws Exception {
        return TicketDAO.getAllTickets();
    }
}
