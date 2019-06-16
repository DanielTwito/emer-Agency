package sample;

public class EventItem {

    private String id;
    private String head;
    private String stat;

    public EventItem(String id, String head, String stat) {
        this.id = id;
        this.head = head;
        this.stat = stat;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: "+this.id+", Headline: "+this.head+", Status: "+this.stat;
    }
}
