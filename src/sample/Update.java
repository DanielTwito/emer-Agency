package sample;

public class Update {

    String date;
    String description;
    Update next;
    Update prev;
    Event event;

    public Update(String date, String description, Update prev, Event event) {
        this.date = date;
        this.description = description;
        this.prev = prev;
        this.next = null;
        this.event = event;
        //event.setTail(this);
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Event getEvent() {
        return event;
    }
}
