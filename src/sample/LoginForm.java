package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;


public class LoginForm {

    public TextField userName;
    public PasswordField logPassword;
    private AccessLayer al;

    public void Login(ActionEvent event) {
        AccessLayer al = new AccessLayer();
        ArrayList<Pair> tmp = new ArrayList<>();
        String user = userName.getText();
        String pass = logPassword.getText();
        tmp.add(new Pair(Fields.userName,user));
        tmp.add(new Pair(Fields.password,pass));
        ArrayList<HashMap<String, String>> userCheck = al.ReadEntries(tmp, Tables.users);
        HashMap<String, String> response = userCheck.get(0);
        if(response.get(Fields.userName+"").equals(user))
            if(response.get(Fields.password+"").equals(pass)) {
                //need to check if user is admin if its admin openn admin panel else open user panel
                System.out.println("LOgin!!");
            }
        }
    }

