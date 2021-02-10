package az.javafx;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import az.javafx.dao.TeacherDao;
import az.javafx.dao.impl.TeacherDaoImpl;
import az.javafx.exceptions.*;
import az.javafx.model.Group;
import az.javafx.model.Teacher;
import az.javafx.service.TeacherService;
import az.javafx.service.impl.TeacherServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TeacherFormController extends GeneralTeacherController {
    TeacherDao teacherDao = new TeacherDaoImpl();
    TeacherService teacherService = new TeacherServiceImpl(teacherDao);


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField seriaNumField;

    @FXML
    private DatePicker dobField;

    @FXML
    private Label ignoreBtn;

    @FXML
    private Label successMessage;
    @FXML
    private Button saveBtn;

//    @FXML
//    private Group genderField;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private RadioButton maleRadio;

    @FXML
    void initialize() {
        saveBtn.setOnAction(event -> {
            if (operation.equals("add")) {
                setTeacher(operation);
            } else if (operation.equals("update")) {
                setTeacher(operation);
            }

        });
        printSelectedTeacher();

    }

    private void printSelectedTeacher() {
        if (selectedTeacher != null) {
            System.out.println(selectedTeacher);
            firstnameField.setText(selectedTeacher.getName());
            surnameField.setText(selectedTeacher.getSurname());
            dobField.setValue(LocalDate.parse(selectedTeacher.getDOB()));
            seriaNumField.setText(selectedTeacher.getSeriaNum());
            emailField.setText(selectedTeacher.getEmail());
            phoneField.setText(selectedTeacher.getPhone());

        }
    }

    private void setTeacher(String operation) {
        Teacher teacher = new Teacher();
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
            teacher.setName(firstnameField.getText());
            teacher.setSurname(surnameField.getText());
            teacher.setDOB(String.valueOf(dobField.getValue()));
            teacher.setEmail(emailField.getText());
            teacher.setPhone(phoneField.getText());
            teacher.setSeriaNum(seriaNumField.getText());
            teacher.setGender(gender);
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
        if (errorNotFound) {
            if (operation.equals("add")) {
                teacherService.addTeacher(teacher);
                successMessage.setText("Muellim ugurla elave edildi");
            } else if (operation.equals("update")) {
                teacher.setId(selectedTeacher.getId());
                teacherService.updateTeacherById(teacher);
                successMessage.setText("Deyishiklik ugurla yerine yetirildi");
            }

            seriaNumField.clear();
            firstnameField.clear();
            surnameField.clear();
            phoneField.clear();
            emailField.clear();
            dobField.getEditor().clear();

        }
    }

}


