package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        AccessLayer al = new AccessLayer();
        al.connectDB("C:\\Users\\Daniel\\dbEmer.db");
        ArrayList<Pair> a = new ArrayList<>();
//        a.add(new Pair(Fields.userName,"netanco"));
//        a.add(new Pair(Fields.name,"netanelCohen"));
//        a.add(new Pair(Fields.password,"a1254a"));
//        a.add(new Pair(Fields.rank,"4.5"));
//        a.add(new Pair(Fields.score,"3.5"));
//        a.add(new Pair(Fields.userStatus,"blabla"));
//        a.add(new Pair(Fields.email,"blabla@bla.com"));
        a.add(new Pair(Fields.password,"123456"));
        al.DeleteEntry(Tables.users,a);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
