package tuktukjava.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tuktukjava.Inventory;
import tuktukjava.Item;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AddItemController {
    public Button clearBtn;
    private Inventory inventory;
    Item item = new Item(new String[]{});
    @FXML
    public Button addItem;
    public TextField itemCodeField;
    public TextField brandField;
    public TextField priceField;
    public TextField fieldInput;
    public TextField quantityField;
    public TextField imgField;
    public TextField nameField;
    public Label errorLabel;
    public DatePicker datePicker;
    public Label nameLabel;
    public Label brandLabel;
    public Label priceLabel;
    public Label qtyLabel;
    public Label fieldLabel;
    public Label imgLabel;
    public Label dateLabel;
    String name;
    String brand;
    String price;
    String field;
    String quantity;
    String date;
    String img;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        String tempCode = inventory.generateItemCode();
        itemCodeField.setText(tempCode);
        item.setCode(tempCode);
    }

    public void onNameText(ActionEvent actionEvent) {
        if(nameField.getText().isEmpty()){
            nameLabel.setText("Name cannot be empty..");
        }
        else
        {nameLabel.setText(null);
        item.setName(nameField.getText());
        }
    }

    public void onBrandText(ActionEvent actionEvent) {
        if (brandField.getText().isEmpty()){
            brandLabel.setText("Brand cannot be empty..");
        }
        else {
            brandLabel.setText(null);
            item.setBrand(brandField.getText());
        }
    }

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

    public void onFieldText(ActionEvent actionEvent) {
        if (fieldInput.getText().isEmpty()) {
            fieldLabel.setText("Field cannot be empty..");
        } else {
            fieldLabel.setText(null);
            item.setField(fieldInput.getText());
        }
    }

    public void onDatePicker(ActionEvent actionEvent) {
        if (datePicker.getValue()==null) {
            dateLabel.setText("Provide a valid date..");
        } else {
            dateLabel.setText(null);
            item.setDate(datePicker.getValue().toString());
        }
    }

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

    public void onAddButtonClick(ActionEvent actionEvent) throws IOException {
        name = nameField.getText().trim();
        brand = brandField.getText().trim();
        price = priceField.getText().trim();
        field = fieldInput.getText().trim();
        quantity = quantityField.getText().trim();
        date = datePicker.getValue().toString();
        img = imgField.getText().trim();

        if(name.isEmpty()||brand.isEmpty()||price.isEmpty()||field.isEmpty()||quantity.isEmpty()||date.isEmpty()||img.isEmpty()){
            imgLabel.setText("Please fill all fields..");
        }
        else{
            inventory.add(item);
        }
    }

    public void onClearBtnClick(ActionEvent actionEvent) {
        itemCodeField.setText(null);
        nameField.setText(null);
        brandField.setText(null);
        priceField.setText(null);
        brandField.setText(null);
        quantityField.setText(null);
        imgField.setText(null);
        fieldInput.setText(null);
        datePicker.setValue(null);

    }
}
