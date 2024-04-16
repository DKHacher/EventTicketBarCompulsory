package XML.Dal.db;

import XML.Be.PromoTicket;
import XML.Be.Ticket;
import XML.Dal.DatabaseConnector;
import XML.Dal.ITicket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements ITicket {
    private DatabaseConnector databaseConnector;

    public TicketDAO() throws Exception {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<Ticket> getAllTickets() throws Exception {
        ArrayList<Ticket> allTickets = new ArrayList<>();
        String sql = "SELECT TickId, TicketType, EventId, CoordinatorId, CustomerId FROM Tickets";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int tickId = rs.getInt("TickId");
                int ticketType = rs.getInt("TicketType");
                int eventId = rs.getInt("EventId");
                int coordinatorId= rs.getInt("CoordinatorId");
                int customerId= rs.getInt("CustomerId");
                Ticket PT = new Ticket(tickId, ticketType, eventId,coordinatorId,customerId);
                allTickets.add(PT);
            }
        }
        return allTickets;
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
        String sql = "DELETE FROM Tickets WHERE id = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticket.getTickId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public PromoTicket createPromoTicket(PromoTicket newPromoTicket) throws Exception {
        String sql = "INSERT INTO TicketTypes (typeName, typeDescription) VALUES (?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, newPromoTicket.getTicketType());
            pstmt.setString(2, newPromoTicket.getTicketDescription());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        newPromoTicket.setId(rs.getInt(1));
                    }
                }
            }
        }
        return newPromoTicket;
    }

    @Override
    public void deletePromoTicket(PromoTicket selectedPromoTicket) throws Exception {
        String sql = "DELETE FROM TicketTypes WHERE id = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, selectedPromoTicket.getId());
            pstmt.executeUpdate();
        }
    }
}
