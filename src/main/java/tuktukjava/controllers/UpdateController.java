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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tuktukjava.Inventory;
import tuktukjava.Item;

import java.io.IOException;
import java.util.Objects;

public class UpdateController {
    private Item current;
    private Inventory inventory;
    public TextField searchCodeField;
    public Label editingLabel;
    public HBox fieldsBox;
    public TextField nameField;
    public TextField brandField;
    public HBox fieldsBox2;
    public TextField priceField;
    public TextField quantityField;
    public TextField categoryField;
    public HBox fieldsBox3;
    public TextField dateField;
    public TextField imgField;
    public Label msgLabel;
    public Label errorLabel;
    public Button saveBtn;
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

    @FXML
    private void onBackBtnClick(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/homePage-view.fxml")));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
        stage.show();
    }

    public void onSearchBtnClick(ActionEvent actionEvent) {
        String code = searchCodeField.getText().trim().toUpperCase();
        current = inventory.searchByCode(code);

        if (current == null){
            errorLabel.setText(("Item "+code+" not found"));
            hideFields();
            return;
        }
        errorLabel.setText("");
        editingLabel.setText("Editing: " + current.item[0] + " — " + current.item[1]);
        nameField.setText(current.item[1]);
        brandField.setText(current.item[2]);
        priceField.setText(current.item[3]);
        quantityField.setText(current.item[4]);
        categoryField.setText(current.item[5]);
        dateField.setText(current.item[6]);
        imgField.setText(current.item[7]);

        showFields();
    }

    @FXML
    private void onSaveBtnClick(ActionEvent event) throws IOException {
        if (current == null) return;

        current.item[2] = brandField.getText().trim();
        current.item[3] = priceField.getText().trim();
        current.item[4] = quantityField.getText().trim();
        current.item[5] = categoryField.getText().trim();
        current.item[6] = dateField.getText().trim();
        current.item[7] = imgField.getText().trim();

        inventory.update(current);
        msgLabel.setText("Item " + current.getCode() + " updated.");
    }
    private void hideFields() {
        editingLabel.setVisible(false); editingLabel.setManaged(false);
        fieldsBox.setVisible(false);    fieldsBox.setManaged(false);
        fieldsBox2.setVisible(false);   fieldsBox2.setManaged(false);
        fieldsBox3.setVisible(false);   fieldsBox3.setManaged(false);
        saveBtn.setVisible(false);      saveBtn.setManaged(false);
    }
    private void showFields() {
        editingLabel.setVisible(true); editingLabel.setManaged(true);
        fieldsBox.setVisible(true);    fieldsBox.setManaged(true);
        fieldsBox2.setVisible(true);   fieldsBox2.setManaged(true);
        fieldsBox3.setVisible(true);   fieldsBox3.setManaged(true);
        saveBtn.setVisible(true);      saveBtn.setManaged(true);
    }
}
