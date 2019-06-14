package sample;

import java.util.Date;

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
        //ToDo remove id from constractor-- added by system
        this.id = id;
        this.time = time;
        this.status = status;
        this.headLine = headLine;
    }

    public void setHead(Update head) {

        this.head = head;
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
