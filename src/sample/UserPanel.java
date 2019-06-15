package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserPanel implements userAction {

    @FXML
    private TextArea Description;
    @FXML
    private TextArea idEvent;
    private String loogedUser;



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
        createAnUpdate(des, event);
        System.out.println("update added");
    }

    private void createAnUpdate(String des, Event event){
        Date date = new Date(System.currentTimeMillis());
        String s=date.toString();/////// hadar-i changed the date to string!!!
        Update update = new Update(s, des, null, event);//TODO fix user
        AccessLayer al = new AccessLayer();
        al.connectDB("Database/dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
        //TODO add event id to db
        a.add(new Pair(Fields.date,update.getDate()));
        a.add(new Pair(Fields.version,update.getEvent().getId()));
        a.add(new Pair(Fields.description,update.getDescription()));

        al.AddEntry(a,Tables.eventUpdates);
    }

    private Event getEvent(String id){
        AccessLayer al = new AccessLayer();
        al.connectDB("Database/dbEmer.db");
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

    public void addComplaimt(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            root = fxmlLoader.load(getClass().getResource("addComplaintScreen.fxml").openStream());
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(root, 650, 400));
            AddComplaintScreen rfv = fxmlLoader.getController();
            rfv.setComplaintOwner(this.loogedUser);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLoogedUser(String loogedUser) {
        this.loogedUser = loogedUser;
    }
}


