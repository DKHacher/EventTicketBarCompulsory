package XML.Dal.db;

import XML.Be.Event;
import XML.Dal.DatabaseConnector;
import XML.Dal.IEvent;

import java.util.List;

public class EventDAO implements IEvent {
    private DatabaseConnector databaseConnector;

    public EventDAO() throws Exception {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<Event> getAllEvents() throws Exception {
        return null;
    }

    @Override
    public Event createEvent(Event event) throws Exception {
        return null;
    }

    @Override
    public void updateEvent(Event event) throws Exception {
        return;
    }

    @Override
    public void deleteEvent(Event event) throws Exception {
        return;
    }
}
