package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserPanel implements userAction {

    @FXML
    private TextArea Description;
    @FXML
    private TextArea idEvent;


    @Override
    public void AddEvent(ActionEvent actionEvent) {
        System.out.println("event added!");

    }

    @Override
    public void AddUpdate(ActionEvent actionEvent) {
        String ev = this.idEvent.getText();
        String des = this.Description.getText();
        Event event = this.getEvent(ev);
        if (event == null){
            System.out.println("event is NULL");
            return;
        }
        createAndUpdate(des, event);
        System.out.println("update added");
    }

    private void createAndUpdate(String des, Event event){
        Date date = new Date(System.currentTimeMillis());
        Update update = new Update(date, des, null, event);//TODO fix user
        AccessLayer al = new AccessLayer();
        al.connectDB("db/dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
        //TODO add event id to db
        a.add(new Pair(Fields.date,update.getDate()));
        a.add(new Pair(Fields.updateId,update.getEvent().getId()));
        a.add(new Pair(Fields.description,update.getDescription()));

        al.AddEntry(a,Tables.eventUpdates);
    }

    private Event getEvent(String id){
        AccessLayer al = new AccessLayer();
        al.connectDB("db/dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
        a.add(new Pair(Fields.eventId,id));
        ArrayList<HashMap<String, String>> res = al.ReadEntries(a,Tables.events);
        al.discoonetDB();
        if (res.size() != 1 ){
            return null;
        }
        HashMap<String, String> line=res.get(0);
        return new Event(line.get("eventId"),line.get("time"),line.get("status"),line.get("headLine"));
    }
}
