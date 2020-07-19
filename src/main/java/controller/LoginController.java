package controller;

// klasa obslugujaca zadania uzytkownika
/**
 * Sample Skeleton for 'loginView.fxml' Controller Class
 */



        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.PasswordField;
        import javafx.scene.control.TextField;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tfLogin"
    private TextField tfLogin; // Value injected by FXMLLoader

    @FXML // fx:id="pfPassword"
    private PasswordField pfPassword; // Value injected by FXMLLoader

    @FXML
    void loginAction(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tfLogin != null : "fx:id=\"tfLogin\" was not injected: check your FXML file 'loginView.fxml'.";
        assert pfPassword != null : "fx:id=\"pfPassword\" was not injected: check your FXML file 'loginView.fxml'.";

    }
}
