package tuktukjava.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tuktukjava.Inventory;
import tuktukjava.Item;
import tuktukjava.RandomDealers;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SearchController {

    @FXML
    private TextField brandField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField maxPriceField;

    @FXML
    private TextField minPriceField;

    @FXML
    private TextField minQtyField;

    @FXML
    private TextField nameField;

    @FXML
    private VBox resultBox;

    @FXML
    private Label resultLabel;
    private RandomDealers dealers;
    private Inventory inventory;
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    public void setDealers(RandomDealers randomDealers) {
        this.dealers = randomDealers;
    }

    @FXML
    void onBackBtnClick(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/homePage-view.fxml")));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setInventory(this.inventory);
        controller.setDealers(this.dealers);
        stage.setScene(new Scene(root, 730, 500));
        stage.show();
    }

    @FXML
    void onClearBtnClick(ActionEvent event) {
        nameField.clear();
        brandField.clear();
        categoryField.clear();
        minPriceField.clear();
        maxPriceField.clear();
        minQtyField.clear();
        resultBox.getChildren().clear();
    }

    @FXML
    void onSearchBtnClick(ActionEvent event) {
        String name = nameField.getText().trim().toLowerCase();
        String brand = brandField.getText().trim().toLowerCase();
        String category = categoryField.getText().trim().toLowerCase();

        double minPrice = 0;
        double maxPrice = 99999;
        int minQty = 0;

        try { minPrice = Double.parseDouble(minPriceField.getText().trim()); } catch (NumberFormatException ignored) {}
        try { maxPrice = Double.parseDouble(maxPriceField.getText().trim()); } catch (NumberFormatException ignored) {}
        try { minQty   = Integer.parseInt(minQtyField.getText().trim()); }     catch (NumberFormatException ignored) {}
        List<Item> results = inventory.search(name,brand,category,minPrice,maxPrice,minQty);
        renderResults(results,name,brand,category);
    }
    private void renderResults(List<Item> results, String name, String brand, String category) {
        resultBox.getChildren().clear();

        for (Item item : results) {
            HBox row = new HBox();

            Label code = new Label(item.item[0]);
            code.setPrefWidth(80);
            Label nm = new Label(item.item[1]);
            nm.setPrefWidth(240);
            Label br = new Label(item.item[2]);
            br.setPrefWidth(100);
            Label price = new Label(item.item[3]);
            price.setPrefWidth(110);
            Label qty = new Label(item.item[4]);
            qty.setPrefWidth(60);
            Label cat = new Label(item.item[5]);
            cat.setPrefWidth(100);

            row.getChildren().addAll(code, nm, br, price, qty, cat);
            resultBox.getChildren().add(row);
        }
    }

}
