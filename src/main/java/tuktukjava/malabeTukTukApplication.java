package tuktukjava;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tuktukjava.controllers.HomeController;

import java.io.IOException;
import java.util.List;

public class malabeTukTukApplication extends Application{


    @Override
    public void start(Stage stage) throws IOException {
        List<Item> items = FileManager.loadItems();
        List<Dealer> dealers = FileManager.loadDealers();

        Inventory inventory = new Inventory(items);
        RandomDealers randomDealers = new RandomDealers(dealers);

        FXMLLoader loader = new FXMLLoader(malabeTukTukApplication.class.getResource("/com/example/tuktukapp/homePage-view.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        controller.setInventory(inventory);
        controller.setDealers(randomDealers);

        stage.setScene(new Scene(root, 730, 500));
        stage.show();
    }
}
