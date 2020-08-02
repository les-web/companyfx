import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() {
        System.out.println("Metoda init");

    }

    public void start(Stage primaryStage) throws Exception {
        System.out.println("Metoda start");
//        Parent root = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));

        primaryStage.setTitle("Logowanie");

        primaryStage.setScene(new Scene(root));
   //     primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Metoda stop");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
