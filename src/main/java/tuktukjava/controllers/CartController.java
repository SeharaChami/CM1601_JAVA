package tuktukjava.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tuktukjava.*;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CartController {

    @FXML private TextField codeField;
    @FXML private TextField qtyField;
    @FXML private VBox cartTableBox;
    @FXML private Label itemCountLabel;
    @FXML private Label subtotalLabel;
    @FXML private Label bulkDiscLabel;
    @FXML private Label bulkDiscAmount;
    @FXML private Label synergyDiscLabel;
    @FXML private Label synergyDiscAmount;
    @FXML private Label totalLabel;
    @FXML private Label discountReasonLabel;
    @FXML private Label errorLabel;
    @FXML private Label msgLabel;

    private Inventory inventory;
    private RandomDealers dealers;
    private Cart cart = new Cart();

    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public void setDealers(RandomDealers dealers) { this.dealers = dealers; }

    @FXML
    private void onAddBtnClick(ActionEvent event) {
            errorLabel.setText("");
            String code = codeField.getText().trim().toUpperCase();
            int qty;

            try { qty = Integer.parseInt(qtyField.getText().trim()); }
            catch (NumberFormatException e) { errorLabel.setText("Invalid quantity"); return; }

            if (qty <= 0) { errorLabel.setText("Quantity must be greater than 0"); return; }
            Item item = inventory.searchByCode(code);
            if (item == null) { errorLabel.setText("Item not found"); return; }

            cart.addItem(item, qty);

            String error = cart.validateSale(inventory);
            if (error != null) {
                errorLabel.setText(error);
                cart.removeItem(code);
                return;
            }
            codeField.clear();
            qtyField.setText("1");
            renderCart();
        }
    private void renderCart() {
        cartTableBox.getChildren().clear();

        for (CartItem cartItem : cart.getCartItems()) {
            HBox row = new HBox();

            Label code = new Label(cartItem.getItem().item[0]); code.setPrefWidth(80);
            Label name = new Label(cartItem.getItem().item[1]); name.setPrefWidth(200);
            Label price = new Label(cartItem.getItem().item[3]); price.setPrefWidth(110);
            Label qty = new Label(String.valueOf(cartItem.getQty())); qty.setPrefWidth(60);
            Label subtotal = new Label(String.format("Rs %.2f", cartItem.getSubtotal())); subtotal.setPrefWidth(120);

            ImageView img = imageCell(cartItem.getItem().item[7]);

            Button delBtn = new Button("✕");
            delBtn.setStyle("-fx-text-fill: red; -fx-background-color: transparent;");
            String itemCode = cartItem.getItem().item[0];
            delBtn.setOnAction(e -> { cart.removeItem(itemCode); renderCart(); });

            row.getChildren().addAll(code, name, price, qty, subtotal, img, delBtn);
            cartTableBox.getChildren().add(row);
        }

        updateSummary();
    }

    private void updateSummary() {
        itemCountLabel.setText(String.valueOf(cart.getItemCount()));
        subtotalLabel.setText(String.format("Rs %,.2f", cart.getSubtotal()));

        double bulk = cart.getBulkDiscount();
        double synergy = cart.getSynergyDiscount();

        bulkDiscLabel.setText(bulk > 0 ? "Bulk discount (5%)" : "");
        bulkDiscAmount.setText(bulk > 0 ? String.format("- Rs %,.2f", bulk) : "");
        synergyDiscLabel.setText(synergy > 0 ? "Synergy discount (10%)" : "");
        synergyDiscAmount.setText(synergy > 0 ? String.format("- Rs %,.2f", synergy) : "");

        totalLabel.setText(String.format("Rs %,.2f", cart.getTotal()));
        discountReasonLabel.setText(cart.getDiscountReason());
    }

    private ImageView imageCell(String filename) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        try {
            File file = new File("src/main/resources/images/" + filename);
            if (file.exists()) {
                imageView.setImage(new Image(file.toURI().toString()));
            }
        } catch (Exception ignored) {}
        return imageView;
    }

    @FXML
    private void onProcessSaleClick(ActionEvent event) throws IOException {
        String result = cart.validateSale(inventory);
        if (result != null ) {
            errorLabel.setText(result);
            return;
        }
        cart.reduceStock();
        FileManager.saveItems(inventory.getFormattedList());

        for (CartItem cartItem : cart.getCartItems()) {
            Inventory.saveToAuditLog(cartItem.getItem(), "SALE: " + cartItem.getQty() + " units sold");
        }
        cart.clear();
        renderCart();
    }
    @FXML
    private void onClearCartClick(ActionEvent event) {
        cart.clear();
        errorLabel.setText("");
        renderCart();
    }

    @FXML
    private void onBackBtnClick(ActionEvent event) throws IOException {
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
}