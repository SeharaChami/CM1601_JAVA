package tuktukjava.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tuktukjava.Inventory;
import tuktukjava.Item;
import tuktukjava.RandomDealers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class InventoryController {
    public VBox tableContent;
    public Label totalItemsLabel;
    public Label totalQtyLabel;
    public Label totalValueLabel;
    public HBox summaryBox;
    private  List<Item> items;
    private Inventory inventory;
    private RandomDealers dealers;
    private List<String> categoryNames;
    private List<List<Item>> groupedItems;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        categoryNames = inventory.getCategories();
        groupedItems = inventory.getItemsByCategory();
        generateTable();
        updateSum();
    }
    public void setDealers(RandomDealers dealers){
        this.dealers = dealers;
    }

    private void generateTable() {
        tableContent.getChildren().clear();
        int count = 0;

        for (int i = 0; i < groupedItems.size(); i++) {
            Label catLabel = new Label(categoryNames.get(i).toUpperCase());
            catLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #888; -fx-font-weight: bold; -fx-padding: 8 0 4 0;");
            tableContent.getChildren().add(catLabel);

            for (Item item : groupedItems.get(i)) {
                int qty = 0;
                try {
                    qty = Integer.parseInt(item.item[4].trim());
                } catch (NumberFormatException ignored) {}

                String qtyColor = "#111111";
                if (qty == 0) {
                    qtyColor = "#cc4400";
                }

                HBox row = new HBox();
                if (count % 2 == 0) {
                    row.setStyle("-fx-background-color: white; -fx-padding: 8 0 8 0;");
                } else {
                    row.setStyle("-fx-background-color: #fafafa; -fx-padding: 8 0 8 0;");
                }

                row.getChildren().addAll(
                        cell(item.item[0], 80,  "#111"),
                        cell(item.item[1], 240, "#111"),
                        cell(item.item[2], 100, "#111"),
                        cell(item.item[3], 110, "#111"),
                        cell(item.item[4], 60,  qtyColor),
                        cell(item.item[6], 120, "#111"),
                        imageCell(item.item[7])
                );

                tableContent.getChildren().add(row);
                count++;
            }
        }
    }
    private Label cell(String text, double width, String color) {
        Label label = new Label();
        if (text != null) {
            label.setText(text);
        } else {
            label.setText("N/A");
        }
        label.setPrefWidth(width);
        label.setTextFill(javafx.scene.paint.Color.web(color));
        return label;
    }
    private ImageView imageCell(String filename) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);

        try {
            File file = new File("src/main/resources/images/" + filename);
            if (file.exists()) {
                imageView.setImage(new Image(file.toURI().toString()));
            }
        } catch (Exception e) {
        }

        return imageView;
    }

    @FXML
    public void onBackBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/homePage-view.fxml")));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setInventory(this.inventory);
        controller.setDealers(this.dealers);
        stage.setScene(new Scene(root, 730, 500));
    }
    private void updateSum() {
        int totalItems = 0;
        int totalQty = 0;
        double totalValue = 0;

        for (List<Item> list : groupedItems) {
            for (Item item : list) {
                totalItems++;
                try {
                    int qty = Integer.parseInt(item.item[4].trim());
                    totalQty += qty;

                    String priceStr = item.item[3];
                    if (priceStr == null) continue;
                    String numPrice = "";
                    boolean found = false;
                    for (int i = 0; i < priceStr.length(); i++) {
                        char c = priceStr.charAt(i);
                        if (Character.isDigit(c)) {
                            numPrice = numPrice + c;
                            found = true;
                        } else if (c == '.' && found) {
                            numPrice = numPrice + c;
                        }
                    }

                    double price = Double.parseDouble(numPrice);
                    totalValue += price * qty;
                } catch (NumberFormatException ignored) {}
            }
        }

        totalItemsLabel.setText(String.valueOf(totalItems));
        totalQtyLabel.setText(totalQty + " units");
        totalValueLabel.setText(String.format("Rs %,.0f", totalValue));
    }


}
