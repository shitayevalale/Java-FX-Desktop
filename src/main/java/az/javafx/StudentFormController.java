package az.javafx;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import az.javafx.dao.StudentDao;
import az.javafx.dao.impl.StudentDaoImpl;
import az.javafx.exceptions.*;
import az.javafx.model.Student;
import az.javafx.service.StudentService;
import az.javafx.service.impl.StudentServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;

public class StudentFormController extends GeneralStudentController {

    StudentDao studentDao = new StudentDaoImpl();
    StudentService studentService = new StudentServiceImpl(studentDao);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField phoneField;

    @FXML
    private Group genderField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField seriaNumField;

    @FXML
    private DatePicker dobField;

    @FXML
    private Button ignoreBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Label successMessage;


    @FXML
    private RadioButton femaleRadio;

    @FXML
    private RadioButton maleRadio;

    @FXML
    void initialize() {
        saveBtn.setOnAction(event -> {
            if (operation.equals("add")) {
                setStudent(operation);
            } else if (operation.equals("update")) {
                setStudent(operation);
            }

        });
        printSelectedStudent();

    }

    private void printSelectedStudent() {
        if (selectedStudent != null) {
            System.out.println(selectedStudent);
            firstnameField.setText(selectedStudent.getName());
            surnameField.setText(selectedStudent.getSurname());
            dobField.setValue(LocalDate.parse(selectedStudent.getDOB()));
            seriaNumField.setText(selectedStudent.getSeriaNum());
            emailField.setText(selectedStudent.getEmail());
            phoneField.setText(selectedStudent.getPhone());
//            genderField.setText(selectedStudent.getGender());

        }
    }

    private void setStudent(String operation) {
        Student student = new Student();
        boolean errorNotFound = true;
        String gender = "";
        try {

            if (femaleRadio.selectedProperty().getValue() && maleRadio.selectedProperty().getValue()) {
                System.out.println("error");
                throw new GenderNotValidException("GenderNotValidException");

            } else if (maleRadio.selectedProperty().getValue()) {
                gender = "M";
            } else if (femaleRadio.selectedProperty().getValue()) {

                gender = "F";
            } else {
                throw new GenderNotValidException("GenderNotValidException");
            }

        } catch (Exception ex) {
            errorNotFound = false;
        }


        try {
            student.setName(firstnameField.getText());
            student.setSurname(surnameField.getText());
            student.setDOB(String.valueOf(dobField.getValue()));
            student.setEmail(emailField.getText());
            student.setPhone(phoneField.getText());
            student.setSeriaNum(seriaNumField.getText());
//            student.setGender(genderField.getText());
            student.setGender(gender);
        } catch (SeriaNumExceptions ex) {
            errorNotFound = false;
            seriaNumField.clear();
            seriaNumField.setPromptText(ex.getMessage());
        } catch (NameException ex) {
            errorNotFound = false;
            firstnameField.clear();
            firstnameField.setPromptText(ex.getMessage());
        } catch (SurnameException ex) {
            errorNotFound = false;
            surnameField.clear();
            surnameField.setPromptText(ex.getMessage());
        } catch (DOBException ex) {
            errorNotFound = false;
            dobField.setPromptText(ex.getMessage());
        } catch (PhoneException ex) {
            errorNotFound = false;
            phoneField.clear();
            phoneField.setPromptText(ex.getMessage());
        } catch (EmailException ex) {
            errorNotFound = false;
            emailField.clear();
            emailField.setPromptText(ex.getMessage());
        }
//        catch (EmailException ex) {
//            errorNotFound = false;
//            genderField.clear();
//            genderField.setPromptText(ex.getMessage());}
        if (errorNotFound) {
            if (operation.equals("add")) {
                studentService.addStudent(student);
                successMessage.setText("Telebe ugurla elave edildi");
            } else if (operation.equals("update")) {
                student.setId(selectedStudent.getId());
                studentService.updateStudentById(student);
                successMessage.setText("Deyishiklik ugurla yerine yetirildi");
            }

            seriaNumField.clear();
            firstnameField.clear();
            surnameField.clear();
            phoneField.clear();
            emailField.clear();
            dobField.getEditor().clear();
//        genderField.clear();
        }
    }


}


