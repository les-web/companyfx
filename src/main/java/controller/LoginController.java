package controller;

// klasa obslugujaca zadania uzytkownika
/**
 * Sample Skeleton for 'loginView.fxml' Controller Class
 */


import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utility.UserData;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tfLogin"
    private TextField tfLogin; // Value injected by FXMLLoader

    @FXML // fx:id="pfPassword"
    private PasswordField pfPassword; // Value injected by FXMLLoader
//metoda dostarczajaca logike  do logowania

    private void login() {
        boolean isLogged = UserData.users.stream()
                .anyMatch(user ->
                        user.getEmail().equals(tfLogin.getText()) &&
                                user.getPassword().equals(pfPassword.getText()));
        if (isLogged) {
            System.out.println("zalogowano");
            tfLogin.setStyle(null);
            pfPassword.setStyle(null);
        } else {
            System.out.println("nie zalogowano");
            tfLogin.clear();
            pfPassword.clear();

            tfLogin.setStyle("-fx-border-color: red; -fx-border-width: 3px");
            pfPassword.setStyle("-fx-border-color: red; -fx-border-width: 3px");
        }
    }

    @FXML
    void loginAction(ActionEvent event) {
        login();
    }

    @FXML
        // wcisniecie Enter w dowolnym miejscu na oknie aplikacji (vbox)
    void keyLoginAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            Platform.exit(); // zamkniecie okna
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tfLogin != null : "fx:id=\"tfLogin\" was not injected: check your FXML file 'loginView.fxml'.";
        assert pfPassword != null : "fx:id=\"pfPassword\" was not injected: check your FXML file 'loginView.fxml'.";

    }
}
