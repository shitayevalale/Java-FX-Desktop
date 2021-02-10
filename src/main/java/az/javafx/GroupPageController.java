package az.javafx;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import az.javafx.dao.GroupDao;
import az.javafx.dao.impl.GroupDaoImpl;
import az.javafx.model.Group;

import az.javafx.service.GroupService;
import az.javafx.service.impl.GroupServiceImpl;
import az.javafx.util.ConrtollerUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GroupPageController extends GeneralGroupController {
    GroupDao groupDao=new GroupDaoImpl();
    GroupService groupService=new GroupServiceImpl(groupDao);

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button homeBtn;

        @FXML
        private Button studentOppBtn;

        @FXML
        private Button teacherOppBtn;

        @FXML
        private Button subjectOppBtn;

        @FXML
        private Button groupOppBtn;

        @FXML
        private Button logoutBtn;

        @FXML
        private TableView<Group> groupTable;

        @FXML
        private TableColumn<?, ?> idcol;

        @FXML
        private TableColumn<?, ?> groupNamecol;

        @FXML
        private Label errorMessage;

        @FXML
        private Button refreshBtn;

        @FXML
        private Button addBtn;

        @FXML
        private Button updateBtn;

        @FXML
        private Button deleteBtn;

        @FXML
        void initialize() {

            printGroups();
            addBtn.setOnAction(event -> {
                operation=addBtn.getText().toLowerCase();
                openGroupAddForm();
            });
            refreshBtn.setOnAction(event -> {
                printGroups();
            });
            updateBtn.setOnAction(event -> {
                operation=updateBtn.getText().toLowerCase();
                openGroupUpdateForm();
            });
            deleteBtn.setOnAction(event->{
                deleteGroupById();
                printGroups();
            });
            homeBtn.setOnAction(event -> {
                homeBtn.getScene().getWindow().hide();
                ConrtollerUtil.openNewScene(getClass().getResource("main.fxml"));
            });
            logoutBtn.setOnAction(event -> {
                logoutBtn.getScene().getWindow().hide();
                ConrtollerUtil.openNewScene(getClass().getResource("login.fxml"));
            });
        }
    private void deleteGroupById() {
        Long groupid = groupTable.getSelectionModel().getSelectedItem().getId();
        groupService.deleteGroup(groupid);
    }

    private void openGroupAddForm() {
        selectedGroup= null;
        ConrtollerUtil.openNewScene(getClass().getResource("groupForm.fxml"));
    }

    private void openGroupUpdateForm() {
        try {
            Long  groupid = groupTable.getSelectionModel().getSelectedItem().getId();
           Group group= groupService.getGroupById(groupid);


            selectedGroup = group;
            errorMessage.setText(" ");
            ConrtollerUtil.openNewScene(getClass().getResource("groupForm.fxml"));

        } catch (NullPointerException ex) {
            errorMessage.setText("Deyishiklik etmek ucun group secilmeyib");

        }
    }
    private void printGroups() {
        List<Group> groupFromDb = groupService.getAllGroups();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupNamecol .setCellValueFactory(new PropertyValueFactory<>("group_name"));

        groupTable.setItems(FXCollections.observableArrayList(groupFromDb));
    }
    }


