package az.javafx;

import az.javafx.util.ConrtollerUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    protected Button logoutBtn;

    @FXML
    protected Button subjectOppBtn;

    @FXML
    protected Button studentOppBtn;

    @FXML
    protected Button teacherOppBtn;

    @FXML
    protected Button groupOppBtn;
    @FXML
    protected Button homeBtn;

    @FXML
    void initialize() {
        studentOppBtn.setOnAction(event -> {
            studentOppBtn.getScene().getWindow().hide();
            ConrtollerUtil.openNewScene(getClass().getResource("studentPage.fxml"));
        });
        teacherOppBtn.setOnAction(event -> {
            teacherOppBtn.getScene().getWindow().hide();
            ConrtollerUtil.openNewScene(getClass().getResource("teacherPage.fxml"));
        });
        subjectOppBtn.setOnAction(event -> {
            subjectOppBtn.getScene().getWindow().hide();
            ConrtollerUtil.openNewScene(getClass().getResource("subjectPage.fxml"));
        });
        groupOppBtn.setOnAction(event -> {
            groupOppBtn.getScene().getWindow().hide();
            ConrtollerUtil.openNewScene(getClass().getResource("groupPage.fxml"));
        });
        logoutBtn.setOnAction(event -> {
            logoutBtn.getScene().getWindow().hide();
            ConrtollerUtil.openNewScene(getClass().getResource("login.fxml"));
        });
    }
}
