package XML.Dal.db;

import XML.Be.PromoTicket;
import XML.Be.Ticket;
import XML.Be.User;
import XML.Dal.DatabaseConnector;
import XML.Dal.ITicket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    public List<PromoTicket> getAllPromoTickets() throws Exception {
        ArrayList<PromoTicket> allPromoTickets = new ArrayList<>();
        String sql = "SELECT Id, TypeName, TypeDescription FROM TicketTypes";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String typeName = rs.getString("TypeName");
                String typeDescription = rs.getString("TypeDescription");


                PromoTicket PT = new PromoTicket(id, typeName, typeDescription);
                allPromoTickets.add(PT);
            }
        }
        return allPromoTickets;
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
