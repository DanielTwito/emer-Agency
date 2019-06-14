package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class mainScreen {
    public javafx.scene.control.Button btn_LogInAsUser;
    public javafx.scene.control.Button btn_LogInAsAdmin;
    public javafx.scene.control.Button btn_LogInAsRepresentive;

    public void Login(ActionEvent event) {
        Stage stage=new Stage();
        stage.setTitle("Log In As Representor");
        FXMLLoader fxmlLoader=new FXMLLoader();
        try {
            Parent root=fxmlLoader.load(getClass().getResource("representiveLogIn.fxml").openStream());
            Scene scene=new Scene(root,600,600);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            representiveLogIn representiveLogIn=fxmlLoader.getController();
            representiveLogIn.setStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    }
