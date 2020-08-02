package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CompanyController {

    @FXML
    private TableView<?> tbl_products;

    @FXML
    private TableColumn<?, ?> tc_name;

    @FXML
    private TableColumn<?, ?> tc_category;

    @FXML
    private TableColumn<?, ?> tc_price;

    @FXML
    private TableColumn<?, ?> tc_quantity;

    @FXML
    private TextField tf_search;

    @FXML
    private CheckBox cb_less5;

    @FXML
    private CheckBox cb_medium;

    @FXML
    private CheckBox cb_more10;

    @FXML
    private ComboBox<?> combo_category;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    void addAction(ActionEvent event) {

    }

    @FXML
    void closeAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void deleteAction(ActionEvent event) {

    }

    @FXML
    void filterAction(ActionEvent event) {

    }

    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));

        primaryStage.setTitle("Logowanie");
        primaryStage.initStyle(StageStyle.UNDECORATED); // brak przyciskow w tytule okna
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true); // brak skalowania
        primaryStage.show();
// zamkniecie okna logowania
        Stage loginStage = (Stage) btn_delete.getScene().getWindow();
        loginStage.close();
    }

    @FXML
    void selectAction(MouseEvent event) {

    }

    @FXML
    void updateAction(ActionEvent event) {

    }

}

