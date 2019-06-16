package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class mainScreen {
    public javafx.scene.control.Button btn_LogInAsUser;
    public javafx.scene.control.Button btn_LogInAsAdmin;
    public javafx.scene.control.Button btn_LogInAsRepresentive;
    private Stage stage;

    public void Login(ActionEvent event) {
        Stage stage=new Stage();
        stage.setTitle("Log In As Representor");
        FXMLLoader fxmlLoader=new FXMLLoader();
        try {
            Parent root=fxmlLoader.load(getClass().getResource("representiveLogIn.fxml").openStream());
            Scene scene=new Scene(root,821,462);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            representiveLogIn representiveLogIn=fxmlLoader.getController();
            representiveLogIn.setStage(stage,this.stage);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void LoginasUser(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            root = fxmlLoader.load(getClass().getResource("loginForm.fxml").openStream());
            Stage stage = new Stage();
            stage.setTitle("user Login");
            stage.setScene(new Scene(root, 450, 300));
            LoginForm rfv = fxmlLoader.getController();

            stage.show();
//            root.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
