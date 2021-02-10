package az.javafx;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import az.javafx.dao.SubjectDao;
import az.javafx.dao.impl.SubjectDaoImpl;

import az.javafx.model.Subject;
import az.javafx.service.SubjectService;
import az.javafx.service.impl.SubjectServiceImpl;
import az.javafx.util.ConrtollerUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SubjectPageController extends GeneralSubjectController {

    SubjectDao subjectDAO = new SubjectDaoImpl();
    SubjectService subjectService = new SubjectServiceImpl(subjectDAO);
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button homeBtn;

        @FXML
        private Button teacherOppBtn;

        @FXML
        private Button studentOppBtn;

        @FXML
        private Button subjectOppBtn;

        @FXML
        private Button groupOppBtn;

        @FXML
        private Button logoutBtn;

        @FXML
        private TableView<Subject> subjectTable;

        @FXML
        private TableColumn<?, ?> idcol;

        @FXML
        private TableColumn<?, ?> subjectNamecol;

        @FXML
        private Button deleteBtn;

        @FXML
        private Button refreshBtn;

        @FXML
        private Button addBtn;

        @FXML
        private Button updateBtn;

        @FXML
        private Label errorMessage;

        @FXML
        void initialize() {
            printSubjects();
            addBtn.setOnAction(event -> {
                operation=addBtn.getText().toLowerCase();
                openSubjectAddForm();
            });
            refreshBtn.setOnAction(event -> {
                printSubjects();
            });
            updateBtn.setOnAction(event -> {
                operation=updateBtn.getText().toLowerCase();
                openSubjectUpdateForm();
            });
            deleteBtn.setOnAction(event->{
                deleteSubjectById();
                printSubjects();
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
    private void deleteSubjectById() {
        Long subjectid = subjectTable.getSelectionModel().getSelectedItem().getId();
        subjectService.deleteSubject(subjectid);
    }

    private void openSubjectAddForm() {
        selectedSubject = null;
        ConrtollerUtil.openNewScene(getClass().getResource("subjectForm.fxml"));
    }

    private void openSubjectUpdateForm() {
        try {
            Long  subjectid = subjectTable.getSelectionModel().getSelectedItem().getId();
            Subject subject = subjectService.getSubjectById(subjectid);


            selectedSubject = subject;
            errorMessage.setText(" ");
            ConrtollerUtil.openNewScene(getClass().getResource("subjectForm.fxml"));

        } catch (NullPointerException ex) {
            errorMessage.setText("Deyishiklik etmek ucun istifadeci secilmeyib");

        }
    }
    private void printSubjects() {
        List<Subject> subjectFromDb = subjectService.getAllSubjects();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        subjectNamecol .setCellValueFactory(new PropertyValueFactory<>("subject_name"));

        subjectTable.setItems(FXCollections.observableArrayList(subjectFromDb));
    }



        }



