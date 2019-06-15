package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
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
        tmp.add(new Pair(Fields.userName, user));
        tmp.add(new Pair(Fields.password, pass));
        ArrayList<HashMap<String, String>> userCheck = al.ReadEntries(tmp, Tables.users);
        try {
            HashMap<String, String> response = userCheck.get(0);
            if (response.get(Fields.userName + "").equals(user))
                if (response.get(Fields.password + "").equals(pass)) {
                    //need to check if user is admin if its admin openn admin panel else open user panel
                    String x = response.get(Fields.isAdmin + "");
                    Parent root;
                    if (x.equals("true")) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            root = fxmlLoader.load(getClass().getResource("adminPanel.fxml").openStream());
                            Stage stage = new Stage();
                            stage.setTitle("Admin Panel");
                            stage.setScene(new Scene(root, 800, 600));
                            AdminPanel rfv = fxmlLoader.getController();
                            stage.show();
                            ((Node) (event.getSource())).getScene().getWindow().hide();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            root = fxmlLoader.load(getClass().getResource("userPanel.fxml").openStream());
                            Stage stage = new Stage();
                            stage.setTitle("User Panel");
                            stage.setScene(new Scene(root, 800, 600));
                            UserPanel rfv = fxmlLoader.getController();
                            rfv.setLoogedUser(user);
                            stage.show();
                            ((Node) (event.getSource())).getScene().getWindow().hide();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR happen during login");
            alert.show();
        }
        }

    }

