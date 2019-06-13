package sample;

public class Event {

    String id;
    String time;
    String status;
    String headLine;
    Update head;
    Update tail;

    public String getId() {
        return id;
    }

    public Event(String id, String time, String status, String headLine) {
        this.id = id;
        this.time = time;
        this.status = status;
        this.headLine = headLine;
    }


    public void setTail(Update addUpdate) {
        if (head == null){
            head = addUpdate;
        }
        else {
            this.tail = addUpdate;
        }
    }
}
