package az.javafx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import az.javafx.dao.TeacherDao;
import az.javafx.dao.impl.TeacherDaoImpl;
import az.javafx.model.Teacher;
import az.javafx.service.TeacherService;
import az.javafx.service.impl.TeacherServiceImpl;
import az.javafx.util.ConrtollerUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeacherPageController extends GeneralTeacherController {
    TeacherDao teacherDao = new TeacherDaoImpl();
    TeacherService teacherService = new TeacherServiceImpl(teacherDao);


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Teacher> teacherTable;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> surnameCol;

    @FXML
    private TableColumn<?, ?> ageCol;

    @FXML
    private TableColumn<?, ?> serianumCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button refreshBtn;

    @FXML
    private Label errorMessage;

    @FXML
    void initialize() {
        printTeachers();
        addBtn.setOnAction(event -> {
            operation = addBtn.getText().toLowerCase();
            openTeacherAddForm();
        });
        refreshBtn.setOnAction(event -> {
            printTeachers();
        });
        updateBtn.setOnAction(event -> {
            operation = updateBtn.getText().toLowerCase();
            openTeacherUpdateForm();
        });
        deleteBtn.setOnAction(event->{
            deleteTeacherById();
            printTeachers();
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
    private void deleteTeacherById() {
        Long teacherid = teacherTable.getSelectionModel().getSelectedItem().getId();
        teacherService.deleteTeacher(teacherid);
    }

    private void openTeacherAddForm() {
        selectedTeacher = null;
        ConrtollerUtil.openNewScene(getClass().getResource("teacherForm.fxml"));
    }

    private void openTeacherUpdateForm() {
        try {
            Long teacherid = teacherTable.getSelectionModel().getSelectedItem().getId();
            Teacher teacher = teacherService.getTeacherById(teacherid);


            selectedTeacher = teacher;
            errorMessage.setText(" ");
            ConrtollerUtil.openNewScene(getClass().getResource("teacherForm.fxml"));


        } catch (NullPointerException ex) {
            errorMessage.setText("Deyishiklik etmek ucun istifadeci secilmeyib");

        }
    }

    private void printTeachers() {
        List<Teacher> teacherFromDb = teacherService.getAllTeachers();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        serianumCol.setCellValueFactory(new PropertyValueFactory<>("seriaNum"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        teacherTable.setItems(FXCollections.observableArrayList(teacherFromDb));
    }
}


