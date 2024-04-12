package XML.Bll;

import XML.Be.Event;
import XML.Dal.db.EventDAO;

import java.util.List;

public class EventManager {
    private EventDAO eventDAO;

    public EventManager() throws Exception {
        eventDAO = new EventDAO();
    }

    public List<Event> getAllEvents() throws Exception {
        return eventDAO.getAllEvents();
    }

    public Event createEvent(Event newEvent) throws Exception {
        return eventDAO.createEvent(newEvent);
    }

    public void updateEvent(Event event) throws Exception {
        eventDAO.updateEvent(event);
    }

    public void deleteEvent(Event event) throws Exception {
        eventDAO.deleteEvent(event);
    }

    public List<Event> searchEvents(String query) throws Exception {
        return eventDAO.searchEvents(query);
    }
}
