package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addEvent {

    public javafx.scene.control.Button btn_addEvent;
    public javafx.scene.control.TextField eventHeadLine;
    public javafx.scene.control.TextField FirstDescription;
    public javafx.scene.control.TextField time;
    public javafx.scene.control.DatePicker date;
    public javafx.scene.control.ChoiceBox category;
    public javafx.scene.control.CheckBox police;
    public javafx.scene.control.CheckBox fire;
    public javafx.scene.control.CheckBox mada;
    private String representiveName;
    private Stage mainstage;
    private Stage stage;
    private Stage eventStage;


    public void init(String representiveName,Stage loginStage,Stage mainstage,Stage eventStage){
        category.setItems(FXCollections.observableArrayList(
                "Fire","Security","Criminal")
        );
        this.representiveName=representiveName;
        this.mainstage=mainstage;
        this.stage=loginStage;
        this.eventStage=eventStage;
    }

    public void backToMain(ActionEvent event){
        eventStage.close();
        mainstage.show();

    }

    public void addEvent(ActionEvent event) {

        if(date.getValue()==null||date.getValue().equals("")||time.getText().equals("")||eventHeadLine.getText().equals("")){
            showAlert("Please fill all fields");
            return;
        }
        if(!police.isSelected()&&!mada.isSelected()&&!fire.isSelected()){
            showAlert("Please choose one or more organization\nIn order to send a massenge to one of its users.");
            return;
        }
        if(category.getValue()==null){
            showAlert("Please select the event category");
            return;
        }
        int eventid=getMaxIdFromDB();
        String eventId=eventid+"";
        String date = this.date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time=this.time.getText();
        String eventTime=date+"  "+time+"";
        Event event1 = new Event(eventId, eventTime, "active", this.eventHeadLine.getText());
        AccessLayer al = new AccessLayer();
        al.connectDB("dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
        //TODO add event id to db
        a.add(new Pair(Fields.eventId,event1.id));
        a.add(new Pair(Fields.time,event1.time));
        a.add(new Pair(Fields.status,event1.status));
        a.add(new Pair(Fields.headLine,event1.headLine));
        String cat=this.category.getValue().toString();
        a.add(new Pair(Fields.categoryName,cat));
        a.add(new Pair(Fields.representiveName_events,representiveName));

        addFirstUpdate(event1,eventTime);

        al.AddEntry(a,Tables.events);

        addpermission(eventId);
    }

    private void addpermission(String eventId) {
        String ans="";
        if(mada.isSelected()){
            String user1=sendMsgToUser("Magen David Adom",eventId);
            ans+=user1+" from Magen David Adom is responsible on this event\n";
        }
        if(police.isSelected()){
            String user2=sendMsgToUser("Police",eventId);
            ans+=user2+" from Police is responsible on this event\n";
        }
        if(fire.isSelected()){
            String user3=sendMsgToUser("Firefighting And Rescuing",eventId);
            ans+=user3+" from Firefighting And Rescuing is responsible on this event\n";
        }
        showAlert(ans);




    }

    private String sendMsgToUser(String organization, String eventId) {
        //read user from this organization
        AccessLayer al = new AccessLayer();
        al.connectDB("dbEmer.db");
        ArrayList<Pair> tmp = new ArrayList<>();
        tmp.add(new Pair(Fields.organization,organization));
        ArrayList<HashMap<String, String>> userCheck = al.ReadEntries(tmp, Tables.organizationMembers);
        HashMap<String, String> response = userCheck.get(0);

        String user=response.get("userName");

        //insert permittion for user at this event
        al.connectDB("dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
        //TODO add event id to db
        a.add(new Pair(Fields.eventId,eventId));
        a.add(new Pair(Fields.userName,user));
        a.add(new Pair(Fields.permission,"write"));

        al.AddEntry(a,Tables.permissions);

        return user;


    }

    private void addFirstUpdate(Event event1,String date) {
        Update update = new Update(date,FirstDescription.getText(), null, event1);
        event1.setHead(update);
        AccessLayer al = new AccessLayer();
        al.connectDB("dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
        a.add(new Pair(Fields.date,date));
        a.add(new Pair(Fields.eventId,event1.id));
        a.add(new Pair(Fields.version,"0"));
        a.add(new Pair(Fields.description,update.description));


        al.AddEntry(a,Tables.eventUpdates);
    }

    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }


    public int getMaxIdFromDB() {
        AccessLayer al = new AccessLayer();
        ArrayList<Pair> tmp = new ArrayList<>();
        ArrayList<HashMap<String, String>> allEvents = al.ReadEntries(tmp, Tables.events);
        if(allEvents.size()==0) return 1;

        int maxId=0;
        for(HashMap<String,String> map:allEvents) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey().equals("eventId")) {
                    String val = entry.getValue();
                    int curr = Integer.parseInt(val);
                    if (maxId < curr)
                        maxId = curr;
                }
            }
        }
        return maxId+1;
    }
}