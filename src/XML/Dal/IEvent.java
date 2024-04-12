package XML.Dal;

import XML.Be.Event;
import XML.Be.Event;

import java.util.List;

public interface IEvent {
    public List<Event> getAllEvents() throws Exception;

    public Event createEvent(Event event) throws Exception;

    public void updateEvent(Event event) throws Exception;

    public void deleteEvent(Event event) throws Exception;

    public List<Event> searchEvents(String query) throws Exception;

    public List<Event> getEvents() throws Exception;

}
