package tuktukjava.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tuktukjava.Inventory;
import tuktukjava.Item;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AddItemController {
    @FXML
    public Button clearBtn;
    @FXML
    public Button backBtn;
    private Inventory inventory;
    Item item = new Item(new String[8]);
    @FXML
    public Button addItem;
    @FXML private TextField itemCodeField;
    @FXML private TextField nameField;
    @FXML private TextField brandField;
    @FXML private TextField priceField;
    @FXML private TextField fieldInput;
    @FXML private TextField quantityField;
    @FXML private TextField imgField;
    @FXML private DatePicker datePicker;
    @FXML private Label nameLabel;
    @FXML private Label brandLabel;
    @FXML private Label priceLabel;
    @FXML private Label qtyLabel;
    @FXML private Label fieldLabel;
    @FXML private Label imgLabel;
    @FXML private Label dateLabel;
    @FXML public Label msgLabel;
    @FXML private Label errorLabel;
    String name;
    String brand;
    String price;
    String field;
    String quantity;
    String date;
    String img;
    @FXML
    public void initialize(){

    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        String tempCode = inventory.generateItemCode();
        itemCodeField.setText(tempCode);
        item.setCode(tempCode);
    }

    @FXML
    public void onNameText(ActionEvent actionEvent) {
        if(nameField.getText().isEmpty()){
            nameLabel.setText("Name cannot be empty..");
        }
        else
        {nameLabel.setText(null);
        item.setName(nameField.getText());
        }
    }
    @FXML
    public void onBrandText(ActionEvent actionEvent) {
        if (brandField.getText().isEmpty()){
            brandLabel.setText("Brand cannot be empty..");
        }
        else {
            brandLabel.setText(null);
            item.setBrand(brandField.getText());
        }
    }
    @FXML
    public void onPriceText(ActionEvent actionEvent) {
        price = item.getPrice(priceField.getText());
        if(price == null){
            priceLabel.setText("Provide only numeric value greater that 0..");
        }
        else {
            priceLabel.setText(null);
            item.setPrice(price);
        }
    }
    @FXML
    public void onQtyText(ActionEvent actionEvent) {
        quantity = item.getQty(quantityField.getText());
        if(quantity == null){
            qtyLabel.setText("Provide only numeric value greater that 0..");
        }
        else {
            qtyLabel.setText(null);
            item.setQty(quantity);
        }
    }
    @FXML
    public void onFieldText(ActionEvent actionEvent) {
        if (fieldInput.getText().isEmpty()) {
            fieldLabel.setText("Field cannot be empty..");
        } else {
            fieldLabel.setText(null);
            item.setField(fieldInput.getText());
        }
    }
    @FXML
    public void onDatePicker(ActionEvent actionEvent) {
        if (datePicker.getValue()==null) {
            dateLabel.setText("Provide a valid date..");
        } else {
            dateLabel.setText(null);
            item.setDate(datePicker.getValue().toString());
        }
    }
    @FXML
    public void onImgRefField(ActionEvent actionEvent) {
        img = item.getImg(imgField.getText());
        if (img == null){
            imgLabel.setText("Provide in only \"png\",\"jpg\",\"jpeg\"file format..");
        }
        else {
            imgLabel.setText(null);
            item.setImg(img);
        }
    }
    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) throws IOException {
        name = nameField.getText().trim();
        brand = brandField.getText().trim();
        price = priceField.getText().trim();
        field = fieldInput.getText().trim();
        quantity = quantityField.getText().trim();
        img = imgField.getText().trim();

        if (datePicker.getValue() == null) {
            msgLabel.setText("Please select a date.");
            return;
        }
        date = datePicker.getValue().toString();

        if (name.isEmpty() || brand.isEmpty() || price.isEmpty() ||
                field.isEmpty() || quantity.isEmpty() || img.isEmpty()) {
            msgLabel.setText("Please fill all fields.");
            return;
        }
        String formattedPrice = item.getPrice(price);
        String validQty = item.getQty(quantity);

        if (formattedPrice == null) {
            priceLabel.setText("Invalid price.");
            return;
        }
        if (validQty == null) {
            qtyLabel.setText("Invalid quantity.");
            return;
        }
        if (item.getImg(img) == null) {
            imgLabel.setText("Invalid image format.");
            return;
        }

        String[] part = new String[8];
        part[0] = item.getCode();
        part[1] = name;
        part[2] = brand;
        part[3] = formattedPrice;
        part[4] = quantity;
        part[5] = field;
        part[6] = date;
        part[7] = img;
        item.setItem(part);

        inventory.add(item);
        msgLabel.setText("Item " + item.getCode() + " added successfully.");
    } @FXML
    public void onClearBtnClick(ActionEvent actionEvent) {
        itemCodeField.setText(inventory.generateItemCode());
        nameField.setText(null);
        brandField.setText(null);
        priceField.setText(null);
        brandField.setText(null);
        quantityField.setText(null);
        imgField.setText(null);
        fieldInput.setText(null);
        datePicker.setValue(null);

    }
    @FXML
    public void onItemAdded(MouseEvent mouseEvent) {
    }

    public void onBackBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/homePage-view.fxml")));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
        stage.show();
    }
}
