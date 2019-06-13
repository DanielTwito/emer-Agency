package sample;

import java.util.Date;

public class Update {

    Date date;
    String description;
    Update next;
    Update prev;
    Event event;

    public Update(Date date, String description, Update prev, Event event) {
        this.date = date;
        this.description = description;
        this.prev = prev;
        this.next = null;
        this.event = event;
        //event.setTail(this);
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Event getEvent() {
        return event;
    }
}
