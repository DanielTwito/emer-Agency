package sample;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;

public class AdminPanel implements  userAction {
    public ButtonBar buttonbar;
    public Button addUser;
    public Label recTitle;
    public Label warning;
    public TableView table;
    public Button addEvent;
    private String loggedUser;


    TableColumn<ComplaintRequest, String> id;
    TableColumn<ComplaintRequest, String> owner;
    TableColumn<ComplaintRequest, String> subject;
    TableColumn<ComplaintRequest, Boolean> isApproved;
    TableColumn<ComplaintRequest, String> complaintInfo;
    TableColumn<ComplaintRequest, String> buttons;

    private AccessLayer al = new AccessLayer();


    @FXML
    public void initialize(){
        iniTable();
    }//end initialize

    public void addNewUser(ActionEvent actionEvent) {
        System.out.println("not implemented in this assignment");
    }



    public void setRecommendedListings() {
        ObservableList<ComplaintRequest> list = FXCollections.observableArrayList(getComplaintRequests());
        table.setItems(list);
    }

    private List<ComplaintRequest> getComplaintRequests() {
//        ArrayList<HashMap<String, String>> a = new ArrayList<>();
        ArrayList<Pair> a = new ArrayList<>();
        a.add(new Pair(Fields.userName,this.loggedUser+""));
        ArrayList<HashMap<String, String>> ResList = al.ReadEntries(a,Tables.organizationMembers);
        String adminOrg = ResList.get(0).get(Fields.organization+"");

        a = new ArrayList<>();
        a.add(new Pair(Fields.organization,adminOrg));
        ResList = al.ReadEntries(a,Tables.complaintsRequests);
        return getComplaintRequestList(ResList);
    }

    private List<ComplaintRequest> getComplaintRequestList(ArrayList<HashMap<String, String>> ResList) {
        List<ComplaintRequest> l = new LinkedList<>();
        for(HashMap<String, String> paired : ResList)
        {

            StringProperty owner = new SimpleStringProperty(paired.get(Fields.owner+""));
            StringProperty subject =  new SimpleStringProperty(paired.get(Fields.complaintUser+""));
            StringProperty content =   new SimpleStringProperty(paired.get(Fields.content+""));
//            boolean f =   paired.get(Fields.isApproved+"").equals ("waiting");
            StringProperty isApp  = new SimpleStringProperty( "waiting" );
            StringProperty reqId =     new SimpleStringProperty(paired.get(Fields.requestId+""));
           l.add(new ComplaintRequest(content,
               subject,
               owner,
               isApp,
               reqId));
        }
        return l;
    }

    private void iniTable() {
        id = new TableColumn<ComplaintRequest, String>("ID");

        owner = new TableColumn<ComplaintRequest, String>("owner");

        subject = new TableColumn<ComplaintRequest, String>("subject");

        isApproved = new TableColumn<ComplaintRequest, Boolean>("is Approved");

        buttons = new TableColumn<ComplaintRequest, String>("View complaint");
        complaintInfo = new TableColumn<ComplaintRequest, String>("complaint info");

        id.setPrefWidth(100);
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        owner.setPrefWidth(100);
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        subject.setPrefWidth(100);
        isApproved.setCellValueFactory(new PropertyValueFactory<>("isApproved"));
//
        isApproved.setPrefWidth(100);
        complaintInfo.setCellValueFactory(new PropertyValueFactory<>("content"));
        complaintInfo.setPrefWidth(100);
        buttons.setCellValueFactory(new PropertyValueFactory<>("ID"));
        buttons.setCellFactory(col -> new TableCell<ComplaintRequest, String>(){
            Button button = new Button("View");
            {
                button.setMaxHeight(17);
                button.setMaxWidth(130);
                button.setStyle("-fx-background-color: deepskyblue");
                setGraphic(button);
            }

            @Override
            protected void updateItem(String item, boolean empty)
            {
                if(empty || item == null) {
                    setGraphic(null);
                }
                else {
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            Parent root;
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                root = fxmlLoader.load(getClass().getResource("ViewComplaintRequest.fxml").openStream());
                                Stage stage = new Stage();
                                stage.setTitle("view complaint contant");
                                stage.setScene(new Scene(root, 600, 600));
                                ViewComplaintRequest viewComplaintRequest = fxmlLoader.getController();
                                viewComplaintRequest.setDetails(item);
                                stage.show();

                            } catch (IOException x) {
//                                x.printStackTrace();
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setTitle("complaint not found");
                                a.setContentText("The selected complaint was not found");
                                a.setHeaderText("complaint not found");
                                a.show();
                            }
                        }
                    });
                    button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent e) {
                                    button.setEffect(new DropShadow());
                                    button.setStyle("-fx-background-color: cyan");
                                }
                            });
                    button.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent e) {
                                    button.setEffect(null);
                                    button.setStyle("-fx-background-color: deepskyblue");
                                }
                            });

                    setGraphic(button);
                }
            }
        });
        buttons.setPrefWidth(140);

        complaintInfo.setSortType(TableColumn.SortType.DESCENDING);
        subject.setSortType(TableColumn.SortType.DESCENDING);

        table.setMaxWidth(650);
        table.setPrefWidth(650);
        table.getColumns().addAll(id, owner, subject, isApproved, complaintInfo, buttons);
    }





    @Override
    public void AddEvent(ActionEvent actionEvent) {

        System.out.println("event added!!");

    }

    @Override
    public void AddUpdate(ActionEvent actionEvent) {
        System.out.println("update added!");
    }

    public void setLoggedUser(String user) {
        this.loggedUser = user;
    }

}
