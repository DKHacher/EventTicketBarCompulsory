package XML.Gui.Models;

import XML.Be.Event;
import XML.Be.PromoTicket;
import XML.Be.Ticket;
import XML.Bll.TicketManager;
import XML.Dal.db.TicketDAO;

import java.util.List;

public class TicketModel {
    TicketManager ticketManager;

    public TicketModel() throws Exception {
        ticketManager = new TicketManager();
    }

    public void deletePromoTicket(PromoTicket selectedPromoTicket) throws Exception {
        ticketManager.deletePromoTicket(selectedPromoTicket);
    }

    public List<PromoTicket> getAllPromoTickets() throws Exception {
        return ticketManager.getAllPromoTickets();
    }

    public List<Ticket> getAllTickets() throws Exception {
        return ticketManager.getAllTickets();
    }

    public PromoTicket createPromoTicket(PromoTicket newPromoTicket) throws Exception {
        return  ticketManager.createPromoTicket(newPromoTicket);
    }

    public void deleteTicket(Ticket seletedTicket) throws Exception {
        ticketManager.deleteTicket(seletedTicket);
    }
}
