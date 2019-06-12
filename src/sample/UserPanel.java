package sample;

import javafx.event.ActionEvent;

public class UserPanel implements userAction {



    @Override
    public void AddEvent(ActionEvent actionEvent) {
        System.out.println("event added!");

    }

    @Override
    public void AddUpdate(ActionEvent actionEvent) {
        System.out.println("update added");
    }
}
