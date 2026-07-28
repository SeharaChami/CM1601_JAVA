package tuktukjava.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tuktukjava.Inventory;
import tuktukjava.Item;
import tuktukjava.RandomDealers;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeController {
    @FXML
    public Button cartDisBtn;
    @FXML
    public Button SearchBtn;
    private Inventory inventory;
    private RandomDealers dealers;

    @FXML public Label welcomeText;
    @FXML public Button AddBtn;
    @FXML public Button DeleteBtn;
    @FXML public Button UpdateBtn;
    @FXML public Button ViewInventoryBtn;
    @FXML public Button DealerViewerBtn;

    @FXML private VBox lowStockTable;
    @FXML private Label lowStockEmptyLabel;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        renderLowStock();
    }

    public void setDealers(RandomDealers randomDealers) {
        this.dealers = randomDealers;
    }

    private void renderLowStock() {
        lowStockTable.getChildren().clear();
        List<Item> lowItems = inventory.getLowStockItems();

        if (lowItems.isEmpty()) {
            lowStockEmptyLabel.setVisible(true);
            lowStockEmptyLabel.setManaged(true);
            return;
        }

        lowStockEmptyLabel.setVisible(false);
        lowStockEmptyLabel.setManaged(false);

        int count = 0;
        for (Item item : lowItems) {
            HBox row = new HBox();
            String bg = "white";
            if (count%2 == 0){
                bg = "#fafafa";
            }
            row.setStyle("-fx-background-color: " + bg + "; -fx-padding: 6 0 6 0;");

            row.getChildren().addAll(
                    cell(item.item[0], 80,  "#111"),
                    cell(item.item[1], 240, "#111"),
                    cell(item.item[2], 100, "#111"),
                    cell(item.item[3], 110, "#111"),
                    cell(item.item[4], 60,  "#cc4400"),
                    cell(item.item[6], 120, "#111")
            );

            lowStockTable.getChildren().add(row);
            count++;
        }
    }

    private Label cell(String text, double width, String color) {
        Label label = new Label(text != null ? text : "N/A");
        label.setPrefWidth(width);
        label.setTextFill(javafx.scene.paint.Color.web(color));
        return label;
    }

    @FXML
    public void onAddBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/addItem-view.fxml")));
        Parent root = loader.load();
        AddItemController controller = loader.getController();
        controller.setInventory(this.inventory);
        controller.setDealers(this.dealers);
        stage.setScene(new Scene(root));
    }

    @FXML
    public void onDeleteBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/deleteItem-view.fxml")));
        Parent root = loader.load();
        DeleteController controller = loader.getController();
        controller.setInventory(this.inventory);
        controller.setDealers(dealers);
        stage.setScene(new Scene(root, 730, 500));
    }

    @FXML
    public void onUpdateBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/update-view.fxml")));
        Parent root = loader.load();
        UpdateController controller = loader.getController();
        controller.setInventory(this.inventory);
        controller.setDealers(this.dealers);
        stage.setScene(new Scene(root, 730, 500));
    }

    @FXML
    public void onviewBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/inventory-view.fxml")));
        Parent root = loader.load();
        InventoryController controller = loader.getController();
        controller.setInventory(this.inventory);
        controller.setDealers(this.dealers);
        stage.setScene(new Scene(root, 730, 500));
    }

    @FXML
    public void onDealerBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/dealer-view.fxml")));
        Parent root = loader.load();
        DealerController controller = loader.getController();
        controller.setRandomDealers(this.dealers);
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
    }
    @FXML
    public void onCartBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/POS-view.fxml")));
        Parent root = loader.load();
        CartController controller = loader.getController();
        controller.setDealers(this.dealers);
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
    }
    @FXML
    public void onSearchBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/searchItem-view.fxml")));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        controller.setDealers(this.dealers);
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
    }
}