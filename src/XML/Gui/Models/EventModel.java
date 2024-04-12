package XML.Gui.Models;

import XML.Be.Event;
import XML.Bll.EventManager;

import java.util.List;

public class EventModel {
    private EventManager eventManager;

    public EventModel() throws Exception {
        eventManager = new EventManager();
    }

    public List<Event> getAllEvents() throws Exception {
        return eventManager.getAllEvents();
    }

    public Event createEvent(Event event) throws Exception {
        return eventManager.createEvent(event);
    }

    public void updateEvent(Event event) throws Exception {
        eventManager.updateEvent(event);
    }

    public void deleteEvent(Event event) throws Exception {
        eventManager.deleteEvent(event);
    }

    public List<Event> searchEvents(String query) throws Exception {
        return eventManager.searchEvents(query);
    }
}
