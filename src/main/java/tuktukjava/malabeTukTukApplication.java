package tuktukjava;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class malabeTukTukApplication extends Application{
    @Override
    public void start(Stage stage)throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(malabeTukTukApplication.class.getResource( "/com/example/tuktukapp/homePage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
