package az.javafx;
import java.net.URL;
import java.util.ResourceBundle;


import az.javafx.dao.UserDao;
import az.javafx.dao.impl.UserDaoImpl;
import az.javafx.model.User;
import az.javafx.service.RegistrationService;
import az.javafx.service.impl.RegistrationServiceImpl;
import az.javafx.util.ConrtollerUtil;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
public class RegistrationController {
   UserDao  userDao = new UserDaoImpl();
  RegistrationService registrationService= new RegistrationServiceImpl(userDao);


  @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField firstnameField;

        @FXML
        private TextField usernameField;

        @FXML
        private Group genderField;

        @FXML
        private TextField surnameField;

        @FXML
        private TextField passwordField;

        @FXML
        private Button registrationBtn;

        @FXML
        void initialize() {
           registrationBtn.setOnAction(event->{
               User user=new User();
               user.setFirstname(firstnameField.getText());
               user .setUsername(usernameField.getText());
               user.setSurname(surnameField.getText());
             user.setPassword(passwordField.getText());
         if(registrationService.addUser(user)==true){
             registrationBtn.getScene().getWindow().hide();
             ConrtollerUtil.openNewScene(getClass().getResource("login.fxml"));
         }else{
             System.out.println("error");
         }

           });
        }
    }


