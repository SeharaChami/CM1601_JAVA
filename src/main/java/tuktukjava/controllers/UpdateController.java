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
import tuktukjava.RandomDealers;

import java.io.IOException;
import java.util.Objects;

public class UpdateController {
    @FXML
    public TextField thresholdField;
    @FXML
    public TextField searchCodeField;
    @FXML
    public Label editingLabel;
    @FXML
    public HBox fieldsBox;
    @FXML
    public TextField nameField;
    @FXML
    public TextField brandField;
    @FXML
    public HBox fieldsBox2;
    @FXML
    public TextField priceField;
    @FXML
    public TextField quantityField;
    @FXML
    public TextField categoryField;
    @FXML
    public HBox fieldsBox3;
    @FXML
    public TextField dateField;
    @FXML
    public TextField imgField;
    @FXML
    public Label msgLabel;
    @FXML
    public Label errorLabel;
    @FXML
    public Button saveBtn;
    private Item current;

    private Inventory inventory;

    private RandomDealers dealers;

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    public void setDealers(RandomDealers dealers){
        this.dealers = dealers;
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
    @FXML
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
        thresholdField.setText(current.item[8]);

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
        String t = thresholdField.getText();
        if(t.trim().isEmpty()){
            current.item[8] = "10";
        }else current.item[8] = thresholdField.getText().trim();

        inventory.update(current);
        msgLabel.setText("Item " + current.item[0] + " updated.");
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
