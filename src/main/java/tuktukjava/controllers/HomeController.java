package tuktukjava.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.stage.Stage;
import tuktukjava.Dealer;
import tuktukjava.Inventory;
import tuktukjava.Item;
import tuktukjava.RandomDealers;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeController {
    public Button cartDisBtn;
    public Button SearchBtn;
    private Inventory inventory;
    private RandomDealers randomDealers;

    @FXML public Label welcomeText;
    @FXML public Button AddBtn;
    @FXML public Button DeleteBtn;
    @FXML public Button UpdateBtn;
    @FXML public Button ViewInventoryBtn;
    @FXML public Button DealerViewerBtn;

    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public void setDealers(RandomDealers randomDealers) { this.randomDealers = randomDealers; }

    @FXML
    public void onAddBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/addItem-view.fxml")));
        Parent root = loader.load();
        AddItemController controller = loader.getController();
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
    }

    @FXML
    public void onDeleteBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/deleteItem-view.fxml")));
        Parent root = loader.load();
        stage.setScene(new Scene(root, 700, 400));
    }

    @FXML
    public void onUpdateBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/update-view.fxml")));
        Parent root = loader.load();
        UpdateController controller = loader.getController();
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
    }

    @FXML
    public void onviewBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/inventory-view.fxml")));
        Parent root = loader.load();
        InventoryController controller = loader.getController();
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
    }

    @FXML
    public void onDealerBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/dealer-view.fxml")));
        Parent root = loader.load();
        DealerController controller = loader.getController();
        controller.setRandomDealers(this.randomDealers);
        stage.setScene(new Scene(root, 730, 500));
    }

    public void onCartBtnClick(ActionEvent actionEvent) {
    }

    public void onSearchBtnClick(ActionEvent actionEvent) {
    }
}