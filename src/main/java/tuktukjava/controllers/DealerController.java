package tuktukjava.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tuktukjava.Dealer;
import tuktukjava.Inventory;
import tuktukjava.RandomDealers;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DealerController {

    @FXML private Label name1;
    @FXML private Label name2;
    @FXML private Label name3;
    @FXML private Label name4;

    @FXML private Label codeLocation1;
    @FXML private Label codeLocation2;
    @FXML private Label codeLocation3;
    @FXML private Label codeLocation4;

    @FXML private Label phone1;
    @FXML private Label phone2;
    @FXML private Label phone3;
    @FXML private Label phone4;
    private Inventory inventory;
    private RandomDealers dealers;

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    public void setRandomDealers(RandomDealers randomDealers) {
        this.dealers = randomDealers;
        generateCards();
    }

    public void generateCards() {
        List<Dealer> randDealers = dealers.getSortedByLocation();

        Label[] names         = {name1, name2, name3, name4};
        Label[] codeLocations = {codeLocation1, codeLocation2, codeLocation3, codeLocation4};
        Label[] phones        = {phone1, phone2, phone3, phone4};

        for (int i = 0; i < randDealers.size(); i++) {
            names[i].setText(randDealers.get(i).getName());
            codeLocations[i].setText(randDealers.get(i).getId()+ " · " + randDealers.get(i).getLocation());
            phones[i].setText(randDealers.get(i).getPhone());
        }
    }

    public void onReshuffleBtnClick(ActionEvent actionEvent) {
        generateCards();
    }

    public void onBackBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/homePage-view.fxml")));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setDealers(dealers);
        controller.setInventory(inventory);
        stage.setScene(new Scene(root, 730, 500));
        stage.show();
    }
}