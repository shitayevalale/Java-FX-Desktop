package az.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import az.javafx.dao.UserDao;
import az.javafx.dao.impl.UserDaoImpl;
import az.javafx.model.Credential;
import az.javafx.service.LoginService;
import az.javafx.service.impl.LoginServiceImpl;
import az.javafx.util.ConrtollerUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class LoginController {
    UserDao userDao = new UserDaoImpl();
    LoginService loginService = new LoginServiceImpl(userDao);
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registrationBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML

    void initialize() {
        registrationBtn.setOnAction(event -> {
            registrationBtn.getScene().getWindow().hide();
            ConrtollerUtil.openNewScene(getClass().getResource("registration.fxml"));
        });
        loginBtn.setOnAction(event -> {
            if (usernameField.getText() != null && passwordField.getText() != null) {
                boolean login = loginService.login(new Credential(usernameField.getText(), passwordField.getText()));
                if (login == true) {
                    registrationBtn.getScene().getWindow().hide();
                    ConrtollerUtil.openNewScene(getClass().getResource("main.fxml"));
                } else {
                    System.out.println("username or password is wrong");
                }

            } else {
                System.out.println("username or password is empty");
            }


        });


    }
}


