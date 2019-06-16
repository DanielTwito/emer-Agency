package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewComplaintRequest {
    public Label complaintOwner;
    public Label complaintUser;
    public TextArea complaintContent;
    private String complaintId;
    private String owner;
    private String subject;

    public void acceptComplaimt(ActionEvent actionEvent) {
        AccessLayer al = new AccessLayer();
        al.connectDB("Database/dbEmer.db");
        ArrayList<Pair> tmp = new ArrayList<>();
        tmp.add(new Pair(Fields.userName, subject));
        ArrayList<HashMap<String, String>> userCheck = al.ReadEntries(tmp, Tables.users);
        String warning = userCheck.get(0).get(Fields.warrings+"");
        String rank = userCheck.get(0).get(Fields.rank+"");
        int oldWarning = Integer.parseInt(warning);
        int oldRank = Integer.parseInt(rank);
        int newWarning = oldWarning;
        int newRank = oldRank;
        tmp = new ArrayList<>();
        tmp.add(new Pair(Fields.userName, subject+""));
        if(oldWarning == 3){
            newWarning =0;
            newRank--;
            al.UpdateEntries(Tables.users,Fields.rank,newRank+"",tmp);
            tmp = new ArrayList<>();
        }else {
            newWarning++;
        }
        al.UpdateEntries(Tables.users,Fields.warrings,newWarning+"",tmp);
        tmp = new ArrayList<>();
        tmp.add(new Pair(Fields.requestId,this.complaintId));
        al.UpdateEntries(Tables.complaintsRequests,Fields.isApproved, "'"+String.valueOf(complaintRequestStatus.approved+"'"),tmp);

        System.out.println("finish");




    }

    public void rejectComplaimt(ActionEvent actionEvent) {
        System.out.println("reject");
    }

    public void setDetails(String complaintId){
        this.complaintId = complaintId;
        AccessLayer al = new AccessLayer();
        ArrayList<Pair> tmp = new ArrayList<>();
        tmp.add(new Pair(Fields.requestId, complaintId));
        ArrayList<HashMap<String, String>> userCheck = al.ReadEntries(tmp, Tables.complaintsRequests);
        this.owner = userCheck.get(0).get(Fields.owner+"");
        this.subject = userCheck.get(0).get(Fields.complaintUser+"");
        complaintOwner.setText(" Request owner: "+ this.owner);
        complaintUser.setText(" complaint subject: "+ this.subject);
        String content = userCheck.get(0).get(Fields.content+"");
        complaintContent.insertText(0,content);

    }
}
