package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class representiveLogIn {
    public javafx.scene.control.Button btn_LogIn;
    public javafx.scene.control.TextField f_UserName;
    public javafx.scene.control.TextField f_Password;
    private Stage stage;
    private Stage mainStage;




    public void Login(ActionEvent event) {
        AccessLayer al = new AccessLayer();
        ArrayList<Pair> tmp = new ArrayList<>();
        String user = f_UserName.getText();
        String pass = f_Password.getText();
        tmp.add(new Pair(Fields.representiveName,user));
        tmp.add(new Pair(Fields.representivePassword,pass));
        ArrayList<HashMap<String, String>> userCheck = al.ReadEntries(tmp, Tables.representive);
        HashMap<String, String> response = userCheck.get(0);
        if(response.get(Fields.representiveName+"").equals(user)) {
            if (response.get(Fields.representivePassword + "").equals(pass)) {
                stage.close();//close the log in window
                openRepresentiveStage();//open the event creation window
            }
        }
        else{showAlert("One or more details are incorrect"); }
    }

    private void openRepresentiveStage() {
        Stage stage=new Stage();
        stage.setTitle("addEvent");
        FXMLLoader fxmlLoader=new FXMLLoader();
        try {
            Parent root=fxmlLoader.load(getClass().getResource("addEvent.fxml").openStream());
            Scene scene=new Scene(root,600,600);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            addEvent event=fxmlLoader.getController();
            event.init(f_UserName.getText(),this.stage,mainStage,stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    public void setStage(Stage stage1, Stage mainStage) {
        this.stage = stage1;
        this.mainStage=mainStage;
    }
}
