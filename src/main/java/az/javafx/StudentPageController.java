package az.javafx;

import az.javafx.dao.StudentDao;
import az.javafx.dao.impl.StudentDaoImpl;
import az.javafx.model.Student;
import az.javafx.service.StudentService;
import az.javafx.service.impl.StudentServiceImpl;
import az.javafx.util.ConrtollerUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentPageController extends GeneralStudentController {
    StudentDao studentDao = new StudentDaoImpl();
    StudentService studentService = new StudentServiceImpl(studentDao);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<?, ?> idcol;

    @FXML
    private TableColumn<?, ?> namecol;

    @FXML
    private TableColumn<?, ?> surnamecol;

    @FXML
    private TableColumn<?, ?> agecol;

    @FXML
    private TableColumn<?, ?> serianumcol;

    @FXML
    private TableColumn<?, ?> phonecol;

    @FXML
    private TableColumn<?, ?> emailcol;
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
        printStudents();
        addBtn.setOnAction(event -> {
            operation=addBtn.getText().toLowerCase();
            openStudentAddForm();
        });
        refreshBtn.setOnAction(event -> {
            printStudents();
        });
        updateBtn.setOnAction(event -> {
            operation=updateBtn.getText().toLowerCase();
            openStudentUpdateForm();
        });
        deleteBtn.setOnAction(event->{
            deleteStudentById();
            printStudents();
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

    private void deleteStudentById() {
        Long studentid = studentTable.getSelectionModel().getSelectedItem().getId();
        studentService.deleteStudent(studentid);
    }

    private void openStudentAddForm() {
        selectedStudent = null;
        ConrtollerUtil.openNewScene(getClass().getResource("studentForm.fxml"));
    }

    private void openStudentUpdateForm() {
        try {
            Long studentid = studentTable.getSelectionModel().getSelectedItem().getId();
            Student student = studentService.getStudentById(studentid);


            selectedStudent = student;
            errorMessage.setText(" ");
            ConrtollerUtil.openNewScene(getClass().getResource("studentForm.fxml"));

        } catch (NullPointerException ex) {
            errorMessage.setText("Deyishiklik etmek ucun istifadeci secilmeyib");

        }
    }

    private void printStudents() {
        List<Student> studentFromDb = studentService.getAllStudents();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnamecol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        agecol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        serianumcol.setCellValueFactory(new PropertyValueFactory<>("seriaNum"));
        phonecol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentTable.setItems(FXCollections.observableArrayList(studentFromDb));
    }
}


