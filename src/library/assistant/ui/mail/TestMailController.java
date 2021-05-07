package library.assistant.ui.mail;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import library.assistant.alert.AlertMaker;
import library.assistant.data.callback.GenericCallback;
import library.assistant.data.model.MailServerInfo;
import library.assistant.email.EmailUtil;
import library.assistant.util.LibraryAssistantUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Villan
 */
public class TestMailController implements Initializable, GenericCallback {

    private final static Logger LOGGER = LogManager.getLogger(TestMailController.class.getName());

    @FXML
    private JFXTextField recepientAddressInput;
    @FXML
    private JFXProgressBar progressBar;

    private MailServerInfo mailServerInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setMailServerInfo(MailServerInfo mailServerInfo) {
        this.mailServerInfo = mailServerInfo;
    }

    @FXML
    private void handleStartAction() {
        String toAddress = recepientAddressInput.getText();
        if (LibraryAssistantUtil.validateEmailAddress(toAddress)) {
            EmailUtil.sendTestMail(mailServerInfo, toAddress, this);
            progressBar.setVisible(true);
        } else {
            AlertMaker.showErrorMessage("Failed", "Invalid email address!");
        }
    }

    @Override
    public Object taskCompleted(Object val) {
        LOGGER.log(Level.INFO, "Callback received from Email Sender client {}", val);
        boolean result = (boolean) val;
        Platform.runLater(() -> {
            if (result) {
                AlertMaker.showSimpleAlert("Success", "Email successfully sent!");
            } else {
                AlertMaker.showErrorMessage("Failed", "Something went wrong!");
            }
            progressBar.setVisible(false);
        });
        return true;
    }

}
