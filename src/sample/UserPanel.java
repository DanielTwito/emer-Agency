package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserPanel implements userAction {

    public Label msg;
    @FXML
    private TextArea Description;
    @FXML
    private ListView selector;
    private String loogedUser;

    @FXML
    public void initialize(){
        AccessLayer al = new AccessLayer();
        al.connectDB("dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
        ArrayList<HashMap<String, String>> res = al.ReadEntries(a,Tables.events);
        ArrayList<EventItem> items = new ArrayList<>();
        for(HashMap map: res){
            items.add(new EventItem((String)map.get("eventId"),(String)map.get("headLine"),(String)map.get("status")));
        }
        selector.getItems().addAll(items.toArray());
        selector.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    @Override
    public void AddEvent(ActionEvent actionEvent) {
        System.out.println("event added!");

    }

    @Override
    public void AddUpdate(ActionEvent actionEvent) {
        if((this.selector.getSelectionModel().getSelectedItems().get(0))==null)
            return;
        String ev = ((EventItem)this.selector.getSelectionModel().getSelectedItems().get(0)).getId();
        String des = this.Description.getText();
        Event event = this.getEvent(ev);
        if (event == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("This event is not exist");
            alert.show();
            return;
        }
        createAnUpdate(des, event);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Update Added!");
        alert.show();
        this.Description.setText("");
        System.out.println("update added");
    }

    private void createAnUpdate(String des, Event event){
        Date date = new Date(System.currentTimeMillis());
        String s=date.toString();/////// hadar-i changed the date to string!!!
        Update update = new Update(s, des, null, event);//TODO fix user
        AccessLayer al = new AccessLayer();
        al.connectDB("dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
        //TODO add event id to db
        a.add(new Pair(Fields.eventId, update.getEvent().getId()));
        a.add(new Pair(Fields.date,update.getDate()));
        a.add(new Pair(Fields.version,""+this.getMaxVersion(update.getEvent().getId())));
        a.add(new Pair(Fields.description,update.getDescription()));

        al.AddEntry(a,Tables.eventUpdates);
    }

    private Event getEvent(String id){
        AccessLayer al = new AccessLayer();
        al.connectDB("dbEmer.db");
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
            root = fxmlLoader.load(getClass().getResource("sample/addComplaintScreen.fxml").openStream());
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(root, 650, 400));
            AddComplaintScreen rfv = fxmlLoader.getController();
            rfv.setComplaintOwner(this.loogedUser);
            stage.show();
//            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLoogedUser(String loogedUser) {
        this.loogedUser = loogedUser;
    }

    public void showUserDetails() {
        AccessLayer al = new AccessLayer();
        ArrayList<Pair> tmp = new ArrayList<>();
        tmp.add(new Pair(Fields.userName, loogedUser));
        ArrayList<HashMap<String, String>> userCheck = al.ReadEntries(tmp, Tables.users);
        String userRank = userCheck.get(0).get(Fields.rank+"");
        String userWarning = userCheck.get(0).get(Fields.warrings+"");
        msg.setText("hello "+ loogedUser+" your'e rank is : "+userRank+" "+" total warning : "+userWarning+"");
    }

    private int getMaxVersion(String id) {
        AccessLayer al = new AccessLayer();
        al.connectDB("dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
        a.add(new Pair(Fields.eventId, id));
        ArrayList<HashMap<String, String>> res = al.ReadEntries(a, Tables.eventUpdates);
        int x = res.size();
        return x;
    }
}


