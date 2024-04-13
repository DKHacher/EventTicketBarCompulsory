package XML.Be;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private int id;
    private LocalDate date;
    private String eventName;
    private BigDecimal price;
    private String city;
    private String address;
    private String eventDescription;
    private String extraNotes;
    private LocalTime eventTime;
    private String filePath;

    public Event(int id, LocalDate date, String eventName, BigDecimal price, String city, String address, String eventDescription, String extraNotes, LocalTime eventTime, String filePath) {
        this.id = id;
        this.date = date;
        this.eventName = eventName;
        this.price = price;
        this.city = city;
        this.address = address;
        this.eventDescription = eventDescription;
        this.extraNotes = extraNotes;
        this.eventTime = eventTime;
        this.filePath = filePath;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getExtraNotes() {
        return extraNotes;
    }

    public void setExtraNotes(String extraNotes) {
        this.extraNotes = extraNotes;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
