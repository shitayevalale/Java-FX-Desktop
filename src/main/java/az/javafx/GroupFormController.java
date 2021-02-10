package az.javafx;
import java.net.URL;
import java.util.ResourceBundle;

import az.javafx.dao.GroupDao;
import az.javafx.dao.impl.GroupDaoImpl;
import az.javafx.exceptions.SeriaNumExceptions;
import az.javafx.model.Group;

import az.javafx.service.GroupService;
import az.javafx.service.impl.GroupServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
public class GroupFormController extends GeneralGroupController{
    GroupDao groupDao=new GroupDaoImpl();
    GroupService groupService=new GroupServiceImpl(groupDao);

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField groupNamreField;

        @FXML
        private Button saveBtn;

        @FXML
        private Button ignoreBtn;

        @FXML
        private Label successMessage;

        @FXML
        void initialize() {
            saveBtn.setOnAction(event -> {
                if(operation.equals("add")) {
                    setGroup(operation);
                }else if(operation.equals("update")){
                    setGroup(operation);
                }

            });
            printSelectedGroup();
        }
    private void printSelectedGroup() {
        if (selectedGroup != null) {
            System.out.println(selectedGroup);
            groupNamreField.setText(selectedGroup.getGroup_name());
        }
        }
    private void setGroup(String operation) {
       Group group= new Group();
        boolean errorNotFound = true;
        try {
            group.setGroup_name(groupNamreField.getText());

        } catch (SeriaNumExceptions ex) {
            errorNotFound = false;
             groupNamreField.clear();
             groupNamreField.setPromptText(ex.getMessage());
        }

        if (errorNotFound) {
            if(operation.equals("add")){
             groupService.addGroup(group);
                successMessage.setText("Group ugurla elave edildi");
            }else if(operation.equals("update")){
                group.setId(selectedGroup.getId());
                groupService.updateGroupById(group);
                successMessage.setText("Deyishiklik ugurla yerine yetirildi");
            }

            groupNamreField.clear();
        }
    }
    }


