package XML.Dal.db;

import XML.Be.Event;
import XML.Dal.DatabaseConnector;
import XML.Dal.IEvent;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEvent {
    private DatabaseConnector databaseConnector;

    public EventDAO() throws Exception {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<Event> getAllEvents() throws Exception {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT id, date, eventname, price, city, address, eventdescription, extranotes FROM Events";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                String eventName = rs.getString("eventname");
                BigDecimal price = rs.getBigDecimal("price");
                String city = rs.getString("city");
                String address = rs.getString("address");
                String eventDescription = rs.getString("eventdescription");
                String extraNotes = rs.getString("extranotes");

                Event event = new Event(id, date, eventName, price, city, address, eventDescription, extraNotes);
                events.add(event);
            }
        }
        return events;
    }


    @Override
    public Event createEvent(Event event) throws Exception {
       /* String sql = "INSERT INTO Events (date, eventname, price, city, address, eventdescription, extranotes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDate(1, Date.valueOf(event.getDate()));
            pstmt.setString(2, event.getEventName());
            pstmt.setBigDecimal(3, event.getPrice());
            pstmt.setString(4, event.getCity());
            pstmt.setString(5, event.getAddress());
            pstmt.setString(6, event.getEventDescription());
            pstmt.setString(7, event.getExtraNotes());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        event.setId(rs.getInt(1));
                    }
                }
            }
        } */
        return event;
    }

    @Override
    public void updateEvent(Event event) throws Exception {
       /* String sql = "UPDATE Events SET date = ?, eventname = ?, price = ?, city = ?, address = ?, eventdescription = ?, extranotes = ? WHERE id = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(event.getDate()));
            pstmt.setString(2, event.getEventName());
            pstmt.setBigDecimal(3, event.getPrice());
            pstmt.setString(4, event.getCity());
            pstmt.setString(5, event.getAddress());
            pstmt.setString(6, event.getEventDescription());
            pstmt.setString(7, event.getExtraNotes());
            pstmt.setInt(8, event.getId());
            pstmt.executeUpdate();
        } */
    }

    @Override
    public void deleteEvent(Event event) throws Exception {
        /*String sql = "DELETE FROM Events WHERE id = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, event.getId());
            pstmt.executeUpdate();
        } */
    }

    @Override
    public List<Event> searchEvents(String query) throws Exception {
        return null;
    }

    @Override
    public List<Event> getEvents() throws Exception {
        return null;
    }
}
