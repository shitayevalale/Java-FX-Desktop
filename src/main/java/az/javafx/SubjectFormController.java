package az.javafx;
import java.net.URL;
import java.util.ResourceBundle;
import az.javafx.dao.SubjectDao;
import az.javafx.dao.impl.SubjectDaoImpl;
import az.javafx.exceptions.SeriaNumExceptions;
import az.javafx.model.Subject;
import az.javafx.service.SubjectService;
import az.javafx.service.impl.SubjectServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
public class SubjectFormController extends GeneralSubjectController {
        SubjectDao subjectDAO = new SubjectDaoImpl();
        SubjectService subjectService = new SubjectServiceImpl(subjectDAO);

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField subjectNameField;

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
                                setSubject(operation);
                        }else if(operation.equals("update")){
                                setSubject(operation);
                        }

                });
                printSelectedSubject();
        }
        private void printSelectedSubject() {
                if (selectedSubject != null) {
                        System.out.println(selectedSubject);
                        subjectNameField.setText(selectedSubject.getSubject_name());
                }
        }
        private void setSubject(String operation) {
   Subject subject=new Subject();
                boolean errorNotFound = true;
                try {
                        subject.setSubject_name(subjectNameField.getText());

                } catch (SeriaNumExceptions ex) {
                        errorNotFound = false;
                         subjectNameField.clear();
                         subjectNameField.setPromptText(ex.getMessage());
                }

                if (errorNotFound) {
                        if(operation.equals("add")){
                                subjectService.addSubject(subject);
                                successMessage.setText("Subject ugurla elave edildi");
                        }else if(operation.equals("update")){
                                subject.setId(selectedSubject.getId());
                                subjectService.updateSubjectById(subject);
                                successMessage.setText("Deyishiklik ugurla yerine yetirildi");
                        }

                         subjectNameField.clear();
                }
        }

}


