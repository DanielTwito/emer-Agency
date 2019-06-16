package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddComplaintScreen {

    public TextArea Description;
    public Button sendRequset;
    public TextArea userName;
    private String owner;
    private String org;
    private AccessLayer al = new AccessLayer();
    public void AddUcomplaintRequest(ActionEvent actionEvent) {

        Date date = new Date(System.currentTimeMillis());
        String s=date.toString();
        al.connectDB("Database/dbEmer.db");
        boolean valid = validate();
        if(valid) {
            ArrayList<Pair> a = new ArrayList<>();
            a.add(new Pair(Fields.content, Description.getText()+""));
            a.add(new Pair(Fields.owner, this.owner));
            a.add(new Pair(Fields.complaintUser, userName.getText()+""));
            a.add(new Pair(Fields.isApproved, complaintRequestStatus.waiting+""));
            a.add(new Pair(Fields.organization, this.org));
            try {
                al.AddEntry(a, Tables.complaintsRequests);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Request send to your Admin in your organization");
                alert.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR happen during sending your request");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("something went wrong");
            alert.show();

        }
    }

    private boolean validate() {
        ArrayList<Pair> tmp = new ArrayList<>();
        String user = userName.getText();
        String userOrg;
        String ownerOrg;
        tmp.add(new Pair(Fields.userName,user));
        ArrayList<HashMap<String, String>> userCheck = al.ReadEntries(tmp, Tables.users);
        if(userCheck.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("user dose not exist");
            alert.show();
            return false;
        }
        String s =userCheck.get(0).get(Fields.isAdmin+"");
        if(s.equals("true")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("you cant complaint about you're admin");
            alert.show();
            return false;
        }
        userCheck = al.ReadEntries(tmp, Tables.organizationMembers);
        userOrg = userCheck.get(0).get("organization")+"";
        tmp  = new ArrayList<>();
        tmp.add(new Pair(Fields.userName,this.owner));
        ArrayList<HashMap<String, String>> check = al.ReadEntries(tmp, Tables.organizationMembers);
        ownerOrg = check.get(0).get("organization")+"";
        return userOrg.equals(ownerOrg);

    }



    public void setComplaintOwner(String owner){
        this.owner = owner;
        ArrayList<Pair> tmp = new ArrayList<>();
        tmp.add(new Pair(Fields.userName,this.owner));
        ArrayList<HashMap<String, String>> check = al.ReadEntries(tmp, Tables.organizationMembers);
        this.org = check.get(0).get("organization")+"";


    }

}
